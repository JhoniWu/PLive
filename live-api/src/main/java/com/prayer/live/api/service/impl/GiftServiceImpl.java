package com.prayer.live.api.service.impl;

import com.alibaba.fastjson2.JSON;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.prayer.live.api.error.ApiErrorEnum;
import com.prayer.live.api.service.IGiftService;
import com.prayer.live.api.vo.req.GiftReqVO;
import com.prayer.live.api.vo.resp.GiftConfigVO;
import com.prayer.live.common.interfaces.dto.SendGiftMq;
import com.prayer.live.common.interfaces.topic.GiftProviderTopicNames;
import com.prayer.live.common.interfaces.utils.ConvertBeanUtils;
import com.prayer.live.framework.web.starter.context.LiveRequestContext;
import com.prayer.live.framework.web.starter.error.ErrorAssert;
import com.prayer.live.gift.interfaces.dto.GiftConfigDTO;
import com.prayer.live.gift.interfaces.interfaces.IGiftConfigRpc;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-21 00:41
 **/
@Service
public class GiftServiceImpl implements IGiftService {
	@DubboReference
	private IGiftConfigRpc giftConfigRpc;
	@Resource
	private MQProducer mqProducer;

	private final Cache<Integer,GiftConfigDTO> giftConfigDTOCache = Caffeine.newBuilder().maximumSize(1000).expireAfterWrite(90,TimeUnit.SECONDS).build();

	@Override
	public List<GiftConfigVO> listGift() {
		List<GiftConfigDTO> giftConfigDTOS = giftConfigRpc.queryGiftList();
		return ConvertBeanUtils.convertList(giftConfigDTOS, GiftConfigVO.class);
	}

	@Override
	public boolean send(GiftReqVO giftReqVO) {
		int giftId = giftReqVO.getGiftId();
		//map集合，判断本地是否有对象，如果有就返回，如果没有就rpc调用，同时注入到本地map中
		GiftConfigDTO giftConfigDTO = giftConfigDTOCache.get(giftId, id -> giftConfigRpc.getByGiftId(id));
		ErrorAssert.isNotNull(giftConfigDTO, ApiErrorEnum.GIFT_CONFIG_ERROR);
		ErrorAssert.IsTrue(!giftReqVO.getReceiverId().equals(LiveRequestContext.getUserId()), ApiErrorEnum.NOT_SEND_TO_YOURSELF);
		SendGiftMq sendGiftMq = new SendGiftMq();
		sendGiftMq.setGiftId(giftId);
		sendGiftMq.setUserId(LiveRequestContext.getUserId());
		sendGiftMq.setRoomId(giftReqVO.getRoomId());
		sendGiftMq.setReceiverId(giftReqVO.getReceiverId());
		sendGiftMq.setUrl(giftConfigDTO.getSvgaUrl());
		sendGiftMq.setPrice(giftConfigDTO.getPrice());
		//
		sendGiftMq.setUuid(UUID.randomUUID().toString());
		Message message = new Message();
		message.setTopic(GiftProviderTopicNames.SEND_GIFT);
		message.setBody(JSON.toJSONBytes(sendGiftMq));
		try {
			mqProducer.send(message);
		} catch (MQClientException e) {
			throw new RuntimeException(e);
		} catch (RemotingException e) {
			throw new RuntimeException(e);
		} catch (MQBrokerException e) {
			throw new RuntimeException(e);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		return true;
	}
}

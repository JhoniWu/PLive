package com.prayer.live.gift.provider.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.prayer.live.bank.interfaces.dto.AccountTradeReqDTO;
import com.prayer.live.common.interfaces.dto.SendGiftMq;
import com.prayer.live.common.interfaces.topic.GiftProviderTopicNames;
import com.prayer.live.framework.mq.starter.properties.RocketMQConsumerProperties;
import com.prayer.live.framework.redis.starter.key.GiftProviderCacheKeyBuilder;
import com.prayer.live.gift.interfaces.constants.SendGiftTypeEnum;
import com.prayer.live.im.constants.AppIdEnum;
import com.prayer.live.living.interfaces.dto.LivingRoomReqDTO;
import com.prayer.live.living.interfaces.rpc.ILivingRoomRpc;
import com.prayer.live.msg.enums.ImMsgBizCodeEnum;
import jakarta.annotation.Resource;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-02-20 12:39
 **/
@Component
public class SendGiftConsumer implements InitializingBean {
	@Resource
	private RocketMQConsumerProperties mqConsumerProperties;
	@Resource
	private GiftProviderCacheKeyBuilder cacheKeyBuilder;
	@Resource
	private RedisTemplate<String , Object> redisTemplate;
	@Resource
	private ILivingRoomRpc livingRoomRpc;
	@Override
	public void afterPropertiesSet() throws Exception {
		DefaultMQPushConsumer mqPushConsumer = new DefaultMQPushConsumer();
		mqPushConsumer.setConsumerGroup(mqConsumerProperties.getGroupName() + "_" + SendGiftConsumer.class.getSimpleName());
		mqPushConsumer.setNamesrvAddr(mqConsumerProperties.getNameSrv());
		mqPushConsumer.setVipChannelEnabled(false);
		mqPushConsumer.setConsumeMessageBatchMaxSize(10);
		mqPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
		mqPushConsumer.subscribe(GiftProviderTopicNames.SEND_GIFT, "");
		mqPushConsumer.setMessageListener((MessageListenerConcurrently) (msgs, ctx) -> {
			for(MessageExt msg : msgs){
				SendGiftMq sendGiftMq = JSON.parseObject(new String(msg.getBody()), SendGiftMq.class);
				String mqConsumerKey = cacheKeyBuilder.buildGiftConsumeKey(sendGiftMq.getUuid());
				boolean lockStatus = redisTemplate.opsForValue().setIfAbsent(mqConsumerKey, -1, 5, TimeUnit.MINUTES);
				if(!lockStatus){
					continue;
				}
				//扣减余额
				AccountTradeReqDTO tradeReqDTO = new AccountTradeReqDTO();
				JSONObject jsonObject = new JSONObject();
				if(true){
					Long receiverId = sendGiftMq.getReceiverId();
					if(SendGiftTypeEnum.DEFAULT_SEND_GIFT.getCode().equals(true)){
						jsonObject.put("url", sendGiftMq.getUrl());
						LivingRoomReqDTO reqDTO = new LivingRoomReqDTO();
						reqDTO.setAppId(AppIdEnum.LIVE_BIZ.getCode());
						reqDTO.setRoomId(sendGiftMq.getRoomId());
						List<Long> userIdList = livingRoomRpc.queryUserIdByRoomId(reqDTO);
						if(!CollectionUtils.isEmpty(userIdList)){
							this.batchSendImMsg(userIdList, ImMsgBizCodeEnum.LIVING_ROOM_SEND_GIFT_SUCCESS);
						}
					}
				} else {
					jsonObject.put("msg", "failed");
					this.sendImMsgSingleton(sendGiftMq.getUserId(), ImMsgBizCodeEnum.LIVING_ROOM_SEND_GIFT_FAIL);
				}
			}
			return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
		});
	}

	private void sendImMsgSingleton(Long userId, ImMsgBizCodeEnum imMsgBizCodeEnum) {

	}

	private void batchSendImMsg(List<Long> userIdList, ImMsgBizCodeEnum imMsgBizCodeEnum) {

	}

}

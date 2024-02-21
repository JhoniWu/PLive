package com.prayer.live.gift.provider.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.prayer.live.common.interfaces.enums.CommonStatusEnum;
import com.prayer.live.common.interfaces.topic.GiftProviderTopicNames;
import com.prayer.live.common.interfaces.utils.ConvertBeanUtils;
import com.prayer.live.framework.redis.starter.key.GiftProviderCacheKeyBuilder;
import com.prayer.live.gift.interfaces.dto.GiftConfigDTO;
import com.prayer.live.gift.provider.dao.mapper.GiftConfigMapper;
import com.prayer.live.gift.provider.dao.po.GiftConfigPO;
import com.prayer.live.gift.provider.service.IGiftConfigService;
import com.prayer.live.gift.provider.service.bo.GiftCacheRemoveBO;
import jakarta.annotation.Resource;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-19 23:28
 **/
@Service
public class GiftConfigServiceImpl implements IGiftConfigService {

	private static final Logger LOGGER = LoggerFactory.getLogger(GiftConfigServiceImpl.class);

	@Resource
	private GiftConfigMapper giftConfigMapper;
	@Resource
	private MQProducer mqProducer;
	@Resource
	private GiftProviderCacheKeyBuilder cacheKeyBuilder;
	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	@Override
	public GiftConfigDTO getByGiftId(Integer giftId) {
		String cacheKey = cacheKeyBuilder.buildGiftConfigCacheKey(giftId);
		GiftConfigDTO result =(GiftConfigDTO) redisTemplate.opsForValue().get(cacheKey);
		if(result != null){
			if(result.getGiftId()!=null){
				redisTemplate.expire(cacheKey, 60, TimeUnit.MINUTES);
				return result;
			}
			return null;
		}

		LambdaQueryWrapper<GiftConfigPO> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(GiftConfigPO::getGiftId, giftId);
		queryWrapper.eq(GiftConfigPO::getStatus, CommonStatusEnum.VALID_STATUS.getCode());
		queryWrapper.last("limit 1");
		GiftConfigPO po = giftConfigMapper.selectOne(queryWrapper);
		if(po != null){
			GiftConfigDTO dto = ConvertBeanUtils.convert(po, GiftConfigDTO.class);
			redisTemplate.opsForValue().set(cacheKey, dto, 60, TimeUnit.MINUTES);
			return dto;
		}

		redisTemplate.opsForValue().set(cacheKey, new GiftConfigDTO(), 5, TimeUnit.MINUTES);
		return null;
	}

	@Override
	public List<GiftConfigDTO> queryGiftList() {
		String cachekey = cacheKeyBuilder.buildGiftListCacheKey();
		List<GiftConfigDTO> cacheList = redisTemplate.opsForList().range(cachekey, 0, 100).stream().map(x -> (GiftConfigDTO) x).collect(Collectors.toList());
		if(!CollectionUtils.isEmpty(cacheList)){
			if(cacheList.get(0) != null){
				redisTemplate.expire(cachekey, 60, TimeUnit.MINUTES);
				return cacheList;
			}
			return null;
		}
		LambdaQueryWrapper<GiftConfigPO> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(GiftConfigPO::getStatus, CommonStatusEnum.VALID_STATUS.getCode());
		List<GiftConfigPO> giftConfigPOList = giftConfigMapper.selectList(queryWrapper);
		if (!CollectionUtils.isEmpty(giftConfigPOList)) {
			List<GiftConfigDTO> resultList = ConvertBeanUtils.convertList(giftConfigPOList, GiftConfigDTO.class);
			redisTemplate.opsForList().leftPushAll(cachekey, resultList.toArray());
			//大部分情况下，一个直播间的有效时间大概就是60min以上
			redisTemplate.expire(cachekey, 60, TimeUnit.MINUTES);
			return resultList;
		}
		//存入一个空的list进入redis中
		redisTemplate.opsForList().leftPush(cachekey, new GiftConfigDTO());
		redisTemplate.expire(cachekey, 5, TimeUnit.MINUTES);
		return Collections.emptyList();
	}

	@Override
	public void insertOne(GiftConfigDTO giftConfigDTO) {
		GiftConfigPO giftConfigPO = ConvertBeanUtils.convert(giftConfigDTO, GiftConfigPO.class);
		giftConfigPO.setStatus(CommonStatusEnum.VALID_STATUS.getCode());
		giftConfigMapper.insert(giftConfigPO);
		redisTemplate.delete(cacheKeyBuilder.buildGiftListCacheKey());
		GiftCacheRemoveBO giftCacheRemoveBO = new GiftCacheRemoveBO();
		giftCacheRemoveBO.setRemoveListCache(true);
		Message message = new Message();
		message.setTopic(GiftProviderTopicNames.REMOVE_GIFT_CACHE);
		message.setBody(JSON.toJSONBytes(giftCacheRemoveBO));
		//1秒之后延迟消费
		message.setDelayTimeLevel(1);
		try {
			SendResult sendResult = mqProducer.send(message);
			LOGGER.info("[insertOne] sendResult is {}", sendResult);
		} catch (Exception e) {
			LOGGER.info("[insertOne] mq send error: }", e);
		}
	}

	@Override
	public void updateOne(GiftConfigDTO giftConfigDTO) {
		GiftConfigPO giftConfigPO = ConvertBeanUtils.convert(giftConfigDTO, GiftConfigPO.class);
		giftConfigMapper.updateById(giftConfigPO);
		redisTemplate.delete(cacheKeyBuilder.buildGiftListCacheKey());
		redisTemplate.delete(cacheKeyBuilder.buildGiftConfigCacheKey(giftConfigDTO.getGiftId()));
		GiftCacheRemoveBO giftCacheRemoveBO = new GiftCacheRemoveBO();
		giftCacheRemoveBO.setRemoveListCache(true);
		giftCacheRemoveBO.setGiftId(giftConfigDTO.getGiftId());
		Message message = new Message();
		message.setTopic(GiftProviderTopicNames.REMOVE_GIFT_CACHE);
		message.setBody(JSON.toJSONBytes(giftCacheRemoveBO));
		//1秒之后延迟消费
		message.setDelayTimeLevel(1);
		try {
			SendResult sendResult = mqProducer.send(message);
			LOGGER.info("[updateOne] sendResult is {}", sendResult);
		} catch (Exception e) {
			LOGGER.info("[updateOne] mq send error: }", e);
		}
	}
}

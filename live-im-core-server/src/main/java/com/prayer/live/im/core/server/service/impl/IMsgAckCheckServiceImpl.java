package com.prayer.live.im.core.server.service.impl;

import com.prayer.live.common.interfaces.topic.ImCoreServerProviderTopicNames;
import com.prayer.live.framework.redis.starter.key.ImCoreServerProviderCacheKeyBuilder;
import com.prayer.live.im.core.server.service.IMsgAckCheckService;
import com.prayer.live.im.dto.ImMsgBody;
import jakarta.annotation.Resource;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-15 18:56
 **/
@Service
public class IMsgAckCheckServiceImpl implements IMsgAckCheckService {
	private static final Logger LOGGER = LoggerFactory.getLogger(IMsgAckCheckServiceImpl.class);

	@Resource
	private MQProducer mqProducer;

	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	@Resource
	private ImCoreServerProviderCacheKeyBuilder cacheKeyBuilder;

	@Override
	public void doMsgAck(ImMsgBody imMsgBody) {
		//key is live-xx-xx:imAckMap:appId:8 value is msgId
		String key = cacheKeyBuilder.buildImAckMapKey(imMsgBody.getUserId(), imMsgBody.getAppId());
		redisTemplate.opsForHash().delete(key);
		redisTemplate.expire(key, 30, TimeUnit.MINUTES);
	}

	@Override
	public void recordMsgAck(ImMsgBody imMsgBody, int times) {
		//key is live-xx-xx:imAckMap:appId:8
		String key = cacheKeyBuilder.buildImAckMapKey(imMsgBody.getUserId(), imMsgBody.getAppId());
		redisTemplate.opsForHash().put(key, imMsgBody.getMsgId(), times);
		redisTemplate.expire(key, 30, TimeUnit.MINUTES);
	}

	/**
	 * 原路返回再去ImAckConsumer消费一次
	 * @param imMsgBody
	 */
	@Override
	public void sendDelayMsg(ImMsgBody imMsgBody) {
		String waitSend = imMsgBody.toString();
		Message message = new Message();

		message.setBody(waitSend.getBytes());
		message.setTopic(ImCoreServerProviderTopicNames.LIVE_IM_ACK_MSG_TOPIC);
		message.setDelayTimeLevel(2);

		try {
			SendResult sendResult = mqProducer.send(message);
			LOGGER.info("[MsgAckCheckServiceImpl] msg is {},sendResult is {}", waitSend, sendResult);
		} catch (Exception e) {
			LOGGER.info("[MsgAckCheckServiceImpl] error is " ,e);
		}
	}

	@Override
	public int getMsgAckTimes(String msgId, long userId, int appId) {
		Object cnt = redisTemplate.opsForHash().get(cacheKeyBuilder.buildImAckMapKey(userId, appId), msgId);
		if(cnt == null){
			return -1;
		}
		return (Integer)cnt;
	}
}

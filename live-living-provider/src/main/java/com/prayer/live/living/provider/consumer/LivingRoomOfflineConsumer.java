package com.prayer.live.living.provider.consumer;

import com.alibaba.fastjson.JSON;
import com.prayer.live.common.interfaces.topic.ImCoreServerProviderTopicNames;
import com.prayer.live.framework.mq.starter.properties.RocketMQConsumerProperties;
import com.prayer.live.im.core.server.interfaces.dto.ImOffLineDTO;
import com.prayer.live.living.provider.service.LivingRoomService;
import jakarta.annotation.Resource;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-19 20:03
 **/
@Component
public class LivingRoomOfflineConsumer implements InitializingBean {
	private static final Logger LOGGER = LoggerFactory.getLogger(LivingRoomOfflineConsumer.class);
	@Resource
	private RocketMQConsumerProperties rocketMQConsumerProperties;
	@Resource
	private LivingRoomService livingRoomService;


	@Override
	public void afterPropertiesSet() throws Exception {
		DefaultMQPushConsumer mqPushConsumer = new DefaultMQPushConsumer();
		mqPushConsumer.setVipChannelEnabled(false);
		mqPushConsumer.setNamesrvAddr(rocketMQConsumerProperties.getNameSrv());
		mqPushConsumer.setConsumerGroup(rocketMQConsumerProperties.getGroupName() + "_" + LivingRoomOfflineConsumer.class.getSimpleName());
		//一次从broker中拉取10条消息到本地内存当中进行消费
		mqPushConsumer.setConsumeMessageBatchMaxSize(10);
		mqPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
		//监听im发送过来的业务消息topic
		mqPushConsumer.subscribe(ImCoreServerProviderTopicNames.LIVE_OFFLINE_TOPIC, "");
		mqPushConsumer.setMessageListener((MessageListenerConcurrently) (msgs, context) -> {
			for (MessageExt msg : msgs) {
				livingRoomService.userOffLineHandler(JSON.parseObject(new String(msg.getBody()), ImOffLineDTO.class));
			}
			return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
		});
		mqPushConsumer.start();
		LOGGER.info("mq消费者启动成功,namesrv is {}", rocketMQConsumerProperties.getNameSrv());
	}
}

package com.prayer.live.gift.provider.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.prayer.live.common.interfaces.topic.GiftProviderTopicNames;
import com.prayer.live.framework.mq.starter.properties.RocketMQConsumerProperties;
import jakarta.annotation.Resource;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-02-19 17:13
 **/
@Component
public class StartLivingRoomConsumer implements InitializingBean{
	@Resource
	private RocketMQConsumerProperties mqConsumerProperties;

	@Override
	public void afterPropertiesSet() throws Exception {
		DefaultMQPushConsumer mqPushConsumer = new DefaultMQPushConsumer();
		mqPushConsumer.setConsumerGroup(mqConsumerProperties.getGroupName()+"_"+StartLivingRoomConsumer.class.getSimpleName());
		mqPushConsumer.setNamesrvAddr(mqConsumerProperties.getNameSrv());
		mqPushConsumer.setVipChannelEnabled(false);
		mqPushConsumer.setConsumeMessageBatchMaxSize(1);
		mqPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
		mqPushConsumer.subscribe(GiftProviderTopicNames.START_LIVING_ROOM, "");
		mqPushConsumer.setMessageListener((MessageListenerConcurrently) (msgs, context) -> {
			for(MessageExt msg : msgs){
				JSONObject jsonObject = JSON.parseObject(new String(msg.getBody()));
				Long anchorId = jsonObject.getLong("anchorId");
				//TODO
			}
			return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
		});

	}
}

package com.prayer.live.msg.provider.consumer;

import com.alibaba.fastjson.JSON;
import com.prayer.live.common.interfaces.topic.ImCoreServerProviderTopicNames;
import com.prayer.live.framework.mq.starter.properties.RocketMQConsumerProperties;
import com.prayer.live.im.dto.ImMsgBody;
import com.prayer.live.msg.provider.consumer.handler.MessageHandler;
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
 * @create: 2024-02-15 13:47
 **/
@Component
public class ImScMsgConsumer implements InitializingBean{

	private static final Logger logger = LoggerFactory.getLogger(ImScMsgConsumer.class);
	@Resource
	private MessageHandler robinMessageHandler;
	@Resource
	private RocketMQConsumerProperties rocketMQConsumerProperties;

	@Override
	public void afterPropertiesSet() throws Exception {
		DefaultMQPushConsumer mqPushConsumer = new DefaultMQPushConsumer();
		mqPushConsumer.setNamesrvAddr(rocketMQConsumerProperties.getNameSrv());
		mqPushConsumer.setConsumerGroup(rocketMQConsumerProperties.getGroupName()+"_" +ImScMsgConsumer.class.getSimpleName());
		mqPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
		mqPushConsumer.subscribe(ImCoreServerProviderTopicNames.LIVE_IM_BIZ_MSG_TOPIC, "");
		mqPushConsumer.setMessageListener((MessageListenerConcurrently)(msgs, ctx) -> {
			logger.info("一共有{}条消息等待处理",msgs.size());
			for(MessageExt msg : msgs){
				ImMsgBody msgBody = JSON.parseObject(new String(msg.getBody()), ImMsgBody.class);
				logger.info("im-msg-provider 收到消息 {}",msgBody);
				robinMessageHandler.onMsgReceive(msgBody);
			}
			return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
		});
		mqPushConsumer.start();
		logger.info("mq 消费者启动成功，namesrv is {}", rocketMQConsumerProperties.getNameSrv());


	}
}

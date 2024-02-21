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
 * @create: 2024-01-16 18:45
 **/
@Component
public class ImMsgConsumer implements InitializingBean {
	private static final Logger LOGGER = LoggerFactory.getLogger(ImMsgConsumer.class);
	@Resource
	private RocketMQConsumerProperties rocketMQConsumerProperties;
	@Resource
	private MessageHandler singleMessageHandler;

	// 记录每个用户连接的im服务器地址，然后根据im服务器的连接地址去做具体机器的调用
	// 基于mq广播思路去做，可能会有消息风暴发生，100台im机器，99%的mq消息都是无效的，
	// 加入一个叫路由层的设计，router中转的设计，router就是一个dubbo的rpc层
	// A-->B im-core-server -> msg-provider(持久化) -> im-core-server -> 通知到b
	@Override
	public void afterPropertiesSet() throws Exception {

		DefaultMQPushConsumer mqPushConsumer = new DefaultMQPushConsumer();
		mqPushConsumer.setNamesrvAddr(rocketMQConsumerProperties.getNameSrv());
		mqPushConsumer.setConsumerGroup(rocketMQConsumerProperties.getGroupName()+"_"+ImMsgConsumer.class.getSimpleName());
		mqPushConsumer.setConsumeMessageBatchMaxSize(10);
		mqPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
		mqPushConsumer.subscribe(ImCoreServerProviderTopicNames.LIVE_IM_BIZ_MSG_TOPIC, "");
		mqPushConsumer.setMessageListener((MessageListenerConcurrently) (msgs, ctx) -> {
			LOGGER.info("一共有{}条消息等待处理",msgs.size());
			for(MessageExt msg : msgs){
				ImMsgBody msgBody = JSON.parseObject(new String(msg.getBody()), ImMsgBody.class);
				LOGGER.info("im-msg-provider 收到消息 {}",msgBody);
				singleMessageHandler.onMsgReceive(msgBody);
			}
			return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
		});
		mqPushConsumer.start();
		LOGGER.info("mq消费者启动成功,namesrv is {}", rocketMQConsumerProperties.getNameSrv());
	}
}
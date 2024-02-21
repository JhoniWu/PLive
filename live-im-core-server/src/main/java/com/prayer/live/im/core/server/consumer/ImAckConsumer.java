package com.prayer.live.im.core.server.consumer;

import com.alibaba.fastjson.JSON;
import com.prayer.live.common.interfaces.topic.ImCoreServerProviderTopicNames;
import com.prayer.live.framework.mq.starter.properties.RocketMQConsumerProperties;
import com.prayer.live.im.core.server.service.IMsgAckCheckService;
import com.prayer.live.im.core.server.service.RouterHandlerService;
import com.prayer.live.im.dto.ImMsgBody;
import jakarta.annotation.Resource;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-16 17:15
 **/
@Component
public class ImAckConsumer implements InitializingBean {
	private static final Logger LOGGER = LoggerFactory.getLogger(ImAckConsumer.class);

	@Resource
	private RocketMQConsumerProperties rocketMQConsumerProperties;
	@Resource
	private IMsgAckCheckService msgAckCheckService;
	@Resource
	private RouterHandlerService routerHandlerService;

	@Override
	public void afterPropertiesSet() throws Exception {
		DefaultMQPushConsumer mqPushConsumer = new DefaultMQPushConsumer();
		mqPushConsumer.setVipChannelEnabled(false);
		//设置nameSrv地址,声明消费组
		mqPushConsumer.setNamesrvAddr(rocketMQConsumerProperties.getNameSrv());
		mqPushConsumer.setConsumerGroup(rocketMQConsumerProperties.getGroupName() + "_" + ImAckConsumer.class.getSimpleName());
		mqPushConsumer.setConsumeMessageBatchMaxSize(1);
		mqPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
		//设置订阅名称
		mqPushConsumer.subscribe(ImCoreServerProviderTopicNames.LIVE_IM_ACK_MSG_TOPIC, "");
		mqPushConsumer.setMessageListener((MessageListenerConcurrently)(msgs, context) -> {
			String jsonBody = new String(msgs.get(0).getBody());
			ImMsgBody msgBody = JSON.parseObject(jsonBody, ImMsgBody.class);
			int msgAckTimes = msgAckCheckService.getMsgAckTimes(msgBody.getMsgId(), msgBody.getUserId(), msgBody.getAppId());
			LOGGER.info("retry times is {}, msgId is {}", msgAckTimes, msgBody.getMsgId());
			/*
			  消息如果ack次数-1，代表发送成功，如果不是
			  继续判断是否小于2次，如果小于，继续尝试发送并记录
			  如果大于，则直接进行消息确认
			 */
			if(msgAckTimes < 0){
				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
			}
			if(msgAckTimes < 2){
				msgAckCheckService.recordMsgAck(msgBody, msgAckTimes+1);
				msgAckCheckService.sendDelayMsg(msgBody);
				routerHandlerService.sendMsgToClient(msgBody);
			} else {
				msgAckCheckService.doMsgAck(msgBody);
			}
			return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
		});
		mqPushConsumer.start();
		LOGGER.info("mq消费者启动成功,namesrv is {}", rocketMQConsumerProperties.getNameSrv());
	}
}

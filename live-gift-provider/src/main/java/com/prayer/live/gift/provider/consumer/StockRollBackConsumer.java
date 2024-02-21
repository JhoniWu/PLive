package com.prayer.live.gift.provider.consumer;

import com.alibaba.fastjson.JSON;
import com.prayer.live.common.interfaces.topic.GiftProviderTopicNames;
import com.prayer.live.framework.mq.starter.properties.RocketMQConsumerProperties;
import com.prayer.live.gift.interfaces.dto.RollBackStockDTO;
import com.prayer.live.gift.provider.service.ISkuStockInfoService;
import jakarta.annotation.Resource;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-02-19 17:08
 **/
public class StockRollBackConsumer implements InitializingBean {
	private static final Logger LOGGER = LoggerFactory.getLogger(StockRollBackConsumer.class);
	@Resource
	private RocketMQConsumerProperties rocketMQConsumerProperties;
	@Resource
	private ISkuStockInfoService skuStockInfoService;
	@Override
	public void afterPropertiesSet() throws Exception {
		DefaultMQPushConsumer mqPushConsumer = new DefaultMQPushConsumer();
		mqPushConsumer.setConsumerGroup(rocketMQConsumerProperties.getGroupName() + "_" + StockRollBackConsumer.class.getSimpleName());
		mqPushConsumer.setNamesrvAddr(rocketMQConsumerProperties.getNameSrv());
		mqPushConsumer.setVipChannelEnabled(false);
		mqPushConsumer.setConsumeMessageBatchMaxSize(1);
		mqPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
		mqPushConsumer.subscribe(GiftProviderTopicNames.ROLL_BACK_STOCK, "");
		mqPushConsumer.setMessageListener((MessageListenerConcurrently) (msgs, context) -> {
			try {
				String mqStr = String.valueOf(msgs.get(0));
				skuStockInfoService.stockRollBackHandler(JSON.parseObject(mqStr, RollBackStockDTO.class));
			} catch (Exception e) {
				return ConsumeConcurrentlyStatus.RECONSUME_LATER;
			}
			return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
		});
		mqPushConsumer.start();
		LOGGER.info("[StockRollBackConsumer] consumer has started!");
	}
}

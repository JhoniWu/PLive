package com.prayer.live.im.core.server.handler.Impl;

import com.prayer.live.common.interfaces.topic.ImCoreServerProviderTopicNames;
import com.prayer.live.im.core.server.common.ImContextUtils;
import com.prayer.live.im.core.server.common.ImMsg;
import com.prayer.live.im.core.server.handler.SimplyHandler;
import io.netty.channel.ChannelHandlerContext;
import jakarta.annotation.Resource;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-12 17:02
 **/
@Component
public class BizImMsgHandler implements SimplyHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(BizImMsgHandler.class);
	@Resource
	MQProducer mqProducer;
	@Override
	public void handler(ChannelHandlerContext ctx, ImMsg imMsg) {
		//参数校验
		Long userId = ImContextUtils.getUserId(ctx);
		Integer appId = ImContextUtils.getAppId(ctx);
		if(userId == null || appId == null) {
			LOGGER.error("attr error, imMsg is {}", imMsg);
			ctx.close();
			throw new IllegalArgumentException("attr is error");
		}
		byte[] body = imMsg.getBody();
		if(body == null || body.length == 0){
			LOGGER.error("body error, imMsg is {}", imMsg);
			return ;
		}

		//进行消息转发，设置topic等
		Message msg = new Message();
		msg.setTopic(ImCoreServerProviderTopicNames.LIVE_IM_BIZ_MSG_TOPIC);
		msg.setBody(body);
		try {
			SendResult sendResult = mqProducer.send(msg);
			LOGGER.info("[BizImMsgHandler] 消息投递结果:{}", sendResult);
		} catch(Exception e) {
			LOGGER.error("send error, error is :", e);
			throw new RuntimeException(e);
		}
	}
}

package com.prayer.live.im.core.server.handler.Impl;

import com.alibaba.fastjson.JSON;
import com.prayer.live.common.interfaces.topic.ImCoreServerProviderTopicNames;
import com.prayer.live.im.constants.ImMsgCodeEnum;
import com.prayer.live.im.core.server.common.ChannelHandlerContextCache;
import com.prayer.live.im.core.server.common.ImContextUtils;
import com.prayer.live.im.core.server.common.ImMsg;
import com.prayer.live.im.core.server.handler.SimplyHandler;
import com.prayer.live.im.core.server.interfaces.constants.ImCoreServerConstants;
import com.prayer.live.im.core.server.interfaces.dto.ImOffLineDTO;
import com.prayer.live.im.dto.ImMsgBody;
import io.netty.channel.ChannelHandlerContext;
import jakarta.annotation.Resource;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-12 17:02
 **/
@Component
public class LogoutImMsgHandler implements SimplyHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(LogoutImMsgHandler.class);

	@Resource
	StringRedisTemplate stringRedisTemplate;
	@Resource
	MQProducer mqProducer;
	@Override
	public void handler(ChannelHandlerContext ctx, ImMsg imMsg) {
		Long userId = ImContextUtils.getUserId(ctx);
		Integer appId = ImContextUtils.getAppId(ctx);
		if(userId == null || appId == null){
			LOGGER.error("attr error, ImMsg is {}",imMsg);
			//可能是错误的 消息包导致，直接放弃连接
			ctx.close();
			throw new IllegalArgumentException("attr is error");
		}
		logoutMsgNotice(ctx, userId, appId);
		logoutHandler(ctx, userId, appId);
	}

	public void logoutHandler(ChannelHandlerContext ctx, Long userId, Integer appId) {
		LOGGER.info("[LogoutMsgHandler] logout success,userId is {},appId is {}", userId, appId);
		ChannelHandlerContextCache.remove(userId);
		stringRedisTemplate.delete(ImCoreServerConstants.IM_BIND_IP_KEY + appId + ":" + userId);
		ImContextUtils.removeAppId(ctx);
		ImContextUtils.removeUserId(ctx);
		sendLogoutMQ(ctx, userId, appId);
	}

	public void sendLogoutMQ(ChannelHandlerContext ctx, Long userId, Integer appId) {
		ImOffLineDTO offLineDTO = new ImOffLineDTO();
		offLineDTO.setUserId(userId);
		offLineDTO.setAppId(appId);
		offLineDTO.setRoomId(ImContextUtils.getRoomId(ctx));
		offLineDTO.setLogoutTime(System.currentTimeMillis());
		Message msg = new Message();
		msg.setTopic(ImCoreServerProviderTopicNames.LIVE_OFFLINE_TOPIC);
		msg.setBody(JSON.toJSONString(offLineDTO).getBytes());
		try {
			SendResult re = mqProducer.send(msg);
			LOGGER.error("[sendLogoutMQ] result is {}", re);
		} catch (Exception e){
			LOGGER.error("[sendLogoutMQ] error is: ", e);
		}
	}

	public void logoutMsgNotice(ChannelHandlerContext ctx, Long userId, Integer appId){
		ImMsgBody respBody = new ImMsgBody();
		respBody.setAppId(appId);
		respBody.setUserId(userId);
		respBody.setData("true");
		ImMsg respMsg = ImMsg.build(ImMsgCodeEnum.IM_LOGOUT_MSG.getCode(), JSON.toJSONString(respBody));
		ctx.writeAndFlush(respMsg);
		ctx.close();
	}
}
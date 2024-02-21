
package com.prayer.live.im.core.server.handler.Impl;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.fastjson.JSON;
import com.prayer.live.common.interfaces.topic.ImCoreServerProviderTopicNames;
import com.prayer.live.im.constants.ImConstants;
import com.prayer.live.im.constants.ImMsgCodeEnum;
import com.prayer.live.im.core.server.common.ChannelHandlerContextCache;
import com.prayer.live.im.core.server.common.ImContextUtils;
import com.prayer.live.im.core.server.common.ImMsg;
import com.prayer.live.im.core.server.handler.SimplyHandler;
import com.prayer.live.im.core.server.interfaces.constants.ImCoreServerConstants;
import com.prayer.live.im.core.server.interfaces.dto.ImOnlineDTO;
import com.prayer.live.im.dto.ImMsgBody;
import com.prayer.live.im.interfaces.ImTokenRPC;
import io.netty.channel.ChannelHandlerContext;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-12 17:02
 **/
@Component
public class LoginImMsgHandler implements SimplyHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginImMsgHandler.class);

	@DubboReference
	private ImTokenRPC imTokenRpc;
	@Resource
	StringRedisTemplate stringRedisTemplate;

	@Resource
	private MQProducer mqProducer;

	@Override
	public void handler(ChannelHandlerContext ctx, ImMsg imMsg) {
		if(ImContextUtils.getUserId(ctx) != null){
			return ;
		}
		byte[] body = imMsg.getBody();
		if(body == null || body.length == 0) {
			ctx.close();
			LOGGER.error("body error,imMsg is {}", imMsg);
			throw new IllegalArgumentException("body error");
		}

		ImMsgBody msgBody = JSON.parseObject(new String(body), ImMsgBody.class);
		Long userIdFromMsg = msgBody.getUserId();
		Integer appId = msgBody.getAppId();
		String token = msgBody.getToken();
		if(StringUtils.isEmpty(token) || userIdFromMsg < 10000 || appId < 10000){
			ctx.close();
			LOGGER.error("param error, imMsg is {}", imMsg);
			throw new IllegalArgumentException("param error");
		}
		Long userId = imTokenRpc.getUserIdByToken(token);
		//token check success
		if(userId!=null && userId.equals(userIdFromMsg)){
			loginSuccessHandler(ctx, userId, appId, null);
			return ;
		}
		ctx.close();
		LOGGER.error("token check error,imMsg is {}", imMsg);
		throw new IllegalArgumentException("token check error");
	}

	public void loginSuccessHandler(ChannelHandlerContext ctx, Long userId, Integer appId, Integer roomId) {
		ChannelHandlerContextCache.put(userId, ctx);
		ImContextUtils.SetUserId(ctx, userId);
		ImContextUtils.setAppId(ctx, appId);
		if(roomId != null){
			ImContextUtils.setRoomId(ctx, roomId);
		}
		ImMsgBody respBody = new ImMsgBody();
		respBody.setAppId(appId);
		respBody.setUserId(userId);
		respBody.setData("true");
		ImMsg respMsg = ImMsg.build(ImMsgCodeEnum.IM_LOGIN_MSG.getCode(), JSON.toJSONString(respBody));
		String cacheKey = ImCoreServerConstants.IM_BIND_IP_KEY + appId + ":" + userId;
		String value = ChannelHandlerContextCache.getServerIpAddress() + "%" + userId;
		stringRedisTemplate.opsForValue().set(cacheKey, value, ImConstants.DEFAULT_HEART_BEAT_GAP*2, TimeUnit.SECONDS);
		LOGGER.info("[LoginMsgHandler] login success,userId is {},appId is {}", userId, appId);
		ctx.writeAndFlush(respMsg);
		sendLoginMQ(userId, appId, roomId);
	}

	public void sendLoginMQ(Long userId, Integer appId, Integer roomId) {
		ImOnlineDTO imOnlineDTO = new ImOnlineDTO();
		imOnlineDTO.setUserId(userId);
		imOnlineDTO.setAppId(appId);
		imOnlineDTO.setRoomId(roomId);
		imOnlineDTO.setLoginTime(System.currentTimeMillis());
		Message msg = new Message();
		msg.setTopic(ImCoreServerProviderTopicNames.LIVE_ONLINE_TOPIC);
		msg.setBody(JSON.toJSONBytes(imOnlineDTO));
		try {
			SendResult send = mqProducer.send(msg);
			LOGGER.info("[sendLoginMQ] sendResult is {}", send);
		} catch (Exception e){
			LOGGER.error("[sendLoginMQ] error is: ", e);
		}
	}
}

package com.prayer.live.im.core.server.handler.Impl;

import com.alibaba.fastjson.JSON;
import com.prayer.live.framework.redis.starter.key.ImCoreServerProviderCacheKeyBuilder;
import com.prayer.live.im.constants.ImConstants;
import com.prayer.live.im.constants.ImMsgCodeEnum;
import com.prayer.live.im.core.server.common.ImContextUtils;
import com.prayer.live.im.core.server.common.ImMsg;
import com.prayer.live.im.core.server.handler.SimplyHandler;
import com.prayer.live.im.dto.ImMsgBody;
import io.netty.channel.ChannelHandlerContext;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-12 17:02
 **/
@Component
public class HeartBeatImMsgHandler implements SimplyHandler{
	private static final Logger LOGGER = LoggerFactory.getLogger(HeartBeatImMsgHandler.class);

	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	@Resource
	private ImCoreServerProviderCacheKeyBuilder cacheKeyBuilder;

	@Override
	public void handler(ChannelHandlerContext ctx, ImMsg imMsg) {
		//心跳包基本校验
		Long userId = ImContextUtils.getUserId(ctx);
		Integer appId = ImContextUtils.getAppId(ctx);
		if (userId == null || appId == null) {
			LOGGER.error("attr error,imMsg is {}", imMsg);
			//有可能是错误的消息包导致，直接放弃连接
			ctx.close();
			throw new IllegalArgumentException("attr is error");
		}

		//心跳包record记录，redis存储心跳记录
		String redisKey = cacheKeyBuilder.buildImOnLineKey(userId, appId);
		//记录当前的，去除掉过期的
		recordOnlineTime(userId, redisKey);
		removeExpireRecord(redisKey);
		redisTemplate.expire(redisKey, 5, TimeUnit.MINUTES);

		ImMsgBody msgBody = new ImMsgBody();
		msgBody.setUserId(userId);
		msgBody.setAppId(appId);
		msgBody.setData("true");
		ImMsg resMsg = ImMsg.build(ImMsgCodeEnum.IM_HEARTBEAT_MSG.getCode(), JSON.toJSONString(msgBody));
		LOGGER.info("[HeartBeatImMsgHandler] imMsg is {}", imMsg);
		ctx.writeAndFlush(resMsg);
	}

	private void removeExpireRecord(String redisKey) {
		redisTemplate.opsForZSet().removeRangeByScore(redisKey, 0, System.currentTimeMillis() - ImConstants.DEFAULT_HEART_BEAT_GAP * 1000 * 2);
	}

	private void recordOnlineTime(Long userId, String redisKey) {
		redisTemplate.opsForZSet().add(redisKey, userId, System.currentTimeMillis());
	}
}
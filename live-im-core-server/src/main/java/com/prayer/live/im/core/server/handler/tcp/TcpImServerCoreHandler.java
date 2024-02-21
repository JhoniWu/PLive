package com.prayer.live.im.core.server.handler.tcp;

import com.prayer.live.im.core.server.common.ImContextUtils;
import com.prayer.live.im.core.server.common.ImMsg;
import com.prayer.live.im.core.server.handler.ImHandlerFactory;
import com.prayer.live.im.core.server.handler.Impl.LogoutImMsgHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-18 13:46
 **/
@Component
@ChannelHandler.Sharable
public class TcpImServerCoreHandler extends SimpleChannelInboundHandler {
	Logger LOGGER = LoggerFactory.getLogger(TcpImServerCoreHandler.class);
	@Resource
	private ImHandlerFactory imHandlerFactory;
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	@Resource
	private LogoutImMsgHandler logoutImMsgHandler;

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		LOGGER.debug("channel read");
		if(!(msg instanceof ImMsg)){
			throw new IllegalArgumentException("error msg, msg is :" + msg);
		}
		ImMsg imMsg = (ImMsg) msg;
		imHandlerFactory.doMsgHandler(ctx, imMsg);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		LOGGER.debug("channel will close");
		Long userId = ImContextUtils.getUserId(ctx);
		Integer appId = ImContextUtils.getAppId(ctx);
		if(userId!=null && appId!=null) {
			LOGGER.debug("send logout msg");
			logoutImMsgHandler.logoutHandler(ctx, userId, appId);
		}
	}
}

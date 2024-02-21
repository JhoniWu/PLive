package com.prayer.live.im.core.server.handler.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.prayer.live.im.core.server.common.ImContextUtils;
import com.prayer.live.im.core.server.common.ImMsg;
import com.prayer.live.im.core.server.handler.ImHandlerFactory;
import com.prayer.live.im.core.server.handler.Impl.LogoutImMsgHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-18 15:26
 **/
@Component
@ChannelHandler.Sharable
public class WsImServerCoreHandler extends SimpleChannelInboundHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(WsImServerCoreHandler.class);

	@Resource
	private ImHandlerFactory imHandlerFactory;
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	@Resource
	private LogoutImMsgHandler logoutImMsgHandler;

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		LOGGER.info("ws msg is :{}", msg.toString());
		if(msg instanceof WebSocketFrame){
			wsMsgHandler(ctx, (WebSocketFrame) msg);
		} else {
			LOGGER.info("error msg {}", msg);
		}
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		Long userId = ImContextUtils.getUserId(ctx);
		Integer appId = ImContextUtils.getAppId(ctx);
		if (userId != null && appId != null) {
			logoutImMsgHandler.logoutHandler(ctx,userId,appId);
		}
	}

	private void wsMsgHandler(ChannelHandlerContext ctx, WebSocketFrame msg) {
		if(!(msg instanceof TextWebSocketFrame)){
			LOGGER.error(String.format("[WebsocketCoreHandler]  wsMsgHandler , %s msg types not supported", msg.getClass().getName()));
			return ;
		}

		try {
			String content = ((TextWebSocketFrame) msg).text();
			JSONObject resp = JSON.parseObject(content, JSONObject.class);
			ImMsg imMsg = new ImMsg();
			imMsg.setMagic(resp.getShort("magic"));
			imMsg.setCode(resp.getInteger("code"));
			imMsg.setLen(resp.getInteger("len"));
			imMsg.setBody(resp.getString("body").getBytes());
			imHandlerFactory.doMsgHandler(ctx, imMsg);
		} catch (Exception e){
			LOGGER.error("[WebsocketCoreHandler] wsMsgHandler error is:", e);
		}
	}
}

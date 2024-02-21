package com.prayer.live.im.core.server.handler.websocket;

import com.prayer.live.im.core.server.handler.Impl.LoginImMsgHandler;
import com.prayer.live.im.interfaces.ImTokenRPC;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-18 14:45
 **/
@Component
@ChannelHandler.Sharable
public class WsSharkHandler extends ChannelInboundHandlerAdapter {
	private static final Logger LOGGER = LoggerFactory.getLogger(WsSharkHandler.class);

	//监听端口
	@Value("${live.im.ws.port}")
	private int port;
	@Value("${spring.cloud.nacos.discovery.ip}")
	private String serverIp;
	@DubboReference
	private ImTokenRPC imTokenRPC;
	@Resource
	private LoginImMsgHandler loginImMsgHandler;

	private WebSocketServerHandshaker webSocketServerHandshaker;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		LOGGER.info("ws shark handler get msg {}", msg);
		if(msg instanceof FullHttpRequest){
			handlerHttpRequest(ctx, ((FullHttpRequest) msg));
			return ;
		}
		if(msg instanceof CloseWebSocketFrame){
			LOGGER.info("webSocketServerHandshaker.close");
			webSocketServerHandshaker.close(ctx.channel(), ((CloseWebSocketFrame) msg).retain());
			return ;
		}
		ctx.fireChannelRead(msg);
	}

	private void handlerHttpRequest(ChannelHandlerContext ctx, FullHttpRequest msg) {
		String webSocketUrl = "ws://" + serverIp + ":" + port;
		WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(webSocketUrl, null, false);
		String uri = msg.uri();
		String[] paramArr = uri.split("/");
		String token = paramArr[1];
		Long userId = Long.valueOf(paramArr[2]);
		Long queryUserId = imTokenRPC.getUserIdByToken(token);
		Integer appId = Integer.valueOf(token.substring(token.lastIndexOf("%") + 1));

		if (queryUserId == null || !queryUserId.equals(userId)) {
			LOGGER.error("[WsSharkHandler] token 校验不通过！");
			//校验不通过，不允许建立连接
			//ctx.close();
			//return;
		}

		//建立ws的握手连接
		webSocketServerHandshaker = wsFactory.newHandshaker(msg);

		if (webSocketServerHandshaker == null) {
			WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
			return;
		}

		ChannelFuture channelFuture = webSocketServerHandshaker.handshake(ctx.channel(), msg);

		//首次握手建立ws连接后，返回一定的内容给到客户端
		if (channelFuture.isSuccess()) {
			Integer code = Integer.valueOf(paramArr[3]);
			Integer roomId = null;
			if (code == ParamCodeEnum.LIVING_ROOM_LOGIN.getCode()) {
				roomId = Integer.valueOf(paramArr[4]);
			}
			loginImMsgHandler.loginSuccessHandler(ctx, userId, appId, roomId);
			LOGGER.info("[WebsocketSharkHandler] channel is connect!");
		}
	}

	enum ParamCodeEnum{
		LIVING_ROOM_LOGIN(1001, "直播登录");
		int code;
		String desc;

		ParamCodeEnum(int code, String desc){
			this.code = code;
			this.desc = desc;
		}

		public int getCode(){return this.code;}
		public String getDesc(){return this.desc;}
	}
}

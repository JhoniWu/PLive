package com.prayer.live.im.core.server.common;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-18 16:53
 **/
public class WebsocketEncoder extends ChannelOutboundHandlerAdapter {
	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		System.out.println("ws - encoder");
		if (!(msg instanceof ImMsg)) {
			super.write(ctx, msg, promise);
			return;
		}
		ImMsg imMsg = (ImMsg) msg;
		ctx.channel().writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(imMsg)));
	}
}

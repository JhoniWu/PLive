package com.prayer.live.im.core.server.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-12 16:23
 **/
public class ImMsgEncoder extends MessageToByteEncoder {
	Logger logger = LoggerFactory.getLogger(ImMsgEncoder.class);
	@Override
	protected void encode(ChannelHandlerContext channelHandlerContext, Object msg, ByteBuf res) throws Exception {
		ImMsg imMsg = (ImMsg) msg;
		res.writeShort(imMsg.getMagic())
			.writeInt(imMsg.getCode())
			.writeInt(imMsg.getLen())
			.writeBytes(imMsg.getBody());
		channelHandlerContext.writeAndFlush(res + "\r\n");
	}
}

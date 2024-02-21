package com.prayer.live.im.core.server.common;

import com.prayer.live.im.constants.ImConstants;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-12 16:22
 **/
public class ImMsgDecoder extends ByteToMessageDecoder {
	Logger LOGGER = LoggerFactory.getLogger(ImMsgDecoder.class);
	private final int BASE_LEN = 10;
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> res) throws Exception {
		if(byteBuf.readableBytes() >= BASE_LEN){
			if(byteBuf.readShort() != ImConstants.DEFAULT_MAGIC){
				LOGGER.info("msg magic error, ctx close");
				ctx.close();
				return ;
			}

			int code = byteBuf.readInt();
			int len = byteBuf.readInt();

			if(byteBuf.readableBytes() < len){
				ctx.close();
				return ;
			}

			byte[] body = new byte[len];
			byteBuf.readBytes(body);
			ImMsg imMsg = new ImMsg();
			imMsg.setCode(code);
			imMsg.setLen(len);
			imMsg.setMagic(ImConstants.DEFAULT_MAGIC);
			imMsg.setBody(body);
			res.add(imMsg);
		}
	}
}

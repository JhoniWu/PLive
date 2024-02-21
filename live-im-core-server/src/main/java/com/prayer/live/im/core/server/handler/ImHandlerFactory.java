package com.prayer.live.im.core.server.handler;

import com.prayer.live.im.core.server.common.ImMsg;
import io.netty.channel.ChannelHandlerContext;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-12 16:24
 **/
public interface ImHandlerFactory {
	void doMsgHandler(ChannelHandlerContext channelHandlerContext, ImMsg imMsg);
}

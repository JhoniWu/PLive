package com.prayer.live.im.core.server.common;

import io.netty.channel.ChannelHandlerContext;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-17 13:46
 **/
public class ImMsgPropertiesUtils {
	public boolean checkProperties(ChannelHandlerContext ctx){
		return ImContextUtils.getUserId(ctx) == null || ImContextUtils.getAppId(ctx) == null;
	}
}

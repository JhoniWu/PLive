package com.prayer.live.im.core.server.common;

import io.netty.channel.ChannelHandlerContext;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-12 16:28
 **/
public class ImContextUtils {
	public static void setAppId(ChannelHandlerContext ctx, int appId){
		ctx.attr(ImContextAttr.APP_ID).set(appId);
	}

	public static Integer getAppId(ChannelHandlerContext ctx){
		return ctx.attr(ImContextAttr.APP_ID).get();
	}

	public static void SetUserId(ChannelHandlerContext ctx, Long userId){
		ctx.attr(ImContextAttr.USER_ID).set(userId);
	}

	public static Long getUserId(ChannelHandlerContext ctx) {
		return ctx.attr(ImContextAttr.USER_ID).get();
	}

	public static void removeUserId(ChannelHandlerContext ctx) {
		ctx.attr(ImContextAttr.USER_ID).remove();
	}

	public static void removeAppId(ChannelHandlerContext ctx) {
		ctx.attr(ImContextAttr.APP_ID).remove();
	}

	public static void removeRoomId(ChannelHandlerContext ctx){ctx.attr(ImContextAttr.ROOM_ID).remove();}

	public static Integer getRoomId(ChannelHandlerContext ctx){
		return ctx.attr(ImContextAttr.ROOM_ID).get();
	}


	public static void setRoomId(ChannelHandlerContext ctx, Integer roomId) {
		ctx.attr(ImContextAttr.ROOM_ID).set(roomId);
	}
}

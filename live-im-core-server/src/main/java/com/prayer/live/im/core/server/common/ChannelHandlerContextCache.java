package com.prayer.live.im.core.server.common;

import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-12 16:27
 **/
public class ChannelHandlerContextCache {
	private static String SERVER_IP_ADDRESS = "";

	public static Map<Long, ChannelHandlerContext> channelHandlerContextMap = new HashMap<>();

	public static ChannelHandlerContext get(Long userId) {
		return channelHandlerContextMap.get(userId);
	}

	public static void put(Long userId, ChannelHandlerContext channelHandlerContext) {
		channelHandlerContextMap.put(userId, channelHandlerContext);
	}

	public static void remove(Long userId) {
		channelHandlerContextMap.remove(userId);
	}

	public static void setServerIpAddress(String s) {
		SERVER_IP_ADDRESS = s;
	}

	public static String getServerIpAddress() {
		return SERVER_IP_ADDRESS;
	}
}
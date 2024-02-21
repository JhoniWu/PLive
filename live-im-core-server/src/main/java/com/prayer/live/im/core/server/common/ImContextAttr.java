package com.prayer.live.im.core.server.common;

import io.netty.util.AttributeKey;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-12 16:27
 **/
public class ImContextAttr {
	public static AttributeKey<Long> USER_ID = AttributeKey.valueOf("userId");
	public static AttributeKey<Integer> APP_ID = AttributeKey.valueOf("appId");
	public static AttributeKey<Integer> ROOM_ID = AttributeKey.valueOf("roomId");
}

package com.prayer.live.im.provider.service;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-15 17:40
 **/
public interface ImOnlineService {
	boolean isOnline(long userId, int appId);
}

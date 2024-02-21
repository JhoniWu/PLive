package com.prayer.live.im.interfaces;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-02-05 20:30
 **/
public interface ImOnlineRpc {
	/**
	 * 判断用户是否在线
	 *
	 * @param userId
	 * @param appId
	 * @return
	 */
	boolean isOnline(long userId,int appId);
}

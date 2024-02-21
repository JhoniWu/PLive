package com.prayer.live.im.interfaces;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-12 15:28
 **/
public interface ImTokenRPC {
	/**
	 * 创建login的token
	 *
	 * @param userId
	 * @param appId
	 * @return
	 */
	String createImLoginToken(long userId, int appId);

	/**
	 * 获取对应token的user信息
	 *
	 * @param token
	 * @return
	 */
	Long getUserIdByToken(String token);
}

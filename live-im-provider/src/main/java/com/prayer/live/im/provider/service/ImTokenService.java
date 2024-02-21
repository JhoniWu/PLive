package com.prayer.live.im.provider.service;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-12 17:51
 **/
public interface ImTokenService {
	/**
	 * 创建用户登录im服务的token
	 *
	 * @param userId
	 * @param appId
	 * @return
	 */
	String createImLoginToken(long userId, int appId);

	/**
	 * 根据token检索用户id
	 *
	 * @param token
	 * @return
	 */
	Long getUserIdByToken(String token);
}

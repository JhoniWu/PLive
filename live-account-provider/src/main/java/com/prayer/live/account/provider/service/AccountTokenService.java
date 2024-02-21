package com.prayer.live.account.provider.service;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-10 00:49
 **/
public interface AccountTokenService {
	/**
	 * 创建登录token
	 * @param userId
	 * @return
	 */
	String createAndSaveLoginToken(Long userId);

	/**
	 * 校验
	 * @param tokenKey
	 * @return
	 */
	Long getUserIdByToken(String tokenKey);
}

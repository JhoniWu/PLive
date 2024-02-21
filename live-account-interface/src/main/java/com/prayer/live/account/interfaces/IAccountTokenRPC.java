package com.prayer.live.account.interfaces;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-10 00:44
 **/
public interface IAccountTokenRPC {
	/**
	 * 创建登录token
	 * @param userId
	 * @return
	 */
	String createAndSaveLoginToken(Long userId);

	/**
	 * 校验用户id
	 * @param tokenKey
	 * @return
	 */
	Long getUserIdByToken(String tokenKey);
}

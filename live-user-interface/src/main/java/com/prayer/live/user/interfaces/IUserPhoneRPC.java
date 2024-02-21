package com.prayer.live.user.interfaces;

import com.prayer.live.user.dto.UserLoginDTO;
import com.prayer.live.user.dto.UserPhoneDTO;

import java.util.List;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2023-11-01 17:08
 **/
public interface IUserPhoneRPC {

	/**
	 * 用户登录（底层会进行手机号的注册）
	 * @param phone
	 * @return
	 */
	UserLoginDTO login(String phone);

	/**
	 * 根据手机信息查询相关用户信息
	 *
	 * @param phone
	 * @return
	 */
	UserPhoneDTO queryByPhone(String phone);

	/**
	 * 根据用户id查询手机相关信息
	 *
	 * @param userId
	 * @return
	 */
	List<UserPhoneDTO> queryByUserId(Long userId);
}

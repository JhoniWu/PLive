package com.prayer.live.user.interfaces;

import com.prayer.live.user.dto.UserDTO;

import java.util.List;
import java.util.Map;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2023-10-22 21:31
 **/
public interface IUserRpc {

	/**
	 * 根据用户id进行查询
	 *
	 * @param userId
	 * @return
	 */
	UserDTO getByUserId(Long userId);


	/**
	 * 用户信息更新
	 *
	 * @param userDTO
	 * @return
	 */
	boolean updateUserInfo(UserDTO userDTO);

	/**
	 * 插入用户信息
	 *
	 * @param userDTO
	 * @return
	 */
	boolean insertOne(UserDTO userDTO);

	/**
	 * 批量查询用户信息
	 *
	 * @param userIdList
	 * @return
	 */
	Map<Long, UserDTO> batchQueryUserInfo(List<Long> userIdList);
}

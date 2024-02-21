package com.prayer.live.user.provider.rpc;

import com.prayer.live.user.dto.UserDTO;
import com.prayer.live.user.interfaces.IUserRpc;
import com.prayer.live.user.provider.service.IUserService;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;
import java.util.Map;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2023-10-22 21:31
 **/
@DubboService
public class IUserRpcImpl implements IUserRpc {
	@Resource
	private IUserService userService;

	@Override
	public UserDTO getByUserId(Long userId) {
		return userService.getByUserId(userId);
	}

	@Override
	public boolean updateUserInfo(UserDTO userDTO) {
		return userService.updateUserInfo(userDTO);
	}

	@Override
	public boolean insertOne(UserDTO userDTO) {
		return userService.insertOne(userDTO);
	}

	@Override
	public Map<Long, UserDTO> batchQueryUserInfo(List<Long> userIdList) {
		return userService.batchQueryUserInfo(userIdList);
	}
}

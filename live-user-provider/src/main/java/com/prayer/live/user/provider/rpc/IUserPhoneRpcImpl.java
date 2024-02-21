package com.prayer.live.user.provider.rpc;

import com.prayer.live.user.dto.UserLoginDTO;
import com.prayer.live.user.dto.UserPhoneDTO;
import com.prayer.live.user.interfaces.IUserPhoneRPC;
import com.prayer.live.user.provider.service.IUserPhoneService;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-05 17:36
 **/

@DubboService
public class IUserPhoneRpcImpl implements IUserPhoneRPC {
	@Resource
	IUserPhoneService userPhoneService;
	@Override
	public UserLoginDTO login(String phone) {
		return userPhoneService.login(phone);
	}

	@Override
	public UserPhoneDTO queryByPhone(String phone) {
		return userPhoneService.queryByPhone(phone);
	}

	@Override
	public List<UserPhoneDTO> queryByUserId(Long userId) {
		return userPhoneService.queryByUserId(userId);
	}
}

package com.prayer.live.user.provider.service;

import com.prayer.live.user.dto.UserLoginDTO;
import com.prayer.live.user.dto.UserPhoneDTO;

import java.util.List;

public interface IUserPhoneService {
	UserLoginDTO login(String phone);

	UserPhoneDTO queryByPhone(String phone);

	List<UserPhoneDTO> queryByUserId(Long userId);
}

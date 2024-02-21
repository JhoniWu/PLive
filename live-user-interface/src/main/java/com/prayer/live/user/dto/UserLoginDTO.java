package com.prayer.live.user.dto;

import java.io.Serial;
import java.io.Serializable;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2023-11-01 17:10
 **/
public class UserLoginDTO implements Serializable {

	@Serial
	private static final long serialVersionUID = 8938125387925240875L;

	private boolean isLoginSuccess;
	private String desc;
	private Long userId;
	private String token;

	@Override
	public String toString() {
		return "UserLoginDTO{" +
			"isLoginSuccess=" + isLoginSuccess +
			", desc='" + desc + '\'' +
			", userId=" + userId +
			", token='" + token + '\'' +
			'}';
	}

	public boolean isLoginSuccess() {
		return isLoginSuccess;
	}

	public void setLoginSuccess(boolean loginSuccess) {
		isLoginSuccess = loginSuccess;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public static UserLoginDTO loginError(String desc) {
		UserLoginDTO userLoginDTO = new UserLoginDTO();
		userLoginDTO.setLoginSuccess(false);
		userLoginDTO.setDesc(desc);
		return userLoginDTO;
	}

	public static UserLoginDTO loginSuccess(Long userId,String token) {
		UserLoginDTO userLoginDTO = new UserLoginDTO();
		userLoginDTO.setLoginSuccess(true);
		userLoginDTO.setUserId(userId);
		userLoginDTO.setToken(token);
		return userLoginDTO;
	}
}

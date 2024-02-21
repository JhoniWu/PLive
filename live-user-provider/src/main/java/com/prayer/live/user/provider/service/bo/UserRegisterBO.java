package com.prayer.live.user.provider.service.bo;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2023-11-02 23:20
 **/
public class UserRegisterBO {
	private Long userId;
	private String phone;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}

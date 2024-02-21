package com.prayer.live.im.core.server.interfaces.dto;

import java.io.Serializable;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-18 13:01
 **/
public class ImOnlineDTO implements Serializable {

	private static final long serialVersionUID = -6858441392464256104L;

	private Long userId;
	private Integer appId;
	private Integer roomId;
	private Long loginTime;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public Long getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Long loginTime) {
		this.loginTime = loginTime;
	}

	@Override
	public String toString() {
		return "ImOnlineDTO{" +
			"userId=" + userId +
			", appId=" + appId +
			", roomId=" + roomId +
			", loginTime=" + loginTime +
			'}';
	}
}

package com.prayer.live.im.core.server.interfaces.dto;

import java.io.Serializable;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-18 12:59
 **/
public class ImOffLineDTO implements Serializable {
	private static final long serialVersionUID = 4729500494479572342L;
	private Long userId;
	private Integer appId;
	private Integer roomId;
	private Long logoutTime;

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

	public Long getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime(Long logoutTime) {
		this.logoutTime = logoutTime;
	}

	@Override
	public String toString() {
		return "ImOffLineDTO{" +
			"userId=" + userId +
			", appId=" + appId +
			", roomId=" + roomId +
			", logoutTime=" + logoutTime +
			'}';
	}
}

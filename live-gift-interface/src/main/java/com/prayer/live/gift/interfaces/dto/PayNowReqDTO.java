package com.prayer.live.gift.interfaces.dto;

import java.io.Serializable;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-02-19 16:16
 **/
public class PayNowReqDTO implements Serializable {


	private static final long serialVersionUID = 3771947235396666191L;

	private Long userId;
	private Integer roomId;

	@Override
	public String toString() {
		return "PayNowReqDTO{" +
			"userId=" + userId +
			", roomId=" + roomId +
			'}';
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}
}

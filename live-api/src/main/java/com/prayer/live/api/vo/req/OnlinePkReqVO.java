package com.prayer.live.api.vo.req;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-26 20:12
 **/
public class OnlinePkReqVO {
	private Integer roomId;

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	@Override
	public String toString() {
		return "OnlinePkReqVO{" +
			"roomId=" + roomId +
			'}';
	}
}

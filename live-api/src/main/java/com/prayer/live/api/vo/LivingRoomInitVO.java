package com.prayer.live.api.vo;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-18 16:55
 **/
public class LivingRoomInitVO {
	private Long anchorId;
	private Long userId;
	private String anchorImg;
	private String roomName;
	private boolean isAnchor;
	private String avatar;
	private Integer roomId;
	private String nickName;
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getAnchorId() {
		return anchorId;
	}

	public void setAnchorId(Long anchorId) {
		this.anchorId = anchorId;
	}

	public String getAnchorImg() {
		return anchorImg;
	}

	public void setAnchorImg(String anchorImg) {
		this.anchorImg = anchorImg;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public boolean isAnchor() {
		return isAnchor;
	}

	public void setAnchor(boolean anchor) {
		isAnchor = anchor;
	}

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}
}

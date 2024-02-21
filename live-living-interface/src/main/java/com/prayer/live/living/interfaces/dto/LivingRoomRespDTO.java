package com.prayer.live.living.interfaces.dto;

import java.io.Serializable;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-17 22:38
 **/
public class LivingRoomRespDTO implements Serializable {
	private static final long serialVersionUID = -8382267727654823826L;
	private Integer id;
	private Long anchorId;
	private String roomName;
	private String covertImg;
	private Integer type;
	private Integer watchNum;
	private Integer goodNum;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getAnchorId() {
		return anchorId;
	}

	public void setAnchorId(Long anchorId) {
		this.anchorId = anchorId;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getCovertImg() {
		return covertImg;
	}

	public void setCovertImg(String covertImg) {
		this.covertImg = covertImg;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getWatchNum() {
		return watchNum;
	}

	public void setWatchNum(Integer watchNum) {
		this.watchNum = watchNum;
	}

	public Integer getGoodNum() {
		return goodNum;
	}

	public void setGoodNum(Integer goodNum) {
		this.goodNum = goodNum;
	}

	@Override
	public String toString() {
		return "LivingRoomRespDTO{" +
			"id=" + id +
			", anchorId=" + anchorId +
			", roomName='" + roomName + '\'' +
			", covertImg='" + covertImg + '\'' +
			", type=" + type +
			", watchNum=" + watchNum +
			", goodNum=" + goodNum +
			'}';
	}
}

package com.prayer.live.living.interfaces.dto;

import java.io.Serializable;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-17 22:38
 **/
public class LivingRoomReqDTO implements Serializable {

	private static final long serialVersionUID = -8386496055812656640L;
	private Integer id;
	private Long anchorId;
	private String roomName;
	private Long pkObjId;

	public Long getPkObjId() {
		return pkObjId;
	}

	public void setPkObjId(Long pkObjId) {
		this.pkObjId = pkObjId;
	}

	private Integer roomId;
	private String covertImg;
	private Integer type;
	private Integer appId;
	private int page;
	private int pageSize;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

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

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public String getCovertImg() {
		return covertImg;
	}

	public void setCovertImg(String covertImg) {
		this.covertImg = covertImg;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	@Override
	public String toString() {
		return "LivingRoomReqDTO{" +
			"id=" + id +
			", anchorId=" + anchorId +
			", roomName='" + roomName + '\'' +
			", roomId=" + roomId +
			", covertImg='" + covertImg + '\'' +
			", type=" + type +
			", appId=" + appId +
			", pkObjId=" + pkObjId +
			", page=" + page +
			", pageSize=" + pageSize +
			'}';
	}
}

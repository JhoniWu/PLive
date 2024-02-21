package com.prayer.live.living.provider.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-17 23:03
 **/
@TableName("t_living_room")
public class LivingRoomPO {
	@TableId(type = IdType.AUTO)
	private Integer id;
	private long anchorId;
	private Integer type;
	private String roomName;
	private String covertImg;
	private Integer status;
	private Integer watchNum;
	private Integer goodNum;
	private Date startTime;
	private Date endTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public long getAnchorId() {
		return anchorId;
	}

	public void setAnchorId(long anchorId) {
		this.anchorId = anchorId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return "LivingRoomPO{" +
			"id=" + id +
			", anchorId=" + anchorId +
			", type=" + type +
			", roomName='" + roomName + '\'' +
			", covertImg='" + covertImg + '\'' +
			", status=" + status +
			", watchNum=" + watchNum +
			", goodNum=" + goodNum +
			", startTime=" + startTime +
			", endTime=" + endTime +
			'}';
	}
}

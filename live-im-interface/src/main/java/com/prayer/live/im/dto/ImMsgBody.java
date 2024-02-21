package com.prayer.live.im.dto;

import java.io.Serial;
import java.io.Serializable;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-12 15:29
 **/
public class ImMsgBody implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	private int appId;
	private long userId;
	private String token;
	private String data;

	/**
	 * 业务标识
	 */
	private int bizCode;
	/**
	 * 唯一的消息id
	 */
	private String msgId;

	public int getBizCode() {
		return bizCode;
	}

	public void setBizCode(int bizCode) {
		this.bizCode = bizCode;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ImMsgBody{" +
			"appId=" + appId +
			", userId=" + userId +
			", token='" + token + '\'' +
			", data='" + data + '\'' +
			", bizCode=" + bizCode +
			", msgId='" + msgId + '\'' +
			'}';
	}
}

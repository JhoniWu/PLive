package com.prayer.live.living.interfaces.dto;

import java.io.Serializable;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-26 19:39
 **/
public class LivingPkRespDTO implements Serializable {

	private static final long serialVersionUID = 1344364802361655434L;

	private boolean onlineStatus;
	private String msg;

	public boolean isOnlineStatus() {
		return onlineStatus;
	}

	public void setOnlineStatus(boolean onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}

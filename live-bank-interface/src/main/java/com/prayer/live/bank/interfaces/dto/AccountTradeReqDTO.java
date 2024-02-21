package com.prayer.live.bank.interfaces.dto;

import java.io.Serializable;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-21 12:28
 **/
public class AccountTradeReqDTO implements Serializable {
	private static final long serialVersionUID = -8325919590713385692L;

	private long userId;
	private int num;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	@Override
	public String toString() {
		return "AccountTradeReqDTO{" +
			"userId=" + userId +
			", num=" + num +
			'}';
	}
}

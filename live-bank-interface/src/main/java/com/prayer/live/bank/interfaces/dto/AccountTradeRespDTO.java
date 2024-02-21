package com.prayer.live.bank.interfaces.dto;

import java.io.Serializable;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-21 12:28
 **/
public class AccountTradeRespDTO implements Serializable {

	private static final long serialVersionUID = -5010767271358980743L;

	private int code;
	private long userId;
	private boolean isSuccess;
	private String msg;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean success) {
		isSuccess = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "AccountTradeRespDTO{" +
			"code=" + code +
			", userId=" + userId +
			", isSuccess=" + isSuccess +
			", msg='" + msg + '\'' +
			'}';
	}

	public static AccountTradeRespDTO buildFail(long userId, String msg,int code) {
		AccountTradeRespDTO tradeRespDTO = new AccountTradeRespDTO();
		tradeRespDTO.setUserId(userId);
		tradeRespDTO.setCode(code);
		tradeRespDTO.setMsg(msg);
		tradeRespDTO.setSuccess(false);
		return tradeRespDTO;
	}

	public static AccountTradeRespDTO buildSuccess(long userId, String msg) {
		AccountTradeRespDTO tradeRespDTO = new AccountTradeRespDTO();
		tradeRespDTO.setUserId(userId);
		tradeRespDTO.setMsg(msg);
		tradeRespDTO.setSuccess(true);
		return tradeRespDTO;
	}
}

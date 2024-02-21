package com.prayer.live.framework.web.starter.constants;

public enum ErrorAppIdEnum {
	API_ERROR(101, "live-api");
	int code;
	String msg;
	ErrorAppIdEnum(int code, String msg){
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}

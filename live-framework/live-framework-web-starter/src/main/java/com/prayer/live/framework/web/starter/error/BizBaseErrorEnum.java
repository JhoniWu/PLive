package com.prayer.live.framework.web.starter.error;

public enum BizBaseErrorEnum implements LiveBaseError {
	PARAM_ERROR(100001, "参数异常"),
	TOKEN_ERROR(100002, "用户token异常");

	int code;
	String errorMsg;

	BizBaseErrorEnum(int code, String msg){
		this.code = code;
		this.errorMsg = msg;
	}

	@Override
	public int getErrorCode() {
		return this.code;
	}

	@Override
	public String getErrorMsg() {
		return this.errorMsg;
	}
}

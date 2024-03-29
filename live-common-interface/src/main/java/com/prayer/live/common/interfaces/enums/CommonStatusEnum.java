package com.prayer.live.common.interfaces.enums;

public enum CommonStatusEnum {
	INVALID_STATUS(0,"无效"),
	VALID_STATUS(1,"有效");
	int code;
	String desc;
	CommonStatusEnum(int code, String desc){
		this.code = code;
		this.desc = desc;
	}
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}

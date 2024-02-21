package com.prayer.live.common.interfaces.enums;

public enum GatewayHeaderEnum {
	USER_LOGIN_ID("用户id","prayer-live_user_id");
	String desc;
	String name;

	GatewayHeaderEnum(String desc, String name){
		this.desc = desc;
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public String getName() {
		return name;
	}
}

package com.prayer.live.living.interfaces.constants;

public enum LivingRoomTypeEnum {
	DEFAULT_LIVING_ROOM(1, "普通直播间"),
	PK_LIVING_ROOM(2,"PK直播间");

	int code;
	String desc;
	LivingRoomTypeEnum(int code, String desc){
		this.code = code;
		this.desc = desc;
	}

	public int getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
}

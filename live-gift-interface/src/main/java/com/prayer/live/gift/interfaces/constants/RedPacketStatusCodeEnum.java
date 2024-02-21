package com.prayer.live.gift.interfaces.constants;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-29 17:30
 **/
public enum RedPacketStatusCodeEnum {
	NOT_PREPARE(1,"待准备"),
	IS_PREPARE(2,"已准备"),
	HAS_SEND(3,"已发送");
	int code;
	String desc;
	RedPacketStatusCodeEnum(int code, String desc) {
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

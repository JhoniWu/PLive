package com.prayer.live.gift.interfaces.constants;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-26 17:06
 **/
public enum SendGiftTypeEnum {
	DEFAULT_SEND_GIFT(0,"直播间默认送礼物"),
	PK_SEND_GIFT(1,"直播间PK送礼物");

	SendGiftTypeEnum(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	private final Integer code;
	private final String desc;

	public Integer getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
}


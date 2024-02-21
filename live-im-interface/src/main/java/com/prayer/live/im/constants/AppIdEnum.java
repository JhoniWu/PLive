package com.prayer.live.im.constants;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-12 15:32
 **/
public enum AppIdEnum {
	LIVE_BIZ(10001, "直播业务");
	int code;
	String desc;

	AppIdEnum(int code, String desc){
		this.code = code;
		this.desc = desc;
	}

	public int getCode(){return code;}
	public String getDesc(){return desc;}
}

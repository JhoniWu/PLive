package com.prayer.live.msg.enums;

public enum ImMsgBizCodeEnum {
	LIVING_ROOM_IM_CHAT_MSG_BIZ(5555,"直播间im聊天消息"),
	LIVING_ROOM_SEND_GIFT_SUCCESS(5556,"送礼成功"),
	LIVING_ROOM_SEND_GIFT_FAIL(5557,"送礼失败"),
	LIVING_ROOM_PK_SEND_GIFT_SUCCESS(5558,"pk送礼成功"),
	LIVING_ROOM_PK_ONLINE(5559,"pk连线");
	int code;
	String desc;

	ImMsgBizCodeEnum(int code, String desc){
		this.code = code;
		this.desc = desc;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}

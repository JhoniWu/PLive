package com.prayer.live.bank.interfaces.constants;

public enum TradeTypeEnum {
	SEND_GIFT_TRADE(0, "送礼交易");
	int code;
	String desc;

	TradeTypeEnum(int code, String desc) {
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

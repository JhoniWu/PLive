package com.prayer.live.gift.interfaces.constants;

public enum SkuOrderInfoEnum {
	PREPARE_PAY(0, "待支付状态"),
	HAS_PAY(1, "已支付"),
	END(2, "订单已关闭");
	Integer code;
	String desc;

	SkuOrderInfoEnum(int code, String desc){
		this.code = code;
		this.desc = desc;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}

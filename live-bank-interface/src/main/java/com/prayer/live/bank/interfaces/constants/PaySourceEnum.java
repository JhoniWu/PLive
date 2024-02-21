package com.prayer.live.bank.interfaces.constants;


/**
 * 支付渠道类型
 *
 * @Author idea
 * @Date: Created in 20:32 2023/8/19
 * @Description
 */
public enum PaySourceEnum {

    LIVING_ROOM(1,"旗鱼直播间内支付"),
    USER_CENTER(2,"用户中心");

    PaySourceEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static PaySourceEnum find(int code) {
        for (PaySourceEnum value : PaySourceEnum.values()) {
            if(value.getCode() == code) {
                return value;
            }
        }
        return null;
    }

    private final int code;
    private final String desc;

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}

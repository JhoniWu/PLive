package com.prayer.live.msg.interfaces;

import com.prayer.live.msg.dto.MsgCheckDTO;
import com.prayer.live.msg.enums.MsgSendResultEnum;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2023-11-02 23:41
 **/
public interface ISmsRpc {
	/**
	 * 发送短信登录验证码接口
	 *
	 * @param phone
	 * @return
	 */
	MsgSendResultEnum sendLoginCode(String phone);
	/**
	 * 校验登录验证码
	 *
	 * @param phone
	 * @param code
	 * @return
	 */
	MsgCheckDTO checkLoginCode(String phone, Integer code);
}

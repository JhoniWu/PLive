package com.prayer.live.msg.provider.service;

import com.prayer.live.msg.dto.MsgCheckDTO;
import com.prayer.live.msg.enums.MsgSendResultEnum;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2023-11-02 23:49
 **/
public interface ISmsService {
	/**
	 * 发送短信接口
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

	/**
	 * 插入一条短信验证码记录
	 *
	 * @param phone
	 * @param code
	 */
	void insertOne(String phone, Integer code);
}

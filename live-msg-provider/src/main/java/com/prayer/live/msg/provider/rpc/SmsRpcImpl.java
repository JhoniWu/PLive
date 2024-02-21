package com.prayer.live.msg.provider.rpc;

import com.prayer.live.msg.dto.MsgCheckDTO;
import com.prayer.live.msg.enums.MsgSendResultEnum;
import com.prayer.live.msg.interfaces.ISmsRpc;
import com.prayer.live.msg.provider.service.ISmsService;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2023-11-02 23:49
 **/
@DubboService
public class SmsRpcImpl implements ISmsRpc {
	@Resource
	private ISmsService smsService;


	@Override
	public MsgSendResultEnum sendLoginCode(String phone) {
		System.out.println("send start");
		return smsService.sendLoginCode(phone);
	}

	@Override
	public MsgCheckDTO checkLoginCode(String phone, Integer code) {
		return smsService.checkLoginCode(phone,code);
	}
}

package com.prayer.live.api.service.impl;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.prayer.live.account.interfaces.IAccountTokenRPC;
import com.prayer.live.api.service.IUserLoginService;
import com.prayer.live.api.vo.UserLoginVO;
import com.prayer.live.common.interfaces.utils.ConvertBeanUtils;
import com.prayer.live.common.interfaces.vo.WebResponseVO;
import com.prayer.live.msg.dto.MsgCheckDTO;
import com.prayer.live.msg.enums.MsgSendResultEnum;
import com.prayer.live.msg.interfaces.ISmsRpc;
import com.prayer.live.user.dto.UserLoginDTO;
import com.prayer.live.user.interfaces.IUserPhoneRPC;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2023-11-02 23:33
 **/
@Service
public class UserLoginServiceImpl implements IUserLoginService {
	Logger logger = LoggerFactory.getLogger(UserLoginServiceImpl.class);
	private static final String PHONE_REG = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$";
	@DubboReference
	private ISmsRpc smsRpc;
	@DubboReference
	private IUserPhoneRPC userPhoneRPC;
	@DubboReference
	private IAccountTokenRPC accountTokenRPC;

	/**
	 * 发送验证码，对外暴露的接口
	 *
	 * @param phone
	 * @return
	 */
	@Override
	public WebResponseVO sendLoginCode(String phone) {
		if (StringUtils.isEmpty(phone)) {
			return WebResponseVO.errorParam("手机号不能为空");
		}
		if (!Pattern.matches(PHONE_REG, phone)) {
			return WebResponseVO.errorParam("手机号格式异常");
		}
		MsgSendResultEnum msgSendResultEnum = smsRpc.sendLoginCode(phone);

		if (msgSendResultEnum == MsgSendResultEnum.SEND_SUCCESS) {
			return WebResponseVO.success();
		}
		return WebResponseVO.sysError("短信发送太频繁，请稍后再试");
	}

	@Override
	public WebResponseVO login(String phone, Integer code, HttpServletResponse response) {

		if (StringUtils.isEmpty(phone)) {
			return WebResponseVO.errorParam("手机号不能为空");
		}
		if (!Pattern.matches(PHONE_REG, phone)) {
			return WebResponseVO.errorParam("手机号格式异常");
		}
		if (code == null || code < 1000) {
			return WebResponseVO.errorParam("验证码格式异常");
		}

		MsgCheckDTO msgCheckDTO = smsRpc.checkLoginCode(phone, code);
		logger.info("check login code result is {}", msgCheckDTO.toString());

		if (!msgCheckDTO.isCheckStatus()) {
			return WebResponseVO.bizError(msgCheckDTO.getDesc());
		}

		//验证码校验通过
		UserLoginDTO userLoginDTO = userPhoneRPC.login(phone);
		logger.info("验证码校验通过，userLoginDTO is {}", userLoginDTO.toString());
		String token = accountTokenRPC.createAndSaveLoginToken(userLoginDTO.getUserId());
		logger.info("token is {}", token);
		Cookie cookie = new Cookie("prayer",token);

		cookie.setDomain("127.0.0.1");
		cookie.setPath("/");
		//cookie有效期，一般他的默认单位是秒
		cookie.setMaxAge(30 * 24 * 3600);
		//加上它，不然web浏览器不会将cookie自动记录下
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.addCookie(cookie);
		return WebResponseVO.success(ConvertBeanUtils.convert(userLoginDTO, UserLoginVO.class));
	}
}

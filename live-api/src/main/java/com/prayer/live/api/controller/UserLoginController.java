package com.prayer.live.api.controller;

import com.prayer.live.api.service.IUserLoginService;
import com.prayer.live.common.interfaces.vo.WebResponseVO;
import com.prayer.live.user.interfaces.IUserPhoneRPC;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2023-11-02 23:32
 **/
@RestController
@RequestMapping("/userLogin")
public class UserLoginController {
	@Resource
	private IUserLoginService IUserLoginService;
	@DubboReference
	private IUserPhoneRPC userPhoneRPC;

	//发送验证码
	@PostMapping("/sendLoginCode")
	public WebResponseVO sendLoginCode(@RequestParam("phone") String phone) {
		System.out.println("start"+ phone);
		return IUserLoginService.sendLoginCode(phone);
	}

	//登录请求 验证码是否合法 -> 初始化注册/老用户登录
	@PostMapping("/login")
	public WebResponseVO login(@RequestParam String phone, @RequestParam Integer code, HttpServletRequest request,  HttpServletResponse response) {
		return IUserLoginService.login(phone,code,request, response);
	}
}

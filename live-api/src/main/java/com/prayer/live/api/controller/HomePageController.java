package com.prayer.live.api.controller;

import com.prayer.live.api.service.IHomePageService;
import com.prayer.live.api.vo.HomePageVO;
import com.prayer.live.common.interfaces.vo.WebResponseVO;
import com.prayer.live.framework.web.starter.context.LiveRequestContext;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-11 16:35
 **/
@RestController
@RequestMapping("/home")
public class HomePageController {
	@Resource
	private IHomePageService homePageService;
	@PostMapping("/initPage")
	public WebResponseVO initPage(){
		Long userId = LiveRequestContext.getUserId();
		HomePageVO homePageVO = new HomePageVO();
		homePageVO.setLoginStatus(false);
		if(userId!=null){
			homePageVO = homePageService.initPage(userId);
			homePageVO.setLoginStatus(true);
		}
		return WebResponseVO.success(homePageVO);
	}
}

package com.prayer.live.api.controller;

import com.prayer.live.api.service.ImService;
import com.prayer.live.api.vo.resp.ImConfigVO;
import com.prayer.live.common.interfaces.vo.WebResponseVO;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-18 17:51
 **/
@RestController
@RequestMapping("/im")
public class ImController {
	@Resource
	private ImService imService;

	@PostMapping("/getImConfig")
	public WebResponseVO getImConfig(){
		ImConfigVO imConfigVO = imService.getImConfig();
		return WebResponseVO.success(imConfigVO);
	}
}


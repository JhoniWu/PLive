package com.prayer.live.api.controller;

import com.prayer.live.api.service.IGiftService;
import com.prayer.live.api.vo.req.GiftReqVO;
import com.prayer.live.api.vo.resp.GiftConfigVO;
import com.prayer.live.common.interfaces.vo.WebResponseVO;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-21 00:39
 **/
@RestController
@RequestMapping("/gift")
public class GiftController {
	@Resource
	private IGiftService giftService;

	@GetMapping("/listGift")
	public WebResponseVO listGift(){
		List<GiftConfigVO> giftConfigVOS = giftService.listGift();
		return WebResponseVO.success(giftConfigVOS);
	}

	@PostMapping("/send")
	public WebResponseVO send(@RequestBody GiftReqVO giftReqVO){
		return WebResponseVO.success(giftService.send(giftReqVO));
	}

}

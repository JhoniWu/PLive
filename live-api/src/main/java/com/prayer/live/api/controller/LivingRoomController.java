package com.prayer.live.api.controller;

import com.prayer.live.api.service.ILivingRoomService;
import com.prayer.live.api.vo.LivingRoomInitVO;
import com.prayer.live.api.vo.req.LivingRoomReqVO;
import com.prayer.live.api.vo.req.OnlinePkReqVO;
import com.prayer.live.common.interfaces.vo.WebResponseVO;
import com.prayer.live.framework.web.starter.config.RequestLimit;
import com.prayer.live.framework.web.starter.context.LiveRequestContext;
import com.prayer.live.framework.web.starter.error.BizBaseErrorEnum;
import com.prayer.live.framework.web.starter.error.ErrorAssert;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-18 17:51
 **/
@RestController
@RequestMapping("/living")
public class LivingRoomController {
	@Resource
	private ILivingRoomService livingRoomService;

	@PostMapping("/list")
	public WebResponseVO list(LivingRoomReqVO livingRoomReqVO){
		if(livingRoomReqVO == null || livingRoomReqVO.getType() == null){
			return WebResponseVO.errorParam("需要给定直播间类型");
		}
		if(livingRoomReqVO.getPage() <= 0 || livingRoomReqVO.getPageSize() > 200){
			return WebResponseVO.errorParam("分页查询参数错误");
		}
		return WebResponseVO.success(livingRoomService.list(livingRoomReqVO));
	}

	@RequestLimit(limit = 1, second = 10, msg = "开播过于频繁，请稍后再尝试")
	@PostMapping("/startLiving")
	public WebResponseVO startLiving(@RequestBody Integer type){
		if(type == null) {
			return WebResponseVO.errorParam("需要给定直播间类型");
		}

		Integer roomId = livingRoomService.startLiving(type);
		LivingRoomInitVO initVO = new LivingRoomInitVO();
		initVO.setRoomId(roomId);
		return WebResponseVO.success(initVO);
	}

	@PostMapping("/closeLiving")
	public WebResponseVO closeLiving(Integer roomId) {
		if (roomId == null) {
			return WebResponseVO.errorParam("需要给定直播间id");
		}
		boolean closeStatus = livingRoomService.closeLiving(roomId);
		if (closeStatus) {
			return WebResponseVO.success();
		}
		return WebResponseVO.bizError("关播异常");
	}

	/**
	 * 获取主播相关配置信息（只有主播才会有权限）
	 *
	 * @return
	 */
	@PostMapping("/anchorConfig")
	public WebResponseVO anchorConfig(Integer roomId) {
		long userId = LiveRequestContext.getUserId();
		return WebResponseVO.success(livingRoomService.anchorConfig(userId, roomId));
	}

	@PostMapping("/onlinePk")
	@RequestLimit(limit = 1,second = 3)
	public WebResponseVO onlinePk(OnlinePkReqVO onlinePkReqVO) {
		ErrorAssert.isNotNull(onlinePkReqVO.getRoomId(), BizBaseErrorEnum.PARAM_ERROR);
		return WebResponseVO.success(livingRoomService.onlinePk(onlinePkReqVO));
	}


}
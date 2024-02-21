package com.prayer.live.api.service.impl;

import com.prayer.live.api.service.ILivingRoomService;
import com.prayer.live.api.vo.LivingRoomInitVO;
import com.prayer.live.api.vo.req.LivingRoomReqVO;
import com.prayer.live.api.vo.req.OnlinePkReqVO;
import com.prayer.live.api.vo.resp.LivingRoomPageRespVO;
import com.prayer.live.api.vo.resp.LivingRoomRespVO;
import com.prayer.live.common.interfaces.dto.PageWrapper;
import com.prayer.live.common.interfaces.utils.ConvertBeanUtils;
import com.prayer.live.framework.web.starter.context.LiveRequestContext;
import com.prayer.live.framework.web.starter.error.ErrorAssert;
import com.prayer.live.framework.web.starter.error.LiveErrorException;
import com.prayer.live.im.constants.AppIdEnum;
import com.prayer.live.living.interfaces.dto.LivingPkRespDTO;
import com.prayer.live.living.interfaces.dto.LivingRoomReqDTO;
import com.prayer.live.living.interfaces.dto.LivingRoomRespDTO;
import com.prayer.live.living.interfaces.rpc.ILivingRoomRpc;
import com.prayer.live.user.dto.UserDTO;
import com.prayer.live.user.interfaces.IUserRpc;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-18 17:07
 **/
@Service
public class LivingRoomServiceImpl implements ILivingRoomService {
	@DubboReference
	private IUserRpc userRpc;
	@DubboReference
	private ILivingRoomRpc livingRoomRpc;
	@Override
	public LivingRoomPageRespVO list(LivingRoomReqVO livingRoomReqVO) {
		PageWrapper<LivingRoomRespDTO>  resultPage = livingRoomRpc.list(ConvertBeanUtils.convert(livingRoomReqVO,LivingRoomReqDTO.class));
		LivingRoomPageRespVO livingRoomPageRespVO = new LivingRoomPageRespVO();
		livingRoomPageRespVO.setList(ConvertBeanUtils.convertList(resultPage.getList(), LivingRoomRespVO.class));
		livingRoomPageRespVO.setHasNext(resultPage.isHasNext());
		return livingRoomPageRespVO;

	}

	@Override
	public Integer startLiving(Integer type) {
		Long userId = LiveRequestContext.getUserId();
		UserDTO userDTO = userRpc.getByUserId(userId);
		LivingRoomReqDTO livingRoomReqDTO = new LivingRoomReqDTO();
		livingRoomReqDTO.setAnchorId(userId);
		livingRoomReqDTO.setRoomName("主播-"+LiveRequestContext.getUserId()+"的直播间");
		livingRoomReqDTO.setCovertImg(userDTO.getAvatar());
		livingRoomReqDTO.setType(type);
		return livingRoomRpc.startLivingRoom(livingRoomReqDTO);
	}

	@Override
	public boolean closeLiving(Integer roomId) {
		LivingRoomReqDTO livingRoomReqDTO = new LivingRoomReqDTO();
		livingRoomReqDTO.setId(roomId);
		livingRoomReqDTO.setAnchorId(LiveRequestContext.getUserId());
		return livingRoomRpc.closeLiving(livingRoomReqDTO);
	}

	@Override
	public LivingRoomInitVO anchorConfig(Long userId, Integer roomId) {
		LivingRoomRespDTO livingRoomReqDTO = livingRoomRpc.queryByRoomId(roomId);
		UserDTO userDTO = userRpc.getByUserId(userId);
		LivingRoomInitVO respVO = new LivingRoomInitVO();
		respVO.setNickName(userDTO.getNickName());
		respVO.setUserId(userId);
		if(livingRoomReqDTO == null || livingRoomReqDTO.getAnchorId() == null || userId == null) {
			respVO.setRoomId(-1);
		} else {
			respVO.setRoomId(livingRoomReqDTO.getId());
			respVO.setAnchorId(livingRoomReqDTO.getAnchorId());
			respVO.setAnchor(livingRoomReqDTO.getAnchorId().equals(userId));
		}
		return respVO;
	}

	@Override
	public boolean onlinePk(OnlinePkReqVO onlinePkReqVO) {
		LivingRoomReqDTO reqDTO = ConvertBeanUtils.convert(onlinePkReqVO, LivingRoomReqDTO.class);
		reqDTO.setAppId(AppIdEnum.LIVE_BIZ.getCode());
		reqDTO.setPkObjId(LiveRequestContext.getUserId());
		LivingPkRespDTO respDTO = livingRoomRpc.onlineOk(reqDTO);
		ErrorAssert.IsTrue(respDTO.isOnlineStatus(), new LiveErrorException(-1, respDTO.getMsg()));
		return true;
	}
}

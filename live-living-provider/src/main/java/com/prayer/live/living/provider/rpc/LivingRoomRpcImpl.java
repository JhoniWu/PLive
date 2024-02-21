package com.prayer.live.living.provider.rpc;

import com.prayer.live.common.interfaces.dto.PageWrapper;
import com.prayer.live.living.interfaces.dto.LivingPkRespDTO;
import com.prayer.live.living.interfaces.dto.LivingRoomReqDTO;
import com.prayer.live.living.interfaces.dto.LivingRoomRespDTO;
import com.prayer.live.living.interfaces.rpc.ILivingRoomRpc;
import com.prayer.live.living.provider.service.LivingRoomService;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-17 22:55
 **/
@DubboService
public class LivingRoomRpcImpl implements ILivingRoomRpc {

	@Resource
	LivingRoomService livingRoomService;

	@Override
	public List<Long> queryUserIdByRoomId(LivingRoomReqDTO livingRoomReqDTO) {
		return livingRoomService.queryUserIdByRoomId(livingRoomReqDTO);
	}

	@Override
	public PageWrapper<LivingRoomRespDTO> list(LivingRoomReqDTO livingRoomReqDTO) {
		return livingRoomService.list(livingRoomReqDTO);
	}

	@Override
	public LivingRoomRespDTO queryByRoomId(Integer roomId) {
		return livingRoomService.queryByRoomId(roomId);
	}

	@Override
	public Integer startLivingRoom(LivingRoomReqDTO livingRoomReqDTO) {
		return livingRoomService.startLivingRoom(livingRoomReqDTO);
	}

	@Override
	public boolean closeLiving(LivingRoomReqDTO livingRoomReqDTO) {
		return livingRoomService.closeLiving(livingRoomReqDTO);
	}

	@Override
	public LivingPkRespDTO onlineOk(LivingRoomReqDTO livingPkRespDTO) {
		return null;
	}

	@Override
	public Long queryOnlinePkUserId(Integer roomId) {
		return null;
	}

	@Override
	public boolean offlinePk(LivingRoomReqDTO livingRoomReqDTO) {
		return false;
	}
}

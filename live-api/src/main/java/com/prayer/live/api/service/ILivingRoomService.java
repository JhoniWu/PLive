package com.prayer.live.api.service;

import com.prayer.live.api.vo.LivingRoomInitVO;
import com.prayer.live.api.vo.req.LivingRoomReqVO;
import com.prayer.live.api.vo.req.OnlinePkReqVO;
import com.prayer.live.api.vo.resp.LivingRoomPageRespVO;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-18 17:07
 **/
public interface ILivingRoomService {
	/**
	 * 直播间列表显示
	 * @param livingRoomReqVO
	 * @return
	 */
	LivingRoomPageRespVO list(LivingRoomReqVO livingRoomReqVO);

	/**
	 * 开始直播
	 * @param type
	 * @return
	 */
	Integer startLiving(Integer type);

	/**
	 * 关闭直播
	 * @param roomId
	 * @return
	 */
	boolean closeLiving(Integer roomId);

	/**
	 * 根据用户Id返回直播间信息
	 * @param userId
	 * @param roomId
	 * @return
	 */
	LivingRoomInitVO anchorConfig(Long userId, Integer roomId);

	/**
	 * 用户在pk直播间中，连上线请求
	 *
	 * @param onlinePkReqVO
	 * @return
	 */
	boolean onlinePk(OnlinePkReqVO onlinePkReqVO);
}

package com.prayer.live.living.provider.service;

import com.prayer.live.common.interfaces.dto.PageWrapper;
import com.prayer.live.im.core.server.interfaces.dto.ImOffLineDTO;
import com.prayer.live.im.core.server.interfaces.dto.ImOnlineDTO;
import com.prayer.live.living.interfaces.dto.LivingPkRespDTO;
import com.prayer.live.living.interfaces.dto.LivingRoomReqDTO;
import com.prayer.live.living.interfaces.dto.LivingRoomRespDTO;

import java.util.List;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-17 22:56
 **/
public interface LivingRoomService {

	/**
	 * 根据roomId查询出用户id
	 *
	 * @param livingRoomReqDTO
	 * @return
	 */
	List<Long> queryUserIdByRoomId(LivingRoomReqDTO livingRoomReqDTO);

	/**
	 *
	 * 用户下线
	 *
	 * @param imOffLineDTO
	 */
	void userOffLineHandler(ImOffLineDTO imOffLineDTO);

	/**
	 * 用户上线
	 *
	 * @param imOnlineDTO
	 */
	void userOnLineHandler(ImOnlineDTO imOnlineDTO);

	/**
	 * 查询所有的直播间类型
	 *
	 * @param type
	 * @return
	 */
	List<LivingRoomRespDTO> listAllLivingRoomFromDB(Integer type);

	/**
	 * 直播间列表的分页查询
	 *
	 * @param livingRoomReqDTO
	 * @return
	 */
	PageWrapper<LivingRoomRespDTO> list(LivingRoomReqDTO livingRoomReqDTO);

	/**
	 * 根据用户id查询是否正在开播
	 *
	 * @param roomId
	 * @return
	 */
	LivingRoomRespDTO queryByRoomId(Integer roomId);

	/**
	 * 开启直播间
	 *
	 * @param livingRoomReqDTO
	 * @return
	 */
	Integer startLivingRoom(LivingRoomReqDTO livingRoomReqDTO);

	/**
	 * 关闭直播间
	 *
	 * @param livingRoomReqDTO
	 * @return
	 */
	boolean closeLiving(LivingRoomReqDTO livingRoomReqDTO);

	Long queryOnlinePkUserId(Integer roomId);

	LivingPkRespDTO onlinePk(LivingRoomReqDTO livingRoomReqDTO);

	boolean offlinePk(LivingRoomReqDTO livingRoomReqDTO);

	boolean uploadAllSkuInfos(LivingRoomReqDTO reqDTO);

}

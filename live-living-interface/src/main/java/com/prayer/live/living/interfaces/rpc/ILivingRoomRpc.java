package com.prayer.live.living.interfaces.rpc;

import com.prayer.live.common.interfaces.dto.PageWrapper;
import com.prayer.live.living.interfaces.dto.LivingPkRespDTO;
import com.prayer.live.living.interfaces.dto.LivingRoomReqDTO;
import com.prayer.live.living.interfaces.dto.LivingRoomRespDTO;

import java.util.List;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-17 22:41
 **/
public interface ILivingRoomRpc {

	/**
	 * 支持根据roomId查询出批量的userId（set）存储，3000个人，元素非常多，O(n)
	 *
	 * @param livingRoomReqDTO
	 * @return
	 */
	List<Long> queryUserIdByRoomId(LivingRoomReqDTO livingRoomReqDTO);

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

	LivingPkRespDTO onlineOk(LivingRoomReqDTO livingPkRespDTO);

	Long queryOnlinePkUserId(Integer roomId);

	boolean offlinePk(LivingRoomReqDTO livingRoomReqDTO);

}

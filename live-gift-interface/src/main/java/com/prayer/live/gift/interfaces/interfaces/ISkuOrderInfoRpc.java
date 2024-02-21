package com.prayer.live.gift.interfaces.interfaces;

import com.prayer.live.gift.interfaces.dto.*;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-02-04 23:29
 **/

public interface ISkuOrderInfoRpc {
	/**
	 * 支持多直播间内用户下单的订单查询
	 *
	 * @param userId
	 * @param roomId
	 */
	SkuOrderInfoRespDTO queryByUserIdAndRoomId(Long userId, Integer roomId);


	/**
	 * 插入一条订单信息
	 *
	 * @param skuOrderInfoReqDTO
	 */
	boolean insertOne(SkuOrderInfoReqDTO skuOrderInfoReqDTO);

	/**
	 * 根据订单id修改状态
	 *
	 * @param reqDTO
	 */
	boolean updateOrderStatus(SkuOrderInfoReqDTO reqDTO);

	/**
	 * 预支付订单生成
	 *
	 * @param prepareOrderReqDTO
	 */
	SkuPrepareOrderInfoDTO prepareOrder(PrepareOrderReqDTO prepareOrderReqDTO);

	/**
	 * 立即支付
	 * @param payNowReqDTO
	 */
	boolean payNow(PayNowReqDTO payNowReqDTO);
}

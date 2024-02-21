package com.prayer.live.gift.provider.service;

import com.prayer.live.gift.interfaces.dto.SkuOrderInfoReqDTO;
import com.prayer.live.gift.interfaces.dto.SkuOrderInfoRespDTO;
import com.prayer.live.gift.provider.dao.po.SkuOrderInfoPO;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-29 21:51
 **/
public interface ISkuOrderInfoService {

	SkuOrderInfoRespDTO queryByUserIdAndRoomId(Long userId, Integer roomId);

	SkuOrderInfoRespDTO queryByOrderId(Long orderId);

	SkuOrderInfoPO insertOne(SkuOrderInfoReqDTO skuOrderInfoReqDTO);

	boolean updateOrderStatus(SkuOrderInfoReqDTO skuOrderInfoReqDTO);

	boolean inValidOldOrder(Long userId);
}

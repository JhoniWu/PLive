package com.prayer.live.api.service;

import com.prayer.live.api.vo.PrepareOrderVO;
import com.prayer.live.api.vo.req.ShopCartReqVO;
import com.prayer.live.api.vo.req.SkuInfoReqVO;
import com.prayer.live.api.vo.resp.ShopCartRespVO;
import com.prayer.live.api.vo.resp.SkuDetailInfoVO;
import com.prayer.live.api.vo.resp.SkuInfoVO;
import com.prayer.live.gift.interfaces.dto.SkuPrepareOrderInfoDTO;

import java.util.List;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-02-22 13:19
 **/
public interface IShopInfoService {
	/**
	 * 根据直播间Id查询商品信息
	 * @param roomId
	 * @return
	 */
	List<SkuInfoVO> queryByRoomId(Integer roomId);

	/**
	 * 查询商品详情
	 * @param skuInfoReqVO
	 * @return
	 */
	SkuDetailInfoVO detail(SkuInfoReqVO skuInfoReqVO);

	Boolean add(ShopCartReqVO shopCartReqVO);

	ShopCartRespVO getCartInfo(ShopCartReqVO shopCartReqVO);

	Boolean removeFromCart(ShopCartReqVO shopCartReqVO);

	Boolean clearCart(ShopCartReqVO shopCartReqVO);

	/**
	 * 预下单接口
	 * @param prepareOrderVO
	 * @return
	 */
	SkuPrepareOrderInfoDTO prepareOrder(PrepareOrderVO prepareOrderVO);

	boolean payNow(PrepareOrderVO prepareOrderVO);

}

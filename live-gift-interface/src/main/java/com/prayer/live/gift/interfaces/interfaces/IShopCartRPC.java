package com.prayer.live.gift.interfaces.interfaces;

import com.prayer.live.gift.interfaces.dto.ShopCartReqDTO;
import com.prayer.live.gift.interfaces.dto.ShopCartRespDTO;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-29 17:40
 **/
public interface IShopCartRPC {
	/**
	 * 查看购物车信息
	 * @param shopCartReqDTO
	 * @return
	 */
	ShopCartRespDTO getCartInfo(ShopCartReqDTO shopCartReqDTO);

	/**
	 * 添加商品到购物车
	 * @param shopCartReqDTO
	 * @return
	 */
	boolean addCart(ShopCartReqDTO shopCartReqDTO);

	/**
	 * 移除购物车
	 * @param shopCartReqDTO
	 * @return
	 */
	boolean removeFromCar(ShopCartReqDTO shopCartReqDTO);

	/**
	 * 清除整个购物车
	 * @param shopCartReqDTO
	 * @return
	 */
	boolean clearShopCart(ShopCartReqDTO shopCartReqDTO);
}

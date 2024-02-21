package com.prayer.live.gift.provider.service;

import com.prayer.live.gift.interfaces.dto.ShopCartReqDTO;
import com.prayer.live.gift.interfaces.dto.ShopCartRespDTO;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-29 18:03
 **/
public interface IShopCartService {
	ShopCartRespDTO getCarInfo(ShopCartReqDTO shopCartReqDTO);

	Boolean addCar(ShopCartReqDTO shopCartReqDTO);

	Boolean removeFromCart(ShopCartReqDTO shopCartReqDTO);

	Boolean clearCart(ShopCartReqDTO shopCartReqDTO);

	Boolean addCartItemNum(ShopCartReqDTO shopCartReqDTO);
}

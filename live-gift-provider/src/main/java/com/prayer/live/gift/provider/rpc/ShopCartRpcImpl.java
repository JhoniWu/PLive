package com.prayer.live.gift.provider.rpc;

import com.prayer.live.gift.interfaces.dto.ShopCartReqDTO;
import com.prayer.live.gift.interfaces.dto.ShopCartRespDTO;
import com.prayer.live.gift.interfaces.interfaces.IShopCartRPC;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-02-19 16:11
 **/
@DubboService
public class ShopCartRpcImpl implements IShopCartRPC {

	@Override
	public ShopCartRespDTO getCartInfo(ShopCartReqDTO shopCartReqDTO) {
		return null;
	}

	@Override
	public boolean addCart(ShopCartReqDTO shopCartReqDTO) {
		return false;
	}

	@Override
	public boolean removeFromCar(ShopCartReqDTO shopCartReqDTO) {
		return false;
	}

	@Override
	public boolean clearShopCart(ShopCartReqDTO shopCartReqDTO) {
		return false;
	}
}

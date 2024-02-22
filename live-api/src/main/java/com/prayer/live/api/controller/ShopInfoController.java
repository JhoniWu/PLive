package com.prayer.live.api.controller;

import com.prayer.live.api.service.IShopInfoService;
import com.prayer.live.api.vo.PrepareOrderVO;
import com.prayer.live.api.vo.req.ShopCartReqVO;
import com.prayer.live.api.vo.req.SkuInfoReqVO;
import com.prayer.live.common.interfaces.vo.WebResponseVO;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-02-22 13:18
 **/
@RestController
@RequestMapping("/shop")
public class ShopInfoController {
	@Resource
	private IShopInfoService shopInfoService;
	@PostMapping("/listSkuInfo")
	public WebResponseVO listSkuInfo(@RequestParam("roomId") Integer roomId){
		return WebResponseVO.success(shopInfoService.queryByRoomId(roomId));
	}

	@PostMapping("/detail")
	public WebResponseVO detail(@RequestBody SkuInfoReqVO reqVO) {
		return WebResponseVO.success(shopInfoService.detail(reqVO));
	}

	@PostMapping("/addCart")
	public WebResponseVO addCar(@RequestBody ShopCartReqVO reqVO) {
		return WebResponseVO.success(shopInfoService.add(reqVO));
	}

	@PostMapping("/removeFromCart")
	public WebResponseVO removeFromCar(@RequestBody ShopCartReqVO reqVO) {
		return WebResponseVO.success(shopInfoService.removeFromCart(reqVO));
	}

	/**
	 * 查看购物车信息
	 */
	@PostMapping("/getCartInfo")
	public WebResponseVO getCarInfo(ShopCartReqVO reqVO) {
		return WebResponseVO.success(shopInfoService.getCartInfo(reqVO));
	}

	/**
	 * 清空购物车信息
	 */
	@PostMapping("/clearCart")
	public WebResponseVO clearCar(ShopCartReqVO reqVO) {
		return WebResponseVO.success(shopInfoService.clearCart(reqVO));
	}

	@PostMapping("/prepareOrder")
	public WebResponseVO prepareOrder(PrepareOrderVO prepareOrderVO) {
		return WebResponseVO.success(shopInfoService.prepareOrder(prepareOrderVO));
	}


	@PostMapping("/payNow")
	public WebResponseVO payNow(PrepareOrderVO prepareOrderVO) {
		return WebResponseVO.success(shopInfoService.payNow(prepareOrderVO));
	}
}


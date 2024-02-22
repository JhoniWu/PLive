package com.prayer.live.api.service.impl;

import com.prayer.live.api.error.ApiErrorEnum;
import com.prayer.live.api.service.IShopInfoService;
import com.prayer.live.api.vo.PrepareOrderVO;
import com.prayer.live.api.vo.req.ShopCartReqVO;
import com.prayer.live.api.vo.req.SkuInfoReqVO;
import com.prayer.live.api.vo.resp.ShopCartRespVO;
import com.prayer.live.api.vo.resp.SkuDetailInfoVO;
import com.prayer.live.api.vo.resp.SkuInfoVO;
import com.prayer.live.common.interfaces.utils.ConvertBeanUtils;
import com.prayer.live.framework.web.starter.context.LiveRequestContext;
import com.prayer.live.framework.web.starter.error.BizBaseErrorEnum;
import com.prayer.live.framework.web.starter.error.ErrorAssert;
import com.prayer.live.gift.interfaces.dto.*;
import com.prayer.live.gift.interfaces.interfaces.IShopCartRPC;
import com.prayer.live.gift.interfaces.interfaces.ISkuInfoRPC;
import com.prayer.live.gift.interfaces.interfaces.ISkuOrderInfoRpc;
import com.prayer.live.living.interfaces.dto.LivingRoomRespDTO;
import com.prayer.live.living.interfaces.rpc.ILivingRoomRpc;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-02-22 13:45
 **/
@Service
public class ShopInfoServiceImpl implements IShopInfoService {
	@DubboReference
	private ILivingRoomRpc livingRoomRpc;
	@DubboReference
	private ISkuInfoRPC skuInfoRPC;
	@DubboReference
	private IShopCartRPC shopCartRPC;
	@DubboReference
	private ISkuOrderInfoRpc skuOrderInfoRpc;

	@Override
	public List<SkuInfoVO> queryByRoomId(Integer roomId) {
		LivingRoomRespDTO respDTO = livingRoomRpc.queryByRoomId(roomId);
		ErrorAssert.isNotNull(respDTO, BizBaseErrorEnum.PARAM_ERROR);
		List<SkuInfoDTO> skuInfoDTOS = skuInfoRPC.queryByAnchorId(respDTO.getAnchorId());
		ErrorAssert.IsTrue(CollectionUtils.isEmpty(skuInfoDTOS), BizBaseErrorEnum.PARAM_ERROR);
		return ConvertBeanUtils.convertList(skuInfoDTOS, SkuInfoVO.class);
	}

	@Override
	public SkuDetailInfoVO detail(SkuInfoReqVO skuInfoReqVO) {
		SkuDetailInfoDTO skuDetailInfoDTO = skuInfoRPC.queryBySkuId(skuInfoReqVO.getSkuId());
		return ConvertBeanUtils.convert(skuDetailInfoDTO, SkuDetailInfoVO.class);
	}

	@Override
	public Boolean add(ShopCartReqVO shopCartReqVO) {
		boolean b = shopCartRPC.addCart(ConvertBeanUtils.convert(shopCartReqVO, ShopCartReqDTO.class));
		return b;
	}

	@Override
	public ShopCartRespVO getCartInfo(ShopCartReqVO shopCartReqVO) {
		ShopCartRespDTO cartInfo = shopCartRPC.getCartInfo(ConvertBeanUtils.convert(shopCartReqVO, ShopCartReqDTO.class));
		cartInfo.setUserId(LiveRequestContext.getUserId());
		return ConvertBeanUtils.convert(cartInfo, ShopCartRespVO.class);
	}

	@Override
	public Boolean removeFromCart(ShopCartReqVO shopCartReqVO) {
		ShopCartReqDTO convert = ConvertBeanUtils.convert(shopCartReqVO, ShopCartReqDTO.class);
		convert.setUserId(LiveRequestContext.getUserId());
		return shopCartRPC.removeFromCar(convert);
	}

	@Override
	public Boolean clearCart(ShopCartReqVO shopCartReqVO) {
		ShopCartReqDTO convert = ConvertBeanUtils.convert(shopCartReqVO, ShopCartReqDTO.class);
		convert.setUserId(LiveRequestContext.getUserId());
		return shopCartRPC.clearShopCart(convert);
	}

	@Override
	public SkuPrepareOrderInfoDTO prepareOrder(PrepareOrderVO prepareOrderVO) {
		PrepareOrderReqDTO orderReqDTO = new PrepareOrderReqDTO();
		orderReqDTO.setUserId(LiveRequestContext.getUserId());
		orderReqDTO.setRoomId(prepareOrderVO.getRoomId());
		SkuPrepareOrderInfoDTO skuPrepareOrderInfoDTO = skuOrderInfoRpc.prepareOrder(orderReqDTO);
		ErrorAssert.isNotNull(skuPrepareOrderInfoDTO, ApiErrorEnum.SKU_IS_NOT_ENOUGH);
		return skuPrepareOrderInfoDTO;
	}

	@Override
	public boolean payNow(PrepareOrderVO prepareOrderVO) {
		prepareOrderVO.setUserId(LiveRequestContext.getUserId());
		boolean b = skuOrderInfoRpc.payNow(ConvertBeanUtils.convert(prepareOrderVO, PayNowReqDTO.class));
		return b;
	}
}

package com.prayer.live.gift.provider.service.impl;

import com.prayer.live.common.interfaces.utils.ConvertBeanUtils;
import com.prayer.live.framework.redis.starter.key.GiftProviderCacheKeyBuilder;
import com.prayer.live.gift.interfaces.dto.ShopCartItemRespDTO;
import com.prayer.live.gift.interfaces.dto.ShopCartReqDTO;
import com.prayer.live.gift.interfaces.dto.ShopCartRespDTO;
import com.prayer.live.gift.interfaces.dto.SkuInfoDTO;
import com.prayer.live.gift.provider.dao.po.SkuInfoPO;
import com.prayer.live.gift.provider.service.IShopCartService;
import com.prayer.live.gift.provider.service.ISkuInfoService;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-29 18:19
 **/
@Service
public class ShopCartServiceImpl implements IShopCartService {

	@Resource
	RedisTemplate<String, Object> redisTemplate;
	@Resource
	private GiftProviderCacheKeyBuilder cacheKeyBuilder;
	@Resource
	private ISkuInfoService skuInfoService;

	@Override
	public ShopCartRespDTO getCarInfo(ShopCartReqDTO shopCartReqDTO) {
		String cacheKey = cacheKeyBuilder.buildUserShopCar(shopCartReqDTO.getUserId(), shopCartReqDTO.getRoomId());
		Cursor<Map.Entry<Object, Object>> allCartData = redisTemplate.opsForHash()
			.scan(cacheKey, ScanOptions.scanOptions().match("*").build());
		List<ShopCartItemRespDTO> shopCartItemRespDTOList = new ArrayList<>();
		Map<Long, Integer> skuCountMap = new HashMap<>();
		while(allCartData.hasNext()){
			Map.Entry<Object, Object> entry = allCartData.next();
			skuCountMap.put((Long) entry.getKey(), (Integer) entry.getValue());
		}
		List<SkuInfoPO> skuInfoPOList = skuInfoService.queryBySkuIds(new ArrayList<>(skuCountMap.keySet()));
		for(SkuInfoPO skuInfoPO : skuInfoPOList){
			SkuInfoDTO skuInfoDTO = ConvertBeanUtils.convert(skuInfoPO, SkuInfoDTO.class);
			Integer count = skuCountMap.get(skuInfoDTO.getSkuId());
			shopCartItemRespDTOList.add(new ShopCartItemRespDTO(count, skuInfoDTO));
		}
		ShopCartRespDTO shopCartRespDTO = new ShopCartRespDTO();
		shopCartRespDTO.setShopCartItemRespDTOList(shopCartItemRespDTOList);
		shopCartRespDTO.setRoomId(shopCartRespDTO.getRoomId());
		shopCartRespDTO.setUserId(shopCartRespDTO.getUserId());
		return shopCartRespDTO;
	}

	@Override
	public Boolean addCar(ShopCartReqDTO shopCartReqDTO) {
		String cacheKey = cacheKeyBuilder.buildUserShopCar(shopCartReqDTO.getUserId(), shopCartReqDTO.getRoomId());
		//一个用户 多个商品
		//读取所有商品的数据
		//每个商品都有数量（目前的业务场景中，没有体现）
		// string （对象，对象里面关联上商品的数据信息）
		// set / list
		// map （k,v） key是skuId，value是商品的数量
		redisTemplate.opsForHash().put(cacheKey, shopCartReqDTO.getSkuId(), 1);
		return true;
	}

	@Override
	public Boolean removeFromCart(ShopCartReqDTO shopCartReqDTO) {
		String cacheKey = cacheKeyBuilder.buildUserShopCar(shopCartReqDTO.getUserId(), shopCartReqDTO.getRoomId());
		redisTemplate.opsForHash().delete(cacheKey, shopCartReqDTO.getSkuId());
		return true;
	}

	@Override
	public Boolean clearCart(ShopCartReqDTO shopCartReqDTO) {
		String cachekey = cacheKeyBuilder.buildUserShopCar(shopCartReqDTO.getUserId(), shopCartReqDTO.getRoomId());
		redisTemplate.opsForHash().delete(cachekey);
		return true;
	}

	@Override
	public Boolean addCartItemNum(ShopCartReqDTO shopCartReqDTO) {
		String cacheKey = cacheKeyBuilder.buildUserShopCar(shopCartReqDTO.getUserId(), shopCartReqDTO.getRoomId());
		redisTemplate.opsForHash().increment(cacheKey, shopCartReqDTO.getSkuId(), 1);
		return true;
	}
}

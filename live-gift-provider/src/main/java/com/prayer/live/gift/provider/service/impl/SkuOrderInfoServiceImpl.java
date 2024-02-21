package com.prayer.live.gift.provider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.prayer.live.bank.interfaces.constants.OrderStatusEnum;
import com.prayer.live.common.interfaces.utils.ConvertBeanUtils;
import com.prayer.live.framework.redis.starter.key.GiftProviderCacheKeyBuilder;
import com.prayer.live.gift.interfaces.dto.SkuOrderInfoReqDTO;
import com.prayer.live.gift.interfaces.dto.SkuOrderInfoRespDTO;
import com.prayer.live.gift.provider.dao.mapper.SkuOrderInfoMapper;
import com.prayer.live.gift.provider.dao.po.SkuOrderInfoPO;
import com.prayer.live.gift.provider.service.IShopCartService;
import com.prayer.live.gift.provider.service.ISkuOrderInfoService;
import com.prayer.live.gift.provider.service.ISkuStockInfoService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-29 21:52
 **/
@Service
public class SkuOrderInfoServiceImpl implements ISkuOrderInfoService {
	@Resource
	private SkuOrderInfoMapper skuOrderInfoMapper;
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	@Autowired
	private GiftProviderCacheKeyBuilder cacheKeyBuilder;
	@Autowired
	private IShopCartService shopCartService;
	@Autowired
	private ISkuStockInfoService skuStockInfoService;
	@Override
	public SkuOrderInfoRespDTO queryByUserIdAndRoomId(Long userId, Integer roomId) {
		String cacheKey = cacheKeyBuilder.buildSkuOrder(userId, roomId);
		Object cacheObj = redisTemplate.opsForValue().get(cacheKey);
		if(cacheObj != null){
			return ConvertBeanUtils.convert(cacheObj, SkuOrderInfoRespDTO.class);
		}

		LambdaQueryWrapper<SkuOrderInfoPO> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(SkuOrderInfoPO::getUserId, userId);
		queryWrapper.eq(SkuOrderInfoPO::getRoomId, roomId);
		queryWrapper.orderByDesc(SkuOrderInfoPO::getId);
		queryWrapper.last("limit 1");
		SkuOrderInfoPO skuOrderInfoPO = skuOrderInfoMapper.selectOne(queryWrapper);
		if(skuOrderInfoPO != null){
			SkuOrderInfoRespDTO skuOrderInfoRespDTO = ConvertBeanUtils.convert(skuOrderInfoPO, SkuOrderInfoRespDTO.class);
			redisTemplate.opsForValue().set(cacheKey, skuOrderInfoRespDTO, 60, TimeUnit.MINUTES );
			return skuOrderInfoRespDTO;
		}
		return null;
	}

	@Override
	public SkuOrderInfoRespDTO queryByOrderId(Long orderId) {
		String cacheKey = cacheKeyBuilder.buildSkuOrderInfo(orderId);
		Object cacheObj = redisTemplate.opsForValue().get(cacheKey);
		if(cacheObj != null) {
			return ConvertBeanUtils.convert(cacheObj, SkuOrderInfoRespDTO.class);
		}
		SkuOrderInfoPO skuOrderInfoPO = skuOrderInfoMapper.selectById(orderId);
		if(skuOrderInfoPO != null){
			SkuOrderInfoRespDTO skuOrderInfoRespDTO = ConvertBeanUtils.convert(skuOrderInfoPO, SkuOrderInfoRespDTO.class);
			redisTemplate.opsForValue().set(cacheKey, skuOrderInfoRespDTO, 60, TimeUnit.MINUTES);
			return skuOrderInfoRespDTO;
		}
		return null;
	}

	@Override
	public SkuOrderInfoPO insertOne(SkuOrderInfoReqDTO skuOrderInfoReqDTO) {
		String skuIdListStr = StringUtils.join(skuOrderInfoReqDTO.getSkuIdList(),",");
		SkuOrderInfoPO skuOrderInfoPO = ConvertBeanUtils.convert(skuOrderInfoReqDTO, SkuOrderInfoPO.class);
		skuOrderInfoPO.setSkuIdList(skuIdListStr);
		skuOrderInfoMapper.insert(skuOrderInfoPO);
		return skuOrderInfoPO;
	}

	@Override
	public boolean updateOrderStatus(SkuOrderInfoReqDTO reqDTO) {
		SkuOrderInfoPO skuOrderInfoPO = new SkuOrderInfoPO();
		skuOrderInfoPO.setStatus(reqDTO.getStatus());
		skuOrderInfoPO.setId(reqDTO.getId());
		skuOrderInfoMapper.updateById(skuOrderInfoPO);
		String cacheKey = cacheKeyBuilder.buildSkuOrder(reqDTO.getUserId(), reqDTO.getRoomId());
		redisTemplate.delete(cacheKey);
		return true;
	}


	@Override
	public boolean inValidOldOrder(Long userId) {
		LambdaQueryWrapper<SkuOrderInfoPO> qw = new LambdaQueryWrapper<>();
		qw.eq(SkuOrderInfoPO::getUserId, userId);
		qw.eq(SkuOrderInfoPO::getStatus, OrderStatusEnum.WAITING_PAY.getCode());
		skuOrderInfoMapper.delete(qw);
		return true;
	}
}

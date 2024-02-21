package com.prayer.live.gift.provider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.prayer.live.common.interfaces.enums.CommonStatusEnum;
import com.prayer.live.framework.redis.starter.key.GiftProviderCacheKeyBuilder;
import com.prayer.live.gift.provider.dao.mapper.AnchorShopInfoMapper;
import com.prayer.live.gift.provider.dao.mapper.SkuInfoMapper;
import com.prayer.live.gift.provider.dao.po.AnchorShopInfoPO;
import com.prayer.live.gift.provider.dao.po.SkuInfoPO;
import com.prayer.live.gift.provider.service.ISkuInfoService;
import jakarta.annotation.Resource;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-29 19:24
 **/
@Service
public class SkuInfoServiceImpl implements ISkuInfoService {
	@Resource
	private SkuInfoMapper skuInfoMapper;
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	@Resource
	private GiftProviderCacheKeyBuilder cacheKeyBuilder;
	@Resource
	private AnchorShopInfoMapper anchorShopInfoMapper;
	@Resource
	private RedissonClient redissonClient;

	@Override
	public List<SkuInfoPO> queryBySkuIds(ArrayList<Long> skuIdList) {
		LambdaQueryWrapper<SkuInfoPO> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.in(SkuInfoPO::getSkuId, skuIdList);
		queryWrapper.eq(SkuInfoPO::getStatus, CommonStatusEnum.VALID_STATUS.getCode());
		return skuInfoMapper.selectList(queryWrapper);
	}

	@Override
	public SkuInfoPO queryBySkuId(Long skuId) {
		LambdaQueryWrapper<SkuInfoPO> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(SkuInfoPO::getSkuId, skuId);
		queryWrapper.eq(SkuInfoPO::getStatus, CommonStatusEnum.VALID_STATUS.getCode());
		return skuInfoMapper.selectOne(queryWrapper);
	}

	@Override
	public SkuInfoPO queryBySkuIdFromCache(Long skuId) {
		String detailKey = cacheKeyBuilder.buildSkuDetail(skuId);
		Object skuInfoCacheObj = redisTemplate.opsForValue().get(detailKey);
		if(skuInfoCacheObj != null){
			return (SkuInfoPO) skuInfoCacheObj;
		}
		SkuInfoPO skuInfoPO = this.queryBySkuId(skuId);
		if(skuInfoPO == null) {
			return null;
		}
		redisTemplate.opsForValue().set(detailKey, skuInfoPO, 1, TimeUnit.DAYS);
		return skuInfoPO;
	}

	@Override
	public boolean uploadSkuInfosToCache(Long anchorId) {
		List<Long> skuIdList = queryAnchorsSkuInfos(anchorId);
		for(Long skuId : skuIdList){
			QueryWrapper<SkuInfoPO> skuqw = new QueryWrapper<>();
			skuqw.eq("sku_id", skuId);
			SkuInfoPO skuInfoPO = skuInfoMapper.selectOne(skuqw);
			String s = cacheKeyBuilder.buildSkuDetail(skuId);
			redisTemplate.opsForValue().getAndDelete(s);
			redisTemplate.opsForValue().set(s, skuInfoPO);
		}
		return true;
	}

	public List<Long> queryAnchorsSkuInfos(Long anchorId){
		QueryWrapper<AnchorShopInfoPO> qw = new QueryWrapper<>();
		qw.eq("anchor_id", anchorId);
		List<AnchorShopInfoPO> anchorShopInfoPOS = anchorShopInfoMapper.selectList(qw);
		List<Long> skuIdList = anchorShopInfoPOS.stream().map(AnchorShopInfoPO::getSkuId).collect(Collectors.toList());
		return skuIdList;
	}

	@Override
	public boolean removeSkuInfos(Long anchorId) {
		List<Long> skuIdList = queryAnchorsSkuInfos(anchorId);
		CountDownLatch latch = new CountDownLatch(skuIdList.size());
		for(Long skuId : skuIdList) {
			Thread th = new Thread(() -> {
				String skuKey = cacheKeyBuilder.buildSkuSemaphore(skuId);
				redisTemplate.delete(skuKey);
				latch.countDown();
			});
		}
		try {
			latch.await();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		return true;
	}
}

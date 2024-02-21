package com.prayer.live.gift.provider.rpc;

import com.prayer.live.framework.redis.starter.key.GiftProviderCacheKeyBuilder;
import com.prayer.live.gift.interfaces.interfaces.ISkuStockInfoRpc;
import com.prayer.live.gift.provider.dao.po.SkuStockInfoPO;
import com.prayer.live.gift.provider.service.IAnchorShopInfoService;
import com.prayer.live.gift.provider.service.ISkuStockInfoService;
import com.prayer.live.gift.provider.service.bo.DecrStockNumBO;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-02-19 16:12
 **/
@DubboService
public class SkuStockInfoRpcImpl implements ISkuStockInfoRpc {
	@Resource
	private ISkuStockInfoService skuStockInfoService;

	@Resource
	private IAnchorShopInfoService iAnchorShopInfoService;

	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	@Resource
	private GiftProviderCacheKeyBuilder cacheKeyBuilder;

	private final int MAX_TRY_TIMES = 5;

	@Override
	public boolean decrStockNumBySkuId(Long skuId, Integer num) {
		for(int i = 0; i < MAX_TRY_TIMES; i++){
			DecrStockNumBO decrStockNumBO = skuStockInfoService.dcrStockNumBySkuId(skuId, num);
			if(decrStockNumBO.isNoStock()){
				return false;
			} else if(decrStockNumBO.isSuccess()){
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean decrStockNumBySkuIdV2(Long skuId, Integer num) {
		return skuStockInfoService.decrStockNumBySkuIdV2(skuId, num);
	}

	@Override
	public boolean prepareStockInfoFromMysql(Long anchorId) {
		List<Long> skuIdList = iAnchorShopInfoService.querySkuIdByAnchorId(anchorId);
		List<SkuStockInfoPO> skuStockInfoPOList = skuStockInfoService.queryBySkuIds(skuIdList);
		Map<String, Integer> saveCacheMap = skuStockInfoPOList.stream().collect(Collectors.toMap(skuStockInfoPO -> cacheKeyBuilder.buildSkuStock(skuStockInfoPO.getSkuId()), x -> x.getStockNum()));
		redisTemplate.opsForValue().multiSet(saveCacheMap);
		//对命令执行批量过期设置操作
		redisTemplate.executePipelined(new SessionCallback<Object>() {
			@Override
			public <K, V> Object execute(RedisOperations<K, V> operations) throws DataAccessException {
				for(String rediskey : saveCacheMap.keySet()){
					operations.expire((K) rediskey, 1, TimeUnit.DAYS);
				}
				return null;
			}
		});
		return true;
	}

	@Override
	public Integer queryStockNum(Long skuId) {
		String cacheKey = cacheKeyBuilder.buildSkuStock(skuId);
		Object stockNumObj = redisTemplate.opsForValue().get(cacheKey);
		return stockNumObj == null ? null : (Integer) stockNumObj;
	}

	@Override
	public boolean syncStockNumToMySQL(Long anchorId) {
		List<Long> skuIdList = iAnchorShopInfoService.querySkuIdByAnchorId(anchorId);
		for (Long skuId : skuIdList) {
			Integer stockNum = this.queryStockNum(skuId);
			if (stockNum != null) {
				skuStockInfoService.updateStockNum(skuId, stockNum);
			}
		}
		return true;
	}
}

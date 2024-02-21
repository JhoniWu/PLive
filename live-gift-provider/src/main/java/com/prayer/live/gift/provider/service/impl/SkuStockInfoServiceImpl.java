package com.prayer.live.gift.provider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.prayer.live.common.interfaces.enums.CommonStatusEnum;
import com.prayer.live.framework.redis.starter.key.GiftProviderCacheKeyBuilder;
import com.prayer.live.gift.interfaces.constants.SkuOrderInfoEnum;
import com.prayer.live.gift.interfaces.dto.RollBackStockDTO;
import com.prayer.live.gift.interfaces.dto.SkuOrderInfoReqDTO;
import com.prayer.live.gift.interfaces.dto.SkuOrderInfoRespDTO;
import com.prayer.live.gift.provider.dao.mapper.SkuStockInfoMapper;
import com.prayer.live.gift.provider.dao.po.SkuStockInfoPO;
import com.prayer.live.gift.provider.service.IAnchorShopInfoService;
import com.prayer.live.gift.provider.service.ISkuInfoService;
import com.prayer.live.gift.provider.service.ISkuOrderInfoService;
import com.prayer.live.gift.provider.service.ISkuStockInfoService;
import com.prayer.live.gift.provider.service.bo.DecrStockNumBO;
import jakarta.annotation.Resource;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-29 22:13
 **/
@Service
public class SkuStockInfoServiceImpl implements ISkuStockInfoService {

	@Resource
	private SkuStockInfoMapper skuStockInfoMapper;
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	@Resource
	private GiftProviderCacheKeyBuilder giftProviderCacheKeyBuilder;
	@Autowired
	private ISkuOrderInfoService skuOrderInfoService;
	@Autowired
	private RedissonClient redissonClient;
	@Autowired
	private IAnchorShopInfoService anchorShopInfoService;
	@Autowired
	private ISkuInfoService skuInfoService;
	private final String LUA_SCRIPT =
		"if (redis.call('exists', KEYS[1])) == 1 then " +
			" local currentStock=redis.call('get',KEYS[1]) " +
			"  if (tonumber(currentStock)>0 and tonumber(currentStock)-tonumber(ARGV[1])>=0)  then " +
			"      return redis.call('decrby',KEYS[1],tonumber(ARGV[1])) " +
			"   else return -1 end " +
			"else " +
			"return -1 end";

	private final String BATCH_LUA_SCRIPT ="for  i=1,ARGV[2] do  \n" +
		"    if (redis.call('exists', KEYS[i]))~= 1 then return -1 end\n" +
		"\tlocal currentStock=redis.call('get',KEYS[i])  \n" +
		"\tif (tonumber(currentStock)<=0 and tonumber(currentStock)-tonumber(ARGV[1])<0) then\n" +
		"        return -1\n" +
		"\tend\n" +
		"end  \n" +
		"\n" +
		"for  j=1,ARGV[2] do \n" +
		"\tredis.call('decrby',KEYS[j],tonumber(ARGV[1]))\n" +
		"end  \n" +
		"return 1";

	@Override
	public boolean updateStockNum(Long skuId, Integer num) {
		SkuStockInfoPO skuStockInfoPO = new SkuStockInfoPO();
		skuStockInfoPO.setStockNum(num);
		LambdaQueryWrapper<SkuStockInfoPO> qw = new LambdaQueryWrapper<>();
		qw.eq(SkuStockInfoPO::getSkuId, skuId);
		skuStockInfoMapper.update(skuStockInfoPO,qw);
		return true;
	}

	@Override
	public DecrStockNumBO decrStockNumBySkuId(Long skuId, Integer num) {
		SkuStockInfoPO skuStockInfoPO = queryBySkuId(skuId);
		DecrStockNumBO decrStockNumBO = new DecrStockNumBO();
		if(skuStockInfoPO.getStockNum() == 0 || skuStockInfoPO.getStockNum() - num < 0){
			decrStockNumBO.setNoStock(true);
			decrStockNumBO.setSuccess(false);
			return decrStockNumBO;
		}
		decrStockNumBO.setNoStock(false);
		boolean updateState = skuStockInfoMapper.decrStockNumBySkuId(skuId, num, skuStockInfoPO.getVersion()) > 0;
		decrStockNumBO.setSuccess(updateState);
		return decrStockNumBO;
	}

	@Override
	public boolean decrStockNumBySkuIdV2(Long skuId, Integer num) {
		//直接使用redis命令操作的话，可能会有多元请求，用lua方案去替代进行改良
		//根据skuId查询库存信息，从缓存好的redis中去取库存信息 网络请求
		//判断：sku库存值>0，sku库存值-num>0，（其他线程 也在这么操作）
		//扣减 decrby 网络请求 导致超卖
		DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
		redisScript.setScriptText(LUA_SCRIPT);
		redisScript.setResultType(Long.class);
		String skuStockCacheKey = giftProviderCacheKeyBuilder.buildSkuStock(skuId);
		return redisTemplate.execute(redisScript, Collections.singletonList(skuStockCacheKey), num) >= 0;
	}

	@Override
	public SkuStockInfoPO queryBySkuId(Long skuId) {
		LambdaQueryWrapper<SkuStockInfoPO> qw = new LambdaQueryWrapper<>();
		qw.eq(SkuStockInfoPO::getSkuId, skuId);
		qw.eq(SkuStockInfoPO::getStatus, CommonStatusEnum.VALID_STATUS.getCode());
		qw.last("limit 1");
		return skuStockInfoMapper.selectOne(qw);
	}

	@Override
	public List<SkuStockInfoPO> queryBySkuIds(List<Long> skuIdList) {
		return null;
	}

	@Override
	public void stockRollBackHandler(RollBackStockDTO rollBackStockDTO) {
		SkuOrderInfoRespDTO orderInfoRespDTO = skuOrderInfoService.queryByOrderId(rollBackStockDTO.getOrderId());
		if(orderInfoRespDTO == null || SkuOrderInfoEnum.HAS_PAY.getCode().equals(orderInfoRespDTO.getStatus())){
			//支付成功 直接返回
			return ;
		}
		SkuOrderInfoReqDTO reqDTO = new SkuOrderInfoReqDTO();
		reqDTO.setStatus(SkuOrderInfoEnum.END.getCode());
		reqDTO.setRoomId(orderInfoRespDTO.getRoomId());
		reqDTO.setUserId(orderInfoRespDTO.getUserId());
		reqDTO.setId(orderInfoRespDTO.getId());
		skuOrderInfoService.updateOrderStatus(reqDTO);
		//库存回滚
		List<Long> skuIdList = Arrays.asList(orderInfoRespDTO.getSkuIdList().split(",")).stream().map(x -> Long.valueOf(x)).collect(Collectors.toList());
		skuIdList.parallelStream().forEach(skuId -> {
			String skuStockCacheKeyu = giftProviderCacheKeyBuilder.buildSkuStock(skuId);
			redisTemplate.opsForValue().increment(skuStockCacheKeyu, 1);
		});

	}

	@Override
	public boolean decrStockNumBySkuIdV3(List<Long> skuIdList, int num) {
		DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
		redisScript.setScriptText(BATCH_LUA_SCRIPT);
		redisScript.setResultType(Long.class);
		List<String> skuStockCacheKeyList = new ArrayList<>();
		for(Long skuId : skuIdList){
			String skuStockCachekey = giftProviderCacheKeyBuilder.buildSkuStock(skuId);
			skuStockCacheKeyList.add(skuStockCachekey);
		}
		return redisTemplate.execute(redisScript, skuStockCacheKeyList, num, skuStockCacheKeyList.size()) >=0;
	}
}

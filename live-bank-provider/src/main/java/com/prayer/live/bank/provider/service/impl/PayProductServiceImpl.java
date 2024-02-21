package com.prayer.live.bank.provider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.prayer.live.bank.interfaces.dto.PayProductDTO;
import com.prayer.live.bank.provider.dao.mapper.PayProductMapper;
import com.prayer.live.bank.provider.dao.po.PayProductPO;
import com.prayer.live.bank.provider.service.PayProductService;
import com.prayer.live.common.interfaces.enums.CommonStatusEnum;
import com.prayer.live.common.interfaces.utils.ConvertBeanUtils;
import com.prayer.live.framework.redis.starter.key.BankProviderCacheKeyBuilder;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-21 18:43
 **/
public class PayProductServiceImpl implements PayProductService {

	@Resource
	private PayProductMapper payProductMapper;
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	@Resource
	private BankProviderCacheKeyBuilder cacheKeyBuilder;
	@Override
	public List<PayProductDTO> products(Integer type) {
		String cacheKey = cacheKeyBuilder.buildPayProductCache(type);
		List<PayProductDTO> cacheList = redisTemplate.opsForList().range(cacheKey, 0,30).stream().map(x -> (PayProductDTO) x).collect(Collectors.toList());
		if(!CollectionUtils.isEmpty(cacheList)){
			if(cacheList.get(0) == null){
				return Collections.emptyList();
			}
			return cacheList;
		}

		LambdaQueryWrapper<PayProductPO> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(PayProductPO::getType, type);
		queryWrapper.eq(PayProductPO::getValidStatus, CommonStatusEnum.VALID_STATUS.getCode());
		queryWrapper.orderByDesc(PayProductPO::getPrice);
		List<PayProductDTO> payProductDTOS = ConvertBeanUtils.convertList(payProductMapper.selectList(queryWrapper), PayProductDTO.class);
		if (CollectionUtils.isEmpty(payProductDTOS)) {
			redisTemplate.opsForList().leftPush(cacheKey, new PayProductDTO());
			redisTemplate.expire(cacheKey, 3, TimeUnit.MINUTES);
			return Collections.emptyList();
		}
		redisTemplate.opsForList().leftPushAll(cacheKey, payProductDTOS.toArray());
		redisTemplate.expire(cacheKey, 30, TimeUnit.MINUTES);
		return payProductDTOS;
	}

	@Override
	public PayProductDTO getByProductId(Integer productId) {
		String cacheKey = cacheKeyBuilder.buildPayProductItemCache(productId);
		PayProductDTO payProductDTO = (PayProductDTO) redisTemplate.opsForValue().get(cacheKey);
		if(payProductDTO != null){
			if(payProductDTO.getId() == null){
				return null;
			}
			return payProductDTO;
		}
		PayProductPO payProductPO = payProductMapper.selectById(productId);
		if(payProductPO != null){
			PayProductDTO resultItem = ConvertBeanUtils.convert(payProductPO, PayProductDTO.class);
			redisTemplate.opsForValue().set(cacheKey, resultItem, 30, TimeUnit.MINUTES);
			return resultItem;
		}
		//空值缓存
		redisTemplate.opsForValue().set(cacheKey, new PayProductDTO(), 5, TimeUnit.MINUTES);
		return null;
	}
}

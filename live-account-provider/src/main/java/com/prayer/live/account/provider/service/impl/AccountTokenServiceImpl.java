package com.prayer.live.account.provider.service.impl;

import com.prayer.live.account.provider.service.AccountTokenService;
import com.prayer.live.framework.redis.starter.key.AccountProviderCacheKeyBuilder;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-10 00:48
 **/
@Service
public class AccountTokenServiceImpl implements AccountTokenService {
	@Resource
	RedisTemplate<String ,Object> redisTemplate;
	@Resource
	private AccountProviderCacheKeyBuilder cacheKeyBuilder;


	@Override
	public String createAndSaveLoginToken(Long userId) {
		String token = UUID.randomUUID().toString();
		redisTemplate.opsForValue().set(cacheKeyBuilder.buildUserLoginTokenKey(token), String.valueOf(userId), 15, TimeUnit.DAYS);
		return token;
	}

	@Override
	public Long getUserIdByToken(String tokenKey) {
		String redisKey = cacheKeyBuilder.buildUserLoginTokenKey(tokenKey);
		Long id = (Long) redisTemplate.opsForValue().get(redisKey);
		return id == null ? null : id;
	}
}

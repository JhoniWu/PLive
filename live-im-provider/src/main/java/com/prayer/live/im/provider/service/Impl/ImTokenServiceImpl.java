package com.prayer.live.im.provider.service.Impl;

import com.prayer.live.framework.redis.starter.key.ImProviderCacheKeyBuilder;
import com.prayer.live.im.provider.service.ImTokenService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-12 17:51
 **/
@Service
public class ImTokenServiceImpl implements ImTokenService {
	Logger logger = LoggerFactory.getLogger(ImTokenServiceImpl.class);
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	@Resource
	private ImProviderCacheKeyBuilder cacheKeyBuilder;

	@Override
	public String createImLoginToken(long userId, int appId) {
		logger.info("createImLoginToken");
		String token = UUID.randomUUID() + "%" + appId;
		redisTemplate.opsForValue().set(cacheKeyBuilder.buildImLoginTokenKey(token), userId, 5, TimeUnit.MINUTES);
		return token;
	}

	@Override
	public Long getUserIdByToken(String token) {
		logger.info("getUserIdByToken");
		Object userId = redisTemplate.opsForValue().get(cacheKeyBuilder.buildImLoginTokenKey(token));
		return userId == null ? null : Long.valueOf((Integer) userId);
	}
}

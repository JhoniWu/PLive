package com.prayer.live.im.provider.service.Impl;

import com.prayer.live.im.core.server.interfaces.constants.ImCoreServerConstants;
import com.prayer.live.im.provider.service.ImOnlineService;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-16 20:57
 **/
@Service
public class ImOnlineServiceImpl implements ImOnlineService {
	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	@Override
	public boolean isOnline(long userId, int appId) {
		return redisTemplate.hasKey(ImCoreServerConstants.IM_BIND_IP_KEY+ appId + ":" + userId);
	}
}

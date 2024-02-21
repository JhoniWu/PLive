package com.prayer.live.framework.redis.starter.key;

import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-12 16:32
 **/
@Configuration
@Conditional(RedisKeyLoadMatch.class)
public class ImCoreServerProviderCacheKeyBuilder extends RedisKeyBuilder{
	private static final String IM_ONLINE_ZSET = "imOnlineZset";
	private static final String IM_ACK_MAP = "imAckMap";
	/**
	 * 根据userId取模，分组存储User在线token
	 * live-xx-xx:imOnlineZset:appId:9865
	 *
	 * @param userId
	 * @param appId
	 * @return
	 */
	public String buildImOnLineKey(Long userId, Integer appId){
		return super.getPrefix() + IM_ONLINE_ZSET + super.getSplitItem() + appId + super.getSplitItem() + userId % 10000;
	}

	/**
	 * live-xx-xx:imAckMap:appId:8
	 *
	 * @param userId
	 * @param appId
	 * @return
	 */
	public String buildImAckMapKey(Long userId, Integer appId){
		return super.getPrefix() + IM_ACK_MAP + super.getSplitItem() + appId + super.getSplitItem() + userId % 10;
	}

}

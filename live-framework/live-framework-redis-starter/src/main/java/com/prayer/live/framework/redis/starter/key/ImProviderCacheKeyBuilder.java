package com.prayer.live.framework.redis.starter.key;

import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-12 16:35
 **/
@Configuration
@Conditional(RedisKeyLoadMatch.class)
public class ImProviderCacheKeyBuilder extends RedisKeyBuilder{
	private static final String IM_LOGIN_TOKEN = "imLoginToken";
	//live-xx-xx:imLoginToken:token
	public String buildImLoginTokenKey(String token){
		return super.getPrefix()+IM_LOGIN_TOKEN + super.getSplitItem()+token;
	}
}

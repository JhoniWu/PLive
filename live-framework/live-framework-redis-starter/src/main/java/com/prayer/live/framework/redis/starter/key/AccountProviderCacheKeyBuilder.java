package com.prayer.live.framework.redis.starter.key;

import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-10 00:56
 **/
@Configuration
@Conditional(RedisKeyLoadMatch.class)
public class AccountProviderCacheKeyBuilder extends RedisKeyBuilder{
	private static final String ACCOUNT_TOKEN_KEY = "account";

	public String buildUserLoginTokenKey(String key){
		return super.getPrefix() + ACCOUNT_TOKEN_KEY + super.getSplitItem() + key;
	}
}


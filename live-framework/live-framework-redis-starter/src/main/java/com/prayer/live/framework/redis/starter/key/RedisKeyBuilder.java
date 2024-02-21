package com.prayer.live.framework.redis.starter.key;

import org.springframework.beans.factory.annotation.Value;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2023-10-27 18:52
 **/
public class RedisKeyBuilder {

	@Value("${spring.application.name}")
	private String applicationName;
	private static final String SPLIT_ITEM = ":";

	public String getSplitItem() {
		return SPLIT_ITEM;
	}

	public String getPrefix() {
		return applicationName + SPLIT_ITEM;
	}
}

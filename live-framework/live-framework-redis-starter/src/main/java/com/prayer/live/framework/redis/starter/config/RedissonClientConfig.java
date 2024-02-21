package com.prayer.live.framework.redis.starter.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-02-16 22:22
 **/
@Configuration
public class RedissonClientConfig {
	@Value("${spring.redis.host}")
	private String host;
	@Value("${spring.redis.port}")
	private String port;
	@Bean
	public RedissonClient getRedisson(){
		Config config = new Config();
		SingleServerConfig singleServerConfig = config.useSingleServer();
		singleServerConfig.setAddress("redis://" + host + ":" + port);
		return Redisson.create(config);
	}
}

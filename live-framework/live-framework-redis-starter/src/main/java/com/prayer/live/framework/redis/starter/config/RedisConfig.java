package com.prayer.live.framework.redis.starter.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2023-10-26 19:11
 **/
@Configuration
@ConditionalOnClass(RedisTemplate.class)
public class RedisConfig {
	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		IGenericJackson2JsonRedisSerializer valueSerializer = new IGenericJackson2JsonRedisSerializer();
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		redisTemplate.setKeySerializer(stringRedisSerializer);
		redisTemplate.setValueSerializer(valueSerializer);
		redisTemplate.setHashKeySerializer(stringRedisSerializer);
		redisTemplate.setHashValueSerializer(valueSerializer);
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}
}

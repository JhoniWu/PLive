package com.prayer.live.framework.web.starter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-02-22 14:44
 **/
@Configuration
public class HttpSessionConfig implements BeanClassLoaderAware {

	private ClassLoader classLoader;

	@Bean
	public RedisSerializer<Object> springSessionDefaultRedisSerializer(){
		return new GenericJackson2JsonRedisSerializer(new ObjectMapper());
	}


	@Override
	public void setBeanClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}
}

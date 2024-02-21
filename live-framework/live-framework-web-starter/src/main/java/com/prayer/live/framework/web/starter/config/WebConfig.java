package com.prayer.live.framework.web.starter.config;

import com.prayer.live.framework.web.starter.context.LiveUserInfoInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2023-11-23 21:04
 **/
@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Bean
	public LiveUserInfoInterceptor userInfoInterceptor(){
		return new LiveUserInfoInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(userInfoInterceptor()).addPathPatterns("/**").excludePathPatterns("/error");
	}
}

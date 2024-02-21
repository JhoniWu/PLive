package com.prayer.live.living.provider.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-17 22:58
 **/
@Configuration
public class MyBatisPageConfig {
	@Bean
	public PaginationInnerInterceptor paginationInnerInterceptor(){
		PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
		paginationInnerInterceptor.setMaxLimit(1000L);
		paginationInnerInterceptor.setDbType(DbType.MYSQL);
		paginationInnerInterceptor.setOptimizeJoin(true);
		return paginationInnerInterceptor;
	}

	@Bean
	public MybatisPlusInterceptor mybatisPlusInterceptor(){
		MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
		mybatisPlusInterceptor.setInterceptors(Collections.singletonList(paginationInnerInterceptor()));
		return mybatisPlusInterceptor;
	}
}

package com.prayer.live.im.router.provider;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-16 18:54
 **/
@SpringBootApplication
@EnableDubbo
public class ImRouterProviderApplication {
	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(ImRouterProviderApplication.class);
		springApplication.setWebApplicationType(WebApplicationType.NONE);
		springApplication.run(args);
	}
}

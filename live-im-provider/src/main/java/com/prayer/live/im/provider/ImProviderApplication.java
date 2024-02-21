package com.prayer.live.im.provider;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-12 17:49
 **/
@SpringBootApplication
@EnableDubbo
public class ImProviderApplication {
	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(ImProviderApplication.class);
		springApplication.setWebApplicationType(WebApplicationType.NONE);
		springApplication.run(args);
	}
}
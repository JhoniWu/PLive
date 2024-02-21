package com.prayer.live.id.generate;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2023-10-29 14:36
 **/
@SpringBootApplication
@EnableDiscoveryClient
@EnableDubbo
public class IdGenerateApplication {
	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(IdGenerateApplication.class);
		springApplication.setWebApplicationType(WebApplicationType.NONE);
		springApplication.run(args);
	}
}
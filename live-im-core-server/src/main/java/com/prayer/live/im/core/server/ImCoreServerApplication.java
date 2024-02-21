package com.prayer.live.im.core.server;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-12 15:58
 **/
@SpringBootApplication
@EnableDubbo
public class ImCoreServerApplication {
	public static void main(String[] args) throws InterruptedException {
		SpringApplication springApplication = new SpringApplication(ImCoreServerApplication.class);
		springApplication.setWebApplicationType(WebApplicationType.NONE);
		springApplication.run(args);
	}
}

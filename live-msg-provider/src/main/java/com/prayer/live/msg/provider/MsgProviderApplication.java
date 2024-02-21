package com.prayer.live.msg.provider;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2023-11-02 23:44
 **/
@SpringBootApplication
@EnableDiscoveryClient
@EnableDubbo
public class MsgProviderApplication {
	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(MsgProviderApplication.class);
		springApplication.setWebApplicationType(WebApplicationType.NONE);
		springApplication.run(args);
	}
}

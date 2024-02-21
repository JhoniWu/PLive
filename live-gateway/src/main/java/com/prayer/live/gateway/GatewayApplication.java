package com.prayer.live.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-10 01:23
 **/
@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {
	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(GatewayApplication.class);
		springApplication.setWebApplicationType(WebApplicationType.REACTIVE);
		springApplication.run(args);
	}
}

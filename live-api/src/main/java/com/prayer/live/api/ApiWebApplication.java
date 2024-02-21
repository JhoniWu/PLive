package com.prayer.live.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2023-10-23 15:03
 **/
@SpringBootApplication
@EnableDiscoveryClient
public class ApiWebApplication {
	@Bean
	RestTemplate restTemplate(){
		return new RestTemplate();
	}
	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(ApiWebApplication.class);
		springApplication.setWebApplicationType(WebApplicationType.SERVLET);
		springApplication.run(args);
	}
}

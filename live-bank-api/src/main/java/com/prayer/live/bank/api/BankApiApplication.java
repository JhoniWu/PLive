package com.prayer.live.bank.api;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-02-16 20:15
 **/
@SpringBootApplication
@EnableDubbo
public class BankApiApplication {
	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(BankApiApplication.class);
		springApplication.setWebApplicationType(WebApplicationType.SERVLET);
		springApplication.run(args);
	}
}

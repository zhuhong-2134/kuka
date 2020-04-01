package com.camelot.kuka.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 认证中心
 *
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class OAuthCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(OAuthCenterApplication.class, args);
	}

}
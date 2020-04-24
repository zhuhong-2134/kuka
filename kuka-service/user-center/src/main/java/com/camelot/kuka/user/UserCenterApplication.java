package com.camelot.kuka.user;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * 用户中心
 *
 *
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@EnableCreateCacheAnnotation
@EnableMethodCache(basePackages = { "com.camelot.kuka.user" })
@ComponentScan({"com.camelot.kuka.common","com.camelot.kuka.user"})
public class UserCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserCenterApplication.class, args);
	}

}
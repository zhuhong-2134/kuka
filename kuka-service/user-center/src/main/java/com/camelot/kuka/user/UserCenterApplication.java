package com.camelot.kuka.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 用户中心
 * 
 *    cuichunsong@camelotchina.com
 *
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan({"com.camelot.kuka.common","com.camelot.kuka.user"})
public class UserCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserCenterApplication.class, args);
	}

}
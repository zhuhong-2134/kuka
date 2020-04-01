package com.camelot.kuka.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * 管理后台
 *
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan({"com.camelot.kuka.common","com.camelot.kuka.backend"})
public class ManageBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManageBackendApplication.class, args);
	}

}
package com.camelot.kuka.file;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 文件中心
 *
 */
@EnableDiscoveryClient
@SpringBootApplication
public class FileCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileCenterApplication.class, args);
	}

}
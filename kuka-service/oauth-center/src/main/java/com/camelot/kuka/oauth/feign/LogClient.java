package com.camelot.kuka.oauth.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.camelot.kuka.model.log.Log;

@FeignClient("log-center")
public interface LogClient {

	@PostMapping("/logs-anon/internal")
	void save(@RequestBody Log log);
}

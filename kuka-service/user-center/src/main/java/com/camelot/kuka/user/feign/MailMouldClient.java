package com.camelot.kuka.user.feign;

import com.camelot.kuka.model.common.MailReq;
import com.camelot.kuka.model.common.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <p>Description: [邮件模板]</p>
 * Created on 2020/2/5
 *
 *
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@FeignClient("manage-backend")
public interface MailMouldClient {

	/**
	 * 发送邮件
	 * @param req
	 * @return
	 */
	@PostMapping("/mailMould/sendMail")
	Result sendMail(@RequestBody MailReq req);
}

package com.camelot.kuka.gateway.feign;

import com.camelot.kuka.model.common.Result;
import com.camelot.kuka.model.user.req.UserReq;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient("user-center")
public interface UserLoginClient {

    /**
     * Description: [校验验证码]
     * @return: void
     * Created on 2019年08月26日
     * Copyright (c) 2019 北京柯莱特科技有限公司
     **/
    @PostMapping("/login/checkCode")
    public Result<Boolean> checkCode(@RequestBody UserReq req);
}

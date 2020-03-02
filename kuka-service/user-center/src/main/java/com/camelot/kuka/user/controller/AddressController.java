package com.camelot.kuka.user.controller;

import com.camelot.kuka.model.common.CommonReq;
import com.camelot.kuka.model.common.Result;
import com.camelot.kuka.model.user.role.resp.RoleResp;
import com.camelot.kuka.user.service.AddressService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>Description: [用户信息]</p>
 * Created on 2020/1/19
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Slf4j
@RestController
@Api(value = "地址中心API", tags = { "地址中心接口" })
public class AddressController {

    @Resource
    private AddressService addressService;

    /***
     * <p>Description:[通过ID获取角色信息]</p>
     * Created on 2020/2/4
     * @param req
     * @return com.camelot.kuka.model.common.Result
     * @author 谢楠
     */
    @GetMapping("/address/queryAll")
    public String queryById(CommonReq req){
        addressService.queryAddress();
        return "123";
    }
}

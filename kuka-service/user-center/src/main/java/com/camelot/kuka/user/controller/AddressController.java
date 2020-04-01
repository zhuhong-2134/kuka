package com.camelot.kuka.user.controller;

import com.alibaba.fastjson.JSON;
import com.camelot.kuka.model.common.CommonReq;
import com.camelot.kuka.model.common.Result;
import com.camelot.kuka.user.service.AddressService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: [地址信息信息]</p>
 * Created on 2020/1/19
 *
 *
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
     * <p>Description:[只是为了控制台打印所有地址信息]</p>
     * Created on 2020/2/4
     * @param req
     * @return com.camelot.kuka.model.common.Result
     *
     */
    @GetMapping("/address/queryAll")
    public String queryById(CommonReq req){
        addressService.queryAddress();
        return "123";
    }

    /***
     * <p>Description:[根据地址编码返回名称]</p>
     * Created on 2020/2/4
     * @param codes
     * @return key code value 名称
     *
     */
    @PostMapping("/address/queryAddressMap")
    public Result<Map<String, String>> queryAddressMap(@RequestBody List<String> codes) {
        try {
            return addressService.queryAddressMap(codes);
        } catch (Exception e) {
            log.error("\n 地址模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "updateRole", JSON.toJSONString(codes), e);
            return Result.error("网络异常, 请稍后再试");
        }
    }

}

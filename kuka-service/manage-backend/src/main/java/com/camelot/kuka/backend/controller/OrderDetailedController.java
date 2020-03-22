package com.camelot.kuka.backend.controller;


import com.alibaba.fastjson.JSON;
import com.camelot.kuka.backend.model.OrderDetailed;
import com.camelot.kuka.backend.service.OrderDetailedService;
import com.camelot.kuka.common.utils.AppUserUtil;
import com.camelot.kuka.model.common.Result;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>Description: [订单明细控制层]</p>
 * Created on 2020/2/12
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Slf4j
@RestController
@Api(value = "订单明细API", tags = { "订单明细接口" })
public class OrderDetailedController {

    @Resource
    private OrderDetailedService orderDetailedService;

    /***
     * <p>Description:[修改订单数量]</p>
     * Created on 2020/1/20
     * @param req
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    @PostMapping("/order/detailed/updateNum")
    public Result updateNum(OrderDetailed req){
        try {
            String loginUserName = AppUserUtil.getLoginUserName();
            if (null == loginUserName) {

            }
            return orderDetailedService.updateNum(req, loginUserName);
        } catch (Exception e) {
            log.error("\n 订单明细模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "updateOrder", JSON.toJSONString(req), e);
            return Result.error("网络异常, 请稍后再试");
        }
    }
}

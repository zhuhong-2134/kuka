package com.camelot.kuka.backend.controller;


import com.camelot.kuka.backend.service.OrderDetailedService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
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
}

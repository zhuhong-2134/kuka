package com.camelot.kuka.backend.service.impl;

import com.camelot.kuka.backend.dao.OrderDetailedDao;
import com.camelot.kuka.backend.service.OrderDetailedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>Description: [订单明细业务层]</p>
 * Created on 2020/2/5
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Slf4j
@Service("orderDetailedService")
public class OrderDetailedServiceImpl implements OrderDetailedService {

    @Resource
    private OrderDetailedDao orderDetailedDao;

}

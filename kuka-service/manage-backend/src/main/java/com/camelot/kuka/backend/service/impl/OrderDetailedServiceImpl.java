package com.camelot.kuka.backend.service.impl;

import com.camelot.kuka.backend.dao.OrderDao;
import com.camelot.kuka.backend.dao.OrderDetailedDao;
import com.camelot.kuka.backend.model.Order;
import com.camelot.kuka.backend.model.OrderDetailed;
import com.camelot.kuka.backend.model.ShopCart;
import com.camelot.kuka.backend.service.OrderDetailedService;
import com.camelot.kuka.common.utils.NumberUtil;
import com.camelot.kuka.model.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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
    @Resource
    private OrderDao orderDao;

    @Override
    public Result updateNum(OrderDetailed req, String loginUserName) {
        if (null == req.getId()) {
            return Result.error("明细ID不能为空");
        }
        if (null == req.getOrderId()) {
            return Result.error("订单ID不能为空");
        }
        if (null == req.getNum()) {
            return Result.error("数量不能为空");
        }
        List<OrderDetailed> orderDetaileds = orderDetailedDao.selectByOrderIds(new Long[]{req.getOrderId()});
        if (orderDetaileds.isEmpty()) {
            return Result.error("获取订单明细失败");
        }
        // 修改数量
        OrderDetailed updatePar = null;
        // 计算总价
        Double sunPrice = Double.valueOf(0);
        for (OrderDetailed orderDetailed : orderDetaileds) {
            if (req.getId() == orderDetailed.getId()) {
                orderDetailed.setNum(req.getNum());
                orderDetailed.setSumPrice(NumberUtil.format(req.getNum() * orderDetailed.getPrice()));
                orderDetailed.setUpdateBy(loginUserName);
                orderDetailed.setUpdateTime(new Date());
                updatePar = orderDetailed;
            }
            sunPrice += null == orderDetailed.getSumPrice() ? Double.valueOf(0) : orderDetailed.getSumPrice();
        }
        if (null == updatePar) {
            return Result.error("获取订单明细失败");
        }

        // 修改订单明细数据
        int con = orderDetailedDao.update(updatePar);
        if (con == 0) {
            return Result.error("修改订单明细失败");
        }


        // 修改订单总价格
        Order order = new Order();
        order.setId(req.getOrderId());
        order.setSunPrice(sunPrice);
        order.setUpdateBy(loginUserName);
        order.setUpdateTime(new Date());
        int update = orderDao.update(order);
        if (update == 0) {
            return Result.error("修改订单总额失败");
        }
        return Result.success();
    }
}

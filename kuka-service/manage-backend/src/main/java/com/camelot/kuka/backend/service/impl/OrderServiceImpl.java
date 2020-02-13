package com.camelot.kuka.backend.service.impl;

import com.camelot.kuka.backend.dao.OrderDao;
import com.camelot.kuka.backend.dao.OrderDetailedDao;
import com.camelot.kuka.backend.model.Comment;
import com.camelot.kuka.backend.model.Order;
import com.camelot.kuka.backend.model.OrderDetailed;
import com.camelot.kuka.backend.service.CommentService;
import com.camelot.kuka.backend.service.OrderService;
import com.camelot.kuka.common.utils.BeanUtil;
import com.camelot.kuka.model.backend.comment.resp.CommentResp;
import com.camelot.kuka.model.backend.order.req.OrderPageReq;
import com.camelot.kuka.model.backend.order.req.OrderReq;
import com.camelot.kuka.model.backend.order.resp.OrderDetailedResp;
import com.camelot.kuka.model.backend.order.resp.OrderResp;
import com.camelot.kuka.model.common.CommonReq;
import com.camelot.kuka.model.common.Result;
import com.camelot.kuka.model.enums.DeleteEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>Description: [订单业务层]</p>
 * Created on 2020/2/5
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Slf4j
@Service("orderService")
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderDao orderDao;
    @Resource
    private OrderDetailedDao orderDetailedDao;
    @Resource
    private CommentService commentService;


    @Override
    public List<Order> pageList(OrderPageReq req) {
        req.setDelState(DeleteEnum.NO);
        req.setQueryTypeCode(null != req.getQueryType() ? req.getQueryType().getCode() : null);
        List<Order> orders = orderDao.pageList(req);
        // 放入订单明细
        getOrderDetaileList(orders);
        return orders;
    }

    @Override
    public Result<OrderResp> queryById(CommonReq req) {
        if (null == req || null == req.getId()) {
            return Result.error("参数错误");
        }
        Order query = new Order();
        query.setId(req.getId());
        query.setDelState(DeleteEnum.NO);
        Order order = orderDao.queryById(query);
        if (null == order) {
            return Result.error("数据获取失败, 刷新后重试");
        }
        OrderResp orderResp = BeanUtil.copyBean(order, OrderResp.class);
        // 放入明细
        Long[] orderIds = new Long[]{req.getId()};
        List<OrderDetailed> deailList = orderDetailedDao.selectByOrderIds(orderIds);
        orderResp.setDetaileList(BeanUtil.copyList(deailList, OrderDetailedResp.class ));

        // 放入评论信息
        List<Comment> comments = commentService.queryByOrderIds(orderIds);
        orderResp.setCommentList(BeanUtil.copyList(comments, CommentResp.class ));

        return Result.success(orderResp);
    }

    @Override
    public Result updateOrder(OrderReq req, String loginUserName) {
        if (null == req || null == req.getId()) {
            return Result.error("参数错误");
        }
        Order order = BeanUtil.copyBean(req, Order.class);
        order.setUpdateBy(loginUserName);
        order.setUpdateTime(new Date());
        int con = orderDao.update(order);
        if (con == 0) {
            return Result.error("修改失败, 联系管理员");
        }
        return Result.success();
    }

    /**
     * 获取明细信息
     * @param orders
     */
    private void getOrderDetaileList(List<Order> orders){
        if (orders.isEmpty()) {
            return;
        }
        Long[] ids = orders.stream().map(Order::getId).toArray(Long[]::new);
        // 获取明细信息
        List<OrderDetailed> deailList = orderDetailedDao.selectByOrderIds(ids);
        for (Order order : orders) {
            List<OrderDetailed> newList = new ArrayList<>();
            for (OrderDetailed orderDetailed : deailList) {
                if (order.getId().compareTo(orderDetailed.getOrderId()) == 0) {
                    newList.add(orderDetailed);
                }
            }
            order.setDetaileList(BeanUtil.copyBeanList(newList, OrderDetailedResp.class));
        }
    }

}

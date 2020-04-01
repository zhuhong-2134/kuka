package com.camelot.kuka.backend.service;

import com.camelot.kuka.backend.model.Order;
import com.camelot.kuka.backend.model.Supplier;
import com.camelot.kuka.model.backend.order.req.OrderPageReq;
import com.camelot.kuka.model.backend.order.req.OrderReq;
import com.camelot.kuka.model.backend.order.resp.OrderResp;
import com.camelot.kuka.model.common.CommonReq;
import com.camelot.kuka.model.common.Result;
import com.camelot.kuka.model.shopcart.req.ShopCartReq;
import com.camelot.kuka.model.user.LoginAppUser;

import java.util.List;

/**
 * <p>Description: [订单业务层]</p>
 * Created on 2020/2/5
 *
 *
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
public interface OrderService {

    /***
     * <p>Description:[kuka后台-分页查询]</p>
     * Created on 2020/2/5
     * @param req
     * @return List
     *
     */
    List<Order> pageList(OrderPageReq req, LoginAppUser loginAppUser);

    /***
     * <p>Description:[单条查询查询]</p>
     * Created on 2020/2/5
     * @param req
     * @return List
     *
     */
    Result<OrderResp> queryById(CommonReq req);

    /***
     * <p>Description:[修改订单的基本信息]</p>
     * Created on 2020/2/5
     * @param req
     * @return List
     *
     */
    Result updateOrder(OrderReq req, String loginUserName);

    /***
     * <p>Description:[创建订单的基本信息]</p>
     * Created on 2020/2/5
     * @param req
     * @return List
     *
     */
    Result<OrderResp> createOrder(CommonReq req, LoginAppUser loginAppUser);

    /***
     * <p>Description:[直接创建订单]</p>
     * Created on 2020/1/20
     * @param req
     * @return com.camelot.kuka.model.common.PageResult
     *
     */
    Result<OrderResp> createInstantly(ShopCartReq req, LoginAppUser loginAppUser);
}

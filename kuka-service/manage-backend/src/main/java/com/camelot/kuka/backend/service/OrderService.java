package com.camelot.kuka.backend.service;

import com.camelot.kuka.backend.model.Order;
import com.camelot.kuka.backend.model.Supplier;
import com.camelot.kuka.model.backend.order.req.OrderPageReq;
import com.camelot.kuka.model.backend.order.req.OrderReq;
import com.camelot.kuka.model.backend.order.resp.OrderResp;
import com.camelot.kuka.model.common.CommonReq;
import com.camelot.kuka.model.common.Result;

import java.util.List;

/**
 * <p>Description: [订单业务层]</p>
 * Created on 2020/2/5
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
public interface OrderService {

    /***
     * <p>Description:[kuka后台-分页查询]</p>
     * Created on 2020/2/5
     * @param req
     * @return List
     * @author 谢楠
     */
    List<Order> pageList(OrderPageReq req);

    /***
     * <p>Description:[集成商后台-分页查询]</p>
     * Created on 2020/2/5
     * @param req
     * @return List
     * @author 谢楠
     */
    List<Order> supplierPageList(OrderPageReq req);

    /***
     * <p>Description:[访客后台-分页查询]</p>
     * Created on 2020/2/5
     * @param req
     * @return List
     * @author 谢楠
     */
    List<Order> visitorPageList(OrderPageReq req);

    /***
     * <p>Description:[单条查询查询]</p>
     * Created on 2020/2/5
     * @param req
     * @return List
     * @author 谢楠
     */
    Result<OrderResp> queryById(CommonReq req);

    /***
     * <p>Description:[修改订单的基本信息]</p>
     * Created on 2020/2/5
     * @param req
     * @return List
     * @author 谢楠
     */
    Result updateOrder(OrderReq req, String loginUserName);


}

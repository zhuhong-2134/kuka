package com.camelot.kuka.backend.dao;

import com.camelot.kuka.backend.model.Order;
import com.camelot.kuka.model.backend.order.req.OrderPageReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>Description: [订单接口]</p>
 * Created on 2020/1/19
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Mapper
public interface OrderDao {

    /***
     * <p>Description:[分页查询]</p>
     * Created on 2020/2/5
     * @param req
     * @return List
     * @author 谢楠
     */
    List<Order> pageList(@Param("entity") OrderPageReq req);

    /***
     * <p>Description:[通过ID获取]</p>
     * Created on 2020/2/5
     * @param query
     * @return List
     * @author 谢楠
     */
    Order queryById(@Param("entity")Order query);

    /***
     * <p>Description:[修改]</p>
     * Created on 2020/2/5
     * @param order
     * @return int
     * @author 谢楠
     */
    int update(Order order);
}

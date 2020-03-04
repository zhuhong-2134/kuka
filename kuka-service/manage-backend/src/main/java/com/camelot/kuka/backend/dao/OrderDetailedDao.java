package com.camelot.kuka.backend.dao;

import com.camelot.kuka.backend.model.OrderDetailed;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>Description: [订单明细接口]</p>
 * Created on 2020/1/19
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Mapper
public interface OrderDetailedDao {

    /***
     * <p>Description:[根据订单ID批量获取]</p>
     * Created on 2020/2/5
     * @param orderIds
     * @return List
     * @author 谢楠
     */
    List<OrderDetailed> selectByOrderIds(@Param("array") Long[] orderIds);

    /***
     * <p>Description:[批量新增数据]</p>
     * Created on 2020/2/5
     * @param orderDetaileds
     * @return List
     * @author 谢楠
     */
    int addBatch(@Param("list") List<OrderDetailed> orderDetaileds);
}

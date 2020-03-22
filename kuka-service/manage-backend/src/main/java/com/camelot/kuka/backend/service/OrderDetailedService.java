package com.camelot.kuka.backend.service;

import com.camelot.kuka.backend.dao.OrderDetailedDao;
import com.camelot.kuka.backend.model.OrderDetailed;
import com.camelot.kuka.model.common.Result;

import javax.annotation.Resource;

/**
 * <p>Description: [订单明细]</p>
 * Created on 2020/2/5
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
public interface OrderDetailedService {

    /***
     * <p>Description:[修改订单数量]</p>
     * Created on 2020/1/20
     * @param req
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    Result updateNum(OrderDetailed req, String loginUserName);
}

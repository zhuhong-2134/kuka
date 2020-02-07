package com.camelot.kuka.backend.service;

import com.camelot.kuka.backend.model.Application;
import com.camelot.kuka.model.backend.application.req.AppPageReq;
import com.camelot.kuka.model.backend.application.req.ApplicationAddReq;
import com.camelot.kuka.model.backend.supplier.req.SupplierReq;
import com.camelot.kuka.model.common.Result;

import java.util.List;

/**
 * <p>Description: [产品接口]</p>
 * Created on 2020/2/5
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
public interface ApplicationService {

    /***
     * <p>Description:[查询产品信息集合]</p>
     * Created on 2020/2/5
     * @param req
     * @return List<Supplier>
     * @author 谢楠
     */
    List<Application> queryList(AppPageReq req);

    /***
     * <p>Description:[新增产品信息]</p>
     * Created on 2020/2/5
     * @param req
     * @return List<Supplier>
     * @author 谢楠
     */
    Result addApplication(ApplicationAddReq req, String loginUserName);
}

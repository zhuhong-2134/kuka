package com.camelot.kuka.backend.service;

import com.camelot.kuka.backend.model.Application;
import com.camelot.kuka.backend.model.Supplier;
import com.camelot.kuka.model.backend.application.req.ApplicationReq;
import com.camelot.kuka.model.backend.application.resp.ApplicationResp;
import com.camelot.kuka.model.backend.home.req.HomeAppPageReq;
import com.camelot.kuka.model.backend.home.req.HomeSupplierPageReq;
import com.camelot.kuka.model.backend.supplier.resp.SupplierResp;
import com.camelot.kuka.model.common.Result;

import java.util.List;

/**
 * <p>Description: [首页业务层]</p>
 * Created on 2020/2/5
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
public interface HomePageService {

    /***
     * <p>Description:[KUKA软件查询]</p>
     * Created on 2020/1/20
     * @param
     * @return com.camelot.kuka.model.common.Result
     * @author 谢楠
     */
    Result<List<ApplicationResp>> appQuery();

    /***
     * <p>Description:[kuka集成商查询]</p>
     * Created on 2020/1/20
     * @param
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    Result<List<SupplierResp>> supplierQuery();

    /***
     * <p>Description:[应用商店-分页查询]</p>
     * Created on 2020/1/20
     * @param req
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    List<Application> appPageList(HomeAppPageReq req);

    /***
     * <p>Description:[集成商-分页查询]</p>
     * Created on 2020/1/20
     * @param req
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    List<Supplier> supplierPageList(HomeSupplierPageReq req);
}

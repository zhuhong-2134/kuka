package com.camelot.kuka.backend.service;

import com.camelot.kuka.backend.model.Supplier;
import com.camelot.kuka.model.backend.supplier.req.SupplierPageReq;
import com.camelot.kuka.model.backend.supplier.req.SupplierReq;
import com.camelot.kuka.model.backend.supplier.resp.SupplierResp;
import com.camelot.kuka.model.common.CommonReq;
import com.camelot.kuka.model.common.Result;
import com.camelot.kuka.model.user.LoginAppUser;

import java.util.List;

/**
 * <p>Description: [供应商接口]</p>
 * Created on 2020/1/19
 *
 *
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
public interface SupplierService {

    /***
     * <p>Description:[kuka-分页查询]</p>
     * Created on 2020/2/5
     * @param req
     * @return List
     *
     */
    List<Supplier> pageList(SupplierPageReq req, LoginAppUser loginAppUser);


    /***
     * <p>Description:[列表查询]</p>
     * Created on 2020/2/5
     * @param req
     * @return List
     *
     */
    Result<List<SupplierResp>> queryList(SupplierPageReq req);

    /***
     * <p>Description:[新增]</p>
     * Created on 2020/2/5
     * @param req
     * @return Result
     *
     */
    Result addSupplier(SupplierReq req, String loginUserName);

    /***
     * <p>Description:[查询单条]</p>
     * Created on 2020/2/5
     * @param req
     * @return Result
     *
     */
    Result<SupplierResp> queryById(CommonReq req);

    /***
     * <p>Description:[修改]</p>
     * Created on 2020/2/5
     * @param req
     * @return Result
     *
     */
    Result updateSupplier(SupplierReq req, String loginUserName);

    /***
     * <p>Description:[删除]</p>
     * Created on 2020/2/5
     * @param req
     * @return Result
     *
     */
    Result delSupplier(CommonReq req, String loginUserName);

    /***
     * <p>Description:[获取当前登录用户拥有的集成商ID]</p>
     * Created on 2020/1/20
     * @param loginUserName
     * @return Result
     *
     */
    Long[] queryLoginSupplierIds(String loginUserName);

    /***
     * <p>Description:[包含应用的集成商]</p>
     * Created on 2020/2/5
     * @param req
     * @return Result
     *
     */
    Result<SupplierResp> querySuppAndAppById(CommonReq req);

    /***
     * <p>Description:[根据创建人获取集成商]</p>
     * Created on 2020/1/20
     * @param userName
     * @return com.camelot.kuka.model.common.Result
     *
     */
    Result<SupplierResp> queryByCreateName(String userName);
}

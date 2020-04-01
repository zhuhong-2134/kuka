package com.camelot.kuka.backend.service;

import com.camelot.kuka.backend.dao.SupplierRequestDao;
import com.camelot.kuka.backend.model.SupplierRequest;
import com.camelot.kuka.model.backend.message.resp.MessageResp;
import com.camelot.kuka.model.backend.supplierrequest.req.SupplierRequestPageReq;
import com.camelot.kuka.model.backend.supplierrequest.req.SupplierRequestReq;
import com.camelot.kuka.model.backend.supplierrequest.resp.SupplierRequestResp;
import com.camelot.kuka.model.common.CommonReq;
import com.camelot.kuka.model.common.Result;
import com.camelot.kuka.model.user.LoginAppUser;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>Description: [集成商请求业务层]</p>
 * Created on 2020/2/5
 *
 *
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
public interface SupplierRequestService {

    /***
     * <p>Description:[分页查询]</p>
     * Created on 2020/1/20
     * @param req
     * @return list
     *
     */
    List<SupplierRequest> queryList(SupplierRequestPageReq req);

    /***
     * <p>Description:[根据ID获取数据]</p>
     * Created on 2020/1/20
     * @param req
     * @return Result
     *
     */
    Result<SupplierRequestResp> queryById(CommonReq req);

    /***
     * <p>Description:[修改状态获取数据]</p>
     * Created on 2020/1/20
     * @param req
     * @return Result
     *
     */
    Result updateStatus(SupplierRequestReq req, String loginUserName);

    /***
     * <p>Description:[新增集成商请求]</p>
     * Created on 2020/1/20
     * @param req
     * @return Result
     *
     */
    Result addSupplierequest(CommonReq req, LoginAppUser loginAppUser);

    /***
     * <p>Description:[新增集成商请求]</p>
     * Created on 2020/1/20
     * @param req
     * @return Result
     *
     */
    Result sendMail(CommonReq req, LoginAppUser loginAppUser);

    /***
     * <p>Description:[消息预览]</p>
     * Created on 2020/1/20
     * @param req
     * @return Result
     *
     */
    Result<MessageResp> previewMessage(CommonReq req, LoginAppUser loginAppUser);
}

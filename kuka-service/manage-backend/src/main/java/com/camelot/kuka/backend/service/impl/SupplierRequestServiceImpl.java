package com.camelot.kuka.backend.service.impl;

import com.camelot.kuka.backend.dao.SupplierRequestDao;
import com.camelot.kuka.backend.model.SupplierRequest;
import com.camelot.kuka.backend.service.SupplierRequestService;
import com.camelot.kuka.backend.service.SupplierService;
import com.camelot.kuka.common.utils.BeanUtil;
import com.camelot.kuka.common.utils.CodeGenerateUtil;
import com.camelot.kuka.model.backend.supplier.resp.SupplierResp;
import com.camelot.kuka.model.backend.supplierrequest.req.SupplierRequestPageReq;
import com.camelot.kuka.model.backend.supplierrequest.req.SupplierRequestReq;
import com.camelot.kuka.model.backend.supplierrequest.resp.SupplierRequestResp;
import com.camelot.kuka.model.common.CommonReq;
import com.camelot.kuka.model.common.Result;
import com.camelot.kuka.model.enums.CommunicateEnum;
import com.camelot.kuka.model.enums.DeleteEnum;
import com.camelot.kuka.model.enums.PrincipalEnum;
import com.camelot.kuka.model.user.LoginAppUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>Description: [集成商请求业务层]</p>
 * Created on 2020/2/5
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Slf4j
@Service("supplierRequestService")
public class SupplierRequestServiceImpl implements SupplierRequestService {

    @Resource
    private SupplierRequestDao supplierRequestDao;
    @Resource
    private SupplierService supplierService;
    @Resource
    private CodeGenerateUtil codeGenerateUtil;

    @Override
    public List<SupplierRequest> queryList(SupplierRequestPageReq req) {
        req.setDelState(DeleteEnum.NO);
        req.setQueryTypeCode(null != req.getQueryType() ? req.getQueryType().getCode() : null);
        List<SupplierRequest> list = supplierRequestDao.pageList(req);
        return list;
    }

    @Override
    public Result<SupplierRequestResp> queryById(CommonReq req) {
        if (null == req || null == req.getId()) {
            return Result.error("ID不能为空");
        }
        SupplierRequest query = new SupplierRequest();
        query.setId(req.getId());
        query.setDelState(DeleteEnum.NO);
        SupplierRequest supplierRequest = supplierRequestDao.queryInfo(query);
        if (null == supplierRequest) {
            return Result.error("获取失败, 刷新后重试");
        }
        return Result.success(BeanUtil.copyBean(supplierRequest, SupplierRequestResp.class));
    }

    @Override
    public Result updateStatus(SupplierRequestReq req, String loginUserName) {
        if (null == req || req.getId() == null) {
            return Result.error("ID不能为空");
        }
        if (null == req.getStatus()) {
            return Result.error("状态不能为空");
        }
        SupplierRequest supplierRequest = BeanUtil.copyBean(req, SupplierRequest.class);
        supplierRequest.setUpdateBy(loginUserName);
        supplierRequest.setUpdateTime(new Date());
        int update = supplierRequestDao.update(supplierRequest);
        if (0 == update) {
            return Result.error("修改失败, 请联系管理员");
        }
        return Result.success();
    }

    @Override
    public Result addSupplierequest(CommonReq req, LoginAppUser loginAppUser) {
        if (null == req.getId()) {
            return Result.error("集成商ID不能为空");
        }
        CommonReq query = new CommonReq();
        query.setId(req.getId());
        Result<SupplierResp> supplierRespResult = supplierService.queryById(query);
        if (!supplierRespResult.isSuccess()) {
            return Result.error("获取集成商失败");
        }
        SupplierResp supplier = supplierRespResult.getData();
        // 新增对象
        SupplierRequest request = new SupplierRequest();
        Long id = codeGenerateUtil.generateId(PrincipalEnum.MANAGE_SUPPLIER_REQUEST);
        request.setId(id);
        request.setSupplierUrl(supplier.getCoverUrl());
        request.setSupplierId(supplier.getId());
        request.setSupplierName(supplier.getSupplierlName());

        request.setLocation(supplier.getSupplierAddress());
        request.setDutyName(supplier.getUserName());
        request.setDutyId(supplier.getUserId());
        request.setDutyPhone(supplier.getUserPhone());
        request.setMail(supplier.getUserMali());
        request.setAddress(supplier.getUserAddress());
        request.setStatus(CommunicateEnum.NO);
        request.setUserId(loginAppUser.getId());
        request.setUserName(loginAppUser.getUserName());
        request.setUserPhotoUrl(loginAppUser.getPhotoUrl());
        request.setUserPhone(loginAppUser.getPhone());
        request.setUserMail(loginAppUser.getMail());
        request.setDelState(DeleteEnum.NO);
        request.setCreateBy(loginAppUser.getCreateBy());
        request.setCreateTime(new Date());
        int con = supplierRequestDao.addBatch(Arrays.asList(request));
        if (con == 0) {
            return Result.error("新增失败");
        }
        return Result.success(id);
    }
}

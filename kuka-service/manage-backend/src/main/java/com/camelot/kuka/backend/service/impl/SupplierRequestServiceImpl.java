package com.camelot.kuka.backend.service.impl;

import com.camelot.kuka.backend.dao.SupplierRequestDao;
import com.camelot.kuka.backend.model.SupplierRequest;
import com.camelot.kuka.backend.service.SupplierRequestService;
import com.camelot.kuka.common.utils.BeanUtil;
import com.camelot.kuka.model.backend.supplierrequest.req.SupplierRequestPageReq;
import com.camelot.kuka.model.backend.supplierrequest.req.SupplierRequestReq;
import com.camelot.kuka.model.backend.supplierrequest.resp.SupplierRequestResp;
import com.camelot.kuka.model.common.CommonReq;
import com.camelot.kuka.model.common.Result;
import com.camelot.kuka.model.enums.DeleteEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
}

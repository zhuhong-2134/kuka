package com.camelot.kuka.backend.service.impl;

import com.camelot.kuka.backend.dao.ApplicationRequestDao;
import com.camelot.kuka.backend.model.ApplicationRequest;
import com.camelot.kuka.backend.service.ApplicationRequestService;
import com.camelot.kuka.backend.service.ApplicationService;
import com.camelot.kuka.common.utils.BeanUtil;
import com.camelot.kuka.common.utils.CodeGenerateUtil;
import com.camelot.kuka.model.backend.application.resp.QyeryUpdateResp;
import com.camelot.kuka.model.backend.applicationrequest.req.AppRequestPageReq;
import com.camelot.kuka.model.backend.applicationrequest.req.ApplicationRequestReq;
import com.camelot.kuka.model.backend.applicationrequest.resp.ApplicationRequestResp;
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
 * <p>Description: [应用商请求业务层]</p>
 * Created on 2020/2/5
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Slf4j
@Service("applicationRequestService")
public class ApplicationRequestServiceImpl implements ApplicationRequestService {

    @Resource
    private ApplicationRequestDao applicationRequestDao;
    @Resource
    private ApplicationService applicationService;
    @Resource
    private CodeGenerateUtil codeGenerateUtil;

    @Override
    public List<ApplicationRequest> queryList(AppRequestPageReq req) {
        req.setDelState(DeleteEnum.NO);
        req.setQueryTypeCode(null != req.getQueryType() ? req.getQueryType().getCode() : null);
        List<ApplicationRequest> applicationRequests = applicationRequestDao.pageList(req);
        return applicationRequests;
    }

    @Override
    public Result<ApplicationRequestResp> queryById(CommonReq req) {
        if (null == req || null == req.getId()) {
            return Result.error("ID不能为空");
        }
        ApplicationRequest query = new ApplicationRequest();
        query.setId(req.getId());
        query.setDelState(DeleteEnum.NO);
        ApplicationRequest applicationRequest = applicationRequestDao.queryInfo(query);
        if (null == applicationRequest) {
            return Result.error("获取失败, 刷新后重试");
        }
        return Result.success(BeanUtil.copyBean(applicationRequest, ApplicationRequestResp.class));
    }

    @Override
    public Result updateStatus(ApplicationRequestReq req, String loginUserName) {
        if (null == req || req.getId() == null) {
            return Result.error("ID不能为空");
        }
        if (null == req.getStatus()) {
            return Result.error("状态不能为空");
        }
        ApplicationRequest applicationRequest = BeanUtil.copyBean(req, ApplicationRequest.class);
        applicationRequest.setUpdateBy(loginUserName);
        applicationRequest.setUpdateTime(new Date());
        int update = applicationRequestDao.update(applicationRequest);
        if (0 == update) {
            return Result.error("修改失败, 请联系管理员");
        }
        return Result.success();
    }

    @Override
    public Result addApprequest(CommonReq req, LoginAppUser loginAppUser) {
        if (null == req.getId()) {
            return Result.error("应用ID不能为空");
        }
        CommonReq query = new CommonReq();
        query.setId(req.getId());
        Result<QyeryUpdateResp> appRespResult = applicationService.qyeryUpdateById(query);
        if (!appRespResult.isSuccess()) {
            return Result.error("获应用失败");
        }
        QyeryUpdateResp data = appRespResult.getData();
        // 新增对象
        ApplicationRequest request = new ApplicationRequest();
        Long id = codeGenerateUtil.generateId(PrincipalEnum.MANAGE_SUPPLIER_REQUEST);
        request.setId(id);
        if (null != data.getCoverUrls() && data.getCoverUrls().length > 0) {
            request.setAppUrl(data.getCoverUrls()[0]);
        }
        request.setAppId(data.getId());
        request.setAppName(data.getAppName());
        request.setClassType(data.getClassType());
        request.setSupplierId(data.getSupplierId());

        // 集成商信息
        if (null != data.getSupplier()) {
            request.setSupplierName(data.getSupplier().getSupplierlName());
            request.setDutyName(data.getSupplier().getUserName());
            request.setDutyId(data.getSupplier().getUserId());
            request.setDutyPhone(data.getSupplier().getUserPhone());
        }

        request.setAppContactName(data.getContactBy());
        request.setAppPhone(data.getContactPhone());
        request.setMail(data.getContactMail());
        request.setAddress(null);
        request.setStatus(CommunicateEnum.NO);
        request.setUserId(loginAppUser.getId());
        request.setUserName(loginAppUser.getUserName());
        request.setUserPhotoUrl(loginAppUser.getPhotoUrl());
        request.setUserPhone(loginAppUser.getPhone());
        request.setUserMail(loginAppUser.getMail());
        request.setDelState(DeleteEnum.NO);
        request.setCreateBy(loginAppUser.getCreateBy());
        request.setCreateTime(new Date());
        int con = applicationRequestDao.addBatch(Arrays.asList(request));
        if (con == 0) {
            return Result.error("新增失败");
        }
        return Result.success(id);
    }
}

package com.camelot.kuka.backend.service.impl;

import com.camelot.kuka.backend.dao.ApplicationRequestDao;
import com.camelot.kuka.backend.model.ApplicationRequest;
import com.camelot.kuka.backend.service.ApplicationRequestService;
import com.camelot.kuka.common.utils.BeanUtil;
import com.camelot.kuka.model.backend.applicationrequest.req.AppRequestPageReq;
import com.camelot.kuka.model.backend.applicationrequest.resp.ApplicationRequestResp;
import com.camelot.kuka.model.common.CommonReq;
import com.camelot.kuka.model.common.Result;
import com.camelot.kuka.model.enums.DeleteEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
}

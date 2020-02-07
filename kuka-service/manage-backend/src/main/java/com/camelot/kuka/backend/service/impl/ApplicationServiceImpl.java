package com.camelot.kuka.backend.service.impl;

import com.camelot.kuka.backend.dao.ApplicationDao;
import com.camelot.kuka.backend.model.Application;
import com.camelot.kuka.backend.service.ApplicationService;
import com.camelot.kuka.model.backend.application.req.AppPageReq;
import com.camelot.kuka.model.backend.application.req.ApplicationAddReq;
import com.camelot.kuka.model.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>Description: [产品业务层]</p>
 * Created on 2020/2/5
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Slf4j
@Service("applicationService")
public class ApplicationServiceImpl implements ApplicationService {

    @Resource
    private ApplicationDao applicationDao;

    @Override
    public List<Application> queryList(AppPageReq req) {
        List<Application> list = applicationDao.queryList(req);

        return list;
    }

    @Override
    public Result addApplication(ApplicationAddReq req, String loginUserName) {
        // 处理应用信息
        // 处理适用产品
        // 处理常常见问题
        return null;
    }
}

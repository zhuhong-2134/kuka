package com.camelot.kuka.backend.service.impl;

import com.alibaba.fastjson.JSON;
import com.camelot.kuka.backend.dao.ApplicationProbleDao;
import com.camelot.kuka.backend.model.ApplicationProblem;
import com.camelot.kuka.backend.service.ApplicationProbleService;
import com.camelot.kuka.common.utils.BeanUtil;
import com.camelot.kuka.common.utils.CodeGenerateUtil;
import com.camelot.kuka.model.backend.application.req.ApplicationProblemReq;
import com.camelot.kuka.model.backend.application.resp.ApplicationProblemResp;
import com.camelot.kuka.model.common.Result;
import com.camelot.kuka.model.enums.PrincipalEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>Description: [应用常见问题业务层]</p>
 * Created on 2020/2/5
 *
 *
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Slf4j
@Service("applicationProbleService")
public class ApplicationProbleServiceImpl implements ApplicationProbleService {

    @Resource
    private ApplicationProbleDao applicationProbleDao;

    @Resource
    private CodeGenerateUtil codeGenerateUtil;

    @Override
    public Result addProbleApplication(ApplicationProblemReq req, String loginUserName) {
        if (null == req.getAppId()) {
            return Result.error("应用主键不能为空");
        }
        ApplicationProblem problem = BeanUtil.copyBean(req, ApplicationProblem.class);
        // 获取ID
        Long id = codeGenerateUtil.generateId(PrincipalEnum.MANAGE_APPLICATION_PROBLEM);
        problem.setId(id);
        problem.setCreateBy(loginUserName);
        problem.setCreateTime(new Date());
        try {
            // 新增
            int addCon = applicationProbleDao.insertBatch(Arrays.asList(problem));
            if (addCon == 0) {
                return Result.error("新增应用常见问题失败, 联系管理员");
            }
        } catch (Exception e) {
            log.error("\n 新增应用常见问题失败, 参数:{}, \n 错误信息:{}", JSON.toJSON(problem), e);
            return Result.error("新增应用常见问题失败, 联系管理员");
        }
        return Result.success(id);
    }

    @Override
    public Result updateProbleApplication(ApplicationProblemReq req, String loginUserName) {
        if (null == req.getId()) {
            return Result.error("主键不能为空");
        }
        ApplicationProblem problem = BeanUtil.copyBean(req, ApplicationProblem.class);
        problem.setUpdateBy(loginUserName);
        problem.setUpdateTime(new Date());
        try {
            // 新增
            int upCon = applicationProbleDao.update(problem);
            if (upCon == 0) {
                return Result.error("修改应用常见问题失败, 联系管理员");
            }
        } catch (Exception e) {
            log.error("\n 修改应用常见问题失败, 参数:{}, \n 错误信息:{}", JSON.toJSON(problem), e);
            return Result.error("修改应用常见问题失败, 联系管理员");
        }
        return Result.success();
    }

    @Override
    public Result<List<ApplicationProblemResp>> queryByAppId(ApplicationProblemReq req, String loginUserName) {
        if (null == req.getAppId()) {
            return Result.error("产品ID不能为空");
        }
        List<ApplicationProblem> applicationProblems = applicationProbleDao.queryListByAppId(req.getAppId());
        return Result.success(BeanUtil.copyBeanList(applicationProblems, ApplicationProblemResp.class));
    }
}

package com.camelot.kuka.backend.service.impl;

import com.alibaba.fastjson.JSON;
import com.camelot.kuka.backend.dao.*;
import com.camelot.kuka.backend.model.*;
import com.camelot.kuka.backend.service.ApplicationService;
import com.camelot.kuka.backend.service.CommentService;
import com.camelot.kuka.backend.service.SupplierService;
import com.camelot.kuka.common.utils.BeanUtil;
import com.camelot.kuka.common.utils.CodeGenerateUtil;
import com.camelot.kuka.model.backend.application.req.AppPageReq;
import com.camelot.kuka.model.backend.application.req.ApplicationCurrencyReq;
import com.camelot.kuka.model.backend.application.req.ApplicationEditReq;
import com.camelot.kuka.model.backend.application.resp.ApplicationProblemResp;
import com.camelot.kuka.model.backend.application.resp.ApplicationResp;
import com.camelot.kuka.model.backend.application.resp.QyeryUpdateResp;
import com.camelot.kuka.model.backend.comment.resp.CommentResp;
import com.camelot.kuka.model.backend.supplier.resp.SupplierResp;
import com.camelot.kuka.model.common.CommonReq;
import com.camelot.kuka.model.common.Result;
import com.camelot.kuka.model.enums.DeleteEnum;
import com.camelot.kuka.model.enums.PrincipalEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
    @Resource
    private CodeGenerateUtil codeGenerateUtil;
    @Resource
    private ApplicationImgDao applicationImgDao;
    @Resource
    private ApplicationCurrencyDao applicationCurrencyDao;
    @Resource
    private CommentService commentService;
    @Resource
    private ApplicationProbleDao applicationProbleDao;
    @Resource
    private SupplierDao supplierDao;
    @Resource
    private SupplierService supplierService;

    @Override
    public List<Application> queryList(AppPageReq req) {
        req.setDelState(DeleteEnum.NO);
        req.setQueryTypeCode(null != req.getQueryType() ? req.getQueryType().getCode() : null);
        List<Application> list = applicationDao.queryList(req);
        if (!list.isEmpty()) {
            // 封面图
            setAppImg(list);
        }
        return list;
    }

    @Override
    public List<Application> supplierPageList(AppPageReq req) {
        // 获取当前用户拥有的集成商
        Long[] supplierIds = supplierService.queryLoginSupplierIds(req.getLoginName());
        req.setSupplierIds(supplierIds);
        // 删除标识
        req.setDelState(DeleteEnum.NO);
        req.setQueryTypeCode(null != req.getQueryType() ? req.getQueryType().getCode() : null);
        List<Application> list = applicationDao.supplierPageList(req);
        if (!list.isEmpty()) {
            // 封面图
            setAppImg(list);
        }
        return list;
    }

    @Override
    public List<Application> visitorPageList(AppPageReq req) {
        req.setDelState(DeleteEnum.NO);
        req.setQueryTypeCode(null != req.getQueryType() ? req.getQueryType().getCode() : null);
        List<Application> list = applicationDao.visitorPageList(req);
        if (!list.isEmpty()) {
            // 封面图
            setAppImg(list);
        }
        return list;
    }

    /**
     * 获取封面图的第一张
     * @param list
     */
    private void setAppImg(List<Application> list){
        // 获取封面图片
        Long[] appIds = list.stream().map(Application::getId).toArray(Long[]::new);
        List<ApplicationImg> appImgs = applicationImgDao.selectList(appIds);
        for (Application application : list) {
            for (ApplicationImg appImg : appImgs) {
                if (application.getId() == appImg.getAppId()) {
                    application.setCoverUrl(appImg.getUrl());
                    break;
                }
            }
        }
    }

    @Override
    public Result addApplication(ApplicationEditReq req, String loginUserName) {
        // 非空校验
        Result result = checkAdd(req);
        if (!result.isSuccess()) {
            return result;
        }
        Long appId = codeGenerateUtil.generateId(PrincipalEnum.MANAGE_APPLICATION);
        req.setId(appId);
        // 处理应用信息
        Result appHan = HandleAppSave(req, loginUserName);
        if (!appHan.isSuccess()) {
            return appHan;
        }
        // 保存封面图片
        Result appImgHan = HandleAppImgSave(req, loginUserName);
        if (!appImgHan.isSuccess()) {
            return appImgHan;
        }
        return Result.success(appId);
    }

    @Override
    public Result<QyeryUpdateResp> qyeryUpdateById(CommonReq req) {
        if (null == req || null == req.getId()) {
            return Result.error("主键不能为空");
        }
        Application qeury = new Application();
        qeury.setId(req.getId());
        qeury.setDelState(DeleteEnum.NO);
        Application app =  applicationDao.selectById(qeury);
        if (null == app) {
            return Result.error("数据获取失败, 刷新后重试");
        }
        QyeryUpdateResp resp = BeanUtil.copyBean(app, QyeryUpdateResp.class);


        // 获取封面图片
        Long[] appids = new Long[]{app.getId()};
        List<ApplicationImg> appImgs = applicationImgDao.selectList(appids);
        if (!appImgs.isEmpty()) {
            String[] imgUrl = appImgs.stream().map(ApplicationImg::getUrl).toArray(String[]::new);
            resp.setCoverUrls(imgUrl);
        }


        // 获取评论信息
        List<Comment> comments = commentService.queryByAppId(app.getId());
        if (!comments.isEmpty()) {
            List<CommentResp> commentResps = BeanUtil.copyBeanList(comments, CommentResp.class);
            resp.setCommentList(commentResps);
        }


        // 获取适用产品
        List<Application> appList = applicationDao.queryCurrencyList(app.getId());
        if (!appList.isEmpty()) {
            // 封面图
            setAppImg(appList);
            List<ApplicationResp> appResps = BeanUtil.copyBeanList(appList, ApplicationResp.class);
            resp.setCurrencyList(appResps);
        }


        // 获取常见问题
        List<ApplicationProblem> applicationProblems = applicationProbleDao.queryListByAppId(app.getId());
        if (!appList.isEmpty()) {
            List<ApplicationProblemResp> problemResp = BeanUtil.copyBeanList(applicationProblems, ApplicationProblemResp.class);
            resp.setProblemList(problemResp);
        }

        // 获取集成商详情
        if (!appList.isEmpty()) {
            Supplier query = new Supplier();
            query.setId(resp.getSupplierId());
            Supplier supplier = supplierDao.queryById(query);
            resp.setSupplier(BeanUtil.copyBean(supplier, SupplierResp.class));
        }

        return Result.success(resp);
    }

    @Override
    public Result updateApplication(ApplicationEditReq req, String loginUserName) {
        // 非空校验
        if (null == req || req.getId() == null) {
            return Result.error("主键不能为空");
        }
        // 处理应用信息
        Result appHan = HandleAppUpdate(req, loginUserName);
        if (!appHan.isSuccess()) {
            return appHan;
        }
        // 保存封面图片
        Result appImgHan = HandleAppImgSave(req, loginUserName);
        if (!appImgHan.isSuccess()) {
            return appImgHan;
        }
        return Result.success();
    }

    @Override
    public Result addCurrency(ApplicationCurrencyReq req) {
        // 没有通用的不处理
        if (StringUtils.isBlank(req.getCurrencyAppIds())) {
            return Result.success();
        }
        // 新增的数据
        List<ApplicationCurrency> addAppCurrencyList = new ArrayList<>();

        // 过滤已经存在的适用产品
        List<ApplicationCurrency> oidList = applicationCurrencyDao.selectByAppId(req.getAppId());
        for (String appIds : req.getCurrencyAppIds().split(",")) {
            boolean status = true;
            for (ApplicationCurrency applicationCurrency : oidList) {
                // 对比数据库数据 如果存在本次不新增
                if (Long.valueOf(appIds) == applicationCurrency.getCurrencyId()) {
                    status = false;
                    break;
                }
            }
            // 不存在的新增
            if (status) {
                ApplicationCurrency crrency = new ApplicationCurrency();
                crrency.setAppId(req.getAppId());
                crrency.setCurrencyId(Long.valueOf(appIds));
                Long id = codeGenerateUtil.generateId(PrincipalEnum.MANAGE_APPLICATION_CURRENCY);
                crrency.setId(id);
                addAppCurrencyList.add(crrency);
            }
        }

        if (addAppCurrencyList.isEmpty()) {
            // 新增的数据, 数据库有, 不代表新增失败
            return Result.success();
        }

        try {
            // 新增新的数据
            int cont = applicationCurrencyDao.insertBatch(addAppCurrencyList);
            if (cont == 0) {
                return Result.error("新增适用产品失败, 联系管理员");
            }
        } catch (Exception e) {
            log.error("\n 新增适用产品失败, 参数:{}, \n 错误信息:{}", JSON.toJSON(addAppCurrencyList), e);
            return Result.error("新增适用产品失败, 联系管理员");
        }
        return Result.success();
    }

    @Override
    public Result updateAppStatus(ApplicationEditReq req, String loginUserName) {
        Application application = new Application();
        application.setId(req.getId());
        application.setAppStatus(req.getAppStatus());
        application.setUpdateBy(loginUserName);
        application.setUpdateTime(new Date());
        try {
            int cont = applicationDao.update(application);
            if (cont == 0) {
                return Result.error("修改状态失败, 联系管理员");
            }
        } catch (Exception e) {
            log.error("\n 修改状态失败, 参数:{}, \n 错误信息:{}", JSON.toJSON(req), e);
            return Result.error("修改状态失败, 联系管理员");
        }
        return Result.success();
    }

    @Override
    public Result deleteApplication(CommonReq req, String loginUserName) {
        Application application = new Application();
        application.setId(req.getId());
        application.setDelState(DeleteEnum.YES);
        application.setUpdateBy(loginUserName);
        application.setUpdateTime(new Date());
        try {
            int cont = applicationDao.update(application);
            if (cont == 0) {
                return Result.error("删除应用失败, 联系管理员");
            }
        } catch (Exception e) {
            log.error("\n 删除应用失败, 参数:{}, \n 错误信息:{}", JSON.toJSON(req), e);
            return Result.error("删除应用失败, 联系管理员");
        }
        return Result.success();
    }

    /**
     * 校验非空字段
     * @param req
     * @return Result
     */
    private Result checkAdd(ApplicationEditReq req) {
        if (null == req) {
            return Result.error("参数不能为空");
        }
        // TODO 等待上次服务器
//        if (null == req.getCoverUrl() || req.getCoverUrl().length == 0) {
//            return Result.error("封面图集不能为空");
//        }
        if (null == req.getClassType()) {
            return Result.error("分类不能为空");
        }
        if (StringUtils.isBlank(req.getAppName())) {
            return Result.error("名称不能为空");
        }
        if (null == req.getTradeCount()) {
            return Result.error("交易数不能为空");
        }
        if (null == req.getFileSum()) {
            return Result.error("文件大小不能为空");
        }
        if (StringUtils.isBlank(req.getAppRange())) {
            return Result.error("适用范围不能为空");
        }
        if (StringUtils.isBlank(req.getIndustry())) {
            return Result.error("适用行业不能为空");
        }
        return Result.success();
    }

    /**
     * 新增应用
     * @param req
     * @param loginUserName
     * @return
     */
    private Result HandleAppSave(ApplicationEditReq req, String loginUserName) {
        Application application = BeanUtil.copyBean(req, Application.class);
        application.setCreateBy(loginUserName);
        application.setCreateTime(new Date());
        application.setDelState(DeleteEnum.NO);
        try {
            int cont = applicationDao.insertBatch(Arrays.asList(application));
            if (cont == 0) {
                return Result.error("新增产品失败, 联系管理员");
            }
        } catch (Exception e) {
            log.error("\n 新增产品失败, 参数:{}, \n 错误信息:{}", JSON.toJSON(req), e);
            return Result.error("新增产品失败, 联系管理员");
        }
        return Result.success();
    }

    /**
     * 修改应用
     * @param req
     * @param loginUserName
     * @return
     */
    private Result HandleAppUpdate(ApplicationEditReq req, String loginUserName) {
        Application application = BeanUtil.copyBean(req, Application.class);
        application.setUpdateBy(loginUserName);
        application.setUpdateTime(new Date());
        try {
            int cont = applicationDao.update(application);
            if (cont == 0) {
                return Result.error("修改产品失败, 联系管理员");
            }
        } catch (Exception e) {
            log.error("\n 修改产品失败, 参数:{}, \n 错误信息:{}", JSON.toJSON(req), e);
            return Result.error("修改产品失败, 联系管理员");
        }
        return Result.success();
    }

    /**
     * 新增封面图册
     * @param req
     * @param loginUserName
     * @return
     */
    private Result HandleAppImgSave(ApplicationEditReq req, String loginUserName) {
        // TODO 等待上次服务器后注释
        if (null == req.getCoverUrl() || req.getCoverUrl().length == 0) {
            return Result.success();
        }
        List<ApplicationImg> addAppImgList = new ArrayList<>();
        for (String coverUrl : req.getCoverUrl()) {
            ApplicationImg img = new ApplicationImg();
            Long id = codeGenerateUtil.generateId(PrincipalEnum.MANAGE_APPLICATION_IMG);
            img.setId(id);
            img.setAppId(req.getId());
            img.setUrl(coverUrl);
            addAppImgList.add(img);
        }
        try {
            // 删除原有数据
            applicationImgDao.deleteByAppId(req.getId());
            // 新增新的数据
            int cont = applicationImgDao.insertBatch(addAppImgList);
            if (cont == 0) {
                return Result.error("新增产品封面图册失败, 联系管理员");
            }
        } catch (Exception e) {
            log.error("\n 新增产品封面图册失败, 参数:{}, \n 错误信息:{}", JSON.toJSON(req), e);
            return Result.error("新增产品封面图册失败, 联系管理员");
        }
        return Result.success();
    }

}

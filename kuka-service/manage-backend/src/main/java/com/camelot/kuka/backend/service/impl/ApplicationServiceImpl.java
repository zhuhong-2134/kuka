package com.camelot.kuka.backend.service.impl;

import com.alibaba.fastjson.JSON;
import com.camelot.kuka.backend.dao.*;
import com.camelot.kuka.backend.feign.user.UserClient;
import com.camelot.kuka.backend.model.*;
import com.camelot.kuka.backend.service.ApplicationService;
import com.camelot.kuka.backend.service.SupplierService;
import com.camelot.kuka.common.utils.BeanUtil;
import com.camelot.kuka.common.utils.CodeGenerateUtil;
import com.camelot.kuka.model.backend.application.req.AppPageReq;
import com.camelot.kuka.model.backend.application.req.ApplicationCurrencyReq;
import com.camelot.kuka.model.backend.application.req.ApplicationEditReq;
import com.camelot.kuka.model.backend.application.req.ApplicationProblemReq;
import com.camelot.kuka.model.backend.application.resp.ApplicationProblemResp;
import com.camelot.kuka.model.backend.application.resp.ApplicationResp;
import com.camelot.kuka.model.backend.application.resp.QyeryUpdateResp;
import com.camelot.kuka.model.backend.comment.resp.CommentResp;
import com.camelot.kuka.model.backend.supplier.resp.SupplierResp;
import com.camelot.kuka.model.common.CommonReq;
import com.camelot.kuka.model.common.EnumVal;
import com.camelot.kuka.model.common.Result;
import com.camelot.kuka.model.enums.DeleteEnum;
import com.camelot.kuka.model.enums.PrincipalEnum;
import com.camelot.kuka.model.enums.application.AppTypeEnum;
import com.camelot.kuka.model.enums.backend.IndustryTypeEnum;
import com.camelot.kuka.model.enums.backend.SkilledAppEnum;
import com.camelot.kuka.model.enums.comment.CommentStatusEnum;
import com.camelot.kuka.model.enums.user.UserTypeEnum;
import com.camelot.kuka.model.user.LoginAppUser;
import com.camelot.kuka.model.user.resp.UserResp;
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
    private CommentDao commentDao;
    @Resource
    private ApplicationProbleDao applicationProbleDao;
    @Resource
    private SupplierDao supplierDao;
    @Resource
    private SupplierService supplierService;
    @Resource
    private UserClient userClient;

    @Override
    public List<Application> queryList(AppPageReq req, LoginAppUser loginAppUser) {
        req.setDelState(DeleteEnum.NO);
        if (null != req.getClassType() && req.getClassType() == AppTypeEnum.ALL) {
            req.setClassType(null);
        }
        req.setQueryTypeCode(null != req.getQueryType() ? req.getQueryType().getCode() : null);
        List<Application> list = null;
        // kuka用户
        if (loginAppUser.getType() == UserTypeEnum.KUKA) {
            list = applicationDao.queryList(req);
        }
        // 集成商
        if (loginAppUser.getType() == UserTypeEnum.SUPPILER) {
            req.setLoginName(loginAppUser.getUserName());
            list = applicationDao.supplierPageList(req);
        }
        // 来访者
        if (loginAppUser.getType() == UserTypeEnum.VISITORS) {
            req.setLoginName(loginAppUser.getUserName());
            list = applicationDao.visitorPageList(req);
        }
        if (!list.isEmpty()) {
            // 封面图
            setAppImg(list);
            // 集成商名称
            setSupplier(list);
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
                if (application.getId().compareTo(appImg.getAppId()) == 0) {
                    application.setCoverUrl(appImg.getUrl());
                    break;
                }
            }
        }
    }

    /**
     * 获取供应商名称
     * @param list
     */
    private void setSupplier(List<Application> list){
        // 获取集成商名称
        List<Long> supplierArray = new ArrayList<>();
        for (Application application : list) {
            if (null != application.getSupplierId()) {
                supplierArray.add(application.getSupplierId());
            }
        }
        if (supplierArray.isEmpty()) {
            return;
        }
        Long[] supplierIds = supplierArray.stream().toArray(Long[]::new);
        List<Supplier> listByIds = supplierDao.findListByIds(supplierIds);
        for (Application application : list) {
            if (null == application.getSupplierId()) {
                continue;
            }
            for (Supplier supplier : listByIds) {
                if (supplier.getId().compareTo(application.getSupplierId()) == 0) {
                    application.setSupplierName(supplier.getSupplierlName());
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
        Long appId = null;
        if (null != req.getId()) {
            appId = req.getId();
        } else {
            appId = codeGenerateUtil.generateId(PrincipalEnum.MANAGE_APPLICATION);
        }
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
        Comment reqCom = new Comment();
        reqCom.setAppId(app.getId());
        reqCom.setDelState(DeleteEnum.NO);
        reqCom.setStatus(CommentStatusEnum.YES);
        List<Comment> comments = commentDao.queryList(reqCom);
        if (!comments.isEmpty()) {

            // 获取用户信息
            Long[] userIds = comments.stream().map(Comment::getUserId).toArray(Long[]::new);
            Result<List<UserResp>> listResult = userClient.queryByIds(userIds);
            if (listResult.isSuccess() && null != listResult.getData()) {
                for (Comment comment : comments) {
                    for (UserResp user : listResult.getData()) {
                        if (comment.getUserId().compareTo(user.getId()) == 0) {
                            comment.setPhotoUrl(user.getPhotoUrl());
                        }
                    }
                    if (StringUtils.isNoneBlank(comment.getCommentUrl())) {
                        comment.setCommentUrls(comment.getCommentUrl().split(","));
                    }
                }
            }
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
        if (!applicationProblems.isEmpty()) {
            List<ApplicationProblemResp> problemResp = BeanUtil.copyBeanList(applicationProblems, ApplicationProblemResp.class);
            resp.setProblemList(problemResp);
        }

        // 获取集成商详情
        if (null != resp.getSupplierId()) {
            Supplier query = new Supplier();
            query.setId(resp.getSupplierId());
            Supplier supplier = supplierDao.queryById(query);
            formatAddress(supplier);
            resp.setSupplier(BeanUtil.copyBean(supplier, SupplierResp.class));
        }

        // 转换枚举
        if (StringUtils.isNoneBlank(resp.getAppRange())) {
            String appRangeStr = "";
            String[] codes = resp.getAppRange().split(",");
            for (String code : codes) {
                List<EnumVal> enumList = EnumVal.getEnumList(SkilledAppEnum.class);
                for (EnumVal enumVal : enumList) {
                    if (enumVal.getName().equals(code) ) {
                        appRangeStr += enumVal.getDes() + ",";
                        break;
                    }
                }
            }
            resp.setAppRangeStr(appRangeStr.substring(0, appRangeStr.length()-1));
        }
        if (StringUtils.isNoneBlank(resp.getIndustry())) {
            String industryStr = "";
            String[] codes = resp.getIndustry().split(",");
            for (String code : codes) {
                List<EnumVal> enumList = EnumVal.getEnumList(IndustryTypeEnum.class);
                for (EnumVal enumVal : enumList) {
                    if (enumVal.getName().equals(code) ) {
                        industryStr += enumVal.getDes() + ",";
                        break;
                    }
                }
            }
            resp.setIndustryStr(industryStr.substring(0, industryStr.length()-1));
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
    public Result delCurrency(ApplicationCurrencyReq req) {
        if (null == req.getAppId()) {
            return Result.error("应用ID不能为空");
        }
        if (null == req.getCurrencyAppId()) {
            return Result.error("应用ID不能为空");
        }
        int cont = applicationCurrencyDao.delCurrency(req);
        if (cont == 0) {
            return Result.error("删除失败");
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

    @Override
    public Result queryAddId() {
        Long aLong = codeGenerateUtil.generateId(PrincipalEnum.MANAGE_APPLICATION);
        return Result.success(aLong);
    }

    @Override
    public Result<List<ApplicationResp>> currencyList(ApplicationProblemReq req) {
        if (null == req.getAppId()) {
            return Result.error("产品ID不能为空");
        }
        // 获取适用产品
        List<Application> appList = applicationDao.queryCurrencyList(req.getAppId());
        if (!appList.isEmpty()) {
            // 封面图
            setAppImg(appList);

        }
        List<ApplicationResp> appResps = BeanUtil.copyBeanList(appList, ApplicationResp.class);
        return Result.success(appResps);
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
        if (null == req.getAppRangeArray()) {
            return Result.error("适用范围不能为空");
        }
        if (null == req.getIndustryArray()) {
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
        // 处理中文枚举
        if (null != req.getAppRangeArray() && req.getAppRangeArray().length > 0) {
            String[] appRangeArray = req.getAppRangeArray();
            StringBuffer appSb = new StringBuffer();
            for (String appRangeStr : appRangeArray) {
                String streN = SkilledAppEnum.getMap().get(appRangeStr);
                appSb.append(streN).append(",");
            }
            application.setAppRange(appSb.toString().substring(0, appSb.toString().length() - 1));
        }
        // 处理中文枚举
        if (null != req.getIndustryArray() && req.getIndustryArray().length > 0) {
            String[] industryArray = req.getIndustryArray();
            StringBuffer industrySb = new StringBuffer();
            for (String industryStr : industryArray) {
                String streN = IndustryTypeEnum.getMap().get(industryStr);
                industrySb.append(streN).append(",");
            }
            application.setIndustry(industrySb.toString().substring(0, industrySb.toString().length() - 1));
        }

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
        // 处理中文枚举
        if (null != req.getAppRangeArray() && req.getAppRangeArray().length > 0) {
            String[] appRangeArray = req.getAppRangeArray();
            StringBuffer appSb = new StringBuffer();
            for (String appRangeStr : appRangeArray) {
                String streN = SkilledAppEnum.getMap().get(appRangeStr);
                appSb.append(streN).append(",");
            }
            application.setAppRange(appSb.toString().substring(0, appSb.toString().length() - 1));
        }
        // 处理中文枚举
        if (null != req.getIndustryArray() && req.getIndustryArray().length > 0) {
            String[] industryArray = req.getIndustryArray();
            StringBuffer industrySb = new StringBuffer();
            for (String industryStr : industryArray) {
                String streN = IndustryTypeEnum.getMap().get(industryStr);
                industrySb.append(streN).append(",");
            }
            application.setIndustry(industrySb.toString().substring(0, industrySb.toString().length() - 1));
        }
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

    /**
     *  格式化地址信息
     * @param supplier
     * @return
     */
    private void formatAddress(Supplier supplier) {
        // 格式化地址
        StringBuffer stringBuffer = new StringBuffer();
        if (StringUtils.isNoneBlank(supplier.getProvinceName())) {
            stringBuffer.append(supplier.getProvinceName());
        }
        if (StringUtils.isNoneBlank(supplier.getCityName())) {
            stringBuffer.append(supplier.getCityName());
        }
        if (StringUtils.isNoneBlank(supplier.getDistrictName())) {
            stringBuffer.append(supplier.getDistrictName());
        }
        supplier.setSupplierAddress(stringBuffer.toString());
    }
}

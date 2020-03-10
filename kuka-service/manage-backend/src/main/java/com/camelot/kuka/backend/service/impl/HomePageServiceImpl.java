package com.camelot.kuka.backend.service.impl;

import com.camelot.kuka.backend.dao.ApplicationDao;
import com.camelot.kuka.backend.dao.ApplicationImgDao;
import com.camelot.kuka.backend.dao.SupplierDao;
import com.camelot.kuka.backend.model.Application;
import com.camelot.kuka.backend.model.ApplicationImg;
import com.camelot.kuka.backend.model.Supplier;
import com.camelot.kuka.backend.service.HomePageService;
import com.camelot.kuka.common.utils.BeanUtil;
import com.camelot.kuka.model.backend.application.resp.ApplicationResp;
import com.camelot.kuka.model.backend.home.req.HomeAppPageReq;
import com.camelot.kuka.model.backend.home.req.HomeSupplierPageReq;
import com.camelot.kuka.model.backend.supplier.resp.SupplierResp;
import com.camelot.kuka.model.common.Result;
import com.camelot.kuka.model.enums.DeleteEnum;
import com.camelot.kuka.model.enums.application.AppStatusEnum;
import com.camelot.kuka.model.enums.application.AppTypeEnum;
import com.camelot.kuka.model.enums.home.IndustryTypeALLEnum;
import com.camelot.kuka.model.enums.home.SkilledAppALLEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>Description: [首页业务层]</p>
 * Created on 2020/2/5
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Slf4j
@Service("homePageService")
public class HomePageServiceImpl implements HomePageService {

    @Resource
    private ApplicationDao applicationDao;
    @Resource
    private SupplierDao supplierDao;
    @Resource
    private ApplicationImgDao applicationImgDao;


    @Override
    public Result<List<ApplicationResp>> appQuery() {
        Application query = new Application();
        query.setDelState(DeleteEnum.NO);
        query.setClassType(AppTypeEnum.SOFTWARE);
        query.setAppStatus(AppStatusEnum.YES);
        query.setSize(5);
        List<Application> appList = applicationDao.homeAppList(query);
        if (!appList.isEmpty()) {
            // 封面图
            setAppImg(appList);
        }
        return Result.success(BeanUtil.copyBeanList(appList, ApplicationResp.class));
    }

    @Override
    public Result<List<SupplierResp>> supplierQuery() {
        Supplier query = new Supplier();
        query.setDelState(DeleteEnum.NO);
        query.setSize(6);
        List<Supplier> supplierList = supplierDao.homeSupplierList(query);
        return Result.success(BeanUtil.copyBeanList(supplierList, SupplierResp.class));
    }

    @Override
    public List<Application> appPageList(HomeAppPageReq req) {
        req.setDelState(DeleteEnum.NO);
        req.setAppStatus(AppStatusEnum.YES);
        req.setQueryTypeCode(null != req.getQueryType() ? req.getQueryType().getCode() : null);
        List<Application> appList = applicationDao.homePageList(req);
        if (!appList.isEmpty()) {
            // 封面图
            setAppImg(appList);
        }
        return appList;
    }

    @Override
    public List<Supplier> supplierPageList(HomeSupplierPageReq req) {
        if (null != req && req.getIndustry() == IndustryTypeALLEnum.ALL) {
            req.setIndustry(null);
        }
        if (null != req && req.getAppType() == SkilledAppALLEnum.ALL) {
            req.setAppType(null);
        }
        req.setIndustryCode(null != req.getIndustry() ? req.getIndustry().toString() : null);
        req.setAppTypeCode(null != req.getAppType() ? req.getAppType().toString() : null);
        req.setDelState(DeleteEnum.NO);
        req.setQueryTypeCode(null != req.getQueryType() ? req.getQueryType().getCode() : null);
        return supplierDao.homeSupplierPageList(req);
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
}

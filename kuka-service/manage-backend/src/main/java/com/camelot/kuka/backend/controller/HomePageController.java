package com.camelot.kuka.backend.controller;

import com.alibaba.fastjson.JSON;
import com.camelot.kuka.backend.model.Application;
import com.camelot.kuka.backend.model.Supplier;
import com.camelot.kuka.backend.service.ApplicationService;
import com.camelot.kuka.backend.service.HomePageService;
import com.camelot.kuka.backend.service.SupplierService;
import com.camelot.kuka.common.controller.BaseController;
import com.camelot.kuka.model.backend.application.resp.ApplicationResp;
import com.camelot.kuka.model.backend.application.resp.QyeryUpdateResp;
import com.camelot.kuka.model.backend.home.req.HomeAppPageReq;
import com.camelot.kuka.model.backend.home.req.HomeSupplierPageReq;
import com.camelot.kuka.model.backend.supplier.resp.SupplierResp;
import com.camelot.kuka.model.common.CommonReq;
import com.camelot.kuka.model.common.EnumVal;
import com.camelot.kuka.model.common.PageResult;
import com.camelot.kuka.model.common.Result;
import com.camelot.kuka.model.enums.application.AppStatusEnum;
import com.camelot.kuka.model.enums.application.AppTypeEnum;
import com.camelot.kuka.model.enums.backend.IndustryTypeEnum;
import com.camelot.kuka.model.enums.backend.PatternTypeEnum;
import com.camelot.kuka.model.enums.backend.SkilledAppEnum;
import com.camelot.kuka.model.enums.backend.SupplierTypeEnum;
import com.camelot.kuka.model.enums.home.HomeQueryEnum;
import com.camelot.kuka.model.enums.home.IndustryTypeALLEnum;
import com.camelot.kuka.model.enums.home.SkilledAppALLEnum;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>Description: [首页控制层]</p>
 * Created on 2020/1/19
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Slf4j
@RestController
@Api(value = "首页API", tags = { "首页接口" })
public class HomePageController extends BaseController {

    @Resource
    private HomePageService homePageService;
    @Resource
    private ApplicationService applicationService;
    @Resource
    private SupplierService supplierService;


    /***
     * <p>Description:[KUKA软件查询]</p>
     * Created on 2020/1/20
     * @param
     * @return com.camelot.kuka.model.common.Result
     * @author 谢楠
     */
    @GetMapping("/home/appQuery")
    public Result<List<ApplicationResp>> appQuery(){
        try {
            return homePageService.appQuery();
        } catch (Exception e) {
            log.error("\n 首页模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "appQuery", JSON.toJSONString(null), e);
            return PageResult.error("网络异常, 请稍后再试");
        }
    }

    /***
     * <p>Description:[kuka集成商查询]</p>
     * Created on 2020/1/20
     * @param
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    @GetMapping("/home/supplierQuery")
    public Result<List<SupplierResp>> supplierQuery(){
        try {
            return homePageService.supplierQuery();
        } catch (Exception e) {
            log.error("\n 首页模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "supplierQuery", JSON.toJSONString(null), e);
            return PageResult.error("网络异常, 请稍后再试");
        }
    }

    /***
     * <p>Description:[应用商店-枚举]</p>
     * Created on 2020/1/20
     * @param req
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    @GetMapping("/home/appEnum")
    public PageResult appEnum(HomeAppPageReq req){
        try {
            PageResult page = new PageResult();
            page.putEnumVal("classTypeEnum", EnumVal.getEnumList(AppTypeEnum.class));
            page.putEnumVal("queryTypeEnum", EnumVal.getEnumList(HomeQueryEnum.class));
            return page;
        } catch (Exception e) {
            log.error("\n 首页模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "appEnum", JSON.toJSONString(req), e);
            return PageResult.error("网络异常, 请稍后再试");
        }
    }

    /***
     * <p>Description:[应用商店-分页查询]</p>
     * Created on 2020/1/20
     * @param req
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    @PostMapping("/home/appPageList")
    public PageResult<List<ApplicationResp>> appPageList(HomeAppPageReq req) {
        try {
            // 开启分页
            startPage();
            // 返回分页
            List<Application> application = homePageService.appPageList(req);
            PageResult<List<ApplicationResp>> page = getPage(application, ApplicationResp.class);
            page.putEnumVal("classTypeEnum", EnumVal.getEnumList(AppTypeEnum.class));
            page.putEnumVal("queryTypeEnum", EnumVal.getEnumList(HomeQueryEnum.class));
            return page;
        } catch (Exception e) {
            log.error("\n 首页模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "visitorPageList", JSON.toJSONString(req), e);
            return PageResult.error("网络异常, 请稍后再试");
        }
    }

    /***
     * <p>Description:[通过ID获取应用详情]</p>
     * Created on 2020/2/4
     * @param req
     * @return com.camelot.kuka.model.common.Result
     * @author 谢楠
     */
    @PostMapping("/home/appById")
    public Result<QyeryUpdateResp> appById(CommonReq req) {
        try {
            Result<QyeryUpdateResp> resp = applicationService.qyeryUpdateById(req);
            resp.putEnumVal("classTypeEnum", EnumVal.getEnumList(AppTypeEnum.class));
            resp.putEnumVal("appRangeEnum", EnumVal.getEnumList(SkilledAppEnum.class));
            resp.putEnumVal("industryEnum", EnumVal.getEnumList(IndustryTypeEnum.class));
            resp.putEnumVal("appStatusEnum", EnumVal.getEnumList(AppStatusEnum.class));
            return resp;
        } catch (Exception e) {
            log.error("\n 产品模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "appById", JSON.toJSONString(req), e);
            return Result.error("网络异常, 请稍后再试");
        }
    }

    /***
     * <p>Description:[集成商-枚举]</p>
     * Created on 2020/1/20
     * @param req
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    @GetMapping("/home/supplierEnum")
    public PageResult supplierEnum(HomeAppPageReq req){
        try {
            PageResult page = new PageResult();
            page.putEnumVal("industryEnum", EnumVal.getEnumList(IndustryTypeALLEnum.class));
            page.putEnumVal("appTypeEnum", EnumVal.getEnumList(SkilledAppALLEnum.class));
            page.putEnumVal("queryTypeEnum", EnumVal.getEnumList(HomeQueryEnum.class));
            return page;
        } catch (Exception e) {
            log.error("\n 首页模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "supplierEnum", JSON.toJSONString(req), e);
            return PageResult.error("网络异常, 请稍后再试");
        }
    }

    /***
     * <p>Description:[集成商-分页查询]</p>
     * Created on 2020/1/20
     * @param req
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    @PostMapping("/home/supplierPageList")
    public PageResult<List<SupplierResp>> supplierPageList(HomeSupplierPageReq req){
        try {
            // 开启分页
            startPage();
            // 返回分页
            List<Supplier> suppliers = homePageService.supplierPageList(req);
            PageResult<List<SupplierResp>> page = getPage(suppliers, SupplierResp.class);
            page.putEnumVal("industryEnum", EnumVal.getEnumList(IndustryTypeALLEnum.class));
            page.putEnumVal("appTypeEnum", EnumVal.getEnumList(SkilledAppALLEnum.class));
            page.putEnumVal("queryTypeEnum", EnumVal.getEnumList(HomeQueryEnum.class));
            return page;
        } catch (Exception e) {
            log.error("\n 集成商模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "supplierPageList", JSON.toJSONString(req), e);
            return PageResult.error("网络异常, 请稍后再试");
        }
    }

    /***
     * <p>Description:[集成商-单条查询]</p>
     * Created on 2020/1/20
     * @param req
     * @return com.camelot.kuka.model.common.Result
     * @author 谢楠
     */
    @PostMapping("/home/supplierById")
    public Result<SupplierResp> supplierById(CommonReq req){
        try {
            Result<SupplierResp> resp = supplierService.querySuppAndAppById(req);
            resp.putEnumVal("typeEnum", EnumVal.getEnumList(SupplierTypeEnum.class));
            resp.putEnumVal("industryEnum", EnumVal.getEnumList(IndustryTypeALLEnum.class));
            resp.putEnumVal("appTypeEnum", EnumVal.getEnumList(SkilledAppALLEnum.class));
            resp.putEnumVal("patternTypeEnum", EnumVal.getEnumList(PatternTypeEnum.class));
            return resp;
        } catch (Exception e) {
            log.error("\n 集成商模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "supplierById", JSON.toJSONString(req), e);
            return Result.error("网络异常, 请稍后再试");
        }
    }


}

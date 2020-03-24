package com.camelot.kuka.backend.controller;

import com.alibaba.fastjson.JSON;
import com.camelot.kuka.backend.model.Application;
import com.camelot.kuka.backend.service.ApplicationService;
import com.camelot.kuka.backend.service.SupplierService;
import com.camelot.kuka.common.controller.BaseController;
import com.camelot.kuka.common.utils.AppUserUtil;
import com.camelot.kuka.model.backend.application.req.AppPageReq;
import com.camelot.kuka.model.backend.application.req.ApplicationCurrencyReq;
import com.camelot.kuka.model.backend.application.req.ApplicationEditReq;
import com.camelot.kuka.model.backend.application.resp.ApplicationResp;
import com.camelot.kuka.model.backend.application.resp.QyeryUpdateResp;
import com.camelot.kuka.model.common.CommonReq;
import com.camelot.kuka.model.common.EnumVal;
import com.camelot.kuka.model.common.PageResult;
import com.camelot.kuka.model.common.Result;
import com.camelot.kuka.model.enums.DeleteEnum;
import com.camelot.kuka.model.enums.application.AppStatusEnum;
import com.camelot.kuka.model.enums.application.AppTypeEnum;
import com.camelot.kuka.model.enums.backend.ApplicationPageEnum;
import com.camelot.kuka.model.enums.backend.IndustryTypeEnum;
import com.camelot.kuka.model.enums.backend.SkilledAppEnum;
import com.camelot.kuka.model.enums.user.UserTypeEnum;
import com.camelot.kuka.model.user.LoginAppUser;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>Description: [产品控制层]</p>
 * Created on 2020/1/19
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Slf4j
@RestController
@Api(value = "产品API", tags = { "产品接口" })
public class ApplicationController extends BaseController {

    @Autowired
    private ApplicationService applicationService;
    @Resource
    private SupplierService supplierService;

    /***
     * <p>Description:[枚举查询]</p>
     * Created on 2020/1/20
     * @param
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    @PostMapping("/application/queryEnum")
    public PageResult queryEnum(){
        PageResult page = new PageResult();
        page.putEnumVal("classTypeEnum", EnumVal.getEnumList(AppTypeEnum.class));
        page.putEnumVal("appRangeEnum", EnumVal.getEnumList(SkilledAppEnum.class));
        page.putEnumVal("industryEnum", EnumVal.getEnumList(IndustryTypeEnum.class));
        page.putEnumVal("appStatusEnum", EnumVal.getEnumList(AppStatusEnum.class));
        page.putEnumVal("delStateEnum", EnumVal.getEnumList(DeleteEnum.class));
        page.putEnumVal("queryTypeEnum", EnumVal.getEnumList(ApplicationPageEnum.class));
        return page;
    }

    /***
     * <p>Description:[分页查询]</p>
     * Created on 2020/1/20
     * @param req
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    @PostMapping("/application/pageList")
    public PageResult<List<ApplicationResp>> pageList(AppPageReq req){
        try {
            LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
            if (null == loginAppUser) {
                return PageResult.error("用户未登陆");
            }
            // 集成商
            if (loginAppUser.getType() == UserTypeEnum.SUPPILER) {
                // 获取当前用户拥有的集成商
                req.setLoginName(loginAppUser.getUsername());
                req.setSupplierIds(supplierService.queryLoginSupplierIds(loginAppUser.getUserName()));
            }
            if (loginAppUser.getType() == UserTypeEnum.VISITORS ) {
                req.setLoginName(loginAppUser.getUsername());
                // 来访者没有集成商
                req.setSupplierIds(new Long[]{-1L});
            }
            // 开启分页
            startPage();
            // 返回分页
            List<Application> application = applicationService.queryList(req, loginAppUser);
            PageResult<List<ApplicationResp>> page = getPage(application, ApplicationResp.class);
            page.putEnumVal("classTypeEnum", EnumVal.getEnumList(AppTypeEnum.class));
            page.putEnumVal("appRangeEnum", EnumVal.getEnumList(SkilledAppEnum.class));
            page.putEnumVal("industryEnum", EnumVal.getEnumList(IndustryTypeEnum.class));
            page.putEnumVal("appStatusEnum", EnumVal.getEnumList(AppStatusEnum.class));
            page.putEnumVal("delStateEnum", EnumVal.getEnumList(DeleteEnum.class));
            page.putEnumVal("queryTypeEnum", EnumVal.getEnumList(ApplicationPageEnum.class));
            return page;
        } catch (Exception e) {
            log.error("\n 产品模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "pageList", JSON.toJSONString(req), e);
            return PageResult.error("网络异常, 请稍后再试");
        }
    }

    //查询全部应用
    @PostMapping("/application/pageListAll")
    public PageResult<List<ApplicationResp>> pageListAll(AppPageReq req){
        try {
            LoginAppUser loginAppUser = new LoginAppUser();
            loginAppUser.setType(UserTypeEnum.KUKA);
            // 开启分页
            startPage();
            // 返回分页
            List<Application> application = applicationService.queryList(req, loginAppUser);
            PageResult<List<ApplicationResp>> page = getPage(application, ApplicationResp.class);
            page.putEnumVal("classTypeEnum", EnumVal.getEnumList(AppTypeEnum.class));
            page.putEnumVal("appRangeEnum", EnumVal.getEnumList(SkilledAppEnum.class));
            page.putEnumVal("industryEnum", EnumVal.getEnumList(IndustryTypeEnum.class));
            page.putEnumVal("appStatusEnum", EnumVal.getEnumList(AppStatusEnum.class));
            page.putEnumVal("delStateEnum", EnumVal.getEnumList(DeleteEnum.class));
            page.putEnumVal("queryTypeEnum", EnumVal.getEnumList(ApplicationPageEnum.class));
            return page;
        } catch (Exception e) {
            log.error("\n 产品模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "pageList", JSON.toJSONString(req), e);
            return PageResult.error("网络异常, 请稍后再试");
        }
    }

    /***
     * <p>Description:[新增产品信息]</p>
     * Created on 2020/2/4
     * @param req
     * @return com.camelot.kuka.model.common.Result
     * @author 谢楠
     */
    @PostMapping("/application/add")
    public Result addApplication(ApplicationEditReq req){
        try {
            String loginUserName = AppUserUtil.getLoginUserName();
            return applicationService.addApplication(req, loginUserName);
        } catch (Exception e) {
            log.error("\n 产品模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "addApplication", JSON.toJSONString(req), e);
            return Result.error("网络异常, 请稍后再试");
        }
    }

    /***
     * <p>Description:[新增适用产品信息]</p>
     * Created on 2020/2/4
     * @param
     * @return com.camelot.kuka.model.common.Result
     * @author 谢楠
     */
    @GetMapping("/application/queryAddId")
    public Result queryAddId(){
        try {
            return applicationService.queryAddId();
        } catch (Exception e) {
            log.error("\n 产品模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "queryAddId", JSON.toJSONString(null), e);
            return Result.error("网络异常, 请稍后再试");
        }
    }

    /***
     * <p>Description:[新增适用产品信息]</p>
     * Created on 2020/2/4
     * @param req
     * @return com.camelot.kuka.model.common.Result
     * @author 谢楠
     */
    @PostMapping("/application/addCurrency")
    public Result addCurrency(ApplicationCurrencyReq req){
        try {
            return applicationService.addCurrency(req);
        } catch (Exception e) {
            log.error("\n 产品模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "addCurrency", JSON.toJSONString(req), e);
            return Result.error("网络异常, 请稍后再试");
        }
    }

    /***
     * <p>Description:[通过ID获取]</p>
     * Created on 2020/2/4
     * @param req
     * @return com.camelot.kuka.model.common.Result
     * @author 谢楠
     */
    @PostMapping("/application/qyeryUpdateById")
    public Result<QyeryUpdateResp> qyeryUpdateById(CommonReq req){
        try {
            return applicationService.qyeryUpdateById(req);
        } catch (Exception e) {
            log.error("\n 产品模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "qyeryUpdateById", JSON.toJSONString(req), e);
            return Result.error("网络异常, 请稍后再试");
        }
    }

    /***
     * <p>Description:[修改]</p>
     * Created on 2020/2/4
     * @param req
     * @return com.camelot.kuka.model.common.Result
     * @author 谢楠
     */
    @PostMapping("/application/update")
    public Result updateApplication(ApplicationEditReq req){
        try {
            String loginUserName = AppUserUtil.getLoginUserName();
            return applicationService.updateApplication(req, loginUserName);
        } catch (Exception e) {
            log.error("\n 产品模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "updateApplication", JSON.toJSONString(req), e);
            return Result.error("网络异常, 请稍后再试");
        }
    }

    /***
     * <p>Description:[应用审核]</p>
     * Created on 2020/2/4
     * @param req
     * @return com.camelot.kuka.model.common.Result
     * @author 谢楠
     */
    @PostMapping("/application/updateAppStatus")
    public Result updateAppStatus(ApplicationEditReq req){
        try {
            String loginUserName = AppUserUtil.getLoginUserName();
            return applicationService.updateAppStatus(req, loginUserName);
        } catch (Exception e) {
            log.error("\n 产品模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "updateAppStatus", JSON.toJSONString(req), e);
            return Result.error("网络异常, 请稍后再试");
        }
    }

    /***
     * <p>Description:[删除产品]</p>
     * Created on 2020/2/4
     * @param req
     * @return com.camelot.kuka.model.common.Result
     * @author 谢楠
     */
    @PostMapping("/application/delete")
    public Result deleteApplication(CommonReq req){
        try {
            String loginUserName = AppUserUtil.getLoginUserName();
            return applicationService.deleteApplication(req, loginUserName);
        } catch (Exception e) {
            log.error("\n 产品模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "deleteApplication", JSON.toJSONString(req), e);
            return Result.error("网络异常, 请稍后再试");
        }
    }



}

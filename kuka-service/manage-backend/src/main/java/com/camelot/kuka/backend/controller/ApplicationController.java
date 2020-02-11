package com.camelot.kuka.backend.controller;

import com.camelot.kuka.backend.model.Application;
import com.camelot.kuka.backend.service.ApplicationService;
import com.camelot.kuka.common.controller.BaseController;
import com.camelot.kuka.common.utils.AppUserUtil;
import com.camelot.kuka.model.backend.application.req.AppPageReq;
import com.camelot.kuka.model.backend.application.req.ApplicationCurrencyReq;
import com.camelot.kuka.model.backend.application.req.ApplicationEditReq;
import com.camelot.kuka.model.backend.application.req.ApplicationProblemReq;
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
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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
        // 开启分页
        startPage();
        // 返回分页
        List<Application> application = applicationService.queryList(req);
        PageResult<List<ApplicationResp>> page = getPage(application, ApplicationResp.class);
        page.putEnumVal("classTypeEnum", EnumVal.getEnumList(AppTypeEnum.class));
        page.putEnumVal("appRangeEnum", EnumVal.getEnumList(SkilledAppEnum.class));
        page.putEnumVal("industryEnum", EnumVal.getEnumList(IndustryTypeEnum.class));
        page.putEnumVal("appStatusEnum", EnumVal.getEnumList(AppStatusEnum.class));
        page.putEnumVal("delStateEnum", EnumVal.getEnumList(DeleteEnum.class));
        page.putEnumVal("queryTypeEnum", EnumVal.getEnumList(ApplicationPageEnum.class));
        return page;
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
        String loginUserName = AppUserUtil.getLoginUserName();
        return applicationService.addApplication(req, loginUserName);
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
        return applicationService.addCurrency(req);
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
        return applicationService.qyeryUpdateById(req);
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
        String loginUserName = AppUserUtil.getLoginUserName();
        return applicationService.updateApplication(req, loginUserName);
    }

    /***
     * <p>Description:[修改上线下线]</p>
     * Created on 2020/2/4
     * @param req
     * @return com.camelot.kuka.model.common.Result
     * @author 谢楠
     */
    @PostMapping("/application/updateAppStatus")
    public Result updateAppStatus(ApplicationEditReq req){
        String loginUserName = AppUserUtil.getLoginUserName();
        return applicationService.updateAppStatus(req, loginUserName);
    }
}

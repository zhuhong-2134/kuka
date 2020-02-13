package com.camelot.kuka.backend.controller;

import com.alibaba.fastjson.JSON;
import com.camelot.kuka.backend.service.ApplicationProbleService;
import com.camelot.kuka.common.controller.BaseController;
import com.camelot.kuka.common.utils.AppUserUtil;
import com.camelot.kuka.model.backend.application.req.ApplicationProblemReq;
import com.camelot.kuka.model.common.Result;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>Description: [应用常见问题控制层]</p>
 * Created on 2020/1/19
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Slf4j
@RestController
@Api(value = "应用常见问题API", tags = { "应用常见问题接口" })
public class AppProbleController extends BaseController {

    @Resource
    private ApplicationProbleService applicationProbleService;


    /***
     * <p>Description:[新增]</p>
     * Created on 2020/2/4
     * @param req
     * @return com.camelot.kuka.model.common.Result
     * @author 谢楠
     */
    @PostMapping("/app/proble/add")
    public Result addProbleApplication(ApplicationProblemReq req){
        try {
            String loginUserName = AppUserUtil.getLoginUserName();
            return applicationProbleService.addProbleApplication(req, loginUserName);
        } catch (Exception e) {
            log.error("\n 应用常见问模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "addProbleApplication", JSON.toJSONString(req), e);
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
    @PostMapping("/app/proble/update")
    public Result updateProbleApplication(ApplicationProblemReq req){
        try {
            String loginUserName = AppUserUtil.getLoginUserName();
            return applicationProbleService.updateProbleApplication(req, loginUserName);
        } catch (Exception e) {
            log.error("\n 应用常见问模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "updateProbleApplication", JSON.toJSONString(req), e);
            return Result.error("网络异常, 请稍后再试");
        }
    }
}

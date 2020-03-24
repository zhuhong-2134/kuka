package com.camelot.kuka.backend.controller;

import com.alibaba.fastjson.JSON;
import com.camelot.kuka.backend.feign.user.UserClient;
import com.camelot.kuka.backend.model.SupplierRequest;
import com.camelot.kuka.backend.service.SupplierRequestService;
import com.camelot.kuka.backend.service.SupplierService;
import com.camelot.kuka.common.controller.BaseController;
import com.camelot.kuka.common.utils.AppUserUtil;
import com.camelot.kuka.model.backend.message.resp.MessageResp;
import com.camelot.kuka.model.backend.supplierrequest.req.SupplierRequestPageReq;
import com.camelot.kuka.model.backend.supplierrequest.req.SupplierRequestReq;
import com.camelot.kuka.model.backend.supplierrequest.resp.SupplierRequestResp;
import com.camelot.kuka.model.common.CommonReq;
import com.camelot.kuka.model.common.EnumVal;
import com.camelot.kuka.model.common.PageResult;
import com.camelot.kuka.model.common.Result;
import com.camelot.kuka.model.enums.CommunicateEnum;
import com.camelot.kuka.model.enums.DeleteEnum;
import com.camelot.kuka.model.enums.backend.SupplierRequestPageEnum;
import com.camelot.kuka.model.enums.user.UserTypeEnum;
import com.camelot.kuka.model.enums.user.WhetherEnum;
import com.camelot.kuka.model.user.LoginAppUser;
import com.camelot.kuka.model.user.req.UserReq;
import com.camelot.kuka.model.user.resp.UserResp;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>Description: [集成商请求控制层]</p>
 * Created on 2020/2/19
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Slf4j
@RestController
@Api(value = "集成商请求API", tags = { "集成商请求接口" })
public class SupplierRequestController extends BaseController {

    @Resource
    private SupplierRequestService supplierRequestService;
    @Resource
    private SupplierService supplierService;
    @Resource
    private UserClient userClient;


    /***
     * <p>Description:[枚举查询]</p>
     * Created on 2020/1/20
     * @param
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    @PostMapping("/supplierequest/queryEnum")
    public PageResult queryEnum(){
        PageResult page = new PageResult();
        page.putEnumVal("statusEnum", EnumVal.getEnumList(CommunicateEnum.class));
        page.putEnumVal("delStateEnum", EnumVal.getEnumList(DeleteEnum.class));
        page.putEnumVal("queryTypeEnum", EnumVal.getEnumList(SupplierRequestPageEnum.class));
        page.putEnumVal("whetherEnum", EnumVal.getEnumList(WhetherEnum.class));
        return page;
    }

    /***
     * <p>Description:[分页查询]</p>
     * Created on 2020/1/20
     * @param req
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    @PostMapping("/supplierequest/pageList")
    public PageResult<List<SupplierRequestResp>> pageList(SupplierRequestPageReq req){
        try {
            LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
            if (null == loginAppUser) {
                return PageResult.error("用户未登陆");
            }
            // 集成商
            if (loginAppUser.getType() == UserTypeEnum.SUPPILER) {
                req.setSupplierIds(supplierService.queryLoginSupplierIds(loginAppUser.getUserName()));
            }
            if (loginAppUser.getType() == UserTypeEnum.VISITORS ) {
                req.setLoginName(loginAppUser.getUsername());
            }
            if(req.getQueryType() != null && (req.getQueryType()== SupplierRequestPageEnum.USERNAME || req.getQueryType()== SupplierRequestPageEnum.ALL)){
                UserReq userReq = new UserReq();
                userReq.setName(req.getQueryName());
                Result<List<UserResp>> userRespResult = userClient.queryByInfo(userReq);
                if (userRespResult.isSuccess() && !userRespResult.getData().isEmpty()) {
                    // 用户ID
                    Long[] userIds = userRespResult.getData().stream().map(UserResp::getId).toArray(Long[]::new);
                    req.setUserIds(userIds);
                }
            }
            // 开启分页
            startPage();
            // 返回分页
            List<SupplierRequest> application = supplierRequestService.queryList(req);
            PageResult<List<SupplierRequestResp>> page = getPage(application, SupplierRequestResp.class);
            page.putEnumVal("statusEnum", EnumVal.getEnumList(CommunicateEnum.class));
            page.putEnumVal("delStateEnum", EnumVal.getEnumList(DeleteEnum.class));
            page.putEnumVal("queryTypeEnum", EnumVal.getEnumList(SupplierRequestPageEnum.class));
            page.putEnumVal("whetherEnum", EnumVal.getEnumList(WhetherEnum.class));
            return page;
        } catch (Exception e) {
            log.error("\n 集成商请求模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "pageList", JSON.toJSONString(req), e);
            return PageResult.error("网络异常, 请稍后再试");
        }
    }

    /***
     * <p>Description:[通过ID获取]</p>
     * Created on 2020/2/4
     * @param req
     * @return com.camelot.kuka.model.common.Result
     * @author 谢楠
     */
    @PostMapping("/supplierequest/queryById")
    public Result<SupplierRequestResp> queryById(CommonReq req){
        try {
            return supplierRequestService.queryById(req);
        } catch (Exception e) {
            log.error("\n 集成商请求模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "queryById", JSON.toJSONString(req), e);
            return Result.error("网络异常, 请稍后再试");
        }
    }

    /***
     * <p>Description:[修改状态]</p>
     * Created on 2020/2/4
     * @param req
     * @return com.camelot.kuka.model.common.Result
     * @author 谢楠
     */
    @PostMapping("/supplierequest/updateStatus")
    public Result updateStatus(SupplierRequestReq req){
        try {
            String loginUserName = AppUserUtil.getLoginUserName();
            if (null == loginUserName) {
                return Result.error("用户未登录");
            }
            return supplierRequestService.updateStatus(req, loginUserName);
        } catch (Exception e) {
            log.error("\n 集成商请求模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "updateStatus", JSON.toJSONString(req), e);
            return Result.error("网络异常, 请稍后再试");
        }
    }

    /***
     * <p>Description:[新增请求]</p>
     * Created on 2020/2/4
     * @param req
     * @return com.camelot.kuka.model.common.Result
     * @author 谢楠
     */
    @PostMapping("/supplierequest/add")
    public Result addSupplierequest(CommonReq req){
        try {
            LoginAppUser loginAppUser = AppUserUtil.getLoginUser();
            if (null == loginAppUser) {
                return Result.error("用户未登录");
            }
            return supplierRequestService.addSupplierequest(req, loginAppUser);
        } catch (Exception e) {
            log.error("\n 集成商请求模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "addSupplierequest", JSON.toJSONString(req), e);
            return Result.error("网络异常, 请稍后再试");
        }
    }

    /***
     * <p>Description:[发送邮件]</p>
     * Created on 2020/2/4
     * @param req
     * @return com.camelot.kuka.model.common.Result
     * @author 谢楠
     */
    @PostMapping("/supplierequest/sendMail")
    public Result sendMail(CommonReq req){
        try {
            LoginAppUser loginAppUser = AppUserUtil.getLoginUser();
            if (null == loginAppUser) {
                return Result.error("用户未登录");
            }
            return supplierRequestService.sendMail(req, loginAppUser);
        } catch (Exception e) {
            log.error("\n 集成商请求模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "addSupplierequest", JSON.toJSONString(req), e);
            return Result.error("网络异常, 请稍后再试");
        }
    }

    /***
     * <p>Description:[预览邮件信息]</p>
     * Created on 2020/2/4
     * @param req
     * @return com.camelot.kuka.model.common.Result
     * @author 谢楠
     */
    @PostMapping("/supplierequest/previewMessage")
    public Result<MessageResp> previewMessage(CommonReq req){
        try {
            LoginAppUser loginAppUser = AppUserUtil.getLoginUser();
            if (null == loginAppUser) {
                return Result.error("用户未登录");
            }
            return supplierRequestService.previewMessage(req, loginAppUser);
        } catch (Exception e) {
            log.error("\n 集成商请求模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "previewMessage", JSON.toJSONString(req), e);
            return Result.error("网络异常, 请稍后再试");
        }
    }
}

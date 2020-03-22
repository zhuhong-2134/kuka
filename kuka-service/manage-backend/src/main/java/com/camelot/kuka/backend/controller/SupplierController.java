package com.camelot.kuka.backend.controller;

import com.alibaba.fastjson.JSON;
import com.camelot.kuka.backend.model.Supplier;
import com.camelot.kuka.backend.service.SupplierService;
import com.camelot.kuka.common.controller.BaseController;
import com.camelot.kuka.common.utils.AppUserUtil;
import com.camelot.kuka.model.backend.supplier.req.SupplierPageReq;
import com.camelot.kuka.model.backend.supplier.req.SupplierReq;
import com.camelot.kuka.model.backend.supplier.resp.SupplierResp;
import com.camelot.kuka.model.common.CommonReq;
import com.camelot.kuka.model.common.EnumVal;
import com.camelot.kuka.model.common.PageResult;
import com.camelot.kuka.model.common.Result;
import com.camelot.kuka.model.enums.DeleteEnum;
import com.camelot.kuka.model.enums.backend.*;
import com.camelot.kuka.model.enums.user.CreateSourceEnum;
import com.camelot.kuka.model.enums.user.UserTypeEnum;
import com.camelot.kuka.model.user.LoginAppUser;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>Description: [供应商控制层]</p>
 * Created on 2020/1/19
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Slf4j
@RestController
@Api(value = "供应商API", tags = { "供应商接口" })
public class SupplierController extends BaseController {

    @Autowired
    private SupplierService supplierService;

    /***
     * <p>Description:[枚举查询]</p>
     * Created on 2020/1/20
     * @param
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    @PostMapping("/supplier/queryEnum")
    public PageResult queryEnum(){
        PageResult page = new PageResult();
        page.putEnumVal("typeEnum", EnumVal.getEnumList(SupplierTypeEnum.class));
        page.putEnumVal("industryEnum", EnumVal.getEnumList(IndustryTypeEnum.class));
        page.putEnumVal("appTypeEnum", EnumVal.getEnumList(SkilledAppEnum.class));
        page.putEnumVal("patternTypeEnum", EnumVal.getEnumList(PatternTypeEnum.class));
        page.putEnumVal("delStateEnum", EnumVal.getEnumList(DeleteEnum.class));
        page.putEnumVal("queryTypeEnum", EnumVal.getEnumList(SuppliePageEnum.class));
        page.putEnumVal("sourceEnum", EnumVal.getEnumList(CreateSourceEnum.class));
        return page;
    }

    /***
     * <p>Description:[kuka后台-分页查询]</p>
     * Created on 2020/1/20
     * @param req
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    @PostMapping("/supplier/pageList")
    public PageResult<List<SupplierResp>> pageList(SupplierPageReq req){
        try {
            LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
            if (null == loginAppUser) {
                return PageResult.error("用户未登陆");
            }
            // 开启分页
            startPage();
            // 返回分页
            List<Supplier> suppliers = supplierService.pageList(req, loginAppUser);
            PageResult<List<SupplierResp>> page = getPage(suppliers, SupplierResp.class);
            page.putEnumVal("typeEnum", EnumVal.getEnumList(SupplierTypeEnum.class));
            page.putEnumVal("industryEnum", EnumVal.getEnumList(IndustryTypeEnum.class));
            page.putEnumVal("appTypeEnum", EnumVal.getEnumList(SkilledAppEnum.class));
            page.putEnumVal("patternTypeEnum", EnumVal.getEnumList(PatternTypeEnum.class));
            page.putEnumVal("delStateEnum", EnumVal.getEnumList(DeleteEnum.class));
            page.putEnumVal("queryTypeEnum", EnumVal.getEnumList(SuppliePageEnum.class));
            page.putEnumVal("sourceEnum", EnumVal.getEnumList(CreateSourceEnum.class));
            return page;
        } catch (Exception e) {
            log.error("\n 集成商模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "queryList", JSON.toJSONString(req), e);
            return PageResult.error("网络异常, 请稍后再试");
        }
    }

    /***
     * <p>Description:[获取列表]</p>
     * Created on 2020/1/20
     * @param req
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    @PostMapping("/supplier/list")
    public Result<List<SupplierResp>> queryList(SupplierPageReq req){
        try {
            LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
            if (null == loginAppUser) {
                return PageResult.error("用户未登陆");
            }
            if (loginAppUser.getType() == UserTypeEnum.SUPPILER) {
                req.setLoginName(loginAppUser.getUsername());
            }
            return supplierService.queryList(req);
        } catch (Exception e) {
            log.error("\n 集成商模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "queryList", JSON.toJSONString(req), e);
            return Result.error("网络异常, 请稍后再试");
        }
    }

    /***
     * <p>Description:[新增]</p>
     * Created on 2020/2/4
     * @param req
     * @return com.camelot.kuka.model.common.Result
     * @author 谢楠
     */
    @PostMapping("/supplier/add")
    public Result addSupplier(SupplierReq req){
        try {
            String loginUserName = AppUserUtil.getLoginUserName();
            return supplierService.addSupplier(req, loginUserName);
        } catch (Exception e) {
            log.error("\n 集成商模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "addSupplier", JSON.toJSONString(req), e);
            return Result.error("网络异常, 请稍后再试");
        }
    }

    /***
     * <p>Description:[单条查询]</p>
     * Created on 2020/1/20
     * @param req
     * @return com.camelot.kuka.model.common.Result
     * @author 谢楠
     */
    @PostMapping("/supplier/queryById")
    public Result<SupplierResp> queryById(CommonReq req){
        try {
            if (null == req.getId()) {
                LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
                if (null == loginAppUser) {
                    return Result.error("用户未登陆");
                }
                req.setUserId(loginAppUser.getId());
            }
            return supplierService.queryById(req);
        } catch (Exception e) {
            log.error("\n 集成商模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "queryById", JSON.toJSONString(req), e);
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
    @PostMapping("/supplier/update")
    public Result updateSupplier(SupplierReq req){
        try {
            String loginUserName = AppUserUtil.getLoginUserName();
            return supplierService.updateSupplier(req, loginUserName);
        } catch (Exception e) {
            log.error("\n 集成商模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "delSupplier", JSON.toJSONString(req), e);
            return Result.error("网络异常, 请稍后再试");
        }
    }

    /***
     * <p>Description:[删除]</p>
     * Created on 2020/2/4
     * @param req
     * @return com.camelot.kuka.model.common.Result
     * @author 谢楠
     */
    @PostMapping("/supplier/del")
    public Result delSupplier(CommonReq req){
        try {
            String loginUserName = AppUserUtil.getLoginUserName();
            return supplierService.delSupplier(req, loginUserName);
        } catch (Exception e) {
            log.error("\n 集成商模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "delSupplier", JSON.toJSONString(req), e);
            return Result.error("网络异常, 请稍后再试");
        }
    }

    /***
     * <p>Description:[根据创建人获取集成商]</p>
     * Created on 2020/1/20
     * @param userName
     * @return com.camelot.kuka.model.common.Result
     * @author 谢楠
     */
    @GetMapping(value = "/supplier/queryByCreateName", params = "userName")
    public Result<SupplierResp> queryByCreateName(String userName){
        try {
            return supplierService.queryByCreateName(userName);
        } catch (Exception e) {
            log.error("\n 集成商模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "queryByCreateName", JSON.toJSONString(userName), e);
            return Result.error("网络异常, 请稍后再试");
        }
    }
}

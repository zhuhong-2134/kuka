package com.camelot.kuka.user.controller;

import com.alibaba.fastjson.JSON;
import com.camelot.kuka.common.controller.BaseController;
import com.camelot.kuka.common.utils.AppUserUtil;
import com.camelot.kuka.model.common.CommonReq;
import com.camelot.kuka.model.common.EnumVal;
import com.camelot.kuka.model.common.PageResult;
import com.camelot.kuka.model.common.Result;
import com.camelot.kuka.model.enums.user.role.RoleMenuEnum;
import com.camelot.kuka.model.enums.user.role.RolePageEnum;
import com.camelot.kuka.model.enums.user.role.RoleStatusEnum;
import com.camelot.kuka.model.user.role.req.RoleMenuReq;
import com.camelot.kuka.model.user.role.req.RolePageReq;
import com.camelot.kuka.model.user.role.req.RoleReq;
import com.camelot.kuka.model.user.role.resp.RoleMenuResp;
import com.camelot.kuka.model.user.role.resp.RoleResp;
import com.camelot.kuka.user.model.Role;
import com.camelot.kuka.user.service.RoleService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


/**
 * <p>Description: [角色信息]</p>
 * Created on 2020/1/19
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Slf4j
@RestController
@Api(value = "角色信息API", tags = { "角色信息接口" })
public class RoleController extends BaseController {

    @Resource
    private RoleService roleService;

    /***
     * <p>Description:[枚举查询]</p>
     * Created on 2020/1/20
     * @param
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    @PostMapping("/role/queryEnum")
    public PageResult queryEnum(){
        PageResult page = new PageResult();
        page.putEnumVal("statusEnum", EnumVal.getEnumList(RoleStatusEnum.class));
        page.putEnumVal("queryTypeEnum", EnumVal.getEnumList(RolePageEnum.class));
        page.putEnumVal("roleMenuEnum", EnumVal.getEnumList(RoleMenuEnum.class));
        return page;
    }

    /***
     * <p>Description:[分页查询]</p>
     * Created on 2020/1/20
     * @param req
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    @PostMapping("/role/pageList")
    public PageResult<List<RoleResp>> pageList(RolePageReq req){
        try {
            // 开启分页
            startPage();
            // 返回分页
            List<Role> roleList = roleService.queryList(req);
            PageResult<List<RoleResp>> page = getPage(roleList, RoleResp.class);
            page.putEnumVal("statusEnum", EnumVal.getEnumList(RoleStatusEnum.class));
            page.putEnumVal("queryTypeEnum", EnumVal.getEnumList(RolePageEnum.class));
            page.putEnumVal("roleMenuEnum", EnumVal.getEnumList(RoleMenuEnum.class));
            return page;
        } catch (Exception e) {
            log.error("\n 角色模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "pageList", JSON.toJSONString(req), e);
            return PageResult.error("网络异常, 请稍后再试");
        }
    }

    /***
     * <p>Description:[新增角色信息]</p>
     * Created on 2020/2/4
     * @param req
     * @return com.camelot.kuka.model.common.Result
     * @author 谢楠
     */
    @PostMapping("/role/add")
    public Result addRole(RoleReq req){
        try {
            String loginUserName = AppUserUtil.getLoginUserName();
            return roleService.addRole(req, loginUserName);
        } catch (Exception e) {
            log.error("\n 角色模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "addRole", JSON.toJSONString(req), e);
            return Result.error("网络异常, 请稍后再试");
        }
    }

    /***
     * <p>Description:[通过ID获取角色信息]</p>
     * Created on 2020/2/4
     * @param req
     * @return com.camelot.kuka.model.common.Result
     * @author 谢楠
     */
    @PostMapping("/role/qeuryById")
    public Result<RoleResp> qeuryById(CommonReq req){
        try {
            return roleService.qeuryById(req );
        } catch (Exception e) {
            log.error("\n 角色模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "qeuryById", JSON.toJSONString(req), e);
            return Result.error("网络异常, 请稍后再试");
        }
    }

    /***
     * <p>Description:[修改角色信息]</p>
     * Created on 2020/2/4
     * @param req
     * @return com.camelot.kuka.model.common.Result
     * @author 谢楠
     */
    @PostMapping("/role/update")
    public Result updateRole(RoleReq req){
        try {
            String loginUserName = AppUserUtil.getLoginUserName();
            return roleService.updateRole(req, loginUserName);
        } catch (Exception e) {
            log.error("\n 角色模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "updateRole", JSON.toJSONString(req), e);
            return Result.error("网络异常, 请稍后再试");
        }
    }

    /***
     * <p>Description:[上线下线修改]</p>
     * Created on 2020/2/4
     * @param req
     * @return com.camelot.kuka.model.common.Result
     * @author 谢楠
     */
    @PostMapping("/role/updateStatus")
    public Result updateStatus(RoleReq req){
        try {
            String loginUserName = AppUserUtil.getLoginUserName();
            return roleService.updateStatus(req, loginUserName);
        } catch (Exception e) {
            log.error("\n 角色模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "updateStatus", JSON.toJSONString(req), e);
            return Result.error("网络异常, 请稍后再试");
        }
    }

    /***
     * <p>Description:[获取权限信息]</p>
     * Created on 2020/2/4
     * @param req
     * @return com.camelot.kuka.model.common.Result
     * @author 谢楠
     */
    @PostMapping("/role/roleMenu")
    public Result<List<RoleMenuResp>> roleMenu(CommonReq req){
        try {
            return roleService.roleMenu(req );
        } catch (Exception e) {
            log.error("\n 角色模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "roleMenu", JSON.toJSONString(req), e);
            return Result.error("网络异常, 请稍后再试");
        }
    }

    /***
     * <p>Description:[权限关联]</p>
     * Created on 2020/2/4
     * @param req
     * @return com.camelot.kuka.model.common.Result
     * @author 谢楠
     */
    @PostMapping("/role/updateRoleMenu")
    public Result updateRoleMenu(RoleMenuReq req){
        try {
            return roleService.updateRoleMenu(req);
        } catch (Exception e) {
            log.error("\n 角色模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "updateRoleMenu", JSON.toJSONString(req), e);
            return Result.error("网络异常, 请稍后再试");
        }
    }
}

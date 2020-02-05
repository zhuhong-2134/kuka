package com.camelot.kuka.user.controller;

import com.camelot.kuka.common.controller.BaseController;
import com.camelot.kuka.common.utils.AppUserUtil;
import com.camelot.kuka.common.utils.EnumUtil;
import com.camelot.kuka.model.common.CommonReq;
import com.camelot.kuka.model.common.PageResult;
import com.camelot.kuka.model.common.Result;
import com.camelot.kuka.model.enums.DeleteEnum;
import com.camelot.kuka.model.enums.SexEnum;
import com.camelot.kuka.model.enums.user.CreateSourceEnum;
import com.camelot.kuka.model.enums.user.UserPageEnum;
import com.camelot.kuka.model.user.LoginAppUser;
import com.camelot.kuka.model.user.req.UserPageReq;
import com.camelot.kuka.model.user.req.UserReq;
import com.camelot.kuka.model.user.resp.UserResp;
import com.camelot.kuka.user.service.UserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>Description: [用户信息]</p>
 * Created on 2020/1/19
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Slf4j
@RestController
@Api(value = "用户信息API", tags = { "用户信息接口" })
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * 当前登录用户 LoginAppUser
     */
    @GetMapping("/users/current")
    public LoginAppUser getLoginAppUser() {
        return AppUserUtil.getLoginAppUser();
    }

    @GetMapping(value = "/users-anon/internal", params = "username")
    public LoginAppUser findByUsername(String username) {
        return userService.findByUsername(username);
    }

    /***
     * <p>Description:[分页查询]</p>
     * Created on 2020/1/20
     * @param req
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    @PostMapping("/users/pageList")
    public PageResult<List<UserResp>> pageList(UserPageReq req){
        // 开启分页
        startPage();
        // 返回分页
        PageResult<List<UserResp>> page = getPage(userService.pageList(req), UserResp.class);
        page.putEnumVal("sexEnum", EnumUtil.getEnumJson(SexEnum.class));
        page.putEnumVal("sourceEnum", EnumUtil.getEnumJson(CreateSourceEnum.class));
        page.putEnumVal("delStateEnum", EnumUtil.getEnumJson(DeleteEnum.class));
        page.putEnumVal("queryTypeEnum", EnumUtil.getEnumJson(UserPageEnum.class));
        return page;
    }

    /***
     * <p>Description:[新增]</p>
     * Created on 2020/2/4
     * @param req
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    @PostMapping("/users/add")
    public Result addUser(UserReq req){
        String loginUserName = AppUserUtil.getLoginUserName();
        return userService.addUser(req, loginUserName);
    }

    /***
     * <p>Description:[单条查询]</p>
     * Created on 2020/1/20
     * @param req
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    @PostMapping("/users/queryById")
    public Result<UserResp> queryById(CommonReq req){
        return userService.queryById(req);
    }

    /***
     * <p>Description:[修改]</p>
     * Created on 2020/2/4
     * @param req
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    @PostMapping("/users/update")
    public Result updateUser(UserReq req){
        String loginUserName = AppUserUtil.getLoginUserName();
        return userService.updateUser(req, loginUserName);
    }

    /***
     * <p>Description:[删除]</p>
     * Created on 2020/2/4
     * @param req
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    @PostMapping("/users/del")
    public Result delUser(CommonReq req){
        String loginUserName = AppUserUtil.getLoginUserName();
        return userService.delUser(req, loginUserName);
    }

}

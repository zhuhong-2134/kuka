package com.camelot.kuka.user.controller;

import com.camelot.kuka.common.controller.BaseController;
import com.camelot.kuka.common.utils.AppUserUtil;
import com.camelot.kuka.model.common.CommonReq;
import com.camelot.kuka.model.common.EnumVal;
import com.camelot.kuka.model.common.PageResult;
import com.camelot.kuka.model.common.Result;
import com.camelot.kuka.model.enums.DeleteEnum;
import com.camelot.kuka.model.enums.SexEnum;
import com.camelot.kuka.model.enums.user.CreateSourceEnum;
import com.camelot.kuka.model.enums.user.UserPageEnum;
import com.camelot.kuka.model.enums.user.UserStatusEnum;
import com.camelot.kuka.model.enums.user.UserTypeEnum;
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
import org.springframework.web.bind.annotation.RequestBody;
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
    public Result<UserResp> getLoginAppUser() {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if (null == loginAppUser) {
            return Result.error("用户未登录");
        }
        CommonReq req = new CommonReq();
        req.setId(loginAppUser.getId());
        Result<UserResp> userRespResult = userService.queryById(req);
        userRespResult.getData().setUserName(userRespResult.getData().getName());
        return userRespResult;
    }

    /**
     * 登录信息
     * @param username
     * @return
     */
    @GetMapping(value = "/users-anon/internal", params = "username")
    public LoginAppUser findByUsername(String username) {
        return userService.findByUsername(username);
    }

    /**
     * 通过ID获取
     * @param id
     * @return
     */
    @GetMapping(value = "/users/client/queryById", params = "id")
    public Result<UserResp> clientById(Long id) {
        CommonReq req = new CommonReq();
        req.setId(id);
        return userService.queryById(req);
    }
    /**
     * 通过ID获取
     * @param ids
     * @return
     */
    @GetMapping(value = "/users/client/queryByIds", params = "ids")
    public Result<List<UserResp>> queryByIds(Long[] ids) {
        Result<List<UserResp>> listResult = userService.queryByIds(ids);
        listResult.getData().forEach(user -> {
            user.setUserName(user.getName());
        });
        return listResult;
    }

    @GetMapping(value = "/users/client/queryByUserName", params = "userName")
    public Result<UserResp> queryByUserName(String userName) {
        return userService.queryByUserName(userName);
    }

    @PostMapping("/users/client/update")
    public Result clientUpDATE(@RequestBody UserReq req){
        return userService.updateUser(req, req.getUpdateBy());
    }

    /***
     * <p>Description:[枚举查询]</p>
     * Created on 2020/1/20
     * @param
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    @PostMapping("/users/queryEnum")
    public PageResult queryEnum(){
        PageResult page = new PageResult();
        page.putEnumVal("sexEnum", EnumVal.getEnumList(SexEnum.class));
        page.putEnumVal("sourceEnum", EnumVal.getEnumList(CreateSourceEnum.class));
        page.putEnumVal("delStateEnum", EnumVal.getEnumList(DeleteEnum.class));
        page.putEnumVal("queryTypeEnum", EnumVal.getEnumList(UserPageEnum.class));
        page.putEnumVal("typeEnum", EnumVal.getEnumList(UserTypeEnum.class));
        page.putEnumVal("statusEnum", EnumVal.getEnumList(UserStatusEnum.class));
        return page;
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
        page.getData().forEach(user -> {
            user.setUserName(user.getName());
        });
        page.putEnumVal("sexEnum", EnumVal.getEnumList(SexEnum.class));
        page.putEnumVal("sourceEnum", EnumVal.getEnumList(CreateSourceEnum.class));
        page.putEnumVal("delStateEnum", EnumVal.getEnumList(DeleteEnum.class));
        page.putEnumVal("queryTypeEnum", EnumVal.getEnumList(UserPageEnum.class));
        page.putEnumVal("typeEnum", EnumVal.getEnumList(UserTypeEnum.class));
        page.putEnumVal("statusEnum", EnumVal.getEnumList(UserStatusEnum.class));
        return page;
    }

    /***
     * <p>Description:[kuka用户-分页查询]</p>
     * Created on 2020/1/20
     * @param req
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    @PostMapping("/users/kuka/pageList")
    public PageResult<List<UserResp>> kukaPageList(UserPageReq req){
        // 开启分页
        startPage();

        req.setType(UserTypeEnum.KUKA);
        // 返回分页
        PageResult<List<UserResp>> page = getPage(userService.kukaPageList(req), UserResp.class);
        page.getData().forEach(user -> {
            user.setUserName(user.getName());
        });
        page.putEnumVal("sexEnum", EnumVal.getEnumList(SexEnum.class));
        page.putEnumVal("sourceEnum", EnumVal.getEnumList(CreateSourceEnum.class));
        page.putEnumVal("delStateEnum", EnumVal.getEnumList(DeleteEnum.class));
        page.putEnumVal("queryTypeEnum", EnumVal.getEnumList(UserPageEnum.class));
        page.putEnumVal("typeEnum", EnumVal.getEnumList(UserTypeEnum.class));
        page.putEnumVal("statusEnum", EnumVal.getEnumList(UserStatusEnum.class));
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
     * <p>Description:[新增kuka用户]</p>
     * Created on 2020/2/4
     * @param req
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    @PostMapping("/users/kuka/add")
    public Result kukaAddUser(UserReq req){
        String loginUserName = AppUserUtil.getLoginUserName();
        req.setType(UserTypeEnum.KUKA);
        return userService.kukaAddUser(req, loginUserName);
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
        Result<UserResp> userRespResult = userService.queryById(req);
        userRespResult.getData().setUserName(userRespResult.getData().getName());
        return userRespResult;
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
        req.setName(req.getUserName());
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

    /***
     * <p>Description:[修改上线下载状态]</p>
     * Created on 2020/2/4
     * @param req
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    @PostMapping("/users/updateStatus")
    public Result updateStatus(UserReq req){
        String loginUserName = AppUserUtil.getLoginUserName();
        return userService.updateUser(req, loginUserName);
    }

    /***
     * <p>Description:[获取来访所有数据]</p>
     * Created on 2020/1/20
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    @GetMapping("/users/queryByType")
    public Result<List<UserResp>> queryByType(){
        Result<List<UserResp>> listResult = userService.queryByType();
        listResult.getData().forEach(user -> {
            user.setUserName(user.getName());
        });
        return listResult;
    }
}

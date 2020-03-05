package com.camelot.kuka.user.controller;

import com.camelot.kuka.common.controller.BaseController;
import com.camelot.kuka.common.utils.AppUserUtil;
import com.camelot.kuka.model.common.Result;
import com.camelot.kuka.model.user.req.UserReq;
import com.camelot.kuka.user.service.UserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>Description: [登录信息]</p>
 * Created on 2020/1/19
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Slf4j
@RestController
@Api(value = "登录API", tags = { "登录接口" })
public class LoginController extends BaseController {

    @Autowired
    private UserService userService;


    /***
     * <p>Description:[新增来访者用户]</p>
     * Created on 2020/2/4
     * @param req
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    @PostMapping("/login/add")
    public Result addUser(UserReq req){
        return userService.visitorAddUser(req);
    }

    /***
     * <p>Description:[修改密码]</p>
     * Created on 2020/2/4
     * @param req
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    @PostMapping("/login/updatePassword")
    public Result updatePassword(UserReq req){
        String loginUserName = AppUserUtil.getLoginUserName();
        return userService.updatePassWord(req, loginUserName);
    }

    /***
     * <p>Description:[发送验证码]</p>
     * Created on 2020/2/4
     * @param req
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    @PostMapping("/login/sendMail")
    public Result sendMail(UserReq req){
        return userService.sendMail(req);
    }
}

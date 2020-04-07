package com.camelot.kuka.user.controller;


import com.alibaba.fastjson.JSON;
import com.camelot.kuka.common.utils.AppUserUtil;
import com.camelot.kuka.model.common.EnumVal;
import com.camelot.kuka.model.common.Result;
import com.camelot.kuka.model.enums.user.UserTypeEnum;
import com.camelot.kuka.model.user.LoginAppUser;
import com.camelot.kuka.model.user.menu.resp.MenuResp;
import com.camelot.kuka.model.user.menu.resp.MenuTreeResp;
import com.camelot.kuka.user.service.MenuService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>Description: [用户信息]</p>
 * Created on 2020/1/19
 *
 *
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Slf4j
@RestController
@Api(value = "用户信息API", tags = { "用户信息接口" })
public class MenuController {

    @Resource
    private MenuService menuService;


    /***
     * <p>Description:[获取登陆用户的菜单]</p>
     * Created on 2020/2/4
     * @param
     * @return com.camelot.kuka.model.common.Result
     *
     */
    @PostMapping("/menu/queryList")
    public Result<List<MenuTreeResp>> queryList(String type){
        try {
            LoginAppUser loginAppUser = AppUserUtil.getLoginUser();

            if (null == loginAppUser) {
                return Result.error("用户未登陆");
            }
            Result<List<MenuTreeResp>> listResult = menuService.queryList(loginAppUser);
            listResult.putEnumVal("typeEnum", EnumVal.getEnumList(UserTypeEnum.class));
            return listResult;
        } catch (Exception e) {
            log.error("\n 菜单模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "queryList", JSON.toJSONString(null), e);
            return Result.error("网络异常, 请稍后再试");
        }
    }
}

package com.camelot.kuka.user.service;

import com.camelot.kuka.model.common.Result;
import com.camelot.kuka.model.user.LoginAppUser;
import com.camelot.kuka.model.user.menu.resp.MenuResp;
import com.camelot.kuka.model.user.menu.resp.MenuTreeResp;

import java.util.List;

/**
 * <p>Description: [菜单信息]</p>
 * Created on 2020/2/17
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
public interface MenuService {

    /***
     * <p>Description:[获取登陆用户的菜单]</p>
     * Created on 2020/2/4
     * @param
     * @return com.camelot.kuka.model.common.Result
     * @author 谢楠
     */
    Result<List<MenuTreeResp>> queryList(LoginAppUser loginAppUser);
}

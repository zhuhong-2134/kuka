package com.camelot.kuka.user.service.impl;

import com.camelot.kuka.user.dao.MenuDao;
import com.camelot.kuka.user.dao.RoleMenuDao;
import com.camelot.kuka.user.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>Description: [菜单信息]</p>
 * Created on 2020/2/17
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Slf4j
@Service("menuService")
public class MenuServiceImpl implements MenuService {

    @Resource
    private RoleMenuDao roleMenuDao;
    @Resource
    private MenuDao menuDao;
}

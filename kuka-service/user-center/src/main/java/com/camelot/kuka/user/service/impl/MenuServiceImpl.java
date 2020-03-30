package com.camelot.kuka.user.service.impl;

import com.camelot.kuka.model.common.Result;
import com.camelot.kuka.model.user.LoginAppUser;
import com.camelot.kuka.model.user.menu.resp.MenuTreeResp;
import com.camelot.kuka.user.dao.MenuDao;
import com.camelot.kuka.user.dao.RoleMenuDao;
import com.camelot.kuka.user.model.Menu;
import com.camelot.kuka.user.model.RoleMenu;
import com.camelot.kuka.user.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public Result<List<MenuTreeResp>> queryList(LoginAppUser loginAppUser) {
        List<Menu> menus = menuDao.queryList(new Menu());
        // 获取角色已经关联的数据
        List<RoleMenu> roleMenus = roleMenuDao.selectByRoleId(loginAppUser.getRoleId());
        if (roleMenus.isEmpty() || menus.isEmpty()) {
            // 没有任何菜单
            return Result.success();
        }
        List<Menu> newList = new ArrayList<>();

        // 匹配自己的ID
        for (RoleMenu roleMenu : roleMenus) {
            for (Menu menu : menus) {
                if (roleMenu.getMenuId().compareTo(menu.getId()) == 0) {
                    newList.add(menu);
                    break;
                }
            }
        }
        newList = newList.stream().sorted(Comparator.comparing(Menu::getOrderBy).reversed()).collect(Collectors.toList());
        // 结构化树信息
        List<MenuTreeResp> respsList = new ArrayList<>();
        for (Menu menu : newList) {
            MenuTreeResp resp = new MenuTreeResp();
            resp.setId(menu.getId());
            resp.setParentId(menu.getPId());
            resp.setHrefUrl(menu.getHrefUrl());
            resp.setMenuName(menu.getMenuName());
            respsList.add(resp);
        }

        // 处理数据为TREE结构
        List<MenuTreeResp> orgTree = getOrgTree(respsList, -1L);

        return Result.success(orgTree);
    }

    /***
     * <p>
     * Description:[循环数据]
     * </p>
     * Created on 2019/11/12
     * @param treeList
     * @param pid
     * @return java.util.List<com.fehorizon.commonService.model.saas.resp.SysOrgTreeResp>
     * @author 谢楠
     */
    public List<MenuTreeResp> getOrgTree(List<MenuTreeResp> treeList, Long pid) {
        List<MenuTreeResp> result = new ArrayList<>();
        treeList = treeList.stream().filter(a -> a != null).collect(Collectors.toList());
        for (MenuTreeResp menuTreeResp : treeList) {
            if (menuTreeResp.getParentId().equals(pid)) {
                MenuTreeResp deptScopeMode = new MenuTreeResp();
                deptScopeMode.setId(menuTreeResp.getId());
                deptScopeMode.setHrefUrl(menuTreeResp.getHrefUrl());
                deptScopeMode.setParentId(menuTreeResp.getParentId());
                deptScopeMode.setMenuName(menuTreeResp.getMenuName());
                List<MenuTreeResp> temp = getOrgTree(treeList, menuTreeResp.getId());
                if (temp.size() > 0) {
                    deptScopeMode.setChildren(temp);
                }
                result.add(deptScopeMode);
            }
        }
        return result;
    }
}

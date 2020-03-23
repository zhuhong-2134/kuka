package com.camelot.kuka.user.service.impl;

import com.camelot.kuka.common.utils.BeanUtil;
import com.camelot.kuka.common.utils.CodeGenerateUtil;
import com.camelot.kuka.model.common.CommonReq;
import com.camelot.kuka.model.common.Result;
import com.camelot.kuka.model.enums.PrincipalEnum;
import com.camelot.kuka.model.enums.user.role.RoleMenuEnum;
import com.camelot.kuka.model.enums.user.role.RoleStatusEnum;
import com.camelot.kuka.model.user.LoginAppUser;
import com.camelot.kuka.model.user.role.req.RoleMenuReq;
import com.camelot.kuka.model.user.role.req.RolePageReq;
import com.camelot.kuka.model.user.role.req.RoleReq;
import com.camelot.kuka.model.user.role.resp.RoleMenuResp;
import com.camelot.kuka.model.user.role.resp.RoleResp;
import com.camelot.kuka.user.dao.MenuDao;
import com.camelot.kuka.user.dao.RoleDao;
import com.camelot.kuka.user.dao.RoleMenuDao;
import com.camelot.kuka.user.model.Menu;
import com.camelot.kuka.user.model.Role;
import com.camelot.kuka.user.model.RoleMenu;
import com.camelot.kuka.user.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>Description: [角色信息]</p>
 * Created on 2020/2/17
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Slf4j
@Service("roleService")
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleDao roleDao;
    @Resource
    private MenuDao menuDao;
    @Resource
    private RoleMenuDao roleMenuDao;
    @Resource
    private CodeGenerateUtil codeGenerateUtil;

    @Override
    public List<Role> queryList(RolePageReq req) {
        req.setQueryTypeCode(null != req.getQueryType() ? req.getQueryType().getCode() : null);
        return roleDao.pageList(req);
    }

    @Override
    public Result addRole(RoleReq req, String loginUserName) {
        if (null == req || null == req.getRoleName()) {
            return Result.error("角色名称不能为空");
        }
        Role role = BeanUtil.copyBean(req, Role.class);
        Long id = codeGenerateUtil.generateId(PrincipalEnum.USER_ROLE);
        role.setId(id);
        role.setCreateBy(loginUserName);
        role.setCreateTime(new Date());
        role.setStatus(null == role.getStatus() ? RoleStatusEnum.OPEN : role.getStatus());
        int con = roleDao.addBatch(Arrays.asList(role));
        if (con == 0) {
            return Result.error("新增失败, 请联系管理员");
        }
        return Result.success(id);
    }

    @Override
    public Result updateRole(RoleReq req, String loginUserName) {
        if (null == req || null == req.getId()) {
            return Result.error("ID不能为空");
        }
        Role role = BeanUtil.copyBean(req, Role.class);
        role.setUpdateBy(loginUserName);
        role.setUpdateTime(new Date());
        int con = roleDao.update(role);
        if (con == 0) {
            return Result.error("修改失败, 请联系管理员");
        }
        return Result.success();
    }

    @Override
    public Result updateStatus(RoleReq req, String loginUserName) {
        if (null == req || null == req.getId()) {
            return Result.error("ID不能为空");
        }
        if (null == req.getStatus()) {
            return Result.error("上线下线状态不能为空");
        }
        Role role = BeanUtil.copyBean(req, Role.class);
        role.setUpdateBy(loginUserName);
        role.setUpdateTime(new Date());
        int con = roleDao.update(role);
        if (con == 0) {
            return Result.error("修改失败, 请联系管理员");
        }
        return Result.success();
    }

    @Override
    public Result<RoleResp> qeuryById(CommonReq req) {
        if (null == req || null == req.getId()) {
            return Result.error("ID不能为空");
        }
        Role role = roleDao.qeuryById(req.getId());
        if (null == role) {
            return Result.error("获取失败, 请刷新后重试");
        }
        return Result.success(BeanUtil.copyBean(role, RoleResp.class));
    }

    @Override
    public Result<List<RoleMenuResp>> roleMenu(CommonReq req, LoginAppUser loginAppUser) {
        if (null == req || null == req.getId()) {
            return Result.error("角色ID不能为空");
        }
        // 获取菜单列表

        Menu qeruy = new Menu();
        qeruy.setType(loginAppUser.getType());
        List<Menu> menus = menuDao.queryList(qeruy);
        // 获取角色已经关联的数据
        List<RoleMenu> roleMenus = roleMenuDao.selectByRoleId(req.getId());
        // 数据处理
        return Result.success(handleRoleMenu(req.getId(), menus, roleMenus));
    }

    @Override
    public Result updateRoleMenu(RoleMenuReq req) {
        if (null == req || null == req.getRoleId()) {
            return Result.error("角色ID不能为空");
        }
        if (null == req.getMenuId()) {
            return Result.error("权限ID不能为空");
        }
        if (null == req.getRoleMenuStates()) {
            return Result.error("关联状态不能为空");
        }


        // 删除
        if (req.getRoleMenuStates() == RoleMenuEnum.NO) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(req.getRoleId());
            roleMenu.setMenuId(req.getMenuId());
            int con = roleMenuDao.delete(roleMenu);
            if (con == 0 ) {
                return Result.error("取消关联失败, 联系管理员");
            }
        }


        // 新增
        if (req.getRoleMenuStates() == RoleMenuEnum.YES) {
            RoleMenu roleMenu = new RoleMenu();
            Long id = codeGenerateUtil.generateId(PrincipalEnum.USER_ROLE_MENU);
            roleMenu.setId(id);
            roleMenu.setRoleId(req.getRoleId());
            roleMenu.setMenuId(req.getMenuId());
            int con = roleMenuDao.addBatch(Arrays.asList(roleMenu));
            if (con == 0 ) {
                return Result.error("关联权限失败, 联系管理员");
            }
        }
        return Result.success();
    }

    @Override
    public Result<List<RoleResp>> findList(RoleReq req) {
        req.setStatus(RoleStatusEnum.OPEN);
        List<Role> list = roleDao.findList(BeanUtil.copyBean(req, Role.class));
        return Result.success(BeanUtil.copyBeanList(list, RoleResp.class));
    }

    /**
     * 数据处理
     * 尽可能减少循环次数
     * @param menus
     * @param roleMenus
     */
    private List<RoleMenuResp> handleRoleMenu(Long roleId, List<Menu> menus, List<RoleMenu> roleMenus) {
        if (menus.isEmpty()) {
            // 不代表失败, 只是没数据
            return new ArrayList<>();
        }
        List<RoleMenuResp> list = new ArrayList<>();
        for (Menu menu : menus) {
            RoleMenuResp resp = new RoleMenuResp();
            resp.setRoleId(roleId);
            resp.setMenuId(menu.getId());
            resp.setMenuName(menu.getMenuName());
            // 检查数据是否关联
            for (RoleMenu roleMenu : roleMenus) {
                if (menu.getId().compareTo(roleMenu.getMenuId()) == 0) {
                    resp.setRoleMenuStates(RoleMenuEnum.YES);
                    break;
                }
            }
            if (null == resp.getRoleMenuStates()) {
                resp.setRoleMenuStates(RoleMenuEnum.NO);
            }
            list.add(resp);
        }
        return list;
    }
}

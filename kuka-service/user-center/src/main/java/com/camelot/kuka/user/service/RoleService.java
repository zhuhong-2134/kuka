package com.camelot.kuka.user.service;

import com.camelot.kuka.model.common.CommonReq;
import com.camelot.kuka.model.common.Result;
import com.camelot.kuka.model.user.LoginAppUser;
import com.camelot.kuka.model.user.role.req.RoleMenuReq;
import com.camelot.kuka.model.user.role.req.RolePageReq;
import com.camelot.kuka.model.user.role.req.RoleReq;
import com.camelot.kuka.model.user.role.resp.RoleMenuResp;
import com.camelot.kuka.model.user.role.resp.RoleResp;
import com.camelot.kuka.user.model.Role;

import java.util.List;

/**
 * <p>Description: [角色信息]</p>
 * Created on 2020/1/19
 *
 *
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
public interface RoleService {

    /***
     * <p>Description:[分页查询]</p>
     * Created on 2020/2/4
     * @param req
     * @return List
     *
     */
    List<Role> queryList(RolePageReq req);

    /***
     * <p>Description:[新增角色信息]</p>
     * Created on 2020/2/4
     * @param req
     * @return Result
     *
     */
    Result addRole(RoleReq req, String loginUserName);

    /***
     * <p>Description:[修改角色信息]</p>
     * Created on 2020/2/4
     * @param req
     * @return Result
     *
     */
    Result updateRole(RoleReq req, String loginUserName);

    /***
     * <p>Description:[上线下线修改]</p>
     * Created on 2020/2/4
     * @param req
     * @return Result
     *
     */
    Result updateStatus(RoleReq req, String loginUserName);

    /***
     * <p>Description:[通过ID获取角色信息]</p>
     * Created on 2020/2/4
     * @param req
     * @return Result
     *
     */
    Result<RoleResp> qeuryById(CommonReq req);

    /***
     * <p>Description:[获取权限列表]</p>
     * Created on 2020/2/4
     * @param req
     * @return Result
     *
     */
    Result<List<RoleMenuResp>> roleMenu(CommonReq req, LoginAppUser loginAppUser);

    /***
     * <p>Description:[权限关联]</p>
     * Created on 2020/2/4
     * @param req
     * @return Result
     *
     */
    Result updateRoleMenu(RoleMenuReq req);

    /***
     * <p>Description:[列表查询]</p>
     * Created on 2020/2/4
     * @param req
     * @return Result
     *
     */
    Result<List<RoleResp>> findList(RoleReq req);
}

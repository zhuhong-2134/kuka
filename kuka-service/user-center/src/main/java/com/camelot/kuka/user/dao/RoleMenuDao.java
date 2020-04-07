package com.camelot.kuka.user.dao;

import com.camelot.kuka.user.model.RoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>Description: [角色菜单DAO]</p>
 * Created on 2020/1/19
 *
 *
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Mapper
public interface RoleMenuDao {

    /***
     * <p>Description:[根据角色ID获取数据]</p>
     * Created on 2020/1/19
     * @param roleId
     * @return List<RoleMenu>
     *
     */
    List<RoleMenu> selectByRoleId(@Param("roleId") Long roleId);

    /***
     * <p>Description:[批量新增]</p>
     * Created on 2020/1/19
     * @param list
     * @return int
     *
     */
    int addBatch(@Param("list") List<RoleMenu> list);

    /***
     * <p>Description:[删除]</p>
     * Created on 2020/1/19
     * @param roleMenu
     * @return int
     *
     */
    int delete(@Param("entity") RoleMenu roleMenu);

}

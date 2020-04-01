package com.camelot.kuka.user.dao;

import com.camelot.kuka.model.user.role.req.RolePageReq;
import com.camelot.kuka.user.model.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>Description: [角色DAO]</p>
 * Created on 2020/1/19
 *
 *
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Mapper
public interface RoleDao {

    /***
     * <p>Description:[分页查询]</p>
     * Created on 2020/1/19
     * @param req
     * @return List<Role>
     *
     */
    List<Role> pageList(@Param("entity") RolePageReq req);

    /***
     * <p>Description:[获取角色列表]</p>
     * Created on 2020/1/19
     * @param query
     * @return List<Role>
     *
     */
    List<Role> findList(@Param("entity") Role query);

    /***
     * <p>Description:[新增]</p>
     * Created on 2020/1/19
     * @param list
     * @return int
     *
     */
    int addBatch(@Param("list") List<Role> list);

    /***
     * <p>Description:[修改]</p>
     * Created on 2020/1/19
     * @param role)
     * @return int
     *
     */
    int update(Role role);

    /***
     * <p>Description:[通过ID获取]</p>
     * Created on 2020/1/19
     * @param id
     * @return int
     *
     */
    Role qeuryById(@Param("id") Long id);

    /***
     * <p>Description:[通过ID获取]</p>
     * Created on 2020/1/19
     * @param id
     * @return int
     *
     */
    List<Role> qeuryByIds(@Param("array") Long[] ids);
}

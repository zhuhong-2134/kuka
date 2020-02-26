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
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
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
     * @author 谢楠
     */
    List<Role> pageList(@Param("entity") RolePageReq req);

    /***
     * <p>Description:[获取角色列表]</p>
     * Created on 2020/1/19
     * @param query
     * @return List<Role>
     * @author 谢楠
     */
    List<Role> findList(@Param("entity") Role query);

    /***
     * <p>Description:[新增]</p>
     * Created on 2020/1/19
     * @param list
     * @return int
     * @author 谢楠
     */
    int addBatch(@Param("list") List<Role> list);

    /***
     * <p>Description:[修改]</p>
     * Created on 2020/1/19
     * @param role)
     * @return int
     * @author 谢楠
     */
    int update(Role role);

    /***
     * <p>Description:[通过ID获取]</p>
     * Created on 2020/1/19
     * @param id
     * @return int
     * @author 谢楠
     */
    Role qeuryById(@Param("id") Long id);

    /***
     * <p>Description:[通过ID获取]</p>
     * Created on 2020/1/19
     * @param id
     * @return int
     * @author 谢楠
     */
    List<Role> qeuryByIds(@Param("array") Long[] ids);
}

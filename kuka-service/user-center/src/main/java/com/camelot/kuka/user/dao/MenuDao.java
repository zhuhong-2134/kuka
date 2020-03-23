package com.camelot.kuka.user.dao;

import com.camelot.kuka.user.model.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>Description: [菜单DAO]</p>
 * Created on 2020/1/19
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Mapper
public interface MenuDao {

    /***
     * <p>Description:[分页查询]</p>
     * Created on 2020/1/19
     * @param query
     * @return List<Role>
     * @author 谢楠
     */
    List<Menu> pageList();

    /***
     * <p>Description:[列表]</p>
     * Created on 2020/1/19
     * @param
     * @return List<Role>
     * @author 谢楠
     */
    List<Menu> queryList(@Param("entity") Menu menuQuer);

    /***
     * <p>Description:[新增]</p>
     * Created on 2020/1/19
     * @param list
     * @return int
     * @author 谢楠
     */
    int addBatch(@Param("list") List<Menu> list);

    /***
     * <p>Description:[修改]</p>
     * Created on 2020/1/19
     * @param menu
     * @return int
     * @author 谢楠
     */
    int update(Menu menu);
}

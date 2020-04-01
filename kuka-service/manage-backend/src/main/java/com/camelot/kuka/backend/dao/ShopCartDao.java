package com.camelot.kuka.backend.dao;

import com.camelot.kuka.backend.model.ShopCart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>Description: [购物车DAO]</p>
 * Created on 2020/1/19
 *
 *
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Mapper
public interface ShopCartDao {

    /***
     * <p>Description:[查询集合]</p>
     * Created on 2020/2/4
     * @param query
     * @return list
     *
     */
    List<ShopCart> queryList(@Param("entity") ShopCart query);

    /***
     * <p>Description:[查询单个]</p>
     * Created on 2020/2/4
     * @param query
     * @return list
     *
     */
    ShopCart queryInfo(@Param("entity") ShopCart query);

    /***
     * <p>Description:[批量新增]</p>
     * Created on 2020/2/4
     * @param list
     * @return int
     *
     */
    int insertBatch(@Param("list") List<ShopCart> list);

    /***
     * <p>Description:[修改]</p>
     * Created on 2020/2/4
     * @param shopCart
     * @return int
     *
     */
    int update(ShopCart shopCart);

    /***
     * <p>Description:[批量逻辑删除]</p>
     * Created on 2020/2/4
     * @param shopCart
     * @return int
     *
     */
    int updateDel(@Param("entity") ShopCart shopCart);

    /***
     * <p>Description:[批量获取]</p>
     * Created on 2020/2/4
     * @param ids
     * @return List
     *
     */
    List<ShopCart> selectIds(@Param("array") Long[] ids);
}

package com.camelot.kuka.backend.dao;

import com.camelot.kuka.backend.model.Supplier;
import com.camelot.kuka.model.backend.home.req.HomeSupplierPageReq;
import com.camelot.kuka.model.backend.supplier.req.SupplierPageReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>Description: [供应商接口]</p>
 * Created on 2020/1/19
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Mapper
public interface SupplierDao {

    /***
     * <p>Description:[kuka后台分页查询]</p>
     * Created on 2020/2/5
     * @param req
     * @return List<Supplier>
     * @author 谢楠
     */
    List<Supplier> pageList(@Param("entity") SupplierPageReq req);

    /***
     * <p>Description:[集成商后台分页查询]</p>
     * Created on 2020/2/5
     * @param req
     * @return List<Supplier>
     * @author 谢楠
     */
    List<Supplier> supplierPageList(@Param("entity") SupplierPageReq req);

    /***
     * <p>Description:[来访者后台分页查询]</p>
     * Created on 2020/2/5
     * @param req
     * @return List<Supplier>
     * @author 谢楠
     */
    List<Supplier> visitorPageList(@Param("entity")SupplierPageReq req);


    /***
     * <p>Description:[查询集合]</p>
     * Created on 2020/2/5
     * @param req
     * @return List<Supplier>
     * @author 谢楠
     */
    List<Supplier> findList(@Param("entity") Supplier req);

    /***
     * <p>Description:[新增]</p>
     * Created on 2020/2/5
     * @param suppliers
     * @return int
     * @author 谢楠
     */
    int addSupplier(@Param("list") List<Supplier> suppliers);

    /***
     * <p>Description:[获取单条数据]</p>
     * Created on 2020/2/5
     * @param user
     * @return int
     * @author 谢楠
     */
    Supplier queryById(@Param("entity")Supplier supplier);

    /***
     * <p>Description:[修改]</p>
     * Created on 2020/2/5
     * @param supplier
     * @return int
     * @author 谢楠
     */
    int updateSupplier(Supplier supplier);

    /***
     * <p>Description:[逻辑删除]</p>
     * Created on 2020/2/5
     * @param supplier
     * @return int
     * @author 谢楠
     */
    int delSupplier(Supplier supplier);

    /***
     * <p>Description:[首页查询]</p>
     * Created on 2020/2/5
     * @param
     * @return List
     * @author 谢楠
     */
    List<Supplier> homeSupplierList(@Param("entity") Supplier query);

    /***
     * <p>Description:[首页分页查询]</p>
     * Created on 2020/2/5
     * @param
     * @return List
     * @author 谢楠
     */
    List<Supplier> homeSupplierPageList(@Param("entity")HomeSupplierPageReq req);
}

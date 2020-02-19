package com.camelot.kuka.backend.dao;

import com.camelot.kuka.backend.model.SupplierRequest;
import com.camelot.kuka.model.backend.supplierrequest.req.SupplierRequestPageReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>Description: [集成商请求用户DAO]</p>
 * Created on 2020/1/19
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Mapper
public interface SupplierRequestDao {

    /***
     * <p>Description:[分页查询]</p>
     * Created on 2020/2/4
     * @param req
     * @return list
     * @author 谢楠
     */
    List<SupplierRequest> pageList(@Param("entity") SupplierRequestPageReq req);

    /***
     * <p>Description:[查询单个]</p>
     * Created on 2020/2/4
     * @param query
     * @return ApplicationRequest
     * @author 谢楠
     */
    SupplierRequest queryInfo(@Param("entity") SupplierRequest query);

    /***
     * <p>Description:[新增]</p>
     * Created on 2020/2/4
     * @param list
     * @return int
     * @author 谢楠
     */
    int addBatch(@Param("list")List<SupplierRequest> list);


    /***
     * <p>Description:[修改]</p>
     * Created on 2020/2/4
     * @param supplierRequest
     * @return int
     * @author 谢楠
     */
    int update(SupplierRequest supplierRequest);
}

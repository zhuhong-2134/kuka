package com.camelot.kuka.backend.dao;

import com.camelot.kuka.backend.model.ApplicationCurrency;
import com.camelot.kuka.model.backend.application.req.ApplicationCurrencyReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>Description: [通用产品DAO]</p>
 * Created on 2020/1/19
 *
 *
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Mapper
public interface ApplicationCurrencyDao {


    /***
     * <p>Description:[根据应用ID删除]</p>
     * Created on 2020/2/5
     * @param appId
     * @return int
     *
     */
    void deleteByAppId(@Param("appId")Long appId);

    /***
     * <p>Description:[批量新增]</p>
     * Created on 2020/2/5
     * @param addAppCurrencyList
     * @return int
     *
     */
    int insertBatch(@Param("list") List<ApplicationCurrency> addAppCurrencyList);

    /***
     * <p>Description:[根据appId获取]</p>
     * Created on 2020/2/5
     * @param appId
     * @return List<ApplicationCurrency>
     *
     */
    List<ApplicationCurrency> selectByAppId(@Param("appId") Long appId);

    /***
     * <p>Description:[删除表示]</p>
     * Created on 2020/2/5
     * @param req
     * @return List<ApplicationCurrency>
     *
     */
    int delCurrency(ApplicationCurrencyReq req);
}

package com.camelot.kuka.backend.dao;

import com.camelot.kuka.backend.model.Application;
import com.camelot.kuka.model.backend.application.req.AppPageReq;
import com.camelot.kuka.model.backend.home.req.HomeAppPageReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>Description: [产品接口]</p>
 * Created on 2020/1/19
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Mapper
public interface ApplicationDao {

    /***
     * <p>Description:[查询集合]</p>
     * Created on 2020/2/5
     * @param req
     * @return List<Supplier>
     * @author 谢楠
     */
    List<Application> queryList(@Param("entity") AppPageReq req);

    /***
     * <p>Description:[集成商-分页]</p>
     * Created on 2020/2/5
     * @param req
     * @return List<Supplier>
     * @author 谢楠
     */
    List<Application> supplierPageList(@Param("entity") AppPageReq req);

    /***
     * <p>Description:[来访者-分页]</p>
     * Created on 2020/2/5
     * @param req
     * @return List<Supplier>
     * @author 谢楠
     */
    List<Application> visitorPageList(@Param("entity") AppPageReq req);

    /***
     * <p>Description:[批量新增]</p>
     * Created on 2020/2/5
     * @param applications
     * @return int
     * @author 谢楠
     */
    int insertBatch(@Param("list") List<Application> applications);

    /***
     * <p>Description:[获取单个对象]</p>
     * Created on 2020/2/5
     * @param qeury
     * @return int
     * @author 谢楠
     */
    Application selectById(@Param("entity") Application qeury);

    /***
     * <p>Description:[获取适用产品]</p>
     * Created on 2020/2/5
     * @param appId
     * @return List<Application>
     * @author 谢楠
     */
    List<Application> queryCurrencyList(@Param("appId")Long appId);

    /***
     * <p>Description:[修改]</p>
     * Created on 2020/2/5
     * @param application
     * @return int
     * @author 谢楠
     */
    int update(Application application);


    /***
     * <p>Description:[首页获取]</p>
     * Created on 2020/2/5
     * @param application
     * @return int
     * @author 谢楠
     */
    List<Application> homeAppList(@Param("entity") Application application);

    /***
     * <p>Description:[首页分页获取]</p>
     * Created on 2020/2/5
     * @param req
     * @return int
     * @author 谢楠
     */
    List<Application> homePageList(@Param("entity") HomeAppPageReq req);
}

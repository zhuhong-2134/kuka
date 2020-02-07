package com.camelot.kuka.backend.dao;

import com.camelot.kuka.backend.model.Application;
import com.camelot.kuka.model.backend.application.req.AppPageReq;
import org.apache.ibatis.annotations.Mapper;

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
    List<Application> queryList(AppPageReq req);
}

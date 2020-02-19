package com.camelot.kuka.backend.dao;

import com.camelot.kuka.backend.model.ApplicationRequest;
import com.camelot.kuka.model.backend.applicationrequest.req.AppRequestPageReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>Description: [应用请求用户DAO]</p>
 * Created on 2020/1/19
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Mapper
public interface ApplicationRequestDao {

    /***
     * <p>Description:[分页查询]</p>
     * Created on 2020/2/4
     * @param req
     * @return list
     * @author 谢楠
     */
    List<ApplicationRequest> pageList(@Param("entity") AppRequestPageReq req);

    /***
     * <p>Description:[查询单个]</p>
     * Created on 2020/2/4
     * @param query
     * @return ApplicationRequest
     * @author 谢楠
     */
    ApplicationRequest queryInfo(@Param("entity") ApplicationRequest query);

    /***
     * <p>Description:[新增]</p>
     * Created on 2020/2/4
     * @param list
     * @return int
     * @author 谢楠
     */
    int addBatch(@Param("list")List<ApplicationRequest> list);


    /***
     * <p>Description:[修改]</p>
     * Created on 2020/2/4
     * @param applicationRequest
     * @return int
     * @author 谢楠
     */
    int update(ApplicationRequest applicationRequest);
}

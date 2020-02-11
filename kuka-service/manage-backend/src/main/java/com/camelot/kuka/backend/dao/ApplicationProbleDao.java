package com.camelot.kuka.backend.dao;

import com.camelot.kuka.backend.model.ApplicationProblem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>Description: [应用常见问题DAO]</p>
 * Created on 2020/1/19
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Mapper
public interface ApplicationProbleDao {

    /***
     * <p>Description:[根据应用ID删除]</p>
     * Created on 2020/2/4
     * @param appIds
     * @return com.camelot.kuka.model.common.Result
     * @author 谢楠
     */
    int deleteByAppIds(@Param("array") Long[] appIds);

    /***
     * <p>Description:[批量新增]</p>
     * Created on 2020/2/4
     * @param addList
     * @return com.camelot.kuka.model.common.Result
     * @author 谢楠
     */
    int insertBatch(@Param("list") List<ApplicationProblem> addList);

    /***
     * <p>Description:[根据应用ID获取数据]</p>
     * Created on 2020/2/4
     * @param appIds
     * @return List<ApplicationProblem>
     * @author 谢楠
     */
    List<ApplicationProblem> queryListByAppId(@Param("appId") Long appIds);

    /***
     * <p>Description:[修改应用常见问题]</p>
     * Created on 2020/2/4
     * @param problem
     * @return com.camelot.kuka.model.common.Result
     * @author 谢楠
     */
    int update(ApplicationProblem problem);
}

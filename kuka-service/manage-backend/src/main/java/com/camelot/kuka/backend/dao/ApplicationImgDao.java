package com.camelot.kuka.backend.dao;

import com.camelot.kuka.backend.model.Application;
import com.camelot.kuka.backend.model.ApplicationImg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>Description: [应用图片dao]</p>
 * Created on 2020/1/19
 *
 *
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Mapper
public interface ApplicationImgDao {

    /***
     * <p>Description:[根据应用ID删除]</p>
     * Created on 2020/2/5
     * @param appId
     * @return int
     *
     */
    int deleteByAppId(@Param("appId") Long appId);

    /***
     * <p>Description:[批量新增]</p>
     * Created on 2020/2/5
     * @param addAppImgList
     * @return int
     *
     */
    int insertBatch(@Param("list") List<ApplicationImg> addAppImgList);

    /***
     * <p>Description:[根据应用主键批量获取]</p>
     * Created on 2020/2/5
     * @param appIds
     * @return int
     *
     */
    List<ApplicationImg> selectList(@Param("array") Long[] appIds);

}

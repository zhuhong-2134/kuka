package com.camelot.kuka.backend.dao;


import com.camelot.kuka.backend.model.CommentType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>Description: [评论分类dao]</p>
 * Created on 2020/1/19
 *
 *
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Mapper
public interface CommentTypeDao {

    /***
     * <p>Description:[获取所有数据]</p>
     * Created on 2020/2/5
     * @param
     * @return List<CommentTypeResp>
     *
     */
    List<CommentType> queryList();

    /***
     * <p>Description:[获取最大的code]</p>
     * Created on 2020/2/5
     * @param
     * @return Integer
     *
     */
    Integer queryMaxCode();

    /***
     * <p>Description:[新增]</p>
     * Created on 2020/2/5
     * @param commentTypes
     * @return int
     *
     */
    int insertBatch(@Param("list") List<CommentType> commentTypes);

    /***
     * <p>Description:[新增]</p>
     * Created on 2020/2/5
     * @param id
     * @return int
     *
     */
    int deleteCommentType(@Param("id") Long id);
}

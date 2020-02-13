package com.camelot.kuka.backend.dao;


import com.camelot.kuka.backend.model.Comment;
import com.camelot.kuka.model.common.CommonReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>Description: [评论dao]</p>
 * Created on 2020/1/19
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Mapper
public interface CommentDao {

    /***
     * <p>Description:[通过订单ID获取评论信息]</p>
     * Created on 2020/2/5
     * @param orderIds
     * @return List<Supplier>
     * @author 谢楠
     */
    List<Comment> queryByOrderIds(@Param("array")Long[] orderIds);

    /***
     * <p>Description:[通过产品ID获取评论信息]</p>
     * Created on 2020/2/5
     * @param appId
     * @return List<Supplier>
     * @author 谢楠
     */
    List<Comment> queryByAppId(@Param("appId") Long appId);

    /***
     * <p>Description:[修改]</p>
     * Created on 2020/2/5
     * @param comment
     * @return int
     * @author 谢楠
     */
    int update(Comment comment);
}

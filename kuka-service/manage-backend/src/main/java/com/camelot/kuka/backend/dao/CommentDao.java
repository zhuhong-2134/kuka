package com.camelot.kuka.backend.dao;


import com.camelot.kuka.backend.model.Comment;
import com.camelot.kuka.model.backend.comment.req.CommentPageReq;
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

    /***
     * <p>Description:[分页列表]</p>
     * Created on 2020/2/5
     * @param req
     * @return int
     * @author 谢楠
     */
    List<Comment> pageList(@Param("entity") CommentPageReq req);

    /***
     * <p>Description:[但条件查询]</p>
     * Created on 2020/2/5
     * @param query
     * @return int
     * @author 谢楠
     */
    Comment queryInfo(@Param("entity")Comment query);

    /***
     * <p>Description:[批量新增]</p>
     * Created on 2020/2/5
     * @param list
     * @return int
     * @author 谢楠
     */
    int addBatch(@Param("list") List<Comment> list);
}

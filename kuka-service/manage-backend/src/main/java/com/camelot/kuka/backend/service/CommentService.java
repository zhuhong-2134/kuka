package com.camelot.kuka.backend.service;

import com.camelot.kuka.backend.model.Comment;
import com.camelot.kuka.model.backend.comment.req.CommentPageReq;
import com.camelot.kuka.model.backend.comment.req.CommentReq;
import com.camelot.kuka.model.backend.comment.resp.CommentResp;
import com.camelot.kuka.model.common.CommonReq;
import com.camelot.kuka.model.common.Result;
import com.camelot.kuka.model.user.LoginAppUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>Description: [评论接口]</p>
 * Created on 2020/2/5
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
public interface CommentService {

    /***
     * <p>Description:[分页获取信息]</p>
     * Created on 2020/2/5
     * @param req
     * @return List<Comment>
     * @author 谢楠
     */
    List<Comment> pageList(CommentPageReq req);

    /***
     * <p>Description:[通过产品ID获取评论信息]</p>
     * Created on 2020/2/5
     * @param appId
     * @return List<Comment>
     * @author 谢楠
     */
    List<Comment> queryByAppId(Long appId);

    /***
     * <p>Description:[通过订单ID获取评论信息]</p>
     * Created on 2020/2/5
     * @param appId
     * @return List<Supplier>
     * @author 谢楠
     */
    List<Comment> queryByOrderIds(@Param("array") Long[] appId);

    /***
     * <p>Description:[删除]</p>
     * Created on 2020/2/5
     * @param req
     * @return List<Supplier>
     * @author 谢楠
     */
    Result delComment(CommonReq req, String loginUserName);

    /***
     * <p>Description:[审核]</p>
     * Created on 2020/2/5
     * @param req
     * @return List<Supplier>
     * @author 谢楠
     */
    Result toExamine(CommentReq req, String loginUserName);

    /***
     * <p>Description:[通过ID获取数据]</p>
     * Created on 2020/2/5
     * @param req
     * @return List<Supplier>
     * @author 谢楠
     */
    Result<CommentResp> queryById(CommonReq req);

    /***
     * <p>Description:[新增评论信息]</p>
     * Created on 2020/2/5
     * @param req
     * @return List<Supplier>
     * @author 谢楠
     */
    Result addComment(CommentReq req, LoginAppUser loginUser);
}

package com.camelot.kuka.backend.service;

import com.camelot.kuka.model.backend.comment.resp.CommentTypeResp;
import com.camelot.kuka.model.common.CommonReq;
import com.camelot.kuka.model.common.Result;

import java.util.List;

/**
 * <p>Description: [评论分类业务层]</p>
 * Created on 2020/2/5
 *
 *
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
public interface CommentTypeService {

    /***
     * <p>Description:[获取所有分类]</p>
     * Created on 2020/2/5
     * @param
     * @return List
     *
     */
    Result<List<CommentTypeResp>> queryList();

    /***
     * <p>Description:[新增分类]</p>
     * Created on 2020/2/5
     * @param req loginUserName
     * @return Result
     *
     */
    Result addCommentType(CommentTypeResp req, String loginUserName);

    /***
     * <p>Description:[删除分类]</p>
     * Created on 2020/2/5
     * @param req loginUserName
     * @return Result
     *
     */
    Result deleteCommentType(CommonReq req, String loginUserName);
}

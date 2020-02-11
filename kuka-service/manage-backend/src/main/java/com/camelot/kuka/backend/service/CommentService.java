package com.camelot.kuka.backend.service;

import com.camelot.kuka.backend.model.Comment;

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
     * <p>Description:[通过产品ID获取评论信息]</p>
     * Created on 2020/2/5
     * @param appId
     * @return List<Supplier>
     * @author 谢楠
     */
    List<Comment> queryByAppId(Long appId);
}

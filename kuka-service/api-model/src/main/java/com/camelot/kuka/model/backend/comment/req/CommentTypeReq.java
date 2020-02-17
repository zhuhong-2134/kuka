package com.camelot.kuka.model.backend.comment.req;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>Description: [评论分类实体]</p>
 * Created on 2020/1/19
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Data
public class CommentTypeReq implements Serializable {

    private static final long serialVersionUID = 6989237516139261160L;
    /**
     * 主键
     */
    private Long id;

    /**
     * 编码
     */
    private Integer code;

    /**
     * 名称
     */
    private String name;

    /**
     * 说明
     */
    private String des;

    /**
     * 产品id
     */
    private Long appId;
}

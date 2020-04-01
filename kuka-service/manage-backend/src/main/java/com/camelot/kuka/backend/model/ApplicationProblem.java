package com.camelot.kuka.backend.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Description: [应用常见问题]</p>
 * Created on 2020/1/20
 *
 *
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Data
public class ApplicationProblem implements Serializable {

    private static final long serialVersionUID = -6585310791759528746L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 应用主键
     */
    private Long appId;

    /**
     * 标题
     */
    private String title;

    /**
     * 回答
     */
    private String answer;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 修改人
     */
    private String updateBy;
}

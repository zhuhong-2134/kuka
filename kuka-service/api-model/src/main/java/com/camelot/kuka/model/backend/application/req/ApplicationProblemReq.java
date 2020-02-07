package com.camelot.kuka.model.backend.application.req;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>Description: [应用常见问题]</p>
 * Created on 2020/1/20
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Data
public class ApplicationProblemReq implements Serializable {

    private static final long serialVersionUID = -1686481381692620226L;

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

}

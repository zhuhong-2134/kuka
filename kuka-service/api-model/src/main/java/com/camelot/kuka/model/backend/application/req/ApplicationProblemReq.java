package com.camelot.kuka.model.backend.application.req;

import com.camelot.kuka.model.enums.application.AppStatusEnum;
import com.camelot.kuka.model.enums.application.AppTypeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>Description: [新增常见问题实体]</p>
 * Created on 2020/1/20
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Data
public class ApplicationProblemReq implements Serializable {


    private static final long serialVersionUID = -152320728199169004L;


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
}

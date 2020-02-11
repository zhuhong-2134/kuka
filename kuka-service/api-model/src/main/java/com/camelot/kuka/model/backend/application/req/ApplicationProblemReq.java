package com.camelot.kuka.model.backend.application.req;

import com.camelot.kuka.model.enums.application.AppStatusEnum;
import com.camelot.kuka.model.enums.application.AppTypeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>Description: [新增适用产品实体]</p>
 * Created on 2020/1/20
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Data
public class ApplicationProblemReq implements Serializable {


    private static final long serialVersionUID = -152320728199169004L;


    /**
     * 主键
     */
    private Long appId;

    /**
     * 产品通用主键
     */
    private String currencyAppIds;
}

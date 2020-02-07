package com.camelot.kuka.model.backend.application.req;

import com.camelot.kuka.model.enums.application.AppStatusEnum;
import com.camelot.kuka.model.enums.application.AppTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>Description: [产品新增实体]</p>
 * Created on 2020/1/20
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Data
public class ApplicationAddReq implements Serializable {

    private static final long serialVersionUID = -4759516597998839763L;
    /**
     * 主键
     */
    private Long id;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 应用编码
     */
    private String appCode;

    /**
     * 分类
     */
    private AppTypeEnum classType;

    /**
     * 交易次数
     */
    private Integer tradeCount;

    /**
     * 文件大小
     */
    private Double fileSum;

    /**
     * 应用联系人
     */
    private String contactBy;

    /**
     * 应用联系电话
     */
    private String contactPhone;

    /**
     * 应用联系邮箱
     */
    private String contactMail;

    /**
     * 应用范围，具体看枚举类
     * 枚举类 SkilledAppEnum
     */
    private String appRange;

    /**
     * 行业，具体看枚举类
     * 枚举类 IndustryTypeEnum
     */
    private String industry;

    /**
     * 特征与优势
     */
    private String advantageDesc;

    /**
     * 组件细节
     */
    private String assemblyDetails;

    /**
     * 描述
     */
    private String appDesc;

    /**
     * 简介
     */
    private String appExplain;

    /**
     * 供应商id
     */
    private Long supplierId;

    /**
     * 新增请求联系次数
     */
    private Integer requestCount;

    /**
     * 应用状态
     */
    private AppStatusEnum appStatus;

    /**
     * 产品通用主键
     */
    private List<Long> appIds;

    /**
     * 产品常见问题
     */
    private List<ApplicationProblemReq> applicationProblem;
}

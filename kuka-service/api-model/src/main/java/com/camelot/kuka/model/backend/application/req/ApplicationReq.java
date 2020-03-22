package com.camelot.kuka.model.backend.application.req;

import com.camelot.kuka.model.enums.DeleteEnum;
import com.camelot.kuka.model.enums.application.AppStatusEnum;
import com.camelot.kuka.model.enums.application.AppTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Description: [产品实体]</p>
 * Created on 2020/1/20
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Data
public class ApplicationReq implements Serializable {

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
     * 价格
     */
    private Double price;

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
     * 审核状态
     */
    private AppStatusEnum appStatus;

    /**
     * 审核意见
     */
    private String opinion;

    /**
     * licence
     */
    private String licenceStr;

    /**
     * 删除标识0:未删除;1已删除
     */
    private DeleteEnum delState;

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

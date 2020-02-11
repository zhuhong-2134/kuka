package com.camelot.kuka.model.backend.application.resp;

import com.camelot.kuka.model.backend.comment.resp.CommentResp;
import com.camelot.kuka.model.enums.DeleteEnum;
import com.camelot.kuka.model.enums.application.AppStatusEnum;
import com.camelot.kuka.model.enums.application.AppTypeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>Description: [修改获取的参数]</p>
 * Created on 2020/2/5
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Data
public class QyeryUpdateResp implements Serializable {

    private static final long serialVersionUID = -1357959815800291674L;
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
     * 删除标识0:未删除;1已删除
     */
    private DeleteEnum delState;

    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 修改时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 修改人
     */
    private String updateBy;

    /**
     * 封面图片
     */
    private String[] coverUrls;

    /**
     * 评论信息
     */
    private List<CommentResp> commentList;

    /**
     * 适用产品
     */
    private List<ApplicationResp> currencyList;

    /**
     * 产品的常见问题
     */
    private List<ApplicationProblemResp> problemList;
}

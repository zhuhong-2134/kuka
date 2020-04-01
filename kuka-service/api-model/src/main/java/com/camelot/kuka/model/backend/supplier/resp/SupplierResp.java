package com.camelot.kuka.model.backend.supplier.resp;

import com.camelot.kuka.model.backend.application.resp.ApplicationResp;
import com.camelot.kuka.model.enums.DeleteEnum;
import com.camelot.kuka.model.enums.backend.JumpStatusEnum;
import com.camelot.kuka.model.enums.backend.MessageStatusEnum;
import com.camelot.kuka.model.enums.user.CreateSourceEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>Description: [供应商实体]</p>
 * Created on 2020/1/20
 *
 *
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Data
public class SupplierResp implements Serializable {

    private static final long serialVersionUID = -7880889865468275561L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 封面图
     */
    private String coverUrl;

    /**
     * 列表图
     */
    private String listImg;

    /**
     * 集成商名称
     */
    private String supplierlName;

    /**
     * 集成商详情
     */
    private String supplierlDesc;

    /**
     * 省名称
     */
    private String provinceName;

    /**
     * 省编码
     */
    private String provinceCode;

    /**
     * 市名称
     */
    private String cityName;

    /**
     * 市编码
     */
    private String cityCode;

    /**
     * 区名称
     */
    private String districtName;

    /**
     * 区编码
     */
    private String districtCode;

    /**
     * 所在地
     */
    private String supplierAddress;

    /**
     * 地址json
     */
    private String addressJson;

    /**
     * 网址
     */
    private String webUrl;

    /**
     * 集成商类型
     *  对应枚举 SupplierTypeEnum
     */
    private String type;
    private String typeStr;

    /**
     * 行业，具体看枚举类
     * 对应枚举 IndustryTypeEnum
     */
    private String industry;
    private String industryStr;

    /**
     * 擅长应用
     * 对应枚举 SkilledAppEnum
     */
    private String appType;
    private String appTypeStr;

    /**
     * 经营模式
     * 对应枚举 PatternTypeEnum
     */
    private String patternType;
    private String patternTypeStr;

    /**
     * 营业执照url
     */
    private String iicenseUrl;

    /**
     * 总负责人
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 负责人id
     */
    private Long userId;

    /**
     * 负责人联系方式
     */
    private String userPhone;

    /**
     * 联系邮箱
     */
    private String userMali;

    /**
     * 详细地址
     */
    private String userAddress;

    /**
     * 0:注册;1后台出创建
     */
    private CreateSourceEnum source;

    /**
     * 注册时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date userCreateTime;

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
     * 应用详情
     */
    private List<ApplicationResp> appList;

    /**
     * 跳转状态
     */
    private JumpStatusEnum jumpStatus;

    /**
     * 消息内容
     */
    private String message;

}

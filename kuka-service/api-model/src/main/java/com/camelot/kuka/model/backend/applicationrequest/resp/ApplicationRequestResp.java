package com.camelot.kuka.model.backend.applicationrequest.resp;

import com.camelot.kuka.model.enums.CommunicateEnum;
import com.camelot.kuka.model.enums.DeleteEnum;
import com.camelot.kuka.model.enums.application.AppTypeEnum;
import com.camelot.kuka.model.enums.backend.JumpStatusEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *  应用请求管理
 *
 */
@Data
public class ApplicationRequestResp implements Serializable {

    private static final long serialVersionUID = 5160936187681622384L;


    /**
     * 主键
     */
    private Long id;

    /**
     * 应用封面图
     */
    private String appUrl;

    /**
     * 应用id
     */
    private Long appId;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 应用分类
     */
    private AppTypeEnum classType;

    /**
     * 集成商id
     */
    private Long supplierId;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 总负责人
     */
    private String dutyName;

    /**
     * 负责人id
     */
    private Long dutyId;

    /**
     * 负责人联系方式
     */
    private String dutyPhone;

    /**
     * 应用联系人
     */
    private String appContactName;

    /**
     * 应用联系方式
     */
    private String appPhone;

    /**
     * 联系邮箱
     */
    private String mail;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 状态;0待沟通;1已沟通
     */
    private CommunicateEnum status;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userPhotoUrl;

    /**
     * 用户手机号
     */
    private String userPhone;

    /**
     * 用户邮箱
     */
    private String userMail;

    /**
     * 跳转状态
     */
    private JumpStatusEnum jumpStatus;

    /**
     * 消息内容
     */
    private String message;

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


}

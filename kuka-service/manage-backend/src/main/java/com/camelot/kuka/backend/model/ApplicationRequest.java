package com.camelot.kuka.backend.model;

import com.camelot.kuka.model.enums.CommunicateEnum;
import com.camelot.kuka.model.enums.DeleteEnum;
import com.camelot.kuka.model.enums.application.AppTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *  应用请求管理
 * @author xienan 2020-02-19
 */
@Data
public class ApplicationRequest implements Serializable {

    private static final long serialVersionUID = -2903806199115904083L;
    /**
     * 主键
     */
    private Long id;

    /**
     * 应用封面图
     */
    private String appUrl;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 应用分类
     */
    private AppTypeEnum classType;

    /**
     * 供应商名称
     */
    private String supplierlName;

    /**
     * 总负责人
     */
    private String userMan;

    /**
     * 负责人id
     */
    private Long userId;

    /**
     * 负责人联系方式
     */
    private String userPhone;

    /**
     * 应用联系人
     */
    private String appMan;

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

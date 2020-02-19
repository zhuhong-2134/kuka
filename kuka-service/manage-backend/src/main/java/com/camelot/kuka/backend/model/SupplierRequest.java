package com.camelot.kuka.backend.model;

import com.camelot.kuka.model.enums.CommunicateEnum;
import com.camelot.kuka.model.enums.DeleteEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *  集成商请求管理
 * @author xienan 2020-02-19
 */
@Data
public class SupplierRequest implements Serializable {

    private static final long serialVersionUID = -3470243369614689092L;
    /**
     * 主键
     */
    private Long id;

    /**
     * 集成商封面图
     */
    private String supplierlUrl;

    /**
     * 集成商名称
     */
    private String supplierlName;

    /**
     * 所在地
     */
    private String location;

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

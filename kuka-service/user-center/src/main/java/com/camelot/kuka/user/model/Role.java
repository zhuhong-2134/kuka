package com.camelot.kuka.user.model;

import com.camelot.kuka.model.enums.user.role.RoleStatusEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *  角色表
 * @author xienan 2020-02-17
 */
@Data
public class Role implements Serializable {


    private static final long serialVersionUID = -5635498992149017202L;

    /**
     * 角色id
     */
    private Long id;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 上线下线
     */
    private RoleStatusEnum status;

    /**
     * 角色说明
     */
    private String roleDsc;

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

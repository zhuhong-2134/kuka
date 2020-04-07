package com.camelot.kuka.model.user.role.resp;

import com.camelot.kuka.model.enums.user.role.RoleStatusEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *  角色表
 *   2020-02-17
 */
@Data
public class RoleResp implements Serializable {


    private static final long serialVersionUID = 3105901164639919123L;
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

package com.camelot.kuka.model.user.role.req;

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
public class RoleReq implements Serializable {


    private static final long serialVersionUID = 3941192823485115608L;
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

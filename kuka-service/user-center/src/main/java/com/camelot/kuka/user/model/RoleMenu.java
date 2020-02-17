package com.camelot.kuka.user.model;

import lombok.Data;

import java.io.Serializable;

/**
 *  角色菜单中间表
 * @author xienan 2020-02-17
 */
@Data
public class RoleMenu implements Serializable {


    private static final long serialVersionUID = 1708891916124390460L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 菜单id
     */
    private Long menuId;

}

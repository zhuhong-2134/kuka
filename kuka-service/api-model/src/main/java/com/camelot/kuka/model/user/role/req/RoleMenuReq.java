package com.camelot.kuka.model.user.role.req;

import com.camelot.kuka.model.enums.user.role.RoleMenuEnum;
import lombok.Data;

import java.io.Serializable;


/**
 *  权限关联状态
 * @author xienan 2020-02-17
 */
@Data
public class RoleMenuReq implements Serializable {

    /**
     * 权限ID
     */
    private Long menuId;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 关联状态
     */
    private RoleMenuEnum roleMenuStates;
}

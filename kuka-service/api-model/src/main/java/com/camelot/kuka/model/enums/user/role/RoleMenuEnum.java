package com.camelot.kuka.model.enums.user.role;

import com.camelot.kuka.model.enums.BaseEnum;

/**
 * <p>Description: [权限关联枚举]</p>
 * Created on 2020年2月5日
 *
 * @version 1.0
 * Copyright (c) 2019 北京柯莱特科技有限公司
 */
public enum RoleMenuEnum implements BaseEnum {

    YES(0, "已关联"),
    NO(1, "未关联");

    private Integer code;
    private String description;

    private RoleMenuEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Description: [根据code获取枚举]
     * @param code
     * @return: com.fehorizon.commonService.model.enums.AuditStatusEnum
     * Created on 2020年2月5日
     * @version 1.0
     * Copyright (c) 2019 北京柯莱特科技有限公司
     **/
    public static RoleMenuEnum get(Integer code) {
        if (code == null) {
            return null;
        }
        for (RoleMenuEnum e : RoleMenuEnum.values()) {
            if (code.equals(e.getCode())) {
                return e;
            }
        }
        return null;
    }

    /**
     * Description: [根据description获取枚举]
     * @param description
     * @return: com.fehorizon.commonService.model.enums.AuditStatusEnum
     * Created on 2020年2月5日
     * @version 1.0
     * Copyright (c) 2019 北京柯莱特科技有限公司
     **/
    public static RoleMenuEnum get(String description) {
        if (description == null || description.trim().length() == 0) {
            return null;
        }
        for (RoleMenuEnum e : RoleMenuEnum.values()) {
            if (e.getDescription().equals(description)) {
                return e;
            }
        }
        return null;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return description;
    }

}

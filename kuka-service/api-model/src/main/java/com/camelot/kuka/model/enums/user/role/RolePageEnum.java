package com.camelot.kuka.model.enums.user.role;

import com.camelot.kuka.model.enums.BaseEnum;

/**
 * <p>Description: [角色查询枚举]</p>
 * Created on 2020年2月5日
 * @author <a href="mailto: hexiaobo@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2019 北京柯莱特科技有限公司
 */
public enum RolePageEnum implements BaseEnum {

    ALL(0, "全部"),
    NAME(1, "名称"),
    ID(2, "ID");

    private Integer code;
    private String description;

    private RolePageEnum(Integer code, String description) {
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
    public static RolePageEnum get(Integer code) {
        if (code == null) {
            return null;
        }
        for (RolePageEnum e : RolePageEnum.values()) {
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
    public static RolePageEnum get(String description) {
        if (description == null || description.trim().length() == 0) {
            return null;
        }
        for (RolePageEnum e : RolePageEnum.values()) {
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

package com.camelot.kuka.model.enums;

import java.util.EnumSet;

/**
 * <p>Description: [生成主键KEY枚举类]</p>
 *
 * @author <a href="mailto: cuichunsong@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2019 北京柯莱特科技有限公司
 */
public enum PrincipalEnum {

    USER_USER("user_t_user", "用户中心"),
    
    MANAGE_SUPPLIER("manage_t_supplier", "集成商");

    private String key;
    private String description;

    PrincipalEnum(String code, String description) {
        this.key = code;
        this.description = description;
    }

    public static String get(PrincipalEnum key) {
        for (PrincipalEnum model : EnumSet.allOf(PrincipalEnum.class)) {
            if (key == model) {
                return model.key;
            }
        }
        return null;
    }

    public String getKey() {
        return this.key;
    }

    public String getDescription() {
        return this.description;
    }

}

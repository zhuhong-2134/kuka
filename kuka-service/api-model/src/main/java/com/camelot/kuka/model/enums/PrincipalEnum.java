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
    USER_ROLE("user_t_role", "角色管理"),
    USER_ROLE_MENU("user_t_role_menu", "角色菜单中间表"),
    USER_MENU("user_t_menu", "菜单表"),

    MANAGE_SUPPLIER("manage_t_supplier", "集成商"),

    MANAGE_APPLICATION("manage_t_application", "应用"),

    MANAGE_APPLICATION_IMG("manage_t_application_img", "应用图片"),

    MANAGE_APPLICATION_CURRENCY("manage_t_application_currency", "通用产品"),

    MANAGE_COMMENT("manage_t_comment", "评论"),

    MANAGE_COMMENT_TYPE("manage_t_comment_type", "评论分类"),

    MANAGE_APPLICATION_PROBLEM("manage_t_application_problem", "应用常见问题");

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

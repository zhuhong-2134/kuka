package com.camelot.kuka.model.enums;

import java.util.EnumSet;

/**
 * <p>Description: [生成主键KEY枚举类]</p>
 *
 *
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

    MANAGE_SHOP_CART("tableIncr:manage:shopCart:shopCartId", "购物车"),

    MANAGE_ORDER("tableIncr:manage:order:orderId", "订单"),

    MANAGE_ORDERDETAILED("tableIncr:manage:orderDetailed:orderId", "订单明细"),

    MANAGE_APPLICATION_REQUEST("tableIncr:manage:applicationRequest:requestId", "应用请求"),

    MANAGE_SUPPLIER_REQUEST("tableIncr:manage:supplierRequest:requestId", "集成商请求"),

    MANAGE_APPLICATION_IMG("manage_t_application_img", "应用图片"),

    MANAGE_APPLICATION_CURRENCY("manage_t_application_currency", "通用产品"),

    MANAGE_COMMENT("manage_t_comment", "评论"),

    MANAGE_COMMENT_TYPE("manage_t_comment_type", "评论分类"),

    MANAGE_APPLICATION_PROBLEM("manage_t_application_problem", "应用常见问题"),

    MANAGE_MAIL_MOULD("tableIncr:manage:mail:mould","消息模板表"),

    MANAGE_MESSAGE("tableIncr:manage:message:messageId","站内消息表"),


    ;

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

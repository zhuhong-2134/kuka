package com.camelot.kuka.model.enums.backend;

import com.camelot.kuka.model.enums.BaseEnum;

/**
 * <p>Description: [应用请求查询枚举]</p>
 * Created on 2020年2月5日
 *
 * @version 1.0
 * Copyright (c) 2019 北京柯莱特科技有限公司
 */
public enum AppRequestPageEnum implements BaseEnum {

    // 0 全部 1 用户名 2 ID 3 应用名称

    ALL(0, "全部"),
    USERNAME(1, "用户名"),
    ID(2, "ID"),
    APPNAME(3, "应用名称");

    private Integer code;
    private String description;

    private AppRequestPageEnum(Integer code, String description) {
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
    public static AppRequestPageEnum get(Integer code) {
        if (code == null) {
            return null;
        }
        for (AppRequestPageEnum e : AppRequestPageEnum.values()) {
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
    public static AppRequestPageEnum get(String description) {
        if (description == null || description.trim().length() == 0) {
            return null;
        }
        for (AppRequestPageEnum e : AppRequestPageEnum.values()) {
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

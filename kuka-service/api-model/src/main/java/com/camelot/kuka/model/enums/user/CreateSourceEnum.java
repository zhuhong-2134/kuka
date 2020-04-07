package com.camelot.kuka.model.enums.user;

import com.camelot.kuka.model.enums.BaseEnum;

/**
 * <p>Description: [用户来源]</p>
 * Created on 2020年2月5日
 *
 * @version 1.0
 * Copyright (c) 2019 北京柯莱特科技有限公司
 */
public enum CreateSourceEnum implements BaseEnum {

    REGISTER(0, "注册"),

    BACKSTAGE(1, "后台创建");

    private Integer code;
    private String description;

    private CreateSourceEnum(Integer code, String description) {
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
    public static CreateSourceEnum get(Integer code) {
        if (code == null) {
            return null;
        }
        for (CreateSourceEnum e : CreateSourceEnum.values()) {
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
    public static CreateSourceEnum get(String description) {
        if (description == null || description.trim().length() == 0) {
            return null;
        }
        for (CreateSourceEnum e : CreateSourceEnum.values()) {
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

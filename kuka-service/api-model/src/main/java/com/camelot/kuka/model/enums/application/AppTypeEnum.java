package com.camelot.kuka.model.enums.application;

import com.camelot.kuka.model.enums.BaseEnum;

/**
 * <p>Description: [应用分类枚举]</p>
 * Created on 2020年2月5日
 *
 * @version 1.0
 * Copyright (c) 2019 北京柯莱特科技有限公司
 */
public enum AppTypeEnum implements BaseEnum {

    ALL(0, "ALL"),
    SOFTWARE(1, "软件"),
    READYTOUSE(2, "Ready to use"),
    JQRFJ(3, "机器人附件"),
    GZHGZ(4, "客制化工站"),
    ZXFG(5, "整线方案");

    private Integer code;
    private String description;

    private AppTypeEnum(Integer code, String description) {
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
    public static AppTypeEnum get(Integer code) {
        if (code == null) {
            return null;
        }
        for (AppTypeEnum e : AppTypeEnum.values()) {
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
    public static AppTypeEnum get(String description) {
        if (description == null || description.trim().length() == 0) {
            return null;
        }
        for (AppTypeEnum e : AppTypeEnum.values()) {
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

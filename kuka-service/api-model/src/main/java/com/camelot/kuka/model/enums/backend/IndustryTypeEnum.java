package com.camelot.kuka.model.enums.backend;

import com.camelot.kuka.model.enums.BaseEnum;

/**
 * <p>Description: [所属行业枚举]</p>
 * Created on 2020年2月5日
 * @author <a href="mailto: hexiaobo@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2019 北京柯莱特科技有限公司
 */
public enum IndustryTypeEnum implements BaseEnum {

    CAR(0, "汽车行业"),
    ELECTRONICS(1, "电子商务和零售..."),
    DIANZI(2, "电子行业"),
    NENGYUAN(3, "能源行业"),
    BAOJIAN(4, "保健"),
    XIAOFEIPIN(5, "消费品行业"),
    JINSHU(6, "金属行业"),
    QITA(7, "其他");

    private Integer code;
    private String description;

    private IndustryTypeEnum(Integer code, String description) {
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
    public static IndustryTypeEnum get(Integer code) {
        if (code == null) {
            return null;
        }
        for (IndustryTypeEnum e : IndustryTypeEnum.values()) {
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
    public static IndustryTypeEnum get(String description) {
        if (description == null || description.trim().length() == 0) {
            return null;
        }
        for (IndustryTypeEnum e : IndustryTypeEnum.values()) {
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

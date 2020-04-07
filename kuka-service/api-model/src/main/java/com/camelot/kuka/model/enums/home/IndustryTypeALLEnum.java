package com.camelot.kuka.model.enums.home;

import com.camelot.kuka.model.enums.BaseEnum;

/**
 * <p>Description: [所属行业枚举]</p>
 * Created on 2020年2月5日
 *
 * @version 1.0
 * Copyright (c) 2019 北京柯莱特科技有限公司
 */
public enum IndustryTypeALLEnum implements BaseEnum {

    ALL(-1, "全部"),
    CAR(0, "汽车行业"),
    ELECTRONICS(1, "电子商务和零售物流"),
    DIANZI(2, "电子行业"),
    NENGYUAN(3, "能源行业"),
    BAOJIAN(4, "保健"),
    XIAOFEIPIN(5, "消费品行业"),
    JINSHU(6, "金属行业"),
    QITA(7, "其他");

    private Integer code;
    private String description;

    private IndustryTypeALLEnum(Integer code, String description) {
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
    public static IndustryTypeALLEnum get(Integer code) {
        if (code == null) {
            return null;
        }
        for (IndustryTypeALLEnum e : IndustryTypeALLEnum.values()) {
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
    public static IndustryTypeALLEnum get(String description) {
        if (description == null || description.trim().length() == 0) {
            return null;
        }
        for (IndustryTypeALLEnum e : IndustryTypeALLEnum.values()) {
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

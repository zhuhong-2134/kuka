package com.camelot.kuka.model.enums.backend;

import com.camelot.kuka.model.enums.BaseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Description: [集成商类型]</p>
 * Created on 2020年2月5日
 *
 * @version 1.0
 * Copyright (c) 2019 北京柯莱特科技有限公司
 */
public enum SupplierTypeEnum implements BaseEnum {

    ROBOT(0, "机器人"),
    AGV(1, "AGV"),
    APP(2, "应用设备");

    private Integer code;
    private String description;

    private SupplierTypeEnum(Integer code, String description) {
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
    public static SupplierTypeEnum get(Integer code) {
        if (code == null) {
            return null;
        }
        for (SupplierTypeEnum e : SupplierTypeEnum.values()) {
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
    public static SupplierTypeEnum get(String description) {
        if (description == null || description.trim().length() == 0) {
            return null;
        }
        for (SupplierTypeEnum e : SupplierTypeEnum.values()) {
            if (e.getDescription().equals(description)) {
                return e;
            }
        }
        return null;
    }

    /**
     *  将枚举转换成map
     * @return
     */
    public static Map<String,String> getMap(){
        Map<String,String> map = new HashMap<>();
        for (SupplierTypeEnum e : SupplierTypeEnum.values()) {
            map.put(e.description, e.name());
        }
        return map;
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

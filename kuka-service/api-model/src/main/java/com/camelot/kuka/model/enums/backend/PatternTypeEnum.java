package com.camelot.kuka.model.enums.backend;

import com.camelot.kuka.model.enums.BaseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Description: [经营模式枚举]</p>
 * Created on 2020年2月5日
 *
 * @version 1.0
 * Copyright (c) 2019 北京柯莱特科技有限公司
 */
public enum PatternTypeEnum implements BaseEnum {

    ZZYF(0, "自主研发"),
    SCJG(1, "生产加工"),
    CJZX(3, "厂家直销"),
    DGDX(4, "代购代销");

    private Integer code;
    private String description;

    private PatternTypeEnum(Integer code, String description) {
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
    public static PatternTypeEnum get(Integer code) {
        if (code == null) {
            return null;
        }
        for (PatternTypeEnum e : PatternTypeEnum.values()) {
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
    public static PatternTypeEnum get(String description) {
        if (description == null || description.trim().length() == 0) {
            return null;
        }
        for (PatternTypeEnum e : PatternTypeEnum.values()) {
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
        for (PatternTypeEnum e : PatternTypeEnum.values()) {
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

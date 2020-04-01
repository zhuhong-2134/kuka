package com.camelot.kuka.model.enums;

/**
 * <p>Description: [沟通标识]</p>
 * Created on 2020年2月5日
 *
 * @version 1.0
 * Copyright (c) 2019 北京柯莱特科技有限公司
 */
public enum CommunicateEnum implements BaseEnum {

    NO(0, "未沟通"),

    YES(1, "已沟通");

    private Integer code;
    private String description;

    private CommunicateEnum(Integer code, String description) {
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
    public static CommunicateEnum get(Integer code) {
        if (code == null) {
            return null;
        }
        for (CommunicateEnum e : CommunicateEnum.values()) {
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
    public static CommunicateEnum get(String description) {
        if (description == null || description.trim().length() == 0) {
            return null;
        }
        for (CommunicateEnum e : CommunicateEnum.values()) {
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

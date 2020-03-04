package com.camelot.kuka.model.enums.order;

import com.camelot.kuka.model.enums.BaseEnum;

/**
 * <p>Description: [支付方式枚举]</p>
 * Created on 2020年2月12日
 * @author <a href="mailto: hexiaobo@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2019 北京柯莱特科技有限公司
 */
public enum PayTypeEnum implements BaseEnum {

    WECHAT(0, "微信"),
    ZIFUBAO(1, "支付宝");

    private Integer code;
    private String description;

    private PayTypeEnum(Integer code, String description) {
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
    public static PayTypeEnum get(Integer code) {
        if (code == null) {
            return null;
        }
        for (PayTypeEnum e : PayTypeEnum.values()) {
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
    public static PayTypeEnum get(String description) {
        if (description == null || description.trim().length() == 0) {
            return null;
        }
        for (PayTypeEnum e : PayTypeEnum.values()) {
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

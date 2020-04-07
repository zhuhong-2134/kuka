package com.camelot.kuka.model.enums.order;

import com.camelot.kuka.model.enums.BaseEnum;

/**
 * <p>Description: [买卖枚举]</p>
 * Created on 2020年2月5日
 *
 * @version 1.0
 * Copyright (c) 2019 北京柯莱特科技有限公司
 */
public enum OrderBusinessEnum implements BaseEnum {

    BUY(0, "已买订单"),
    SELL(1, "已卖订单");

    private Integer code;
    private String description;

    private OrderBusinessEnum(Integer code, String description) {
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
    public static OrderBusinessEnum get(Integer code) {
        if (code == null) {
            return null;
        }
        for (OrderBusinessEnum e : OrderBusinessEnum.values()) {
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
    public static OrderBusinessEnum get(String description) {
        if (description == null || description.trim().length() == 0) {
            return null;
        }
        for (OrderBusinessEnum e : OrderBusinessEnum.values()) {
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

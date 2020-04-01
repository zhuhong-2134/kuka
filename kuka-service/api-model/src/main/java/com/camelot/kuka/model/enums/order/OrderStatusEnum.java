package com.camelot.kuka.model.enums.order;

import com.camelot.kuka.model.enums.BaseEnum;

/**
 * <p>Description: [应用分类枚举]</p>
 * Created on 2020年2月12日
 *
 * @version 1.0
 * Copyright (c) 2019 北京柯莱特科技有限公司
 */
public enum OrderStatusEnum implements BaseEnum {

    WAIT(0, "待支付"),
    END(1, "已完成"),
    CANCEL(2, "取消");

    private Integer code;
    private String description;

    private OrderStatusEnum(Integer code, String description) {
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
    public static OrderStatusEnum get(Integer code) {
        if (code == null) {
            return null;
        }
        for (OrderStatusEnum e : OrderStatusEnum.values()) {
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
    public static OrderStatusEnum get(String description) {
        if (description == null || description.trim().length() == 0) {
            return null;
        }
        for (OrderStatusEnum e : OrderStatusEnum.values()) {
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

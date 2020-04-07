package com.camelot.kuka.model.enums.mailmould;

import com.camelot.kuka.model.enums.BaseEnum;

/**
 * <p>Description: [邮件发送类型]</p>
 * Created on 2020年2月5日
 *
 * @version 1.0
 * Copyright (c) 2019 北京柯莱特科技有限公司
 */
public enum MailTypeEnum implements BaseEnum {

    MAIL(0, "邮件"),
    MESSAGE(1, "站内消息"),
    ALL(2, "全发");

    private Integer code;
    private String description;

    private MailTypeEnum(Integer code, String description) {
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
    public static MailTypeEnum get(Integer code) {
        if (code == null) {
            return null;
        }
        for (MailTypeEnum e : MailTypeEnum.values()) {
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
    public static MailTypeEnum get(String description) {
        if (description == null || description.trim().length() == 0) {
            return null;
        }
        for (MailTypeEnum e : MailTypeEnum.values()) {
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

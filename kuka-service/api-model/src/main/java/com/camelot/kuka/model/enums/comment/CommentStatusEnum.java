package com.camelot.kuka.model.enums.comment;

import com.camelot.kuka.model.enums.BaseEnum;

/**
 * <p>Description: [评论审核枚举]</p>
 * Created on 2020年2月5日
 *
 * @version 1.0
 * Copyright (c) 2019 北京柯莱特科技有限公司
 */
public enum CommentStatusEnum implements BaseEnum {

    WAIT(0, "待审核"),
    NO(1, "审核不通过"),
    YES(2, "审核通过");

    private Integer code;
    private String description;

    private CommentStatusEnum(Integer code, String description) {
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
    public static CommentStatusEnum get(Integer code) {
        if (code == null) {
            return null;
        }
        for (CommentStatusEnum e : CommentStatusEnum.values()) {
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
    public static CommentStatusEnum get(String description) {
        if (description == null || description.trim().length() == 0) {
            return null;
        }
        for (CommentStatusEnum e : CommentStatusEnum.values()) {
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

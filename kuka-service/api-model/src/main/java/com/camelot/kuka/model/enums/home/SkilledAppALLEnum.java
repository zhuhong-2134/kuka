package com.camelot.kuka.model.enums.home;

import com.camelot.kuka.model.enums.BaseEnum;

/**
 * <p>Description: [擅长应用枚举]</p>
 * Created on 2020年2月5日
 * @author <a href="mailto: hexiaobo@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2019 北京柯莱特科技有限公司
 */
public enum SkilledAppALLEnum implements BaseEnum {

    ALL(-1, "全部"),
    ROBOT(0, "安装、置放"),
    BZHJP(1, "包装和炼配"),
    BHQTHJ(2, "保护气体焊接"),
    BJ(3, "编辑"),
    ZZJC(4, "操作机床"),
    ZCJSYZJ(5, "操作金属压铸机"),
    ZCSLJGSB(6, "操作熟料加工设备"),
    CLJCJY(7, "测量、检测和检验"),
    CX(8, "拆卸"),
    ZYZDHZW(9, "冲压、锻造和折弯"),
    DH(10, "点焊"),
    DD(11, "堆垛"),
    GDHYZ(12, "固定和压装"),
    HJHHH(13, "焊接和钎焊"),
    HXJG(14, "机械加工"),
    JGH(15, "激光焊"),
    JGHQ(16, "激光切割"),
    JSQXCJ(17, "金属切削机床"),
    SLXL(18, "上料、卸料"),
    SSLQG(19, "水射流切割"),
    SLJGSB(20, "塑料加工设备"),
    TQHSY(21, "涂漆和上釉"),
    QT(22, "其他");

    private Integer code;
    private String description;

    private SkilledAppALLEnum(Integer code, String description) {
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
    public static SkilledAppALLEnum get(Integer code) {
        if (code == null) {
            return null;
        }
        for (SkilledAppALLEnum e : SkilledAppALLEnum.values()) {
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
    public static SkilledAppALLEnum get(String description) {
        if (description == null || description.trim().length() == 0) {
            return null;
        }
        for (SkilledAppALLEnum e : SkilledAppALLEnum.values()) {
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

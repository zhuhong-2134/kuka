package com.camelot.kuka.backend.model;

import com.camelot.kuka.model.enums.DeleteEnum;
import com.camelot.kuka.model.enums.backend.IndustryTypeEnum;
import com.camelot.kuka.model.enums.backend.PatternTypeEnum;
import com.camelot.kuka.model.enums.backend.SkilledAppEnum;
import com.camelot.kuka.model.enums.backend.SupplierTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Description: [供应商实体]</p>
 * Created on 2020/1/20
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Data
public class Supplier implements Serializable {

    private static final long serialVersionUID = -7193812869676145912L;
    /**
     * 主键
     */
    private Long id;

    /**
     * 封面图
     */
    private String coverUrl;

    /**
     * 集成商名称
     */
    private String supplierlName;

    /**
     * 集成商详情
     */
    private String supplierlDesc;

    /**
     * 省名称
     */
    private String provinceName;

    /**
     * 省编码
     */
    private String provinceCode;

    /**
     * 市名称
     */
    private String cityName;

    /**
     * 市编码
     */
    private String cityCode;

    /**
     * 区名称
     */
    private String districtName;

    /**
     * 区编码
     */
    private String districtCode;

    /**
     * 网址
     */
    private String webUrl;

    /**
     * 集成商类型
     */
    private SupplierTypeEnum type;

    /**
     * 行业，具体看枚举类
     */
    private IndustryTypeEnum industry;

    /**
     * 擅长应用
     */
    private SkilledAppEnum appType;

    /**
     * 经营模式
     */
    private PatternTypeEnum patternType;

    /**
     * 营业执照url
     */
    private String iicenseUrl;

    /**
     * 删除标识0:未删除;1已删除
     */
    private DeleteEnum delState;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 修改人
     */
    private String updateBy;

    /**********************************/
    /*****以下字段,不是数据库字段******/
    /**********************************/

    /**
     * 地址json
     */
    private String addressJson;
}

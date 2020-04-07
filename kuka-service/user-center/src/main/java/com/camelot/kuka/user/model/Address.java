package com.camelot.kuka.user.model;

import lombok.Data;

import java.io.Serializable;

/**
 *  地区码表
 *    2020-03-01
 */
@Data
public class Address implements Serializable {

    /**
     * 地区id
     */
    private Integer areaid;

    /**
     * 地区编码
     */
    private String areacode;

    /**
     * 地区名
     */
    private String areaname;

    /**
     * 地区级别（1:省份province，2:市city，3:区县district，4:街道street）
     */
    private boolean level;

    /**
     * 城市编码
     */
    private String citycode;

    /**
     * 城市中心点（即：经纬度坐标）
     */
    private String center;

    /**
     * 地区父节点
     */
    private Integer parentid;

}

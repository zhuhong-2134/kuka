package com.camelot.kuka.model.shopcart.resp;

import com.camelot.kuka.model.enums.DeleteEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *  购物车
 *    2020-03-02
 */
@Data
public class ShopCartResp implements Serializable {

    private static final long serialVersionUID = 8239523153303632112L;
    /**
     * id
     */
    private Long id;

    /**
     * 应用封面图
     */
    private String appUrl;

    /**
     * 应用主键
     */
    private Long appId;

    /**
     * 应用标题
     */
    private String appName;

    /**
     * 集成商id
     */
    private Long supplierId;

    /**
     * 单价
     */
    private Double price;

    /**
     * 数量
     */
    private Integer num;

    /**
     * 总价
     */
    private Double sunPrice;

    /**
     * 删除标识0:未删除;1已删除
     */
    private DeleteEnum delState;

    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 修改时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 修改人
     */
    private String updateBy;

    /**********************************/
    /*****以下字段,不是数据库字段******/
    /**********************************/

    /**
     * 订单主键
     */
    private Long[] ids;

}

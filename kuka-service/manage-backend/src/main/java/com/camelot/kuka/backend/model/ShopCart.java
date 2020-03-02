package com.camelot.kuka.backend.model;

import com.camelot.kuka.model.enums.DeleteEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *  购物车
 * @author 谢楠 2020-03-02
 */
@Data
public class ShopCart implements Serializable {

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
     * 订单主键
     */
    private Long[] ids;

}

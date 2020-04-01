package com.camelot.kuka.model.user.menu.resp;

import com.camelot.kuka.model.enums.user.UserTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *  菜单
 *   2020-02-17
 */
@Data
public class MenuResp implements Serializable {


    private static final long serialVersionUID = 5256870081109171915L;
    /**
     * 菜单主键
     */
    private Long id;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 父级菜单
     */
    private Long pId;

    /**
     * 跳转url
     */
    private String hrefUrl;

    /**
     * 待确定,来访者,集成商,kuka用户
     */
    private UserTypeEnum type;

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

}

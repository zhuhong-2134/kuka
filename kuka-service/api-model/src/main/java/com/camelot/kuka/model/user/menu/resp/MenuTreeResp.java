package com.camelot.kuka.model.user.menu.resp;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *  tree菜单
 *   2020-02-17
 */
@Data
public class MenuTreeResp implements Serializable {

    private static final long serialVersionUID = -4754380131495535239L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 组织架构名称
     */
    private String MenuName;

    /**
     * 跳转url
     */
    private String hrefUrl;

    /**
     * 父ID
     */
    private Long parentId;

    private List<MenuTreeResp> children;

}

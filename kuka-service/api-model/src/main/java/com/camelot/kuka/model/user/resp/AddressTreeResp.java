package com.camelot.kuka.model.user.resp;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *  tree地址
 * @author xienan 2020-02-17
 */
@Data
public class AddressTreeResp implements Serializable {

    private static final long serialVersionUID = -4754380131495535239L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 编码
     */
    private String code;

    /**
     * 父ID
     */
    private Long parentId;

    private List<AddressTreeResp> children;

}

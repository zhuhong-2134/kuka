package com.camelot.kuka.common.utils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>Description: [代理]</p >
 * Created on 2019年09月06日
 *
 * @author <a href="xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2019 北京柯莱特科技有限公司
 */
@Getter
@Setter
public class HttpProxy {

    @ApiModelProperty(value = "是否需要代理, true 需要 false 不需要")
    private Boolean proxy = false;

    @ApiModelProperty(value = "代理地址")
    private String proxyUrl;

    @ApiModelProperty(value = "代理端口")
    private Integer proxyPort;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "密码")
    private String password;


    public HttpProxy() {
    }

    public HttpProxy(Boolean proxy, String proxyUrl, Integer proxyPort, String userName, String password) {
        this.proxy = proxy;
        this.proxyUrl = proxyUrl;
        this.proxyPort = proxyPort;
        this.userName = userName;
        this.password = password;
    }
}

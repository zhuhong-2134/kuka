package com.camelot.kuka.common.model.unpay;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 中行支付请求数据
 */

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Request {

    private Head head;

    private Body body;


}

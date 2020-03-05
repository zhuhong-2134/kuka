package com.camelot.kuka.common.model.unpay;

import lombok.Data;

@Data
public class Body {
    private String orderNo;//订单号
    private int currency = 156;//币种 人民币：156
    private int amount;//金额  单位：分
    private Long orderTime;//商户订单时间 格式：YYYYMMDDHHMISS
    private String orderNote;//订单描述
    private String backNotifyUrl;//异步通知URL
    private String frontNotifyUrl;//前端通知页面
    private Long closeTime;//超时时间
    private String payerBankEpsbtp = "104";//付款银行人行行别  中行：104
    private String subMerchantInfo;//二级商户信息

}

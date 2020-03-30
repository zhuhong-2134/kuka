package com.camelot.kuka.common.payconfig;

public class BocPayConfig {

    public static String pgwPortalUrl = "https://101.231.206.170/PGWPortal";	//上传下载BocnetExpress地址
    //public static String signKeyFile = "/data/payfile/config/payments/test.pfx";    // 签名证书
    public static String signKeyFile = "/Users/cuichunsong/ccs/d/jar/payments/test.pfx";
    public static String signkeyPassword = "123456"; // 签名证书密码
    //public static String verifyCerFile = "/data/payfile/config/payments/bocnetcaSIT.cer"; // 验签的中行根证书
    public static String verifyCerFile = "/Users/cuichunsong/ccs/d/jar/payments/bocnetcaSIT.cer";
    public static String notify_url = "http://52.83.60.249:1111/bocpay/notice";//异步通知
    public static String return_url = "http://52.83.60.249/";//跳转页面
    public static String merchantNo = "6876";//商户编码
}

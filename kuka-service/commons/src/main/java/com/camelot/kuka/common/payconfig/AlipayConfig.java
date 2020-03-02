package com.camelot.kuka.common.payconfig;

/* *
 *类名：AlipayConfig
 *功能：支付宝支付基础配置类
 *说明：
 */
public class AlipayConfig {

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2016101800717567";
    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCNCQpMt1IuPBi5FVPbiCIA/CsoG2A+B6EP4icnPtrPsWtKF4eu3n+/28jfFqzH7NMS4JpDKnaMQSLqpInTKKWXky1GGdgeF3Nj3q1XzZZ6T1kRfglQcdvS0rzxYova4Uytrx29JE20Z3sjJxYdjJdI/EPIj8wMS0fnxi6uJ594JINSkv29rTxQHgP5C34ciCpKMHQHfLmPxSS2GYE3f852DBnenT7vDJRpM8br7saE/Gg4+Om5ThZeUr1oaXyUu7bPeDbJqexsAIIOjFVu9mCSbJjOHPk5Hexgq/QNJelV+OOKMJPDt1/UFBoBVVf6sszWN8gwH5kOaVJGv2DBgH1JAgMBAAECggEAa9AUWo7tOMDiGWKPZ0vKyrv6db9vtBt66Tx1t8+Zz5mOLeaM54XZXJ40ES4xSOfmdxDRfDTSyObmPT8oyQ3SgGuW+Xa6Ok6s/BR2qJ9VwAw2R2P01NUlOSW2IES4Kst+JWuvrS46ZIWSoU243z/hSn7lhkU+s8s8nZZniuRpSJpkFCIsMqyZAx993v8Tb+yaE6/8kIP0KxZqGqC99y7G/EuHpuTi0BusYg7nETUkc+Sn17c5iLAZNqLzcSa5LWQaO6Rqq8NtrsPzEntoN/wh5FkVrSvsz7Aix/yTKtwYNgfRBAgDyj/iKfO2OXCFU9msIfLvMmJGS/J5TDiXdIjPDQKBgQDX3+XZJM7M4BK861CB1lGslK0mqfmRqXnHx+XqARpM1uZgmEeudmg1FCG7x8OxZpNwekZ9dAbimlpOs9LPD2/YrbECXQcVzQJeQNMJE1kvhjlDNtlp8hCUfJ4ZFH/P8KTWBcI5ykkWiLK12oKCRKH186M8XBUxAmcV2GOpgTI76wKBgQCnQAQDrhismrltb8OG5ngCjJZoIiXuypScuNb90G9rLHAmQZL94lPJezQ+SmeLWy5qOtyVUuh3Kozs25DgXQoN6SBtRVh/XRzm9UGsHsOFfboWQKiWVhXuPk7jVswMyLM4EW2KNyOwrF8q4mnszQT1gWPT7VZXRm+xj7YGQSMimwKBgBu/g+Ptf0eGZeIyvR5zVfxBACDAKHw4tJahc0Qo8CCAXwx7hns+bRalh9CDVPwwmzMSBI2LpHbsMD99jdNfwJixetc67fY3a96Bpp68BfK95g5ltnyPyOHNQuUmZWdtH0MDanvTLKHg4FfPxobIG6lpr8nKbE7oaCe+sNDQ8owRAoGATRPwha2nS2ynBitaxgHSzwHxkx4hszIDWVLKKgD0SEUbjlXsIzs5+AHiqs5pd8zW0Dg+Tkb+QWUHu2TZGL1oE88blaA+z7uexUXENUmcPBocN4Mm7tnPk5N9kcH/meiox68od2k/9n0KAt6DiJFXtrkhw5p+jww0D3W2op1ETv8CgYBZQNzhJA00zPVdO4q/9v/td+hE5grfSvFCrtiGqa/Kf9ibaZr7LkBp3D5bDOT63Q4Ph+zBteFd2xmgsNhHp5p8/A/devHvDRIIQE7tSqr5LCs+mJNGborUjcbie75lmlqN1DNH9GinV/ql8s+aLjzUlzXVc60d2TO4d6oqcfV5qg==";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtDzWXLxeIoZryzJBQGzWIuiMnY1emqJbC/S1FHjP+1r2NjdU8/a4W8ETsqfY6Mr7WYunHP/vMFfg/ZeruU8z4ykoFwWuhfY8wL7FoBT6ePfAqAEy3uAijjrcBdwUkRZHsqK1AzQo8lSSfkqG2bdhlzRo9IJ2D+3a5KFVxu4YccW/fCA0mwRVMk1mytf7jJTQ2wJIX4Pho2cNjuqJCAZqN9yOkTyHu2E1UzGiHRyCgCd3AvLKz5sV8FHnaHF40nboQHENpzA4ehm7jArw30y7AysqCn7zPnbRRKL+g6RaZUB7uhs7QWIJL8Wgn4Y+3KUsQGutoulEp1RbnJ58ETekuQIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://161.189.2.249:1111/alipay/notice";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://161.189.2.249/#/webIndex";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关
    public static String log_path = "C:\\";
}

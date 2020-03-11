package com.camelot.kuka.backend.controller;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.camelot.kuka.backend.service.OrderService;
import com.camelot.kuka.common.controller.BaseController;
import com.camelot.kuka.common.payconfig.AlipayConfig;
import com.camelot.kuka.model.backend.order.req.OrderReq;
import com.camelot.kuka.model.backend.order.resp.OrderDetailedResp;
import com.camelot.kuka.model.backend.order.resp.OrderResp;
import com.camelot.kuka.model.common.CommonReq;
import com.camelot.kuka.model.common.Result;
import com.camelot.kuka.model.enums.order.OrderStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/***
 * 支付入口
 */

@Slf4j
@RestController
public class PayController extends BaseController {

    @Resource
    private OrderService orderService;

    @GetMapping("/pay/alipay")
    public String aliPay(CommonReq req) throws Exception {

        OrderResp order = orderService.queryById(req).getData();

        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = order.getOrderNo();
        //付款金额，必填
        String total_amount = order.getSunPrice().toString();
        //订单名称，必填
        String subject = order.getDetaileList().get(0).getAppName();
        //商品描述，可空
        String body = "用户订购商品 ";
        for (OrderDetailedResp detail : order.getDetaileList()) {
            body += detail.getAppName() + " ，个数：" + detail.getNum() + "";
        }

        // 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
        String timeout_express = "1c";

        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
                + "\"total_amount\":\"" + total_amount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"body\":\"" + body + "\","
                + "\"timeout_express\":\"" + timeout_express + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        //请求
        String result = alipayClient.pageExecute(alipayRequest).getBody();

        return result;

    }


    /***
     * 支付宝异步通知
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/alipay/notice")
    @ResponseBody
    public String alipayNotifyNotice(HttpServletRequest request, HttpServletRequest response) {

        log.info("支付成功, 进入异步通知接口...");

        try {
            //获取支付宝POST过来反馈信息
            Map<String,String> params = new HashMap<String,String>();
            Map<String,String[]> requestParams = request.getParameterMap();
            for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                params.put(name, valueStr);
            }

            boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名


            /*
            1、需要验证该通知数据中的out_trade_no是否为系统中创建的订单号，
            2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
            3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
            4、验证app_id是否为该商户本身。
            */
            if(signVerified) {//验证成功
                //商户订单号
                String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

                //支付宝交易号
                String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

                //交易状态
                String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

                //付款金额
                String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");

                if(trade_status.equals("TRADE_FINISHED")){//如果没有退款此处不用处理
                    //判断该笔订单是否在商户网站中已经做过处理
                    //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                    //如果有做过处理，不执行商户的业务程序

                    //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
                }else if (trade_status.equals("TRADE_SUCCESS")){
                    //判断该笔订单是否在商户网站中已经做过处理
                    //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                    //如果有做过处理，不执行商户的业务程序

                    //注意：
                    //付款完成后，支付宝系统发送该交易状态通知

                    // 修改订单状态，改为 支付成功，已付款; 同时新增支付流水
                    OrderReq req = new OrderReq();
                    req.setOrderNo(out_trade_no);
                    req.setStatus(OrderStatusEnum.END);
                    req.setSerialNumber(trade_no);
                    Result result = orderService.updateOrder(req, "admin");
                    if (!result.isSuccess()) {
                        log.info("********************** 支付后修改订单异常 **********************");
                    }

                    log.info("********************** 支付成功(支付宝异步通知) **********************");
                    log.info("* 订单号: {}", out_trade_no);
                    log.info("* 支付宝交易号: {}", trade_no);
                    log.info("* 实付金额: {}", total_amount);
                    log.info("***************************************************************");
                    log.info("所有请求参数：{}", JSON.toJSONString(params));
                }
                log.info("支付成功...");

            }else {//验证失败
                log.info("支付, 验签失败...");
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return "error";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "error";
        }


        return "success";
    }

}

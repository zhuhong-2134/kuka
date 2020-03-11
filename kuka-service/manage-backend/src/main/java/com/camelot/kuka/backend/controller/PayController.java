package com.camelot.kuka.backend.controller;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.camelot.kuka.backend.service.OrderService;
import com.camelot.kuka.common.controller.BaseController;
import com.camelot.kuka.common.model.unpay.Body;
import com.camelot.kuka.common.model.unpay.Request;
import com.camelot.kuka.common.payconfig.AlipayConfig;
import com.camelot.kuka.common.payconfig.BocPayConfig;
import com.camelot.kuka.common.utils.DateUtils;
import com.camelot.kuka.common.utils.XmlUtils;
import com.camelot.kuka.common.utils.pay.payment.common.security.Base64;
import com.camelot.kuka.common.utils.pay.payment.common.security.PKCSTool;
import com.camelot.kuka.common.utils.pay.payment.common.util.Constants;
import com.camelot.kuka.common.utils.pay.payment.common.util.XmlUtil;
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
import org.w3c.dom.Document;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
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

    /**
     * 支付宝支付
     * @param req
     * @return
     * @throws Exception
     */
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
        String timeout_express = "1h";

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

    /**
     * 中行支付
     * @param req
     * @param httpServletRequest
     * @param httpServletResponse
     * @return
     */
    @GetMapping("/pay/bocPay")
    public String bocPay(CommonReq req, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        OrderResp order = orderService.queryById(req).getData();
        Request request = new Request();
        Body body = new Body();
        body.setOrderNo(order.getOrderNo());//订单号
        body.setAmount((int)(order.getSunPrice()*100));//价格 单位：分
        body.setOrderTime(Long.parseLong(DateUtils.dateToStr(order.getCreateTime(),"yyyyMMddHHmmss")));//订单时间
        //商品描述，可空
        String note = "用户订购商品 ";
        for (OrderDetailedResp detail : order.getDetaileList()) {
            note += detail.getAppName() + " ，个数：" + detail.getNum() + "";
        }
        body.setOrderNote(note);//商品描述
        body.setBackNotifyUrl(BocPayConfig.notify_url);//异步通知URL
        body.setFrontNotifyUrl(BocPayConfig.return_url);//前端通知URL
        body.setCloseTime(Long.parseLong(DateUtils.dateToStr(DateUtils.addDate(new Date(), 0,1), "yyyyMMddHHmmss")));//订单关闭时间
        request.setBody(body);
        String xml = XmlUtils.converToXml(request, "UTF-8");
        String merchantNo = BocPayConfig.merchantNo;//商户编码
        try {
            //对message原文进行加签
            //获取私钥证书
            String path = ClassLoader.getSystemClassLoader().getResource(BocPayConfig.signKeyFile).getPath();
            PKCSTool tool = PKCSTool.getSigner(path,BocPayConfig.signkeyPassword,BocPayConfig.signkeyPassword,"PKCS7");
            //签名
            byte requestPlainTextByte[] = xml.getBytes("UTF-8");
            String requestSignature = tool.p7Sign(requestPlainTextByte);
            String requestMessage = Base64.encode(requestPlainTextByte);
            String requestMerchantNo = Base64.encode(merchantNo.getBytes("UTF-8"));
            String requestVersion = Base64.encode(Constants.B2B_DIRECT_VERSION.getBytes("UTF-8"));
            String requestMessageId = Base64.encode(Constants.MessageId.NB2BRecvOrder.getBytes("UTF-8"));
            String requestSecurity = Base64.encode(Constants.SECURITY.getBytes("UTF-8"));
            String action = BocPayConfig.pgwPortalUrl+"/"+"NB2BRecvOrder.do";
            String boc = "<form name=\"form1\" method=\"post\" action=\"#{action}\"><input type=\"hidden\" name=\"merchantNo\" value=\"#{merchantNo}\"><input type=\"hidden\" name=\"version\" value=\"#{version}\"><input type=\"hidden\" name=\"messageId\" value=\"#{messageId}\"><input type=\"hidden\" name=\"security\" value=\"#{security}\"><input type=\"hidden\" name=\"signature\" value=\"#{signature}\"><input type=\"hidden\" name=\"message\" value=\"#{message}\"><input type=\"submit\" value=\"立即支付\" style=\"display:none\" ></form><script>document.forms[0].submit();</script>";
            // 将参数放置到request对象
            boc = boc.replace("#{merchantNo}", requestMerchantNo).replace("#{version}", requestVersion).replace("#{messageId}", requestMessageId).replace("#{security}", requestSecurity).replace("#{signature}", requestSignature).replace("#{message}", requestMessage).replace("#{action}",action);
            return boc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

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

    /**
     * 中国银行异步通知
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/bocpay/notice")
    @ResponseBody
    public String bocNotifyNotice(HttpServletRequest request, HttpServletResponse response){
        try {
            // 获得参数message和signature
            String message = request.getParameter("message");
            String signature = request.getParameter("signature");
            //对返回数据进行签名验证
            String plainText = new String(Base64.decode(message), "UTF-8");
            //获取验签根证书，对P7验签使用二级根证书
            String path = ClassLoader.getSystemClassLoader().getResource(BocPayConfig.verifyCerFile).getPath();
            InputStream fis4cer = new FileInputStream(path);
            PKCSTool tool = PKCSTool.getVerifier(fis4cer,null);
            //验证签名,验证失败将抛出异常
            tool.p7Verify(signature, plainText.getBytes("UTF-8"));
            //获取返回数据
            Document document = XmlUtil.createDocument(plainText);
            //获取返回码,商户根据业务逻辑处理
            String responseCode = XmlUtil.getNodeText(document, "responseCode");
            if("OK".equals(responseCode)){//获取返回数据
                String tranStatus = XmlUtil.getNodeText(document, "tranStatus");//交易状态 1成功 2 失败
                if(!tranStatus.equals("1")){
                    return "error";
                }
                String orderNo = XmlUtil.getNodeText(document, "orderNo");// 订单号
                String payCatalog = XmlUtil.getNodeText(document, "payCatalog");//支付方式 1 中行网银 2 跨行
                String transSeq = XmlUtil.getNodeText(document, "transSeq");//银行交易流水号
                OrderReq req = new OrderReq();
                req.setOrderNo(orderNo);
                req.setStatus(OrderStatusEnum.END);
                req.setSerialNumber(transSeq);
                Result result = orderService.updateOrder(req, "admin");
                if (!result.isSuccess()) {
                    log.error("********************** 支付后修改订单异常 **********************");
                }
                //request.setAttribute("result",responseCode+",Notice Success!");
                //request.getRequestDispatcher("/jsp/ResultDisplay.jsp").forward(request, response);
            }
            log.info("---------- End ReceiveNB2BRecvOrderNotice ----------");
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        return "OK,Notice Success!";
    }

}

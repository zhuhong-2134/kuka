package com.camelot.kuka.common.utils.pay.payment.common.util;

public final class Constants {

	public final static String B2B_DIRECT_VERSION = "1.0.1";
	public final static String SECURITY= "P7";
	
	public static final class MessageId{
		/**发送B2B保付订单实付请求(跨行)**/
		public final static String NB2BRealPayOrder = "0000116";
		/**查询付款银行信息**/
		public final static String NB2BQueryBankInfo = "0000117";
		/**B2B发送保付支付订单请求**/
		public final static String NB2BRecvOrder2 = "0000115";
		/**B2B发送直付支付订单请求**/
		public final static String NB2BRecvOrder = "0000110";
		/**B2B发送直付指定账号支付订单请求**/
		public final static String NB2BRecvAccountOrder = "0000118";
		/**B2B订单查询**/
		public final static String NB2BQueryOrder = "0000111";
		/**B2B商户交易查询**/
		public final static String NB2BQueryTrans = "0000112";
		/**B2B商户联机退款**/
		public final static String NB2BRefundOrder = "0000113";
		/**商户上传下载文件前取票**/
		public final static String NGetTicket = "0000310";
	}
	
}

package com.py.controller.phone;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.google.common.collect.Maps;
import com.py.utils.OrderUtil;
import com.py.utils.ali.AliPayUtil;

/**
 * 
 * @content 支付宝支付
 *
 * @author cl
 *
 * 2019年1月12日
 */
@Controller
@RequestMapping(value = "/api/alipay")
public class MAliPayController {
	
	public static final Logger logger = LoggerFactory.getLogger(MAliPayController.class);

	
	/**
	 * 支付宝APP支付 统一下单
	 * @param request
	 * @return
	 */
	@RequestMapping(value="createOrder",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createOrder(HttpServletRequest request){
		Map<String, Object> resultMap = Maps.newHashMap();
		
		//实例化客户端  
        AlipayClient client = new DefaultAlipayClient(AliPayUtil.URL, AliPayUtil.APPID, AliPayUtil.RSA_PRIVATE_KEY, AliPayUtil.FORMAT, AliPayUtil.CHARSET, AliPayUtil.ALIPAY_PUBLIC_KEY,AliPayUtil.SIGNTYPE);
        
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay 
        AlipayTradeAppPayRequest ali_request = new AlipayTradeAppPayRequest();
        
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。  
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();  
        
        //商品名称
        model.setSubject("支付宝充值");
        
        //商户订单号
        model.setOutTradeNo(OrderUtil.createOrder());
        
        //交易超时时间 	该笔订单允许的最晚付款时间，逾期将关闭交易。
        //取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。
        //该参数数值不接受小数点 如 1.5h，可转换为 90m。
        //若为空，则默认为15d。
        model.setTimeoutExpress("2h");
        
        //订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]
        model.setTotalAmount("0.01");
        
        //销售产品码，商家和支付宝签约的产品码，为固定值
        model.setProductCode("QUICK_MSECURITY_PAY");
        
        ali_request.setBizModel(model);
        
        //回调地址
        ali_request.setNotifyUrl(AliPayUtil.NOTIFY_URL);
        
        try {
        	 AlipayTradeAppPayResponse response = client.sdkExecute(ali_request);  
             String orderStr = response.getBody();
             resultMap.put("code","1");
             resultMap.put("msg","统一下单成功");
             resultMap.put("orderStr",orderStr);
		} catch (AlipayApiException e) {
			 resultMap.put("code","3");
             resultMap.put("msg","统一下单失败");
		}
		return resultMap;
	}
	
	
	/**
	 * 支付宝异步通知接口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/notify")
	@ResponseBody
	public String notify(HttpServletRequest request,HttpServletResponse response){
		logger.info("支付宝异步通知开始");
		Map<String, String> params = new HashMap<String, String>();
		//从支付宝回调的request域中取值
	    Map<String, String[]> requestParams = request.getParameterMap();
	    for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {  
	         String name = iter.next();  
	         String[] values = requestParams.get(name);  
	         String valueStr = "";  
	         for (int i = 0; i < values.length; i++) {  
	             valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";  
	         }  
	         // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化  
	         //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");  
	         params.put(name, valueStr);  
	     }      
	     //签名验证
	     boolean signVerified = false;  
	     try {
	         signVerified = AlipaySignature.rsaCheckV1(params, AliPayUtil.ALIPAY_PUBLIC_KEY, AliPayUtil.CHARSET, AliPayUtil.SIGNTYPE);
	     } catch (AlipayApiException e) {}
	     if(signVerified) {
	    	 logger.info("支付宝验签成功");
		     //商户订单号 
		     String out_trade_no = request.getParameter("out_trade_no");       
		 	 //交易状态 
		     String tradeStatus = request.getParameter("trade_status");   
	    	 if(tradeStatus.equals("TRADE_SUCCESS") || tradeStatus.equals("TRADE_FINISHED")) {
	    		 logger.info("支付宝支付成功，订单号："+out_trade_no);
	    		 return "success";
	    	 }else {
	    		 String str = ""; 
	    		 switch (tradeStatus) {
	    		 case "WAIT_BUYER_PAY":
					str = "交易创建，等待买家付款";
					break;
	    		 case "TRADE_CLOSED":
					str = "未付款交易超时关闭，或支付完成后全额退款";
					break;	
	    		 }
	    		 logger.error("支付宝支付失败，订单号："+out_trade_no+"，错误结果："+str);
	    		 return "failure";
	    	 }
	     }else {
	    	 logger.error("支付宝验签失败");
	    	 return "failure";
	     }
	}
	
	
	
	
}

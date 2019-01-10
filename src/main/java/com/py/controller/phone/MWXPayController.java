package com.py.controller.phone;

import java.math.BigDecimal;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.py.utils.HttpClientUtil;
import com.py.utils.OrderUtil;
import com.py.utils.Utils;
import com.py.utils.wxpay.WxPayConfig;
import com.py.utils.wxpay.WxPayUtil;

@Controller
@RequestMapping(value = "/api/wechatpay")
public class MWXPayController {
	
	/**
	 * 微信APP支付  统一下单
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="createOrder",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createOrder(HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = Maps.newHashMap();
		
		/****************组装请求参数，请求微信支付统一下单接口*****************/
		SortedMap<Object,Object> parameters_server = new TreeMap<Object,Object>();
		
		//应用ID
		parameters_server.put("appid", WxPayConfig.APP_ID);
        
        //商户号
		parameters_server.put("mch_id", WxPayConfig.MCH_ID);
        
        //随机字符串，不长于32位 
		parameters_server.put("nonce_str", WxPayUtil.CreateRandomStr(32));
        
        //商品描述  需传入应用市场上的APP名字-实际商品名称
		parameters_server.put("body", "微信充值0.01元");
        
        //订单号 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*且在同一个商户号下唯一
		parameters_server.put("out_trade_no", OrderUtil.createOrder());
        
        //订单总金额，单位为分
		BigDecimal bd= new BigDecimal(String.valueOf(0.01));
		int val = bd.movePointRight(2).intValue();
		parameters_server.put("total_fee", val+"");
        
        //终端IP 支持IPV4和IPV6两种格式的IP地址
		parameters_server.put("spbill_create_ip",Utils.getIpAddress(request));
        
        //异步通知回调地址
		parameters_server.put("notify_url", WxPayConfig.NOTIFY_URL);
        
        //交易类型
		parameters_server.put("trade_type", "APP");
        
        //设置签名
        String sign_server = WxPayUtil.createSign(parameters_server);
        parameters_server.put("sign", sign_server);
        
        //封装请求参数
        String requestXML = WxPayUtil.getRequestXml(parameters_server);
        
        /****************调用统一下单接口*****************/
        String strxml = HttpClientUtil.doPostXml(WxPayConfig.UNIFIED_ORDER_URL, requestXML);
        System.out.println(strxml);
        
        /****************解析微信返回的XML*****************/
        @SuppressWarnings("unchecked")
		Map<String, String> map = WxPayUtil.doXMLParse(strxml);
        //返回异常信息
        if(map.get("return_code").equals("FAIL")) {
       	   resultMap.put("code", 3);
  	   	   resultMap.put("msg", map.get("return_msg"));
  	       return resultMap;
        }
        if(map.get("result_code").equals("FAIL")) {
      	   resultMap.put("code", 3);
 	   	   resultMap.put("msg", map.get("err_code_des"));
 	       return resultMap;
        }
        
        /****************已经拿到prepay_id，组装APP端调起支付的参数*****************/
        SortedMap<Object, Object> parameters_app = new TreeMap<Object, Object>();
        
        //应用ID
        parameters_app.put("appid", WxPayConfig.APP_ID);
        
        //商户号
        parameters_app.put("partnerid", WxPayConfig.MCH_ID);
        
        //预支付交易会话ID
        parameters_app.put("prepayid", map.get("prepay_id"));
        
        //扩展字段 暂填写固定值Sign=WXPay
        parameters_app.put("package", "Sign=WXPay");
        
        //随机字符串，不长于32位 
        parameters_app.put("noncestr", WxPayUtil.CreateRandomStr(32));
        
        //时间戳  注意：部分系统取到的值为毫秒级，需要转换成秒(10位数字)
        long second = System.currentTimeMillis()/1000;
        String timeStamp = String.format("%010d", second);
        parameters_app.put("timestamp", timeStamp);
        
        //设置签名
        String sign_app = WxPayUtil.createSign(parameters_app);
        parameters_app.put("sign", sign_app);
        
        resultMap.put("map",parameters_app);
		resultMap.put("code","1");
        resultMap.put("msg","统一下单成功");
	    return resultMap;
	}
	
	
	
	
	
	
	
	
}

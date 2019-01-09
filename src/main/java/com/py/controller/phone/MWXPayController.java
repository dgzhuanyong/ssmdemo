package com.py.controller.phone;

import java.math.BigDecimal;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
	public Map<String, Object> createOrder(HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = Maps.newHashMap();
		
		/***************************请求参数******************************/
		SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
		
		//应用ID
        parameters.put("appid", WxPayConfig.APP_ID);
        
        //商户号
        parameters.put("mch_id", WxPayConfig.MCH_ID);
        
        //随机字符串，不长于32位 
        parameters.put("nonce_str", WxPayUtil.CreateRandomStr(32));
        
        //商品描述  需传入应用市场上的APP名字-实际商品名称
        parameters.put("body", "腾讯充值中心-QQ会员充值");
        
        //订单号
        parameters.put("out_trade_no", OrderUtil.createOrder());
        
        //总金额 分
		BigDecimal bd= new BigDecimal(String.valueOf(0.01));
		int val = bd.movePointRight(2).intValue();
        parameters.put("total_fee", val+"");
        
        //用户端实际ip
        parameters.put("spbill_create_ip",Utils.getIpAddress(request));
        
        //异步通知回调地址
        parameters.put("notify_url", WxPayConfig.NOTIFY_URL);
        
        //交易类型
        parameters.put("trade_type", "APP");
        
        //设置签名
        String sign = WxPayUtil.createSign(parameters);
        parameters.put("sign", sign);
        
        //封装请求参数
        String requestXML = WxPayUtil.getRequestXml(parameters);
        
        //调用统一下单接口
        String result = HttpClientUtil.doPostXml(WxPayConfig.UNIFIED_ORDER_URL, requestXML);
        System.out.println("微信服务器返回的XML数据");
        System.out.println(result);

		resultMap.put("code","1");
        resultMap.put("msg","统一下单成功");
	    return resultMap;
	}
}

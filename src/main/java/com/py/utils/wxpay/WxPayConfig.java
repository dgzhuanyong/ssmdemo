package com.py.utils.wxpay;

public class WxPayConfig {
	
	//AppID
	public final static String APP_ID = "wx76be99258cfaf85a";
	
	//AppSecret
    public final static String APP_SECRET = "c713d641d87847e811c996a000ca5a2e";
    
	//商户号  微信商户平台(pay.weixin.qq.com)
    public final static String MCH_ID = "1517376531";
    
    //商户密钥  微信商户平台-->账户设置-->API安全-->密钥设置
    public final static String API_KEY = "Da4s6d4as5das5d4a5sd4as5d4as5ds4";
    
    //加密方式
    public final static String SIGN_TYPE = "MD5";
    
    //统一下单接口地址
    public final static String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    
    //异步通知接口地址
    public final static String NOTIFY_URL = "http://1f9w438490.imwork.net/api/wechatpay/notify";
    
}

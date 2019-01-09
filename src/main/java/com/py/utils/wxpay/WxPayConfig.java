package com.py.utils.wxpay;

public class WxPayConfig {
	
	//AppID
	public final static String APP_ID = "wx7d4bb73b5d2f4330";
	
	//AppSecret
    public final static String APP_SECRET = "2e52acdd6b8c127a969bad4b8e00f75c";
    
	//商户号  微信商户平台(pay.weixin.qq.com)
    public final static String MCH_ID = "1520772811";
    
    //商户密钥  微信商户平台-->账户设置-->API安全-->密钥设置
    public final static String API_KEY = "5ETz4bHeoCOH5FpM9PnHKnIxmTx71r7G";
    
    //加密方式
    public final static String SIGN_TYPE = "MD5";
    
    //统一下单接口地址
    public final static String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    
    //异步通知接口地址
    public final static String NOTIFY_URL = "http://1f9w438490.imwork.net/api/wechatpay/notify";
    
}

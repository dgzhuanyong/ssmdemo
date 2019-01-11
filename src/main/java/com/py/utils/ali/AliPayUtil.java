package com.py.utils.ali;

public class AliPayUtil {
	
    //商户appid
    public static String APPID = "";    
    
    //私钥 pkcs8格式的
    public static String RSA_PRIVATE_KEY ="";
    
    //支付宝公钥
    public static String ALIPAY_PUBLIC_KEY = "";
    
    //(转账)网关地址
  	public static String GATEWAY_URL = "https://openapi.alipay.com/gateway.do";
    
    //服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String NOTIFY_URL = "http://15386nv005.iok.la/api/alipay/notify";
    
    //页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
    //public static String RETURN_URL = "http://15386nv005.iok.la/api/alipay/return_url";
    
    //请求网关地址
    public static String URL = "https://openapi.alipay.com/gateway.do";
    
    //沙箱支付网关
    public static String SANDBOX_URL = "https://openapi.alipaydev.com/gateway.do";
    
    //编码
    public static String CHARSET = "UTF-8";
    
    //返回格式
    public static String FORMAT = "json";
    
    //加密类型
    public static String SIGNTYPE = "RSA2";
    
    
    
}

package com.py.utils.ali;

public class AliPayUtil {
	
    //商户appid
    public static String APPID = "2018113062393492";    
    
    //私钥 pkcs8格式的
    public static String RSA_PRIVATE_KEY ="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCOMxTLBZrcwhp1SFTBWgdAs2SFybPj+2cW9xWbSMI4/s68tAtV+EDqcTaxmzg3MFCHFBH6MDB92t6WAw+ZQHokD+e6YmebA6psYCoCUY3GnSFc2WfybcY2hk/OvJK3HIW0QEIfHoJgC5vNfOccbnDsuoYCZCZqCn0h8k/khXzzWyvrLXZVQW+MmMMfDHVAzP3/82dHgV6Mpqg0EFC1L57d6hUayVGGTOmAZJ+IwWu9+X9KmQI24MCWJsEXnCsi6OHj0EVOgbRHE+olcof6mlIgfnrfL8RPSluTuub8aj9XpkJRodRD47kq4AXvvlVg0ScfdSLKgPgY76gpgt5XdXApAgMBAAECggEACn7AqR9Xx7VXyJI3FzIy2Naks68oZMo4WJyR7q0XEKfV0F2lfInfGK6Ub9VZ99DMELwte361BYTy6rV9ID1/BkxaOQpsRSenG2qZjyQ/x4nvXW/6WQ07FOki2MpYNcWYJK6dcriIvNf+ftMaJ6ILGZ3GqESGBUuS4r+EoAQuLbPPScCgjo4xRqbe2EQigYKbNgWSzKjsTKpS3L6b3nnZanFig8nK3rON5PeldVtUSI1mIgAtFQje+WdGT//7JX6wK0Pp3/cfW7CydqJ4nxaaBtp5MN8byGI/iSzOtv3AXFMmST3xGRZ5+TNFtC2rrCoqBKnquzjJylpqqZ6i2rh2kQKBgQDl1Hy/qRs22SGHHZPzNhGgFICFocExaDNS9wHqAHBBnfJvAm2hiAQjENspkFs8VJpxsv5O8xib580ECfOaMJJ08+TsFeN1ebKU1McGthRbcaYzDtWIa3U7ZSjzFpzC9dl3ZtnrD3dmAozCeV7SaGf9cRNVy2+TNcnL3+e/Rvld1QKBgQCeZC1xOYa2nmTKQwvdQElT1KY6fy7K4btIlfIo1PDoeyYDf8+cs8u74D1HV8grSimCYJk2xe2CFG4uxoLgJpVsrM47bjtPiV5BZcpmWhrjtizMzAy3cdRjliB8j8Y3ao7/FK+MG4SPuKPEoR3x6R++4wvUPWLzkQy4WWoxdqqvBQKBgQDNLFZ5m3PKESjXQFZ2BszRRbw+j9qySr2W0SAKUH3mdiSZaWeJjq1yyHd+sXphb325Bvv0axyLAfKKKoAoJDh648h34kp+8zezmghbO20M/XZLmnCSKqwra/GUoZksiuGqsEzWhaYnkRsr+cHmwIIlRS7npodiud/+e1Kd9bRkSQKBgFFkzW1X0IBb2ROPbLNf686mgl2hKdycL2/Xjj+pXbs6PYiiz3+JbXK4gwT8eSK106rqFRHumGS2NwDWhj7F1Repnbkwpk3zG3qj4mTCfPrzjQYY/ZfvaONcLImWIJgtrBos+SEYqwBoM9BBs2+zCts/Nb71ZLohmdXKLSYKAyA5AoGAeCxjE/Mnjskmb55NzyLNPJvTYLjw1UbuPazqZ3ou7yueFtrJVnMNamEQo6zEf5czPqNojhN5cJV3advrKFV5yxw+zI+Gkf5My4r9xmkJb5ZfDhhH2e/sO/fz+OEKyPfTX163HToT3hzxn2yja0D5eUJO8jrCHvdn4J3sDz3L3GU=";
    
    //支付宝公钥
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAl+Qy2OvS2IFDmiiENxSQNuiYFBs4EnZjjbWR3Ip3MeAi9j7lbr7zhVvem6zybcAfU5Cf3lVixODQTxeHxnEIlJkMOF2kf15dmZCEVNXLjKjK8exngQzsX0lEvY2H5gZvbsKeETV8TzSRW8Q8NeSGEfcXiWpPKPzgu/gwFMzsuKU1qqUjjIIYDs9lsEqkNMBxWlyhzs88JRw/OZ6I86nT6s1D2B7YUhAhMyq9+sIpS6Kk2p0SVfxMUAObMceBWJ+e5mrOj55+A9NDzwrAJNMULy2XJ79O076ZSsf3iB7kwgrNJbGUJmqGYj0lupdBeeHs3eFDMYvV26+5yn6B+Ov/QQIDAQAB";
    
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

package com.py.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utils {
	
	private static final Logger logger = LoggerFactory.getLogger(Utils.class);
	static Properties prop = null;
	
	private Utils() {}
	
	static{
		prop = new Properties();
		InputStream in = Utils.class.getResourceAsStream("/paramConfig.properties");
		try {
			prop.load(in);
		} catch (IOException e) {
			logger.error("工具类：Utils 读取属性文件时出现异常");
			logger.error(e.getMessage());
		}
	}
	
	/**
	 * 空判断
	 * @param values
	 * @return
	 */
	public static boolean isNull(List<String> values){
		for (String str : values) {
			if(StringUtils.isBlank(str)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 空判断
	 * @param values
	 * @return
	 */
	public static boolean isNull(String[] values){
		for (String str : values) {
			if(StringUtils.isBlank(str)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 空判断
	 * @param values
	 * @return
	 */
	public static boolean isNull(String str){
		if(StringUtils.isBlank(str)) {
			return true;
		}
		return false;
	}
	
	
	
	/**
	 * 根据参数名称返回/paramConfig.properties中配置的参数值
	 * @param 参数名称
	 * @return 参数值
	 */
	public static String getProperties(String key) {
		return prop.getProperty(key).trim();
	}
	
	
	/**
	 * 获取客户端IP地址
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	public static String getIpAddress(final HttpServletRequest request) throws Exception{
		if (request == null) {
			throw (new Exception("getIpAddr method HttpServletRequest Object is null"));
		}
		String ipString = request.getHeader("x-forwarded-for");
		if (StringUtils.isBlank(ipString) || "unknown".equalsIgnoreCase(ipString)) {
			ipString = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isBlank(ipString) || "unknown".equalsIgnoreCase(ipString)) {
			ipString = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isBlank(ipString) || "unknown".equalsIgnoreCase(ipString)) {
			ipString = request.getRemoteAddr();
		}
		// 多个路由时，取第一个非unknown的ip
		final String[] arr = ipString.split(",");
		for (final String str : arr) {
			if (!"unknown".equalsIgnoreCase(str)) {
				ipString = str;
				break;
			}
		}
		return ipString;
	}
	
	
	
	/**
	 * 去除HTML标签
	 * @param htmlStr
	 * @return
	 */
	public static String removeHTml(String htmlStr) {
		String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // script
		String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // style
		String regEx_html = "<[^>]+>"; // HTML tag
		String regEx_space = "\\s+|\t|\r|\n";// other characters
		Pattern p_script = Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE);
		Matcher m_script = p_script.matcher(htmlStr);
		htmlStr = m_script.replaceAll("");
		Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
		Matcher m_style = p_style.matcher(htmlStr);
		htmlStr = m_style.replaceAll("");
		Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		Matcher m_html = p_html.matcher(htmlStr);
		htmlStr = m_html.replaceAll("");
		Pattern p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);
		Matcher m_space = p_space.matcher(htmlStr);
		htmlStr = m_space.replaceAll(" ");
		return htmlStr;
	}
	
	
	/**
	 * 以二进制的方式从request中读取数据
	 * @param request
	 * @return
	 */
    public static String readRequestData(HttpServletRequest request){
    	StringBuilder sb = new StringBuilder();
        InputStream is = null;
        try{
            is = request.getInputStream();
            byte[] b = new byte[4096];
            for (int n; (n = is.read(b)) != -1;){
                sb.append(new String(b, 0, n,"UTF-8"));
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        finally{
            if (null != is){
                try{
                    is.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
	
	
	
}

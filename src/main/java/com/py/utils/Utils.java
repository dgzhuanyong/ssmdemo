package com.py.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 工具类
 */
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
	
	
	
	
}

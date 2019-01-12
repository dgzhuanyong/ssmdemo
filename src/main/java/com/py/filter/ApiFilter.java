package com.py.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.py.service.TokenService;
import com.py.utils.Utils;


public class ApiFilter implements Filter {
	
	private Logger logger = LoggerFactory.getLogger(ApiFilter.class);
	
	private TokenService tokenService;
	

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		response.setContentType("text/html;charset=UTF-8");
		HttpServletRequest req = (HttpServletRequest)request;
		String uri = req.getRequestURI();// 获取用户请求的地址
		if (StringUtils.isNotBlank(uri)) {
			// 如果请求登陆地址，直接放行
			if (checkPass(uri)){
				chain.doFilter(request, response);// 放行。让其走到下个链或目标资源中
			} else {
				String token = request.getParameter("TOKEN");
				if (StringUtils.isNotBlank(token)) {
					boolean valid = tokenService.valid(token);// 验证用户传入的token是否有效
					// 如果token有效，将token信息放入session中，并放行
					if (valid) {
						chain.doFilter(request, response);// 放行。让其走到下个链或目标资源中
					} else {
						PrintWriter pw = response.getWriter();
						pw.write("{\"code\":0,\"msg\":\"token无效\"}");
						pw.flush();
						pw.close();
						logger.warn("=================传入了无效的token=====================");
					}
				} else {
					logger.error("=================token为空=====================");
					PrintWriter pw = response.getWriter();
					pw.write("{\"code\":0,\"msg\":\"token无效\"}");
					pw.flush();
					pw.close();
				}
			}
		}
	}
	
	/**
	 * 检查uri是否是直接放行请求
	 * @param uri
	 * @return
	 */
	private boolean checkPass(String uri){
		String[] mobilePassUri = Utils.getProperties("api_pass_uri").split(",");
		for(String u : mobilePassUri){
			if(uri.indexOf(u) > 0){
				return true;
			}
		}
		return false;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		ServletContext sc = filterConfig.getServletContext();
		XmlWebApplicationContext cxt = (XmlWebApplicationContext) WebApplicationContextUtils
				.getWebApplicationContext(sc);
		if (cxt != null && cxt.getBean("tokenService") != null && tokenService == null)
			tokenService = (TokenService) cxt.getBean("tokenService");
		logger.info("手机端请求过滤器----配置初始化");
	}
	
	@Override
	public void destroy() {
		logger.info("API请求过滤器----已销毁");
	}

}

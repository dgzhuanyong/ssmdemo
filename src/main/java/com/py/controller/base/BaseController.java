package com.py.controller.base;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.py.entity.SysUser;
import com.py.service.ShiroDbRealm.ShiroUser;
import com.py.service.SysUserService;

/**
 * 
 * @content base
 *
 * @author cl
 *
 * 2019年1月11日
 */
public class BaseController {
	
	@Autowired
	private SysUserService sysUserService;
	
	/**
	 * 获取当前用户
	 * @return
	 */
	public SysUser getCurrentUser(){
		ShiroUser shirouser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		SysUser sysUser = sysUserService.selectByLoginName(shirouser.getLoginName());
		return sysUser;
	}
	
	/**
	 * 当前页 
	 */
	protected int pageNum = 1;
	/**
	 * 每页多少条记录
	 */
	protected int pageSize = 10;
	
	/**
	 * 排序列
	 */
	protected String orderTable="id";
	
	/**
	 * 排序方式
	 */
	protected String orderStyle="DESC";
	
	
	public int getPageNum() {
		return pageNum;
	}


	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}


	public int getPageSize() {
		return pageSize;
	}


	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}


	public String getOrderTable() {
		return orderTable;
	}


	public void setOrderTable(String orderTable) {
		this.orderTable = orderTable;
	}


	public String getOrderStyle() {
		return orderStyle;
	}


	public void setOrderStyle(String orderStyle) {
		this.orderStyle = orderStyle;
	}


	/**
	 * 接受前台传入的分页参数(LayerUI)
	 * @param request
	 */
	protected void LayerPage(HttpServletRequest request) {
		String pageStr =  request.getParameter("page");
		String pagesizeStr =  request.getParameter("limit");
		String pageTable =  request.getParameter("field");
		String pageStyle =  request.getParameter("order");
		if(StringUtils.isNotBlank(pageStr)) {
			setPageNum(Integer.parseInt(pageStr));
		}
		if(StringUtils.isNotBlank(pagesizeStr)) {
			setPageSize(Integer.parseInt(pagesizeStr));
		}
		if(StringUtils.isNotBlank(pageTable)) {
			setOrderTable(pageTable);
		}
		if(StringUtils.isNotBlank(pageStyle)) {
			setOrderStyle(pageStyle);
		}
	}
	
	
	/**
	 * 接受前台传入的分页参数
	 * @param request
	 */
	protected void pageParameter(HttpServletRequest request) {
		//获取page和pagesize的值
		String pageStr =  request.getParameter("pageNum");
		String pagesizeStr =  request.getParameter("pageSize");
		if(StringUtils.isNotBlank(pageStr)) {
			setPageNum(Integer.parseInt(pageStr));
		}
		if(StringUtils.isNotBlank(pagesizeStr)) {
			setPageSize(Integer.parseInt(pagesizeStr));
		}
	}
	
	/**
	 * 接受前台传入的分页参数进行设置
	 * 设置每页数据条数 num
	 * @param request
	 */
	protected void pageParameter(HttpServletRequest request,int num) {
		//获取page和pagesize的值
		String pageStr =  request.getParameter("pageNum");
		String pagesizeStr =  request.getParameter("pageSize");
		if(StringUtils.isNotBlank(pageStr)) {
			setPageNum(Integer.parseInt(pageStr));
		}
		if(StringUtils.isNotBlank(pagesizeStr)) {
			setPageSize(Integer.parseInt(pagesizeStr));
		}else {
			setPageSize(num);
		}
	}
	
	
}

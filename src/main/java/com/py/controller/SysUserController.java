package com.py.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Maps;
import com.py.controller.base.BaseController;
import com.py.service.SysUserService;

@Controller
@RequestMapping(value = "/sys_god")
public class SysUserController extends BaseController {
	
	@Autowired
	private SysUserService sysUserService;
	
	
	/***************************************************list*********************************************************/
	
	/**
	 * 跳转列表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toList")
	public String toList(HttpServletRequest request,Model model) {
		return "jsp/god/godList";
	}
	
	/**
	 * 获取列表数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "toListData")
	@ResponseBody
	public Map<String,Object> toListData(HttpServletRequest request) {
		//返回map
		Map<String,Object> resultMap = Maps.newHashMap();
		//条件map
		Map<String,Object> searchMap = Maps.newHashMap();
		//获取分页和排序条件
		LayerPage(request);
		//获取搜索条件
		String conditionName = request.getParameter("conditionName");
		searchMap.put("conditionName", conditionName);
		//排序插件
		PageHelper.orderBy(orderTable+" "+orderStyle);
		//分页插件
		Page<?> page = PageHelper.startPage(pageNum, pageSize);
		//调用service
		//List<God> list = godService.selectConditionList(searchMap);
		//返回layui数据
		resultMap.put("code", 0);
		resultMap.put("msg", "查询成功");
		resultMap.put("count", page.getTotal());
		//resultMap.put("data", list);
		return resultMap;
	}
}

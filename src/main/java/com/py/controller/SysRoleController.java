package com.py.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Maps;
import com.py.controller.base.BaseController;
import com.py.entity.SysPrivilege;
import com.py.entity.SysRole;
import com.py.entity.SysRolePrivilege;
import com.py.service.SysRolePrivilegeService;
import com.py.service.SysRoleService;
import com.py.utils.Utils;

@Controller
@RequestMapping(value = "/sys_role")
public class SysRoleController extends BaseController {
	
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysRolePrivilegeService sysRolePrivilegeService;
	
	
	/***************************************************list*********************************************************/
	
	/**
	 * 跳转列表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toList")
	public String toList(HttpServletRequest request,Model model) {
		return "jsp/sysrole/roleList";
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
		List<SysRole> list = sysRoleService.selectConditionList(searchMap);
		//返回layui数据
		resultMap.put("code", 0);
		resultMap.put("msg", "");
		resultMap.put("count", page.getTotal());
		resultMap.put("data", list);
		return resultMap;
	}
	
	
	/***************************************************form*********************************************************/
	
	/**
	 * 跳转form
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toForm")
	public String getUserList(HttpServletRequest request,Model model) {
		int id = 0;
		try {
			id= Integer.parseInt(request.getParameter("id"));
		} catch (Exception e) {
			id = 0;
		}
		SysRole sysRole = sysRoleService.selectByPrimaryKey(id);
		model.addAttribute("obj", sysRole);
		//查询角色的权限
		StringBuffer sb = new StringBuffer();
		List<SysRolePrivilege> list = sysRolePrivilegeService.selectByRoleId(id);
		for (int i = 0; i < list.size(); i++) {
			SysRolePrivilege sysRolePrivilege = list.get(i);
			if(i == list.size()-1) {
				sb.append(sysRolePrivilege.getPrivilegeId());
			}else {
				sb.append(sysRolePrivilege.getPrivilegeId()+",");
			}
		}
		model.addAttribute("ids", sb.toString());
		return "jsp/sysrole/roleForm";
	}
	
	
	/**
	 * 新增
	 * @param request
	 * @param commodityClassification
	 * @return
	 */
	@RequestMapping(value="insert")
	@ResponseBody
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> insert(HttpServletRequest request,@ModelAttribute("obj") SysRole sysRole) {
		//返回map
		Map<String, Object> resultMap = Maps.newHashMap();
		sysRoleService.insert(sysRole);
		//插入中间表
		String privilege=request.getParameter("privilege");
		String[] privilegeArray = privilege.split(",");
		//遍历选择权限 存入
		for (int i = 0; i < privilegeArray.length; i++) {
			int privilegeId = 0;
			try {
				privilegeId = Integer.parseInt(privilegeArray[i]);
			} catch (Exception e) {
				privilegeId = 0;
			}
			SysRolePrivilege sysRolePrivilege = new SysRolePrivilege();
			sysRolePrivilege.setRoleId(sysRole.getId());
			sysRolePrivilege.setPrivilegeId(privilegeId);
			sysRolePrivilegeService.insert(sysRolePrivilege);
		}
		resultMap.put("result", "success");
		return resultMap;
	}
	
	/**
	 * 修改
	 * @param request
	 * @param commodityClassification
	 * @return
	 */
	@RequestMapping(value="update")
	@ResponseBody
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> update(HttpServletRequest request,@ModelAttribute("obj") SysRole sysRole) {
		//返回map
		Map<String, Object> resultMap = Maps.newHashMap();
		sysRoleService.update(sysRole);
		//删除中间表
		sysRolePrivilegeService.deleteByRoleId(sysRole.getId());
		//插入中间表
		String privilege=request.getParameter("privilege");
		String[] privilegeArray = privilege.split(",");
		//遍历选择权限 存入
		for (int i = 0; i < privilegeArray.length; i++) {
			int privilegeId = 0;
			try {
				privilegeId = Integer.parseInt(privilegeArray[i]);
			} catch (Exception e) {
				privilegeId = 0;
			}
			SysRolePrivilege sysRolePrivilege = new SysRolePrivilege();
			sysRolePrivilege.setRoleId(sysRole.getId());
			sysRolePrivilege.setPrivilegeId(privilegeId);
			sysRolePrivilegeService.insert(sysRolePrivilege);
		}
		resultMap.put("result", "success");
		return resultMap;
	}
	
	/***************************************************delete*********************************************************/
	
	/**
	 * 删除
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> delete(HttpServletRequest request,Model model) {
		//返回map
		Map<String, Object> resultMap = Maps.newHashMap();
		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (Exception e) {
			id = 0;
		}
		sysRoleService.delelte(id);
		//删除中间表
		sysRolePrivilegeService.deleteByRoleId(id);
		resultMap.put("result", "success");
		return resultMap;
	}
	
	/**
	 * 批量删除 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "batchDelete")
	@ResponseBody
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> batchDelete(HttpServletRequest request,Model model,@RequestParam("ids[]") int[] ids) {
		//返回map
		Map<String, Object> resultMap = Maps.newHashMap();
		for (int id : ids) {
			sysRoleService.delelte(id);
			//删除中间表
			sysRolePrivilegeService.deleteByRoleId(id);
		}
		resultMap.put("result", "success");
		return resultMap;
	}
	
	
	/***************************************************check*********************************************************/
	
	/**
	 *  检测重复
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "checkRepeat")
	@ResponseBody
	public boolean checkRepeat(HttpServletRequest request){
		Map<String, Object> searchMap = Maps.newHashMap();
		String oldName = request.getParameter("oldName");		
		String newName = request.getParameter("newName");
		if(Utils.isNull(oldName)) {
			searchMap.put("name", newName);
			long count = sysRoleService.checkRepeat(searchMap);
			if(count == 0) {
				return true;
			}else {
				return false;
			}
		}else {
			if(oldName.equals(newName)) {
				return true;
			}else {
				searchMap.put("name", newName);
				long count = sysRoleService.checkRepeat(searchMap);
				if(count == 0) {
					return true;
				}else {
					return false;
				}
			}
		}
	}
	
	
	/************************************权限树数据*************************************************/
	
	
	
	/**
	 * 权限树数据
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "tree")
	@ResponseBody
	public List<Map<String, Object>> tree(HttpServletRequest req,Model model) {
		List<Map<String, Object>> returnlist = new ArrayList<Map<String, Object>>();
		List<SysPrivilege> list=sysRoleService.selectPrivilegeAll();
		for (SysPrivilege sysPrivilege : list) {
			Map<String, Object> map=Maps.newHashMap();
			map.put("id", sysPrivilege.getId() == null ? "":sysPrivilege.getId());
			map.put("name", sysPrivilege.getName() == null ? "":sysPrivilege.getName());
			map.put("pId", sysPrivilege.getpId() == null ? "": sysPrivilege.getpId());
			returnlist.add(map);
		}
		return returnlist;
	}
	
	
}

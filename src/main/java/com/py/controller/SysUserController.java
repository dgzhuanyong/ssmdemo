package com.py.controller;

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
import com.py.entity.SysRole;
import com.py.entity.SysUser;
import com.py.entity.SysUserRole;
import com.py.service.SysRoleService;
import com.py.service.SysUserRoleService;
import com.py.service.SysUserService;
import com.py.special.ShiroUtils;
import com.py.utils.Utils;
/**
 * 
 * @content 系统角色
 *
 * @author cl
 *
 * 2019年1月12日
 */
@Controller
@RequestMapping(value = "/sys_user")
public class SysUserController extends BaseController {
	
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysUserRoleService sysUserRoleService;
	
	
	/***************************************************list*********************************************************/
	
	/**
	 * 跳转列表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toList")
	public String toList(HttpServletRequest request,Model model) {
		return "jsp/sysuser/userList";
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
		List<SysUser> list = sysUserService.selectConditionList(searchMap);
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
		//获取ID
		int id = 0;
		try {
			id= Integer.parseInt(request.getParameter("id"));
		} catch (Exception e) {
			id = 0;
		}
		//查询对象
		SysUser sysUser = sysUserService.selectByPrimaryKey(id);
		model.addAttribute("obj", sysUser);
		//查询所有角色
		List<SysRole> list = sysRoleService.selectConditionList(null);
		model.addAttribute("roleList", list);
		SysUserRole sysUserRole = sysUserRoleService.selectByUserId(id);
		model.addAttribute("sysUserRole", sysUserRole);
		return "jsp/sysuser/userForm";
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
	public Map<String, Object> insert(HttpServletRequest request,@ModelAttribute("obj") SysUser sysUser) {
		//返回map
		Map<String, Object> resultMap = Maps.newHashMap();
		//生成盐值
		String salt = ShiroUtils.getSalt();
		//生成密码
		String password = ShiroUtils.getPassWord(salt);
		sysUser.setSalt(salt);
		sysUser.setPassword(password);
		sysUserService.insert(sysUser);
		//添加中间表
		int roleId = 0;
		try {
			roleId = Integer.parseInt(request.getParameter("roleId"));
		} catch (Exception e) {
			roleId = 0;
		}
		SysUserRole sysUserRole = new SysUserRole();
		sysUserRole.setUserId(sysUser.getId());
		sysUserRole.setRoleId(roleId);
		sysUserRoleService.insert(sysUserRole);
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
	public Map<String, Object> update(HttpServletRequest request,@ModelAttribute("obj") SysUser sysUser) {
		//返回map
		Map<String, Object> resultMap = Maps.newHashMap();
		sysUserService.update(sysUser);
		//删除中间表
		sysUserRoleService.deleteByUserId(sysUser.getId());
		//添加中间表
		int roleId = 0;
		try {
			roleId = Integer.parseInt(request.getParameter("roleId"));
		} catch (Exception e) {
			roleId = 0;
		}
		SysUserRole sysUserRole = new SysUserRole();
		sysUserRole.setUserId(sysUser.getId());
		sysUserRole.setRoleId(roleId);
		sysUserRoleService.insert(sysUserRole);
		resultMap.put("result", "success");
		return resultMap;
	}
	
	
	/***************************************************delete*********************************************************/
	
	/**
	 * 	删除
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
		sysUserService.delete(id);
		//删除中间表
		sysUserRoleService.deleteByUserId(id);
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
			sysUserService.delete(id);
			//删除中间表
			sysUserRoleService.deleteByUserId(id);
		}
		resultMap.put("result", "success");
		return resultMap;
	}
	
	
    /***************************************************password*********************************************************/

	/**
	 * 批量重置密码
	 * @param request
	 * @param model
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "batchResetPassword")
	@ResponseBody
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> batchResetPassword(HttpServletRequest request,Model model,@RequestParam("ids[]") int[] ids) {
		//返回map
		Map<String, Object> resultMap = Maps.newHashMap();
		//获取配置文件里的初始密码
		for (int id : ids) {
			//生成盐值
			String salt = ShiroUtils.getSalt();
			//生成密码
			String password = ShiroUtils.getPassWord(salt);
			SysUser sysUser = new SysUser();
			sysUser.setId(id);
			sysUser.setSalt(salt);
			sysUser.setPassword(password);
			sysUserService.update(sysUser);
		}
		resultMap.put("result", "success");
		return resultMap;
	}
	
	
	/***************************************************state*********************************************************/
	
	/**
	 * 修改状态
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "updateState")
	@ResponseBody
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> updateState(HttpServletRequest request,Model model) {
		//返回map
		Map<String, Object> resultMap = Maps.newHashMap();
		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (Exception e) {
			id = 0;
		}
		int state = 0;
		try {
			state = Integer.parseInt(request.getParameter("state"));
		} catch (Exception e) {
			state = 0;
		}
		SysUser sysUser = new SysUser();
		sysUser.setId(id);
		sysUser.setState(state);
		sysUserService.update(sysUser);
		resultMap.put("result", "success");
		return resultMap;
	}
	
	/***************************************************password*********************************************************/
	
	/**
	 * 跳转修改密码
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toUpdatePassWord")
	public String toUpdatePassWord(HttpServletRequest request,Model model) {
		//当前登录用户
		SysUser currUser = getCurrentUser();
		model.addAttribute("obj", currUser);
		return "jsp/sysuser/updatePassWord";
	}
	
	
	/**
	 * 修改密码
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "updatePassWord")
	@ResponseBody
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> updatePassWord(HttpServletRequest request,Model model) {
		//当前登录用户
		SysUser currUser = getCurrentUser();
		//返回map
		Map<String, Object> resultMap = Maps.newHashMap();
		
		String oldpass = request.getParameter("oldpass");
		
		String newpass = request.getParameter("newpass");
		
		String repeatpass = request.getParameter("repeatpass");	
		
		String[] values = {oldpass,newpass,repeatpass};
		if(Utils.isNull(values)) {
			//参数为空
			resultMap.put("type", "password");
			resultMap.put("code", "parameter");
			return resultMap;
		}
		//验证旧密码是否正确
		String oldpassword = ShiroUtils.getPassWord(currUser.getSalt(), oldpass);
		if(!oldpassword.equals(currUser.getPassword())) {
			//旧密码不正确
			resultMap.put("type", "password");
			resultMap.put("code", "incorrect");
			return resultMap;
		}
		//验证新密码与旧密码是否一致
		String newpassword = ShiroUtils.getPassWord(currUser.getSalt(), newpass);
		if(newpassword.equals(currUser.getPassword())) {
			//新密码与旧密码不能相同
			resultMap.put("type", "password");
			resultMap.put("code", "agreement");
			return resultMap;
		}
		//两次输入是否一致
		if(!newpass.equals(repeatpass)) {
			//两次输入不一致
			resultMap.put("type", "password");
			resultMap.put("code", "atypism");
			return resultMap;
		}
		currUser.setPassword(newpassword);
		sysUserService.update(currUser);
		resultMap.put("type", "password");
		resultMap.put("code", "success");
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
		String oldLoginName = request.getParameter("oldLoginName");		
		String newLoginName = request.getParameter("newLoginName");
		if(Utils.isNull(oldLoginName)) {
			searchMap.put("loginName", newLoginName);
			long count = sysUserService.checkRepeat(searchMap);
			if(count == 0) {
				return true;
			}else {
				return false;
			}
		}else {
			if(oldLoginName.equals(newLoginName)) {
				return true;
			}else {
				searchMap.put("loginName", newLoginName);
				long count = sysUserService.checkRepeat(searchMap);
				if(count == 0) {
					return true;
				}else {
					return false;
				}
			}
		}
	}
	
}

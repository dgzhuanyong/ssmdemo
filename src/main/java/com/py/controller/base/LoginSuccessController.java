package com.py.controller.base;


import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.py.service.ShiroDbRealm.ShiroUser;
import com.py.entity.SysUser;
import com.py.service.SysUserService;
import com.py.utils.Utils;


/**
 * 
 * @content 登陆后进行一些操作
 *
 * @author cl
 *
 * 2018年11月20日
 */
@Controller
@RequestMapping(value = "/loginSuccess")
public class LoginSuccessController {
	
	@Autowired
	private SysUserService sysUserService;
	
	@RequestMapping(value = "")
	public String loginSuccess(HttpServletRequest request) throws Exception {
		ShiroUser shiroUser = (ShiroUser)SecurityUtils.getSubject().getPrincipal();
		if(StringUtils.isNotBlank(shiroUser.getLoginName())){
			SysUser sysUser = sysUserService.selectByLoginName(shiroUser.getLoginName());
			sysUser.setLastLoginTime(new Date());
			sysUser.setLastLoginIp(Utils.getIpAddress(request));
			sysUserService.update(sysUser);
			return "redirect:/index";
		}
		return "login";
	}
}

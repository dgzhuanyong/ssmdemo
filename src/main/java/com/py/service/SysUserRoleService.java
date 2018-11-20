package com.py.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.py.dao.SysUserRoleMapper;
import com.py.entity.SysUserRole;

@Service
public class SysUserRoleService {
	
	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;
	
	public int insert(SysUserRole record) {
		return sysUserRoleMapper.insertSelective(record);
	}
	
	
	public SysUserRole selectByUserId(Integer userId) {
		return sysUserRoleMapper.selectByUserId(userId);
	}
	
	
	public int deleteByUserId(Integer userId) {
		return sysUserRoleMapper.deleteByUserId(userId);
	}
	
}

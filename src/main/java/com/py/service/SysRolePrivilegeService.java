package com.py.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.py.dao.SysRolePrivilegeMapper;
import com.py.entity.SysRolePrivilege;

@Service
public class SysRolePrivilegeService {
	
	@Autowired
	private SysRolePrivilegeMapper sysRolePrivilegeMapper;
	
	
	public int insert(SysRolePrivilege record) {
		return sysRolePrivilegeMapper.insertSelective(record);
	}
	
	
	public List<SysRolePrivilege> selectByRoleId(Integer roleId){
		return sysRolePrivilegeMapper.selectByRoleId(roleId);
	}
	
	
	public int deleteByRoleId(Integer roleId) {
		return sysRolePrivilegeMapper.deleteByRoleId(roleId);
	}
	
	
	
}

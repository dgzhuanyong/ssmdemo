package com.py.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.py.dao.SysUserMapper;
import com.py.entity.SysUser;

@Service
public class SysUserService {
	
	@Autowired
	private SysUserMapper sysUserMapper;
	
	
	public int insert(SysUser record) {
		return sysUserMapper.insertSelective(record);
	}
	
	
	public int update(SysUser record) {
		return sysUserMapper.updateByPrimaryKeySelective(record);
	}
	
	
	public SysUser selectByPrimaryKey(Integer id) {
		return sysUserMapper.selectByPrimaryKey(id);
	}
	
	
	/**
     * 登录查询
     * @param loginName
     * @return
     */
	public SysUser selectByLoginName(String loginName) {
		return sysUserMapper.selectByLoginName(loginName);
	}
	
	
}

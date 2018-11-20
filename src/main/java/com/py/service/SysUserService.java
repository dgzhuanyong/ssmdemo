package com.py.service;

import java.util.List;
import java.util.Map;

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
	
	
	public int delete(Integer id) {
		return sysUserMapper.deleteByPrimaryKey(id);
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
	
	/**
     * 根据条件查询列表
     * @param searchMap
     * @return
     */
	public List<SysUser> selectConditionList(Map<String, Object> searchMap){
		return sysUserMapper.selectConditionList(searchMap);
	}
	
	/**
     * 检测是否重复
     * @param searchMap
     * @return
     */
	public long checkRepeat(Map<String, Object> searchMap) {
		return sysUserMapper.checkRepeat(searchMap);
	}
	
	
	
	
	
	
	
	
}

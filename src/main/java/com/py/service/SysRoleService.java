package com.py.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.py.dao.SysRoleMapper;
import com.py.entity.SysRole;

@Service
public class SysRoleService {
	
	@Autowired
	private SysRoleMapper sysRoleMapper;
	
	
	public int insert(SysRole record) {
		return sysRoleMapper.insertSelective(record);
	} 
	
	public int update(SysRole record) {
		return sysRoleMapper.updateByPrimaryKeySelective(record);
	}
	
	public int delelte(Integer id){
		return sysRoleMapper.deleteByPrimaryKey(id);
	}
	
	
	/**
     * 根据条件查询列表
     * @param searchMap
     * @return
     */
	public List<SysRole> selectConditionList(Map<String, Object> searchMap){
		return sysRoleMapper.selectConditionList(searchMap);
	}
	
    /**
     * 检测重复
     * @param searchMap
     * @return
     */
	public long checkRepeat(Map<String, Object> searchMap) {
		return sysRoleMapper.checkRepeat(searchMap);
	}
	
	
}

package com.py.dao;

import java.util.List;
import java.util.Map;

import com.py.entity.SysRole;

public interface SysRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);
    
    /**
     * 根据条件查询列表
     * @param searchMap
     * @return
     */
    List<SysRole> selectConditionList(Map<String, Object> searchMap);
    
    
    /**
     * 检测重复
     * @param searchMap
     * @return
     */
    long checkRepeat(Map<String, Object> searchMap);
}
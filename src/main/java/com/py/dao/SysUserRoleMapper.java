package com.py.dao;

import com.py.entity.SysUserRole;

public interface SysUserRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUserRole record);

    int insertSelective(SysUserRole record);

    SysUserRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUserRole record);

    int updateByPrimaryKey(SysUserRole record);
    
    /**
     * 根据userId查询
     * @param userId
     * @return
     */
    SysUserRole selectByUserId(Integer userId);
    
    /**
     * 根据userId删除
     * @param userId
     * @return
     */
    int deleteByUserId(Integer userId);
    
    
    
    
    
    
}
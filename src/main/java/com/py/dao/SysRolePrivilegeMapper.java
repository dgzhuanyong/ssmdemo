package com.py.dao;

import java.util.List;

import com.py.entity.SysRolePrivilege;

public interface SysRolePrivilegeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysRolePrivilege record);

    int insertSelective(SysRolePrivilege record);

    SysRolePrivilege selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRolePrivilege record);

    int updateByPrimaryKey(SysRolePrivilege record);
    
    
    /**
     * 根据角色ID查询
     * @param roleId
     * @return
     */
    List<SysRolePrivilege> selectByRoleId(Integer roleId);
    
    
    /**
     * 根据角色ID删除全部 
     * @param roleId
     * @return
     */
    int deleteByRoleId(Integer roleId);
    
    
}
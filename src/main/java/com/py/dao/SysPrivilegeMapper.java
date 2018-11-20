package com.py.dao;

import java.util.List;

import com.py.entity.SysPrivilege;

public interface SysPrivilegeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysPrivilege record);

    int insertSelective(SysPrivilege record);

    SysPrivilege selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysPrivilege record);

    int updateByPrimaryKey(SysPrivilege record);
    
    /**
     * 查询所有菜单
     * @return
     */
    List<SysPrivilege> selectMenuAll();
    
    /**
     * 查询用户菜单
     * @param userId
     * @return
     */
    List<SysPrivilege> selectMenuByUserId(Integer userId);
    
    /**
     * 查询全部权限
     * @return
     */
    List<SysPrivilege> selectPrivilegeAll();
    
    /**
     * 查询用户权限
     * @param userId
     * @return
     */
    List<SysPrivilege> selectPrivilegeByUserId(Integer userId);
}
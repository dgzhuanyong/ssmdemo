package com.py.dao;

import com.py.entity.SysUser;

public interface SysUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);
    
    /**
     * 登录查询
     * @param loginName
     * @return
     */
    SysUser selectByLoginName(String loginName);
}
package com.py.dao;

import java.util.List;
import java.util.Map;

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
    
    
    /**
     * 根据条件查询列表
     * @param searchMap
     * @return
     */
    List<SysUser> selectConditionList(Map<String, Object> searchMap);
    
    
    /**
     * 检测是否重复
     * @param searchMap
     * @return
     */
    long checkRepeat(Map<String, Object> searchMap);
    
    
    
}
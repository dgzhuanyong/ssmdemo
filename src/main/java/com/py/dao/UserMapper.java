package com.py.dao;

import org.apache.ibatis.annotations.Param;

import com.py.entity.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    /**
     * 根据手机号查询
     * @param phone
     * @return
     */
    User selectByPhone(@Param("phone") String phone);
    
}
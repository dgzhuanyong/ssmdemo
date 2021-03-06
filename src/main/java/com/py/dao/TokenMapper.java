package com.py.dao;

import org.apache.ibatis.annotations.Param;

import com.py.entity.Token;

public interface TokenMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Token record);

    int insertSelective(Token record);

    Token selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Token record);

    int updateByPrimaryKey(Token record);
    
    /**
     * 查询token是否存在
     * @param token
     * @return
     */
    Token selectByToken(@Param("token") String token);
    
    
    
}
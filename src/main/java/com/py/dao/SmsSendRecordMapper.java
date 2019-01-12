package com.py.dao;

import org.apache.ibatis.annotations.Param;

import com.py.entity.SmsSendRecord;

public interface SmsSendRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SmsSendRecord record);

    int insertSelective(SmsSendRecord record);

    SmsSendRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SmsSendRecord record);

    int updateByPrimaryKey(SmsSendRecord record);
    
    /**
     * 根据手机号查询最新的一条短信记录
     * @param phone
     * @return
     */
    SmsSendRecord selectByPhoneLast(@Param("phone") String phone);
    
    
}
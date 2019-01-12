package com.py.dao;

import com.py.entity.SmsSendRecord;

public interface SmsSendRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SmsSendRecord record);

    int insertSelective(SmsSendRecord record);

    SmsSendRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SmsSendRecord record);

    int updateByPrimaryKey(SmsSendRecord record);
}
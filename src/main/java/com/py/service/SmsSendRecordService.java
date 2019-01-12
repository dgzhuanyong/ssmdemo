package com.py.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.py.dao.SmsSendRecordMapper;
import com.py.entity.SmsSendRecord;

@Service
public class SmsSendRecordService {
	 
	@Autowired
	private SmsSendRecordMapper smsSendRecordMapper;
	
	
	public int insert(SmsSendRecord record) {
		return smsSendRecordMapper.insertSelective(record);
	}
	
	/**
	 * 根据手机号查询最新的一条短信记录
	 * @param phone
	 * @return
	 */
	public SmsSendRecord selectByPhoneLast(String phone) {
		return smsSendRecordMapper.selectByPhoneLast(phone);
	}
	
	
	
	
}

package com.py.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.py.dao.UserMapper;
import com.py.entity.User;

@Service
public class UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	public int insert(User record) {
		return userMapper.insertSelective(record);
	}
	
	public int update(User record) {
		return userMapper.updateByPrimaryKeySelective(record);
	}
	
	
	public User selectByPrimaryKey(Integer id) {
		return userMapper.selectByPrimaryKey(id);
	}
	
	
    /**
     * 根据手机号查询
     * @param phone
     * @return
     */
	public User selectByPhone(String phone) {
		return userMapper.selectByPhone(phone);
	}

	
	
}

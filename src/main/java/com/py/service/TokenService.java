package com.py.service;

import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.py.dao.TokenMapper;
import com.py.entity.Token;

@Service
public class TokenService {
	
	@Autowired
	private TokenMapper tokenMapper;
	
	
	/**
	 * 创建token
	 * @param userId
	 * @return
	 */
	public String createToken(int userId) {
		//TOKEN 32位即可，否则作为极光推送的别名会报错
		String str = CreateRandomStr(32);
		Token token = new Token();
		token.setToken(str);
		token.setCreateUser(userId);
		token.setCreateTime(new Date());
		tokenMapper.insertSelective(token);
		return token.getToken();
	}
	
	
	/**
	 * 创建随机字符串
	 * @param length
	 * @return
	 */
	public String CreateRandomStr(int length) {
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuffer sb = new StringBuffer();
        Random rd = new Random();
        for (int i = 0; i < length; i++) {
        	sb.append(str.charAt(rd.nextInt(str.length()-1)));
        }
        return sb.toString();
    }
	
	/**
	 * 验证token是否有效
	 * @param token
	 * @return
	 */
	public boolean valid(String str){
		Token token = tokenMapper.selectByToken(str);
		if(null == token) {
			return false;
		}else {
			return true;
		}
	}
	
	
}

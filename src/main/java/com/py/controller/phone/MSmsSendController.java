package com.py.controller.phone;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aliyuncs.exceptions.ClientException;
import com.google.common.collect.Maps;
import com.py.entity.SmsSendRecord;
import com.py.entity.User;
import com.py.service.SmsSendRecordService;
import com.py.service.UserService;
import com.py.utils.Utils;

/**
 * 
 * @content 发送短信
 *
 * @author cl
 *
 * 2019年1月12日
 */
public class MSmsSendController {
	
	@Autowired
	private SmsSendRecordService smsSendRecordService;
	
	@Autowired
	private UserService userService;
	
	/**
	 * 发送短信验证码
	 * @param request
	 * @return
	 * @throws ClientException 
	 */
	@RequestMapping(value = "/send",method=RequestMethod.POST)
	@ResponseBody
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> send(HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = Maps.newHashMap();
		//手机号
		String phone = request.getParameter("phone");
		//短信类型
		String type = request.getParameter("type");
		//验证非空
		if(Utils.isNull(new String[] {phone,type})) {
			resultMap.put("code", 2);
			resultMap.put("msg", "参数有空值");
			return resultMap;
		}
		//验证手机号是否存在
		/*User user = userService.selectByAccountNumber(phone);
		//注册
		if(StringUtils.equals(type, Constants.REG)) {
			if(null != user) {
				resultMap.put("code", 3);
				resultMap.put("msg", "手机已存在");
				return resultMap;
			}
		}
		
		if(StringUtils.equals(type, Constants.LOGIN)) {
			if(null == user) {
				resultMap.put("code", 3);
				resultMap.put("msg", "手机不存在");
				return resultMap;
			}
		}
		if(StringUtils.equals(type, Constants.VALIDATE_IDENTITY)) {
			if(null == user) {
				resultMap.put("code", 3);
				resultMap.put("msg", "手机不存在");
				return resultMap;
			}
		}
		
		if(StringUtils.equals(type, Constants.UPDATE_PASS)) {
			if(null == user) {
				resultMap.put("code", 3);
				resultMap.put("msg", "手机不存在");
				return resultMap;
			}
		}*/
		
		//查询短信记录表，1分钟之内发送过的，不能在发送
		/*SmsSendRecord smsSendRecord = smsSendRecordService.selectByPhoneLast(phone);
		if(null != sms) {
			long currtime = System.currentTimeMillis();
			long smstime = sms.getSendTime().getTime();
			int diff = (int) ((currtime-smstime)/1000);
			if(diff<=60){
				resultMap.put("code", 3);
				resultMap.put("msg", "两次短信间隔不足一分钟");
				return resultMap;
			}
		}*/
		//生产code
		String code = Utils.getRandomCode(4);
		SmsSendRecord smsSendRecord = new SmsSendRecord();
		smsSendRecord.setCode(code);
		smsSendRecord.setPhone(phone);
		smsSendRecord.setSendTime(new Date());
		smsSendRecord.setIsDelete(false);
		smsSendRecordService.insert(smsSendRecord);
		resultMap.put("code", 1);
		resultMap.put("msg", "发送成功");
		return resultMap;
	}
}

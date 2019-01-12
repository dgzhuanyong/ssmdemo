package com.py.controller.base;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.py.utils.FileUtils;

/**
 * 
 * @content 文件上传
 *
 * @author cl
 *
 * 2019年1月11日
 */
@Controller
@RequestMapping("upload")
public class UploadController{
	
	
	/**
	 * 异步上传文件带监听
	 * @param request
	 * @return
	 * @throws FileUploadException 
	 */
	@RequestMapping("/uploadFileMonitor")
	@ResponseBody
	public Map<String,Object> uploadFileMonitor(HttpServletRequest request) throws FileUploadException{
		String name=request.getParameter("name");
		return FileUtils.uploadFileMonitor(request,name);
	}
	
	
	/**
	 * 获取文件上传进度
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/getFileUploadSchedule")
	@ResponseBody
	public Map<String, Object> getFileUploadSchedule(HttpServletRequest request, HttpServletResponse response) throws IOException{
		Session session = SecurityUtils.getSubject().getSession();
		Map<String, Object> map = new HashMap<String, Object>();
		Object percentage = session.getAttribute("percentage");
		if(percentage == null){
			map.put("percentage", "0%");
		}else{
			map.put("percentage", percentage);  
		} 
		if(null != percentage && "100%".equals(percentage.toString())){
			session.setAttribute("percentage", null);
		}
		return map;
	}
	
	
}

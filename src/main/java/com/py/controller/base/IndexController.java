package com.py.controller.base;


import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 
 * @content index
 *
 * @author cl
 *
 * 2019年1月11日
 */
@Controller
@RequestMapping(value = "/index")
public class IndexController {
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request) {
		return "index";
	}
}

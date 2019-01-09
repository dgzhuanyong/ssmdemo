package com.py.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class OrderUtil {
	
	
	public static String createOrder() {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String str = sdf.format(new Date());
		StringBuffer sb = new StringBuffer();
		Random random=new Random();
		for(int i=0;i<13;i++){
			sb.append(random.nextInt(10));
		}
		str+=sb.toString();
		return str;
	}
	
	
}

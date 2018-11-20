/**
 * 
 */
package com.py.function;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.core.convert.converter.Converter;

/**
 * 
 * @content Spring 多种格式日期类型  自定义转换器
 *
 * @author cl
 *
 * 2018年11月20日
 */
public class DateConverter implements Converter<String, Date>{

	
	private static final List<String> formarts = new ArrayList<String>(4);
    static{
        formarts.add("yyyy-MM");
        formarts.add("yyyy-MM-dd");
        formarts.add("yyyy-MM-dd HH");
        formarts.add("yyyy-MM-dd HH:mm");
        formarts.add("yyyy-MM-dd HH:mm:ss");
    }
	
    /**
     * 重写 convert 
     */
	@Override
	public Date convert(String source) {
		 String value = source.trim();
		 if("".equals(value)){
			 return null;
		 }
		 if(source.matches("^\\d{4}-\\d{1,2}$")){ 
             return parseDate(source, formarts.get(0));
         }else if(source.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")){
             return parseDate(source, formarts.get(1));
         }else if(source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}$")){
             return parseDate(source, formarts.get(2));
         }else if(source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}$")){
             return parseDate(source, formarts.get(3));
         }else if(source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$")){
             return parseDate(source, formarts.get(4));
         }else {
             throw new IllegalArgumentException("Invalid boolean value '" + source + "'");
         }
	}
	
	/**
	 * 字符串转时间
	 * @param dateStr
	 * @param format
	 * @return
	 */
	public Date parseDate(String dateStr, String format) {
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			date = sdf.parse(dateStr);
		} catch (Exception e) {
		}
		return date;
	}
	
	
}

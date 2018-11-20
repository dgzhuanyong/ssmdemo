package com.py.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class OfTime {
	/**
	 * 获取当前系统时间（yyyy-MM-dd HH:mm:ss）格式
	 * @return
	 */
	public static String getLongTime(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		return df.format(new Date());
	}
	/**
	 * 获取当前系统时间（yyyyMMddHHmmss）格式
	 * @return
	 */
	public static String getLongTimeWu(){
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
		return df.format(new Date());
	}
	/**
	 * 获取当前系统时间（yyyy-MM-dd ）格式
	 */
	public static String getShortTime(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		return df.format(new Date());
	}
	
	/**
	 * 获取当前系统时间（yyyy-M-d ）格式
	 */
	public static String getShortTime2(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-M-d");//设置日期格式
		return df.format(new Date());
	}
	
	
	/** 
     * 获取当月的 天数 
     */  
    public static int getCurrentMonthDay() {  

        Calendar a = Calendar.getInstance();  
        a.set(Calendar.DATE, 1);  
        a.roll(Calendar.DATE, -1);  
        int maxDate = a.get(Calendar.DATE);  
        return maxDate;  
    }  

    /** 
     * 根据年 月 获取对应的月份 天数 
     */  
    public static int getDaysByYearMonth(int year, int month) {  

        Calendar a = Calendar.getInstance();  
        a.set(Calendar.YEAR, year);  
        a.set(Calendar.MONTH, month - 1);  
        a.set(Calendar.DATE, 1);  
        a.roll(Calendar.DATE, -1);  
        int maxDate = a.get(Calendar.DATE);  
        return maxDate;  
    }  

    /** 
     * 根据日期 找到对应日期的 星期 
     */  
    public static String getDayOfWeekByDate(String date) {  
        String dayOfweek = "-1";  
        try {  
            SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");  
            Date myDate = myFormatter.parse(date);  
            SimpleDateFormat formatter = new SimpleDateFormat("E");  
            String str = formatter.format(myDate);  
            dayOfweek = str;  

        } catch (Exception e) {  
            System.out.println("错误!");  
        }  
        return dayOfweek;  
    }  
    
    
    
    /**
     * 返回当前年月日  
     * @return
     */
    public static String getNowDate()  
    {  
        Date date = new Date();  
        String nowDate = new SimpleDateFormat("yyyy年MM月dd日").format(date);  
        return nowDate;  
    }  
  
    /**
     * 返回当前年份  
     * @return
     */
    public static int getYear()  
    {  
        Date date = new Date();  
        String year = new SimpleDateFormat("yyyy").format(date);  
        return Integer.parseInt(year);  
    }  
  
    /**
     * 返回当前月份  
     * @return
     */
    public static int getMonth()  
    {  
        Date date = new Date();  
        String month = new SimpleDateFormat("MM").format(date);  
        return Integer.parseInt(month);  
    }  
  
    /**
     * 判断闰年  
     * @param year
     * @return
     */
    public static boolean isLeap(int year)  
    {  
        if (((year % 100 == 0) && year % 400 == 0) || ((year % 100 != 0) && year % 4 == 0))  
            return true;  
        else  
            return false;  
    }  
  
    /**
     * 返回当月天数  
     * @param year
     * @param month
     * @return
     */
    public static int getDays(int year, int month)  
    {  
        int days;  
        int FebDay = 28;  
        if (isLeap(year))  
            FebDay = 29;  
        switch (month)  
        {  
            case 1:  
            case 3:  
            case 5:  
            case 7:  
            case 8:  
            case 10:  
            case 12:  
                days = 31;  
                break;  
            case 4:  
            case 6:  
            case 9:  
            case 11:  
                days = 30;  
                break;  
            case 2:  
                days = FebDay;  
                break;  
            default:  
                days = 0;  
                break;  
        }  
        return days;  
    }  
  
    /**
     * 返回当月星期天数  
     * @param year
     * @param month
     * @return
     */
    public  static int getSundays(int year, int month)  
    {  
        int sundays = 0;  
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");  
        Calendar setDate = Calendar.getInstance();  
        //从第一天开始  
        int day;  
        for (day = 1; day <= getDays(year, month); day++)  
        {  
            setDate.set(Calendar.DATE, day);  
            String str = sdf.format(setDate.getTime());  
            if (str.equals("星期日"))  
            {  
                sundays++;  
            }  
        }  
        return sundays;  
    }  
  
}  
    


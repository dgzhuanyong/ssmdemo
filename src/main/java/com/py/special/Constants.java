package com.py.special;

import java.io.Serializable;

/**
 * 常量
 */
public class Constants implements Serializable{

	private static final long serialVersionUID = -2087804150399520300L;

	/**当前用户所拥有的权限 */
	public static final String MENU = "menu";
	
	/** 密码加密类型 */
	public static final String HASH_ALGORITHM = "MD5";
	
	/** 密码迭代次数 */
	public static final int HASH_INTERATIONS = 1024;
	
	/**用户密码加盐长度 */
	public static final int SALT_SIZE = 8;
	
	/**默认密码值 */
	public static final String DEFAULT_PASSWORD = "123456";
	
	
}

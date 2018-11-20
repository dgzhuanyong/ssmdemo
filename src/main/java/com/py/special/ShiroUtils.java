package com.py.special;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.hash.SimpleHash;

public class ShiroUtils {
	
	private static final char[] DIGITS_LOWER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	private static SecureRandom random = new SecureRandom();
	
	/**
	 * 生成salt
	 * @return
	 */
	public static String getSalt() {
		return encodeHex(generateSalt(Constants.SALT_SIZE));
	}
	
	
	/**
	 * 生成密码
	 * @param salt
	 * @return
	 */
	public static String getPassWord(String salt) {
		return getPassWord(salt, Constants.DEFAULT_PASSWORD);
	}
	
	/**
	 * 生成密码
	 * @param salt
	 * @return
	 */
	public static String getPassWord(String salt,String password) {
		return new SimpleHash(Constants.HASH_ALGORITHM, password, salt, Constants.HASH_INTERATIONS).toHex();
	}
	
	/**
	 * 生成X位字节数
	 * @param nums
	 * @return
	 */
	private static byte[] generateSalt(int nums){
		byte[] bytes = new byte[nums];
		random.nextBytes(bytes);
		return bytes;
	}
	
	
	/**
	 * 生成十六进制的字符串
	 * @param data
	 * @return
	 */
	private static String encodeHex(byte[] data){
		int l = data.length;
		char[] out = new char[l << 1];
		int i = 0; for (int j = 0; i < l; i++) {
			out[(j++)] = DIGITS_LOWER[((0xF0 & data[i]) >>> 4)];
			out[(j++)] = DIGITS_LOWER[(0xF & data[i])];
		}
		return new String(out);
	}
	
	
	public static String getShiroCipherKey() {
		String secret = "";
		try {
			KeyGenerator keygen = KeyGenerator.getInstance("AES");
			SecretKey deskey = keygen.generateKey();
			secret = Base64.encodeToString(deskey.getEncoded());
		} catch (Exception e) {}
	    return secret;
	}
	

	public static void main(String[] args) {
		String salt = ShiroUtils.getSalt();
		String password = ShiroUtils.getPassWord(salt);
		String key = ShiroUtils.getShiroCipherKey();
		System.out.println(salt);
		System.out.println(password);
		System.out.println(key);
	}
}

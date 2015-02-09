package com.platform.base.util;

import java.security.MessageDigest;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @title       :EncryptUtil
 * @description :加密工具类，提供MD5/SHA-1加密算法
 * @update      :2015-1-26 上午10:02:50
 * @author      :172.17.5.73
 * @version     :1.0.0
 * @since       :2015-1-26
 */
public final class EncryptUtil {
	/**
	 * MD5加密算法
	 * @param s
	 * @return
	 */
	public static String encryptByMD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * SHA加密算法
	 * @param s
	 * @return
	 */
	public static String encryptBySHA1(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("SHA-1");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 校验输入的明文和系统存储的MD5密文是否一致
	 * @param plain
	 * @param ciperText
	 * @return
	 */
	public static boolean checkByMD5(String plain,String ciperText){
		return checkEncrypt(plain,ciperText,true);
	}
	
	/**
	 * 校验输入信息的明文和系统存储的SHA-1密文是否一致
	 * @param plain
	 * @param cyperText
	 * @return
	 */
	public static boolean checkBySHA1(String plain,String cyperText){
		return checkEncrypt(plain,cyperText,false);
	}
	
	/**
	 * 校验输入信息的明文和系统存储的密文是否一致
	 * @param plain
	 * @param cyperText
	 * @return
	 */
	private static boolean checkEncrypt(String plain,String cyperText,boolean useMD5){
		if(StringUtils.isEmpty(plain)||StringUtils.isEmpty(cyperText)){
			return false;
		}
		
		String encryptInput = null;
		if(useMD5){
			encryptInput = encryptByMD5(plain);
		}else{
			//使用SHA加密算法
			encryptInput = encryptBySHA1(plain);
		}
		
		//判断密文是否为空
		if(StringUtils.isEmpty(encryptInput)){
			return false;
		}
		
		return encryptInput.equals(cyperText);
	}
}

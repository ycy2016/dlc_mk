package com.mas.dtc.utils;

import java.security.MessageDigest;

import org.apache.log4j.Logger;

/**
 * md5加密工具
 * @author Mathsys
 *
 */
public class MD5Util {
	/**
	 * 日志
	 */
	private static final Logger LOG = Logger.getLogger(MD5Util.class);
	
	 /** 
     * 32位字符串md5加密(小写+字母) 
     * 
     * @param encryptStr 传入要加密的字符串 
     * @return  MD5加密后的字符串 
     */  
    public static String getMD5(String encryptStr) {
		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("MD5");
			byte[] md5Bytes = md5.digest(encryptStr.getBytes("UTF-8"));
			StringBuffer hexValue = new StringBuffer();
			for (int i = 0; i < md5Bytes.length; i++) {
				int val = ((int) md5Bytes[i]) & 0xff;
				if (val < 16)
					hexValue.append("0");
				hexValue.append(Integer.toHexString(val));
			}
			encryptStr = hexValue.toString();
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
		}
		return encryptStr;
	}
    
    
}

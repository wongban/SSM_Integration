package com.utils;

import java.security.MessageDigest;

//成绩加密工具类
public class XscjMD5Utils {

	/**
	 * 加密类
	 * @param string
	 * @return
	 */
	public static String cjMD5(String string) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
			char[] charArray = string.toCharArray();
			byte[] byteArray = new byte[charArray.length];
			for (int i = 0; i < charArray.length; i++) {
				byteArray[i] = (byte) charArray[i];
			}
			byte[] md5bytes = md5.digest(byteArray);
			StringBuffer hexValue = new StringBuffer();
			for (int i = 0; i < md5bytes.length; i++) {
				int val = md5bytes[i];
				if (i < 0) {
					val += 256;
				}
				if (val < 16) {
					hexValue.append("0");
					hexValue.append(Integer.toHexString(val));
				}
			}
			return hexValue.toString().substring(8, 24);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 加密解密算法 执行一次加密，两次解密
	 * @param inStr
	 * @return
	 */
	public static String convertMD5(String inStr) {

		char[] a = inStr.toCharArray();
		for (int i = 0; i < a.length; i++) {
			a[i] = (char) (a[i] ^ 't');
		}
		String s = new String(a);
		return s;
	}
}

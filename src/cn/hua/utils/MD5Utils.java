package cn.hua.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

public class MD5Utils {	//md5加密
	public static String md5(String data){
		try {
			MessageDigest dig = MessageDigest.getInstance("md5");
			byte[] md5 = dig.digest(data.getBytes());
			BASE64Encoder encoder = new BASE64Encoder();
			return encoder.encode(md5);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		
	}
}

package cn.hua.utils;

import java.util.UUID;

public class UuidUtils {		//获取UUID
	public static String getUuid(){
		return UUID.randomUUID().toString();
	}
}

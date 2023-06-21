package com.hellobody.util;

public class DownUtil {
	public static String getPath(String type) {
		String path = System.getProperty("user.dir");
		String realpath = path.replace("hellobody","data\\"+type);
		System.out.println(realpath);
		return realpath;
	}
}

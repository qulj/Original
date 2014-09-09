package com.qlj.toolbox.util;

import java.io.File;

import android.content.Context;
import android.os.Environment;

public class Config {
	// public static String mUrl =
	// "http://app.91160.com/mobile/doctor/index.php?c=%1$s&a=%2$s";
	// public static String mUrl1 =
	// "http://app.91160.cn/mobile/doctor/index.php?c=%1$s&a=%2$s";
	public static String mUrl = "http://192.168.1.251/ws3/doc/index.php?c=%1$s&a=%2$s";
	public static String mUrl1 = "http://192.168.1.251/ws3/doc/index.php?c=%1$s&a=%2$s";
	public static String pagecount = "15";
	public static String mSetting = "nysetting";
	public static String finduser_id = "";
	public static String findsms = "";
	public static String findphone = "";
	public static String findpass = "";
	public static String sms = "";
	public static String yzm = "";
	public static String phone = "";
	public static String pass = "";
	public static String path_zgz = "";
	public static String path_zcz = "";
	public static String path_gzz = "";
	public static String keshiphone = "";
	public static String shanchang = "";
	public static int mOutTime = 15000;
	public static long mTimeReLoad = 7 * 24 * 60 * 60 * 1000;// 7天
	
	public static String realname = "";
	public static String cityname = "";
	public static String cityid = "";
	public static String hospitalname = "";
	public static String hospitalid = "";
	public static String keshiname = "";//科室
	public static String keshiid = "";
	
	public static String iconHead = "";
	

	public static void clear() {
		finduser_id = "";
		findsms = "";
		findphone = "";
		findpass = "";
		sms = "";
		yzm = "";
		phone = "";
		pass = "";
		path_zgz = "";
		path_zcz = "";
		path_gzz = "";
		keshiphone = "";
		shanchang = "";
		iconHead = "";
	}

	public static String getSDCardPath(Context pContext) {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			String path = Environment.getExternalStorageDirectory().getAbsolutePath()
					+ "/.jiuyi160";
			File PathDir = new File(path);
			if(!PathDir.exists()){
				PathDir.mkdirs();
			}
			return path;
		} else {
			return pContext.getCacheDir().getAbsolutePath();
		}
	}
}

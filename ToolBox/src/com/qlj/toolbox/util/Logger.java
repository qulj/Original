package com.qlj.toolbox.util;

/**
 * 日志工具类
 * 
 * @author qlj
 * @time 2014年9月3日下午3:03:55
 */
public class Logger {

	private final static String TAG = "Log";
	private final static boolean debug = true;// true打印日志 false 不打印日志

	/**
	 * log.i
	 */
	public static void i(String msg) {
		if (debug) {
			android.util.Log.i(TAG, msg);
		}
	}

	public static void i(String tag, String msg) {
		if (debug) {
			android.util.Log.i(tag, msg);
		}
	}

	/**
	 * log.d
	 */
	public static void d(String msg) {
		if (debug) {
			android.util.Log.d(TAG, msg);
		}
	}

	/**
	 * log.d
	 */
	public static void d(String tag, String msg) {
		if (debug) {
			android.util.Log.d(tag, msg);
		}
	}

	/**
	 * log.e
	 */
	public static void e(String msg) {
		if (debug) {
			android.util.Log.e(TAG, msg);
		}
	}

	/**
	 * log.e
	 */
	public static void e(String tag, String msg) {
		if (debug) {
			android.util.Log.e(tag, msg);
		}
	}

}

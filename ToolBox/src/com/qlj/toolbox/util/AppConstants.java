package com.qlj.toolbox.util;

import android.os.Environment;

/**
 * 系统常量定义类
 */
public class AppConstants {

	/**
	 * ----------------------------用户登录信息---------------------------
	 */
	public static String ALREADY_LOGGED = "already_logged";// 是否已经登录
	public static String LOGIN_ID = "login_id";// 登录用户名/手机号码
	public static String LOGIN_PWD = "login_pwd";// 登录密码

	public static final String USER_ID = "user_id";
	public static final String USER_NAME = "user_name";
	public static final String USER_TEL = "user_tel";
	public static String USER_PHOTO = "user_photo";// 用户头像
	public static final String ENTERPRISE_ID = "enterprise_id";

	public static String USER_NICK_NAME = "user_nick_name";// 用户昵称
	public static String CITY_NAME = "cityName";// 城市
	public static String REMEMBER_PWD = "remember_pwd";
	public static String SECONDDIMENSIONCODE = "secondDimensionCode";// 下载地址
	public static String USER_BASEACCOUNT = "userBaseAccount";// 账户余额
	public static String NEW_MSG_COUNT = "update_msg_count";// 抢单消息提醒数
	public static String REFRESH_TIME = "refreshTime";// 抢单刷新时间

	/**
	 * 记录分享类型： 1,分享到新浪微博; 2,分享到qq; 3,分享到腾讯微博; 4,分享到微信会话; 5,分享到微信朋友圈; 6,其它
	 */
	public static final String SHARE_SINO = "1";
	public static final String SHARE_QQ = "2";
	public static final String SHARE_TENCENT_WEIBO = "3";
	public static final String SHARE_WEBCHAT_SESSION = "4";
	public static final String SHARE_WEBCHAT_ONLINE = "5";
	public static final String SHARE_OTHER = "6";

	/**
	 * ---------------消费者订单来源类型 [1-Android 2-微信 3-IOS]-------------
	 */
	public static final String BILL_SOURCE = "1";

	/**
	 * ----------------------------分页信息---------------------------
	 */
	public static final int PAGE_SIZE = 10;// 每页更新数量

	/**
	 * ----------------------------List刷新----------------------------
	 */
	public static final String LOCAL_DATA = "local_data";// 加载本地数据
	public static final String FIRST_LOAD = "first_load";// 首次加载
	public static final String HEAD_REFRESH = "head_refresh";// 下拉刷新
	public static final String FOOTER_MORE = "footer_more";// 上拉加载更多

	public static String MESSAGE_REFRESH_TIME = "message_refresh_time";// 消息列表刷新时间
	public static String SHOPS_SHOW_REFRESH_TIME = "shops_show_refresh_time";// 店铺秀刷新时间

	/**
	 * ----------------------------缓存---------------------------
	 */
	public static final String CACHE_PATH_PREFIX = Environment.getExternalStorageDirectory() + "/com.janu.youmayoula";
	public static final String JSON_CACHE_PATH = CACHE_PATH_PREFIX + "/files/";

	/**
	 * ----------------------------APK下载路径---------------------------
	 */
	public static String APK_DOWN_LOAD_URL = CommonUtil.getApiUrl() + "/download/index.do?enterpriseId=";// apk下载地址（正式上的）http://www.janu.cc:18080/appDownLoad/8d3e911ccf35490792eaedd8e84ba9b8/8d3e911ccf35490792eaedd8e84ba9b8.apk

	public static class Config {
		public static final boolean DEVELOPER_MODE = false;
	}

	public static class Extra {
		public static final String IMAGES = "com.nostra13.example.universalimageloader.IMAGES";
		public static final String IMAGE_POSITION = "com.nostra13.example.universalimageloader.IMAGE_POSITION";
	}

}

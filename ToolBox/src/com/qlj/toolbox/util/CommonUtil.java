package com.qlj.toolbox.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.qlj.toolbox.ToolBoxApplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;

/**
 * 常用工具类
 * 
 * @author zxq
 * @date 2013-9-1
 */
public class CommonUtil {

	public static final String DATA_SAVED = "com.janu.reservation";
	
	/**
	 * 更多详情处理 html标签
	 * @param str
	 * @return
	 */
	public static String htmldecode(String str) {
		str = str.replace("&amp;", "&");
		str = str.replace("&nbsp;", " ");
		str = str.replace("&quot;", "\"");
		str = str.replace("&#39;", "'");
		str = str.replace("&lt;", "<");
		str = str.replace("&gt;", ">");
		str = str.replace("<br[^>]*>(?:(rn)|r|n)?", "n");
		return str;
	}
	
	/**
	 * 获取当前应用版本号
	 * @return
	 * @throws Exception
	 */
	public static String getVersionName(Context context) {
		String version = "";
		try {
			PackageManager packageManager = context.getPackageManager();
			// getPackageName()是你当前类的包名，0代表是获取版本信息
			PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			version = packInfo.versionName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return version;
	}
	
	/**
	 * 获取当前应用包名
	 * @param context
	 * @return
	 */
	public static String getPackageName(Context context) {
		String packageName = "";
		try {
			PackageManager packageManager = context.getPackageManager();
			// getPackageName()是你当前类的包名，0代表是获取版本信息
			PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			packageName = packInfo.packageName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return packageName;
	}
	
	/**
	 * 更新apk
	 * @param apkurl
	 * @param handler
	 * @param context
	 */
	public void upgrade(String apkurl,final Handler handler,final Context context) {
		final String url = apkurl;
		final Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					File file = getFileFromServer(url,handler);
					Thread.sleep(2000);
					handler.sendEmptyMessage(2);
					installAPK(file,context);				
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});
		
		t.start();
	}
	
	public void installAPK(File file,Context context){
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
		context.startActivity(intent);
		
		android.os.Process.killProcess(Process.myPid());
	}
	
	/**下载文件*/
	public File getFileFromServer(String path,Handler handler) throws Exception {
		
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			Message msg = new Message();
			msg.what = 0;
			msg.arg1 = conn.getContentLength();
			handler.sendMessage(msg);

			InputStream is = conn.getInputStream();
			File file = new File(Environment.getExternalStorageDirectory() + File.separator + "Download" + File.separator + "APK" + File.separator);
			if(!file.exists()) {
				file.mkdirs();
			}
			
			FileOutputStream fos = new FileOutputStream(file + "reservation_janu.apk");
			BufferedInputStream bis = new BufferedInputStream(is);
			byte[] buffer = new byte[1024];
			int len;
			int total = 0;
			while ((len = bis.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
				total += len;
				// 获取当前下载量
				// pd.setProgress(total);
				msg = new Message();
				msg.what = 1;
				msg.arg1 = total;
				handler.sendMessage(msg);
//				if(isStop){
//					conn.disconnect();
//					try {
//						if(is != null)
//							is.close();
//						
//					} catch (IOException e) {
//
//						e.printStackTrace();
//					}
//					return null;
//				}
			}
			fos.close();
			bis.close();
			is.close();
			return file;
		
	}

	public static void sharedPreferences(Context context, String key, String value) {
		Editor editor = context.getSharedPreferences(DATA_SAVED, Context.MODE_PRIVATE).edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static void sharedPreferences(Context context, String key, boolean value) {
		Editor editor = context.getSharedPreferences(DATA_SAVED, Context.MODE_PRIVATE).edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public static void sharedPreferences(Context context, String key, int value) {
		Editor editor = context.getSharedPreferences(DATA_SAVED, Context.MODE_PRIVATE).edit();
		editor.putInt(key, value);
		editor.commit();
	}

	public static String getSharedPreferences(Context context, String key, String defValue) {
		return context.getSharedPreferences(DATA_SAVED, Context.MODE_PRIVATE).getString(key, defValue);
	}

	public static boolean getSharedPreferences(Context context, String key, boolean defValue) {
		return context.getSharedPreferences(DATA_SAVED, Context.MODE_PRIVATE).getBoolean(key, defValue);
	}

	public static int getSharedPreferences(Context context, String key, int defValue) {
		return context.getSharedPreferences(DATA_SAVED, Context.MODE_PRIVATE).getInt(key, defValue);
	}

	public static void removeSharedPreferences(Context context, String key) {
		Editor editor = context.getSharedPreferences(DATA_SAVED, Context.MODE_PRIVATE).edit();
		editor.remove(key);
		editor.commit();
	}

	/**
	 * 设备标识符号
	 * @param context
	 * @return
	 */
	public static String getAndroidId(Context context) {
		String androidId = android.provider.Settings.Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
		if (androidId == null) {
			androidId = "1234567890000";
		}
		// Logger.i("ANDROID_ID:" + androidId);
		return androidId;
	}

	/**
	 * 获取SIM卡 IMEI 手机串号
	 * @param context
	 * @return
	 */
	public static String getIMEI(Context context) {
		String imei = null;
		imei = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
		return imei;
	}

	/**
	 * 获取SIM卡 IMSI sim 卡串号
	 * @param context
	 * @return
	 */
	public static String getIMSI(Context context) {
		String imsi = null;
		imsi = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();
		return imsi;
	}

	/**
	 * 获取设备信息
	 * @return
	 */
	public static String getModel() {
		String model = null;
		model = Build.MODEL.replace(" ", "_");
		return model;
	}

	/**
	 * 获取屏幕宽度【像素】
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenWidth(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;//(int)(dm.widthPixels * dm.density +0.5f);
	}

	/**
	 * 获取屏幕高度度【像素】
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenHeight(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.heightPixels;//(int)(dm.heightPixels * dm.density +0.5f);
	}

	/**
	 * 获取屏幕像素密度
	 * 
	 * @param context
	 * @return
	 */
	public static float getDensity(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.density;
	}

	/**
	 * 获取屏幕宽度【DIP】
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenWidthDip(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}

	/**
	 * 获取屏幕高度度【DIP】
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenHeightDip(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.heightPixels;
	}

	/**
	 * dip转换成px
	 * 
	 * @param context
	 * @param dipValue
	 * @return
	 */
	public static int dipToPx(Context context, float dipValue) {
		return (int) (dipValue * getDensity((Activity)context) + 0.5f);
	}

	/**
	 * px转换成dip
	 * 
	 * @param context
	 * @param pxValue
	 * @return
	 */
	public static int pxToDip(Context context, float pxValue) {
		return (int) (pxValue / getDensity((Activity)context) + 0.5f);
	}
	
	public static int spToPx(Context context, float spValue) {  
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;  
        return (int) (spValue * fontScale + 0.5f);  
    }
	
	public static int pxToSp(Context context, float pxValue) {  
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;  
        return (int) (pxValue / fontScale + 0.5f);  
    }  

	/**
	 * 加载loading
	 * 
	 * @param context
	 * @param layout
	 */
	public static void startLoading(Context context, View layout) {
//		layout.setVisibility(View.VISIBLE);
//		ImageView loading_img = (ImageView) layout.findViewById(R.id.loading_img);
//		Animation anim = AnimationUtils.loadAnimation(context, R.anim.loading_round);
//		LinearInterpolator lin = new LinearInterpolator();
//		anim.setInterpolator(lin);
//		if (anim != null) {
//			loading_img.startAnimation(anim);
//		}
	}

	/**
	 * 提示
	 * 
	 * @param context
	 * @param msg
	 */
	public static void toast(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
	
	public static boolean isEmpty(String value) {
		if (value == null || value.length()==0) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isNotEmpty(String value) {
		return !isEmpty(value);
	}
	
	/**
	 * 手机号验证
	 * 
	 * @param  str
	 * @return 验证通过返回true
	 */
	public static boolean isMobile(String str) { 
		Pattern p = null;
		Matcher m = null;
		boolean b = false; 
		p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号
		m = p.matcher(str);
		b = m.matches(); 
		return b;
	}
	
	 /**  
     * 电话号码验证  
     *   
     * @param  str  
     * @return 验证通过返回true  
     */  
    public static boolean isPhone(String str) {    
        Pattern p1 = null,p2 = null;   
        Matcher m = null;   
        boolean b = false;     
        p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");  // 验证带区号的   
        p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // 验证没有区号的   
        if(str.length() >9)   
        {   m = p1.matcher(str);   
            b = m.matches();     
        }else{   
            m = p2.matcher(str);   
            b = m.matches();    
        }     
        return b;   
    }  

    
	/**
	 * 电话号码和手机号验证
	 * 
	 * @param str
	 * @return 验证通过返回true
	 */
	public static boolean isPhoneNumber(String str) {
		Pattern p1 = null, p2 = null, p3 = null;
		Matcher m = null;
		boolean b = false;
		p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$"); // 验证带区号的
		p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$"); // 验证没有区号的
		p3 = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号

		if (str.length() == 11) {
			m = p3.matcher(str);
			b = m.matches();
		} else if (str.length() > 9) {
			m = p1.matcher(str);
			b = m.matches();
		} else {
			m = p2.matcher(str);
			b = m.matches();
		}
		return b;
	}
	
	/**手机号码 第一位必须是1，第二必须是3,4,5,8中一位，其它9位随便数字*/
	public static boolean isMobilePhoneNumber(String phoneNumber){
		//  /^0{0,1}(13[4-9]|15[7-9]|15[0-2]|18[7-8])[0-9]{8}$/; 
		String reg_VerCode = "^(13|14|15|18)[0-9]{9}$";
		
		Pattern p = Pattern.compile(reg_VerCode);
		Matcher m = p.matcher(phoneNumber);
		return m.matches();
	}
	
	/***邮箱验证*/
	public static boolean isEmail(String strEmail) {
		String strPattern = "[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9][@][\\w\\-]{1,}([.]([\\w\\-]{1,})){1,3}$";
		
		Pattern p = Pattern.compile(strPattern);
		Matcher m = p.matcher(strEmail);
		return m.matches();
	}
	
	/**
	 * 6-12位字符或数字
	 * @param str
	 * @return
	 */
	public static boolean checkPassword(String pwd) {
		String strPattern = "[A-Za-z0-9]{6,12}";
		Pattern p = Pattern.compile(strPattern);
		Matcher m = p.matcher(pwd);
		return m.matches();
	}
	
	/** 
     * double 乘法 
     * @param d1 
     * @param d2 
     * @return 
     */ 
    public static double mul(double d1,double d2){ 
        BigDecimal bd1 = new BigDecimal(Double.toString(d1)); 
        BigDecimal bd2 = new BigDecimal(Double.toString(d2)); 
        return bd1.multiply(bd2).doubleValue(); 
    } 
    
    /**
     * 加法
     * @param d1
     * @param d2
     * @return
     */
    public static double add(double d1,double d2){ 
        BigDecimal bd1 = new BigDecimal(Double.toString(d1)); 
        BigDecimal bd2 = new BigDecimal(Double.toString(d2)); 
        return bd1.add(bd2).doubleValue(); 
    } 
    
    /**
     * 减法
     * @param d1
     * @param d2
     * @return
     */
    public static double subtract(double d1,double d2) {
    	 BigDecimal bd1 = new BigDecimal(Double.toString(d1)); 
         BigDecimal bd2 = new BigDecimal(Double.toString(d2)); 
         return bd1.subtract(bd2).doubleValue(); 
    }
    
    /**
     * 除法
     * @param d1
     * @param d2
     * @return
     */
    public static double divide(double d1,double d2) {
    	 BigDecimal bd1 = new BigDecimal(Double.toString(d1)); 
         BigDecimal bd2 = new BigDecimal(Double.toString(d2)); 
         return bd1.divide(bd2).doubleValue(); 
    }
	/**
	 * 检查网络连接
	 * @param context
	 * @return
	 */
	 public static boolean checkNetworkAvailable(Context context) {
	        final ConnectivityManager connMgr = (ConnectivityManager) context
	            .getSystemService(Context.CONNECTIVITY_SERVICE);
	        final android.net.NetworkInfo wifi = connMgr
	            .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
	        final android.net.NetworkInfo mobile = connMgr
	            .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
	        // if(wifi.isAvailable() || mobile.isAvailable())
	        if (wifi.isConnected() || mobile.isConnected())
	            return true;
	        else
	            return false;
	    }
	 
	/**
	 * 判读是否登录 
	 * @return
	 */
	public static boolean hasLoggedIn() {
		if (CommonUtil.getSharedPreferences(ToolBoxApplication.getAppContext(), AppConstants.ALREADY_LOGGED, false)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 获取配置文件
	 * @return
	 */
	public static Properties getNetConfigProperties() {
		Properties props = new Properties();
		InputStream in = CommonUtil.class.getResourceAsStream("/appconfig.properties");
		try {
			props.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return props;
	}
	
	/**
	 * 获取appconfig.properties中参数
	 * @return
	 */
	public static String getApiUrl() {
		return getNetConfigProperties().getProperty("api_url");
	}
	
	public static void setApiUrl(String api_url) {
		CommonUtil.sharedPreferences(ToolBoxApplication.getAppContext(), "API_URL", "http://" + api_url + "/bookingonline_android");
	}
	
	public static String getEnterpriseId() {
//		String enterprise_id = CommonUtil.getSharedPreferences(ToolBoxApplication.getAppContext(), "ENTERPRISE_ID", "");
//		if(TextUtils.isEmpty(enterprise_id)) {
//			enterprise_id = getNetConfigProperties().getProperty("enterprise_id");
//			CommonUtil.sharedPreferences(ToolBoxApplication.getAppContext(), "ENTERPRISE_ID", enterprise_id);
//		}
//		return enterprise_id;
		return getNetConfigProperties().getProperty("enterprise_id");
	}

	public static void setEnterpriseId(String enterprise_id) {
		CommonUtil.sharedPreferences(ToolBoxApplication.getAppContext(), AppConstants.ENTERPRISE_ID, enterprise_id);
	}
	
	public static String getUserId() {
		return CommonUtil.getSharedPreferences(ToolBoxApplication.getAppContext(), AppConstants.USER_ID, "1fba6452a23a4f34a1551a5b503bf2ea");
	}
	
	public static String getUserPhoto() {
		return CommonUtil.getSharedPreferences(ToolBoxApplication.getAppContext(), AppConstants.USER_PHOTO, "http://img.hb.aicdn.com/fee9bfe3aeae774ab629a08d7b71ffae4de4c0631b32e-egmysH_fw658");
	}
	
	public static void setUserId(String userId) {
		CommonUtil.sharedPreferences(ToolBoxApplication.getAppContext(), AppConstants.USER_ID, userId);
	}
	
	public static String getLoginId() {
		return CommonUtil.getSharedPreferences(ToolBoxApplication.getAppContext(), AppConstants.LOGIN_ID, "");
	}
	
	public static void setLoginId(String loginId) {
		CommonUtil.sharedPreferences(ToolBoxApplication.getAppContext(), AppConstants.LOGIN_ID, loginId);
	}
	
	public static String getCityName() {
		return CommonUtil.getSharedPreferences(ToolBoxApplication.getAppContext(), AppConstants.CITY_NAME, "");
	}
	
	public static void setCityName(String cityName) {
		CommonUtil.sharedPreferences(ToolBoxApplication.getAppContext(), AppConstants.CITY_NAME, cityName);
	}
	
	public static String getLongitude() {
		return CommonUtil.getSharedPreferences(ToolBoxApplication.getAppContext(), "longitude", "");
	}
	
	public static void setLongitude(String longItude){
		CommonUtil.sharedPreferences(ToolBoxApplication.getAppContext(), "longitude", longItude);
	}
	
	public static String getLatitude() {
		return CommonUtil.getSharedPreferences(ToolBoxApplication.getAppContext(), "latitude", "");
	}
	
	public static void setLatitude(String latItude){
		CommonUtil.sharedPreferences(ToolBoxApplication.getAppContext(), "latitude", latItude);
	}
	
}

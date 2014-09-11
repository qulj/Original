package com.qlj.toolbox.api;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONObject;
import com.qlj.toolbox.util.CommonUtil;
import com.qlj.toolbox.util.Logger;
import com.qlj.toolbox.util.NetworkUtil;

import android.text.TextUtils;

/**
 * 接口 父类
 * @author qlj
 * @time 2014年9月11日下午3:21:48
 */
public class BaseApi {

	// 返回成功指令
	public final static String RET_SUCESS = "1";
	public final static int SUCESS = 1;
	public static final int SUCCESS_RET = 1;

	/**
	 * callback  请求
	 * @param cmd
	 * @param keys
	 * @param values
	 * @param listener
	 * @throws Exception
	 */
	public static void post(String cmd, String[] keys, String[] values, IRequestListener listener) throws Exception {
		String response = "";
		JSONObject jsonObject = null;

		if (!NetworkUtil.checkNetWork()) {
			listener.onNetworkBusy("请检查网络连接");
			return;
		}
		// **************************
		String param = "";
		for (int i = 0; i < values.length; i++) {
			param += keys[i] + "=" + values[i];
			if (i != values.length - 1)
				param += "&";
		}
		Logger.i("post params", CommonUtil.getApiUrl() + cmd + "?" + param);
		// **************************

		try {
			response = NetworkUtil.post(CommonUtil.getApiUrl() + cmd, keys, values);
			Logger.i(response);
		} catch (ConnectTimeoutException e) {
			listener.onNetworkBusy("连接超时！");
			e.printStackTrace();
		} catch (Exception e) {
			listener.onNetworkBusy("请求异常！");
			e.printStackTrace();
		}
		if (!TextUtils.isEmpty(response)) {
			jsonObject = new JSONObject(response);
			listener.onComplete(jsonObject, 1);
		}
	}

	/**
	 * 提交请求
	 * 
	 * @param cmd
	 * @param keys
	 * @param values
	 * @return
	 * @throws Exception
	 */
	public static JSONObject post(String cmd, String[] keys, String[] values) throws Exception {
		String response = "";
		JSONObject jsonObject = null;

		if (!NetworkUtil.checkNetWork()) {
			response = "{\"status\":0,\"msg\":\"请检查网络连接\"}";
			jsonObject = new JSONObject(response);
			return jsonObject;
		}

		String param = "";
		for (int i = 0; i < values.length; i++) {
			param += keys[i] + "=" + values[i];
			if (i != values.length - 1)
				param += "&";
		}
		Logger.i("post params", CommonUtil.getApiUrl() + cmd + "?" + param);

		try {
			response = NetworkUtil.post(CommonUtil.getApiUrl() + cmd, keys, values);
			Logger.i(response);
		} catch (ConnectTimeoutException e) {
			response = "{\"status\":0,\"msg\":\"连接超时\"}";
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!TextUtils.isEmpty(response)) {
			jsonObject = new JSONObject(response);
		}
		return jsonObject;
	}

}

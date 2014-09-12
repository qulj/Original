package com.qlj.toolbox.request;

import org.json.JSONException;
import org.json.JSONObject;

import com.qlj.toolbox.bean.User;
import com.qlj.toolbox.util.MJSONObject;

public class LoginRequest extends BaseRequest {

	public static final String LOGIN_CMD = "/customer/customerRegist.do";
	public static final String POST_LOCATION_CMD = "/user/reportPosition.do";

	public static void login(String telphone, String pass, String flag, final IResponseListener listener) {
		String[] keys = { "telphone", "pass", "flag", "regSource" };// 消费者注册来源类型
																	// 1：Android
																	// 2：微信
																	// 3:IOS
		String[] values = { telphone, pass, flag, "1" };

		try {
			post(LOGIN_CMD, keys, values, new IRequestListener() {

				@Override
				public void onNetworkBusy(String msg) {
					listener.doFail(-1, msg);
				}

				@Override
				public void onComplete(JSONObject response, int code) throws JSONException {
					if (response != null) {
						MJSONObject json = new MJSONObject(response);

						if (json.getInt("status") == SUCCESS_RET) {
							User user = new User();
							user.setUserID(json.getString("customerID"));
							user.setName(json.getString("name"));
							user.setTelphone(json.getString("telphone"));

							listener.doComplete(user, 1, json.getString("msg"));
						} else {
							listener.doFail(-1, json.getString("msg"));
						}
					}
				}
			});
		} catch (Exception e) {
			listener.doFail(-1, "Network busy!");
			e.printStackTrace();
		}
	}

	public static void postLocation(String userId, String latitude, String longitude, String addr, final IResponseListener listener) {
		String[] keys = { "userID", "latitude", "longitude", "addr", "app_flag" };// app_flag:APP标识
																					// 1:商家app，包括商家、商家职员可登陆
																					// 2:配送员app，配送员登陆
		String[] values = { userId, latitude, longitude, addr, "2" };

		try {
			post(POST_LOCATION_CMD, keys, values, new IRequestListener() {

				@Override
				public void onNetworkBusy(String msg) {
					listener.doFail(-1, msg);
				}

				@Override
				public void onComplete(JSONObject response, int code) throws JSONException {
					if (response != null) {
						MJSONObject json = new MJSONObject(response);

						if (json.getInt("status") == SUCCESS_RET) {
							listener.doComplete(null, 1, json.getString("msg"));
						} else {
							listener.doFail(-1, json.getString("msg"));
						}
					}
				}
			});
		} catch (Exception e) {
			listener.doFail(-1, "Network busy!");
			e.printStackTrace();
		}
	}
}

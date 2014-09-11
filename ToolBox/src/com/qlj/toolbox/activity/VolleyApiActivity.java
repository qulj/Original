package com.qlj.toolbox.activity;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.NoConnectionError;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.qlj.toolbox.R;
import com.qlj.toolbox.ToolBoxApplication;
import com.qlj.toolbox.util.CommonUtil;
import com.qlj.toolbox.util.Logger;
import com.qlj.toolbox.util.MD5;
import com.qlj.toolbox.util.NetworkUtil;
import com.qlj.toolbox.volley.MofingStringRequest;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 基于网络通信框架volley进行的请求
 * 
 * 优点：网络请求放在消息队列中统一管理 (推荐使用)
 * 
 * 
 * @author qlj
 * @time 2014年9月10日下午5:31:24
 */
public class VolleyApiActivity extends Activity {
	private Context context;
	private Button btn_login;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context = this;
		setContentView(R.layout.api_layout);

		initView();
	}

	// 初始化页面控件
	void initView() {
		btn_login = (Button) findViewById(R.id.btn_login);
		btn_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				login();
			}
		});
	}

	private void login() {
		final Message msg = new Message();
		/** 封装URL参数 */
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("telphone", "15889722562");
		hashMap.put("pass", MD5.MD5Encode("000000"));
		hashMap.put("flag", "1");
		hashMap.put("regSource", "1");

		MofingStringRequest mofingStringRequest = new MofingStringRequest(this, CommonUtil.getApiUrl() + "/customer/customerRegist.do?", hashMap, new Response.Listener<String>() {

			@Override
			public void onResponse(String response) {
				Logger.d("TAg", response);
				msg.what = 1;
				msg.obj = response;
				handler.sendMessage(msg);
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				Logger.e("TAg", error.getMessage());
				msg.what = 0;
				msg.obj = NetworkUtil.errorInfo(error);
				handler.sendMessage(msg);
			}
		});

		ToolBoxApplication.getRequestQueue().add(mofingStringRequest);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// 关闭该activity中所有 正在进行的请求
		ToolBoxApplication.getRequestQueue().cancelAll(this);
	}

	// 更新主UI
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			CommonUtil.toast(context, msg.obj.toString());

		}
	};
}

package com.qlj.toolbox.activity;

import com.android.volley.NoConnectionError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.qlj.toolbox.R;
import com.qlj.toolbox.ToolBoxApplication;
import com.qlj.toolbox.util.CommonUtil;
import com.qlj.toolbox.util.Logger;
import com.qlj.toolbox.util.MD5;
import com.qlj.toolbox.util.NetworkUtil;

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
 * 优点：网络请求放在消息队列中统一管理 缺点：要组转URL，不怎么方便，希望封装URL工具方法
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
		// 声明 请求对象
		StringRequest stringRequest = new StringRequest(NetworkUtil.jointURL("/customer/customerRegist.do", new String[] { "telphone", "pass", "flag", "regSource" }, new String[] { "15889722562", MD5.MD5Encode("000000"), "1", "1" }), new Response.Listener<String>() {

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
				boolean b = error.getClass().equals(NoConnectionError.class);
				Logger.e("TAg", error.getMessage());
				msg.what = 0;
				msg.obj = NetworkUtil.errorInfo(error);
				handler.sendMessage(msg);
			}
		});
		// 设置该request的tag，方便在退出该activity时取消正在请求的request，节省网络请求资源
		stringRequest.setTag(this);
		// 把请求加入到请求队列中，以便对请求进行资源配置
		ToolBoxApplication.getRequestQueue().add(stringRequest);
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

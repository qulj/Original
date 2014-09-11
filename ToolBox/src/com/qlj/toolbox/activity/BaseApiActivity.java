package com.qlj.toolbox.activity;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.android.volley.toolbox.NetworkImageView;
import com.qlj.toolbox.R;
import com.qlj.toolbox.ToolBoxApplication;
import com.qlj.toolbox.api.BaseApi;
import com.qlj.toolbox.api.ImplApi;
import com.qlj.toolbox.util.CommonUtil;
import com.qlj.toolbox.util.MD5;
import com.qlj.toolbox.util.MJSONObject;

/**
 * 最基本的网络请求，httpconnection
 * 
 * @author qlj
 * @time 2014年9月10日下午4:39:06
 */
public class BaseApiActivity extends Activity {

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
		// 新起线程，来进行网络请求 但new Thread 不方便管理，可以通过 java自带线程池{@link
		// ToolBoxApplication#getCachedExecutorService}进行管理；这种方式每次返回数据都要频繁取值，赋值，比较繁琐。
		new Thread() {
			@Override
			public void run() {
				try {
					JSONObject jsonObj = ImplApi.enterpriseLogin("15889722562", MD5.MD5Encode("000000"), "1");
					MJSONObject json = new MJSONObject(jsonObj);// CommonUtil.login());
					if (json != null) {
						int status = json.getInt("status");
						if (status == BaseApi.SUCESS) {
							Message msg = new Message();
							msg.what = 0;
							msg.obj = "登录成功！";
							handler.sendMessage(msg);
						} else {
							Message msg = new Message();
							msg.what = 0;
							msg.obj = json.getString("msg");
							handler.sendMessage(msg);
						}

					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			CommonUtil.toast(context, msg.obj.toString());

		}
	};

}

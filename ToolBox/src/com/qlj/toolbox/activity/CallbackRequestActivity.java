package com.qlj.toolbox.activity;

import com.qlj.toolbox.R;
import com.qlj.toolbox.request.IResponseListener;
import com.qlj.toolbox.request.LoginRequest;
import com.qlj.toolbox.util.CommonUtil;
import com.qlj.toolbox.util.MD5;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 通过Listener回掉请求，底层也是基于HttpConnection
 * 
 * 优点：对象都在api封装，避免view层多次调用重复代码
 * 
 * @author qlj
 * @time 2014年9月10日下午5:05:12
 */
public class CallbackRequestActivity extends Activity {
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
		// ToolBoxApplication#getCachedExecutorService}进行管理；这种方式每次返回数据api{@link
		// LoginApi#login}层就完成了对象赋值操作，减少代码冗余。
		new Thread() {
			public void run() {
				try {
					LoginRequest.login("15889722562", MD5.MD5Encode("000000"), "1", new IResponseListener() {

						@Override
						public void doComplete(Object data, int code, String msg) {
							Message message = new Message();
							message.what = 1;
							message.obj = data;
							handler.sendMessage(message);
						}

						@Override
						public void doFail(int code, String msg) {
							Message message = new Message();
							message.what = code;
							message.obj = msg;
							handler.sendMessage(message);
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
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

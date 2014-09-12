package com.qlj.toolbox.activity;

import org.json.JSONObject;

import com.qlj.toolbox.R;
import com.qlj.toolbox.ToolBoxApplication;
import com.qlj.toolbox.bean.User;
import com.qlj.toolbox.request.BaseRequest;
import com.qlj.toolbox.request.ImplRequest;
import com.qlj.toolbox.util.AppConstants;
import com.qlj.toolbox.util.CommonUtil;
import com.qlj.toolbox.util.MD5;
import com.qlj.toolbox.util.MJSONObject;
import com.qlj.toolbox.widget.LoadingDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * 登陆
 * 
 * @author qlj
 * @time 2014年9月11日下午5:18:40
 */
public class LoginActivity extends Activity implements OnClickListener {

	private static final String TAG = "LoginActivity";
	private Context context;

	private User user = new User();
	private EditText tv_username, tv_password;
	private Button btn_login, btn_forget_password;
	private Dialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ToolBoxApplication.getInstance().addActivity(this);
		context = this;
		setContentView(R.layout.login_layout);
		initView();

	}

	void initView() {
		tv_username = (EditText) findViewById(R.id.tv_username);
		tv_password = (EditText) findViewById(R.id.tv_password);
		btn_login = (Button) findViewById(R.id.btn_login);
		btn_login.setOnClickListener(this);
		btn_forget_password = (Button) findViewById(R.id.btn_forget_password);
		btn_forget_password.setOnClickListener(this);
	}

	private String username = "";
	private String password = "";

	boolean checkLogin() {
		username = tv_username.getText().toString();
		password = tv_password.getText().toString();
		if (TextUtils.isEmpty(username)) {
			CommonUtil.toast(context, "请输入用户名");
		} else if (TextUtils.isEmpty(password)) {
			CommonUtil.toast(context, "请输入密码");
		} else {
			return true;
		}

		return false;
	}

	private void login() {
		// 登录...
		if (checkLogin()) {
			if (dialog == null) {
				dialog = new LoadingDialog(context, R.style.CustomDialog);
			}
			dialog.show();

			new Thread() {
				@Override
				public void run() {
					try {
						JSONObject jsonObj = ImplRequest.enterpriseLogin(username, MD5.MD5Encode(password));
						MJSONObject json = new MJSONObject(jsonObj);// CommonUtil.login());
						if (json != null) {
							int status = json.getInt("status");
							if (status == BaseRequest.SUCESS) {
								user.setUserID(json.getString("userID"));
								user.setEnterpriseID(json.getString("enterpriseID"));
								user.setName(json.getString("name"));
								user.setTelphone(json.getString("telphone"));
								user.setUserType(json.getString("userType"));
								handler.sendEmptyMessage(1);
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
		} else {
			return;
		}
		Log.d("login info", "登录信息：" + username + " # " + password);
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			if (dialog.isShowing()) {
				dialog.dismiss();
			}
			if (msg.what == 1) {
				successLogin();
			}
			if (msg.what == 0) {
				CommonUtil.toast(context, msg.obj.toString());
			}

		}
	};

	void successLogin() {
		CommonUtil.sharedPreferences(getApplicationContext(), AppConstants.USER_ID, user.getUserID());
		CommonUtil.sharedPreferences(getApplicationContext(), AppConstants.ENTERPRISE_ID, user.getEnterpriseID());
		CommonUtil.sharedPreferences(getApplicationContext(), AppConstants.USER_NAME, user.getName());
		CommonUtil.sharedPreferences(getApplicationContext(), AppConstants.USER_TEL, user.getTelphone());
		CommonUtil.sharedPreferences(getApplicationContext(), AppConstants.ALREADY_LOGGED, true);

		Log.d("login info", "本地保存的登录信息：" + CommonUtil.getSharedPreferences(getApplicationContext(), AppConstants.USER_ID, "") + " # " + CommonUtil.getSharedPreferences(getApplicationContext(), AppConstants.USER_NAME, ""));

		CommonUtil.toast(context, "登录成功！");

		Intent intent = new Intent();
		intent.setClass(context, MainActivity.class);
		startActivity(intent);
		finish();
	}

	private long exitTime = 0;

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
			if (System.currentTimeMillis() - exitTime > 2000) {
				CommonUtil.toast(getApplicationContext(), "再按一次退出程序");
				exitTime = System.currentTimeMillis();
			} else {
				ToolBoxApplication.getInstance().exit();
			}
			return true;
		}
		return super.dispatchKeyEvent(event);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.btn_login:
			login();
			break;
		case R.id.btn_forget_password:// 忘记密码
			// intent.setClass(context, ForgetActivity.class);
			// startActivity(intent);
			break;
		default:
			break;
		}
	}

}

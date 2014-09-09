package com.qlj.toolbox.activity;

import com.qlj.toolbox.R;
import com.qlj.toolbox.util.AppConstants;
import com.qlj.toolbox.util.CommonUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

/**
 * 启动页
 * 
 * @author qlj
 * @time 2014年9月3日上午10:44:21
 */
public class Splash extends Activity {

	private Context context;
	private final int SPLASH_DISPLAY_LENGHT = 1000;// 展现时间

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		ImageView iv = new ImageView(context);
		iv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		iv.setScaleType(ScaleType.CENTER_CROP);
		iv.setImageResource(R.drawable.splash);

		setContentView(iv);

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				if (CommonUtil.getSharedPreferences(context, AppConstants.ALREADY_LOGGED, false)) {
					Intent i = new Intent(context, MainActivity.class);
					startActivity(i);
				} else {
					startActivity(new Intent(context, LoginActivity.class));
				}
				finish();
			}
		}, SPLASH_DISPLAY_LENGHT);
	}
}

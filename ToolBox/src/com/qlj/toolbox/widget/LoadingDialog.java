package com.qlj.toolbox.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.qlj.toolbox.R;

/**
 * 正在进行中的模式窗口
 * @author qlj
 * @time 2014年9月3日下午3:15:38
 */
public class LoadingDialog extends Dialog {

	private Context context;

	public LoadingDialog(Context context) {
		super(context);
		this.context = context;
	}

	public LoadingDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading_dialog);
	}
}

package com.qlj.toolbox.view;

import com.qlj.toolbox.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;

/**
 * 模式窗口 PopupWindow
 * @author Administrator
 *
 */
public class ExtPopupWindow extends PopupWindow {

	private View popupWindow;
	private View shadow;
	private boolean hasShadow = true;// 是否显示遮罩层
	private Animation animation;// 遮罩层渐隐渐现动画

	public ExtPopupWindow(Context context) {
		super(context);
	}

	public ExtPopupWindow(Context context, View view) {
		this(context, view, true);
	}

	public ExtPopupWindow(Context context, View view, boolean hasShadow) {
		super(context);
		this.popupWindow = view;
		this.hasShadow = hasShadow;

		if (hasShadow) {
			shadow = new View(context);
			shadow.setBackgroundColor(context.getResources().getColor(R.color.alpha_black));

			animation = AnimationUtils.loadAnimation(context, R.anim.gradually);
			shadow.setAnimation(animation);

			((Activity) context).addContentView(shadow, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		}

		this.setContentView(popupWindow);
		this.setWidth(LayoutParams.MATCH_PARENT);
		this.setHeight(LayoutParams.WRAP_CONTENT);
		this.setFocusable(true);
		this.setAnimationStyle(R.style.DialogAnimation);

		ColorDrawable dw = new ColorDrawable(Color.TRANSPARENT);
		this.setBackgroundDrawable(dw);

		// popupWindow添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
		popupWindow.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				int height = popupWindow.getTop();
				int y = (int) event.getY();
				if (event.getAction() == MotionEvent.ACTION_UP) {
					if (y < height) {
						dismiss();
					}
				}
				return true;
			}
		});
	}

	public ExtPopupWindow(Context context, View view, int i) {
		super(context);
		this.popupWindow = view;

		// if (hasShadow) {
		shadow = new View(context);
		shadow.setBackgroundColor(context.getResources().getColor(R.color.alpha_black));

		animation = AnimationUtils.loadAnimation(context, R.anim.gradually);
		shadow.setAnimation(animation);

		((Activity) context).addContentView(shadow, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		// }

		this.setContentView(popupWindow);
		this.setWidth(LayoutParams.WRAP_CONTENT);
		this.setHeight(LayoutParams.WRAP_CONTENT);
		this.setFocusable(true);
		this.setAnimationStyle(R.style.DialogAnimation);

		ColorDrawable dw = new ColorDrawable(Color.TRANSPARENT);
		this.setBackgroundDrawable(dw);

		// popupWindow添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
		popupWindow.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				int height = popupWindow.getTop();
				int y = (int) event.getY();
				if (event.getAction() == MotionEvent.ACTION_UP) {
					if (y < height) {
						dismiss();
					}
				}
				return true;
			}
		});
	}

	@Override
	public void dismiss() {
		if (hasShadow)
			dismissShadow();
		super.dismiss();
	}

	@Override
	public void showAtLocation(View parent, int gravity, int x, int y) {
		if (hasShadow)
			showShadow();
		super.showAtLocation(parent, gravity, x, y);
	}

	public void showShadow() {
		shadow.setVisibility(View.VISIBLE);
		shadow.startAnimation(animation);
	}

	public void dismissShadow() {
		shadow.setVisibility(View.GONE);
	}
}

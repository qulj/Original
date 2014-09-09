package com.qlj.toolbox.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

/**
 * 重写button,来拦截litview中相应父类item的点击
 * 
 * @author Administrator
 * 
 */
public class FixedListButton extends Button {

	public FixedListButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public FixedListButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public FixedListButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setPressed(boolean pressed) {
		// TODO Auto-generated method stub
		if (pressed && getParent() instanceof View && ((View) getParent()).isPressed()) {
			return;
		}
		super.setPressed(pressed);
	}

}

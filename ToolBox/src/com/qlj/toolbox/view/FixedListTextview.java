package com.qlj.toolbox.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * 重写TextView,来拦截litview中相应父类item的点击
 * 
 * @author Administrator
 * 
 */
public class FixedListTextview extends TextView {

	public FixedListTextview(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public FixedListTextview(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public FixedListTextview(Context context) {
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

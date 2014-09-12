package com.qlj.toolbox.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.GridView;

/**
 * 不滚动的GridView
 * @author Administrator
 *
 */
public class MyGridView extends GridView {

    public MyGridView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
    }

    public MyGridView(Context context, AttributeSet attrs) {
            super(context, attrs);
    }

    public MyGridView(Context context) {
            super(context);
    }

    /**
     * 设置不滚动
     */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                            MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, expandSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
            Log.d("MyGridView", "onTouchEvent");
            return super.onTouchEvent(ev);
    }

}

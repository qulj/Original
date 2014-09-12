package com.qlj.toolbox.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 自定义控件解决ListView和ScrollView冲突 ,对于GridView来说，仍然存在同样的问题。
	  一种简单的方法，主要是通过手动计算Listview的高度。
	   参见:http://www.67tgb.com/?p=470
	  但是存在一个问题就是，当删除ListView中的一个条目后，ListView不会自动计算自身的高度，这让人感觉不舒服。当然仅作数据展示，上篇文章介绍的方法是最简单可行的。 
	  想要ListView自动计算高度其实也很简单，我们只要继承ListView控件，重写onMeasure方法即可，代码如下
 */
public class ListViewExt extends ListView {

	public ListViewExt(Context context) {
		super(context);
	}
	
	public ListViewExt(Context context,AttributeSet attrs) {
		 super(context, attrs);
	}
	
	public ListViewExt(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/*解释一下MeasureSpec这个类:
	  * 一个MeasureSpec封装了父布局传递给子布局的布局要求，每个MeasureSpec代表了一组宽度和高度的要求。一个MeasureSpec由大小和模式组成。它有三种模式：UNSPECIFIED(未指定),父元素部队自元素施加任何束缚，子元素可以得到任意想要的大小；EXACTLY(完全)，父元素决定自元素的确切大小，子元素将被限定在给定的边界里而忽略它本身大小；AT_MOST(至多)，子元素至多达到指定大小的值。
		  它常用的三个函数：
		1.static int getMode(int measureSpec):根据提供的测量值(格式)提取模式(上述三个模式之一)
		2.static int getSize(int measureSpec):根据提供的测量值(格式)提取大小值(这个大小也就是我们通常所说的大小)
		3.static int makeMeasureSpec(int size,int mode):根据提供的大小值和模式创建一个测量值(格式)
		  makeMeasureSpec(int size, int mode)  这个方法的主要作用就是根据你提供的大小和模式，返回你想要的大小值.
	  */
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		
		//根据模式计算每个child的高度和宽度
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}

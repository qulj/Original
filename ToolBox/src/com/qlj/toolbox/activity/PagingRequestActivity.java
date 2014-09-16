package com.qlj.toolbox.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.NoConnectionError;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.qlj.toolbox.R;
import com.qlj.toolbox.ToolBoxApplication;
import com.qlj.toolbox.bean.Follow;
import com.qlj.toolbox.parsing.volley.RonJsonObjectRequest;
import com.qlj.toolbox.util.CommonUtil;
import com.qlj.toolbox.util.DateUtil;
import com.qlj.toolbox.util.Logger;
import com.qlj.toolbox.util.NetworkUtil;
import com.qlj.toolbox.widget.pulltorefresh.ExtListView;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 基于网络通信框架volley进行的分页请求
 * 
 * @author qlj
 * @time 2014年9月15日上午10:58:20
 */
public class PagingRequestActivity extends Activity {

	private Context context;
	private int currPage = 1;
	private int pageSize = 10;

	private ExtListView listView;
	private TextView tv_empty;
	private List<Follow> listData;
	private ListAdapter adapter;

	protected ImageLoader imageLoader = ImageLoader.getInstance();
	protected DisplayImageOptions optionsOfCircular;

	private final Gson gson = new Gson();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context = this;
		setContentView(R.layout.paging_layout);

		initView();
	}

	// 初始化页面控件
	void initView() {
		optionsOfCircular = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.picker_default_bg_circle).showImageForEmptyUri(R.drawable.picker_default_bg_circle).showImageOnFail(R.drawable.picker_default_bg_circle).cacheInMemory(true).cacheOnDisk(true).considerExifParams(true).displayer(new RoundedBitmapDisplayer(CommonUtil.dipToPx(context, 48) / 2)).build();

		listView = (ExtListView) findViewById(R.id.list_view);
		tv_empty = (TextView) findViewById(R.id.tv_empty);

		listData = new ArrayList<Follow>();
		adapter = new ListAdapter(context);
		listView.setAdapter(adapter);

		listView.setPullLoadEnable(false);
		listView.setListViewListener(new ExtListView.ExtListViewListener() {

			@Override
			public void onRefresh() {// 下拉刷新
				currPage = 1;
				getFollows();
			}

			@Override
			public void onLoadMore() {// 加载更多
				currPage++;
				getFollows();
			}
		});

		listView.startLoad();// 开始加载

		/** 点击提示 继续加载 */
		tv_empty.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				listView.startLoad();
			}
		});
	}

	private void finishRefresh() {
		listView.stopRefresh();
		listView.stopLoadMore();
	}

	/**
	 * 获取列表数据
	 * 
	 * http://www.dian100.me:18080/bookingonline_android/focus/getMyFocus.do?
	 * customerID
	 * =19885e63196342459e2395daa3d7ba13&enterpriseNameOrNo=&currentPage
	 * =1&pageSize=10
	 */
	private void getFollows() {
		final Message msg = new Message();
		/** 封装URL参数 */
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("customerID", "19885e63196342459e2395daa3d7ba13");
		hashMap.put("enterpriseNameOrNo", "");
		hashMap.put("currentPage", String.valueOf(currPage));
		hashMap.put("pageSize", "10");

		/** jsonObject请求 */
		RonJsonObjectRequest mofingStringRequest = new RonJsonObjectRequest(this, CommonUtil.getApiUrl() + "/focus/getMyFocus.do?", hashMap, new Response.Listener<JSONObject>() {

			@SuppressWarnings("unchecked")
			@Override
			public void onResponse(JSONObject response) {
				// TODO Auto-generated method stub
				Logger.d("TAg", response.toString());
				try {
					/** 成功 */
					if (response.getString("status").equals("1")) {
						msg.obj = (List<Follow>) gson.fromJson(response.getString("enterpriseList"), new TypeToken<List<Follow>>() {
						}.getType());
						msg.what = 1;
						/** 异常 */
					} else if (response.getString("status").equals("0")) {
						msg.obj = response.getString("msg");
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				handler.sendMessage(msg);
			}

		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				Logger.e("TAg", error.getMessage());
				msg.what = 0;
				msg.obj = NetworkUtil.errorInfo(error);
				handler.sendMessage(msg);
			}
		});
		ToolBoxApplication.getRequestQueue().add(mofingStringRequest);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// 关闭该activity中所有 正在进行的请求
		ToolBoxApplication.getRequestQueue().cancelAll(this);
	}

	/** 适配器 */
	public class ListAdapter extends BaseAdapter {

		private Context context;
		private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

		public ListAdapter(Context context) {
			this.context = context;
		}

		public int getCount() {
			return listData.size();
		}

		public Object getItem(int position) {
			return listData.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(final int position, View convertView, final ViewGroup parent) {
			final ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(context).inflate(R.layout.main_item, null);
				holder.iv_pic = (ImageView) convertView.findViewById(R.id.iv_pic);
				holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
				holder.tv_intro = (TextView) convertView.findViewById(R.id.tv_intro);
				holder.tv_intro_time = (TextView) convertView.findViewById(R.id.tv_intro_time);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			Follow follow = listData.get(position);

			holder.tv_name.setText(follow.getEnterpriseName());
			holder.tv_intro.setText(follow.getNewMsgTitle());
			holder.tv_intro_time.setText(DateUtil.fromToday(follow.getNewMsgTime()));

			imageLoader.displayImage(follow.getEnterpriseImg(), holder.iv_pic, optionsOfCircular, animateFirstListener);

			return convertView;
		}

	}

	static class ViewHolder {
		private ImageView iv_pic;
		private TextView tv_name;
		private TextView tv_intro, tv_intro_time;
	}

	private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}

	// 更新主UI
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				finishRefresh();
				updateUI(msg.obj);
				break;
			case 0:
				CommonUtil.toast(context, msg.obj.toString());
				break;
			default:
				break;
			}

		}
	};

	/**
	 * 分页中更新ui界面
	 * 
	 * @param obj
	 */
	private void updateUI(Object obj) {

		if (currPage == 1) {

			// 更新数据
			listData.clear();
			if (obj != null) {
				listData = (ArrayList<Follow>) obj;
			}

			if (listData != null && listData.size() > 0) {

				if (listData.size() == pageSize) {
					listView.setPullLoadEnable(true);
				} else {
					listView.setPullLoadEnable(false);
				}
				adapter.notifyDataSetChanged();
				listView.setVisibility(View.VISIBLE);
				tv_empty.setVisibility(View.GONE);
			} else {
				// listView.setEmptyView(tv_empty);
				listView.setVisibility(View.GONE);
				tv_empty.setVisibility(View.VISIBLE);
			}
		} else {
			if (obj != null && ((ArrayList<Follow>) obj).size() > 0) {
				if (((ArrayList<Follow>) obj).size() == pageSize) {
					listView.setPullLoadEnable(true);
				} else {
					listView.setPullLoadEnable(false);
				}
				listData.addAll((ArrayList<Follow>) obj);
				adapter.notifyDataSetChanged();
			} else {
				listView.setPullLoadEnable(false);
			}
		}
	}
}

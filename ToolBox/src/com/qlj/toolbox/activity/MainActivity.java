package com.qlj.toolbox.activity;

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpResponse;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.qlj.toolbox.R;
import com.qlj.toolbox.ToolBoxApplication;
import com.qlj.toolbox.util.BitmapCache;
import com.qlj.toolbox.util.Logger;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.net.http.AndroidHttpClient;
import android.os.Bundle;
import android.util.LruCache;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener {

	NetworkImageView img_view;

	ImageLoader imageLoader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		findViewById(R.id.btn_str_req).setOnClickListener(this);
		findViewById(R.id.btn_show_img).setOnClickListener(this);

	}

	void stringRequest() {
		StringRequest stringRequest = new StringRequest("http://www.baidu.com", new Response.Listener<String>() {

			@Override
			public void onResponse(String response) {
				Logger.d("TAg", response);
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				Logger.e("TAg", error.getMessage());
			}
		});
		stringRequest.setTag(this);
		ToolBoxApplication.getRequestQueue().add(stringRequest);
		ToolBoxApplication.getRequestQueue().cancelAll(this);
	}

	void showImg() {
		imageLoader = new ImageLoader(ToolBoxApplication.getRequestQueue(), new BitmapCache());
		img_view = (NetworkImageView) findViewById(R.id.img_view);
		img_view.setDefaultImageResId(R.drawable.ic_launcher);
		img_view.setErrorImageResId(R.drawable.btn_clear_text);
		img_view.setImageUrl("http://img.my.csdn.net/uploads/201404/13/1397393290_5765.jpeg", imageLoader);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_str_req:
			stringRequest();
			break;
		case R.id.btn_show_img:
			showImg();
			break;
		default:
			break;
		}
	}

}

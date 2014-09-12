package com.qlj.toolbox.parsing.volley;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;

/**
 * 动态的JSONObject 请求
 * 
 * @author qlj
 * @time 2014年9月11日下午4:49:29
 */
public class RonJsonObjectRequest extends RonRequest<JSONObject> {

	/**
	 * 构造函数
	 * 
	 * @param method
	 *            Method.GET or Method.POST
	 * @param url
	 *            请求链接
	 * @param params
	 *            参数集合
	 * @param listener
	 *            监听
	 * @param errorListener
	 *            错误监听
	 */
	public RonJsonObjectRequest(int method, String url, Map<String, String> params, Listener<JSONObject> listener, ErrorListener errorListener) {
		super(url, params, listener, errorListener);
	}

	/**
	 * 构造函数
	 * 
	 * @param context
	 *            请求组件
	 * @param url
	 *            请求链接
	 * @param params
	 *            参数集合
	 * @param listener
	 *            监听
	 * @param errorListener
	 *            错误监听
	 */
	public RonJsonObjectRequest(Context context, String url, Map<String, String> params, Listener<JSONObject> listener, ErrorListener errorListener) {
		this(params == null ? Method.GET : Method.POST, url, params, listener, errorListener);
		/** 设置请求标签，方便取消请求 */
		setTag(context);
	}

	@Override
	protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
		try {
			String str = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
			return Response.success(new JSONObject(str), HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			return Response.error(new ParseError(e));
		} catch (JSONException je) {
			return Response.error(new ParseError(je));
		}
	}

}

package com.qlj.toolbox.parsing.volley;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import android.content.Context;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * Google Gson 请求处理类
 * 
 * @author qlj
 * @time 2014年9月12日上午10:52:32
 * @param <T>
 *            Gson要转换的类型
 */
public class RonGsonObjectRequest<T> extends RonRequest<T> {

	/** Gson对象 */
	private Gson gson = new Gson();
	/** Gson对象要转换的类型 */
	private Class<T> clazz;

	/**
	 * 构造函数
	 * 
	 * @param method
	 *            GET or POST
	 * @param url
	 *            请求连接
	 * @param params
	 *            请求连接参数
	 * @param listener
	 *            请求监听
	 * @param errorListener
	 *            异常监听
	 */
	public RonGsonObjectRequest(int method, String url, Map<String, String> params, Listener<T> listener, ErrorListener errorListener) {
		super(url, params, listener, errorListener);
	}

	/**
	 * 构造函数
	 * 
	 * @param context
	 *            绑定的控件名称
	 * @param url
	 *            请求谅解
	 * @param clazz
	 *            Gson要解析成的类型
	 * @param params
	 *            请求参数
	 * @param listener
	 *            请求监听
	 * @param errorListener
	 *            异常监听
	 */
	public RonGsonObjectRequest(Context context, String url, Class<T> clazz, Map<String, String> params, Listener<T> listener, ErrorListener errorListener) {
		this(params == null ? Method.GET : Method.POST, url, params, listener, errorListener);
		this.clazz = clazz;
		/** 设置请求标签，方便取消请求 */
		setTag(context);
	}

	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		// TODO Auto-generated method stub
		try {
			String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
			return Response.success(gson.fromJson(json, clazz), HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			return Response.error(new ParseError(e));
		} catch (JsonSyntaxException e) {
			return Response.error(new ParseError(e));
		}
	}

}

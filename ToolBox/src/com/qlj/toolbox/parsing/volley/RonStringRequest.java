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

/**
 * 动态String请求
 * 
 * @author qlj
 * @time 2014年9月11日下午4:53:18
 */
public class RonStringRequest extends RonRequest<String> {

	public RonStringRequest(int method, String url, Map<String, String> params, Listener<String> listener, ErrorListener errorListener) {
		super(url, params, listener, errorListener);
	}

	public RonStringRequest(Context context, String url, Map<String, String> params, Listener<String> listener, ErrorListener errorListener) {
		this(params == null ? Method.GET : Method.POST, url, params, listener, errorListener);
		/** 设置请求标签，方便取消请求 */
		setTag(context);
	}

	@Override
	protected Response<String> parseNetworkResponse(NetworkResponse response) {
		try {
			String str = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
			return Response.success(str, HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			return Response.error(new ParseError(e));
		}
	}

}

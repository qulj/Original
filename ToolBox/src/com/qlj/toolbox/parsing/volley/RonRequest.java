package com.qlj.toolbox.parsing.volley;

import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;

/**
 * 父抽象类
 * 
 * @author qlj
 * @time 2014年9月11日下午4:58:00
 * @param <T>
 */
public abstract class RonRequest<T> extends Request<T> {

	/** 请求参数 */
	protected final Map<String, String> mParams;
	/** 监听 */
	private final Listener<T> mListener;

	public RonRequest(String url, Map<String, String> params, Listener<T> listener, ErrorListener errorListener) {
		this(Method.DEPRECATED_GET_OR_POST, url, params, listener, errorListener);
	}

	public RonRequest(int method, String url, Map<String, String> params, Listener<T> listener, ErrorListener errorListener) {
		super(method, url, errorListener);
		mListener = listener;
		mParams = params;
	}

	@Override
	protected void deliverResponse(T response) {
		mListener.onResponse(response);
	}

	@Override
	abstract protected Response<T> parseNetworkResponse(NetworkResponse response);

	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		// TODO Auto-generated method stub
		return mParams;
	}
}

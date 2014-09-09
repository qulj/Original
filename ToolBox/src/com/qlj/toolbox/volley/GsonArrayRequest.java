package com.qlj.toolbox.volley;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class GsonArrayRequest<T> extends Request<List<T>> {

	private final Gson gson = new Gson();
	private final Class<T> clazz;
	private final Map<String, String> headers;
	private final Listener<List<T>> listener;

	public GsonArrayRequest(String url, Class<T> clazz, Map<String, String> headers, Listener<List<T>> listener, ErrorListener errorListener) {
		super(Method.GET, url, errorListener);
		this.clazz = clazz;
		this.headers = headers;
		this.listener = listener;
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		// TODO Auto-generated method stub
		return headers != null ? headers : super.getHeaders();
	}

	@Override
	protected void deliverResponse(List<T> response) {
		// TODO Auto-generated method stub
		listener.onResponse(response);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Response<List<T>> parseNetworkResponse(NetworkResponse response) {
		try {
			String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
			Type type = new TypeToken<List<T>>() {}.getType();
			return Response.success((List<T>) gson.fromJson(json, type), HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			return Response.error(new ParseError(e));
		} catch (JsonSyntaxException e) {
			return Response.error(new ParseError(e));
		}
	}

}

package com.qlj.toolbox.volley;

import android.content.Context;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;

public class MStringRequest extends StringRequest {

	public MStringRequest(Context context, int method, String url, Listener<String> listener, ErrorListener errorListener) {
		super(method, url, listener, errorListener);
		setTag(context);
	}

}

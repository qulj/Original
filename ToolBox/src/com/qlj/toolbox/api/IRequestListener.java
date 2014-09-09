package com.qlj.toolbox.api;

import org.json.JSONException;
import org.json.JSONObject;

public interface IRequestListener {

	public void onComplete(JSONObject response, int code) throws JSONException;
	
	public void onNetworkBusy(String msg);
	
}

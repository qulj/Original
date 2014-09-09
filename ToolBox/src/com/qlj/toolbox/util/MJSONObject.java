package com.qlj.toolbox.util;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * json对象类
 * @author qlj
 * @time 2014年9月1日上午9:57:16
 */
public class MJSONObject {

	JSONObject mJsonObject;

	public MJSONObject(String str) throws JSONException {
		mJsonObject = new JSONObject(str);
	}

	public MJSONObject(JSONObject jobject) {
		mJsonObject = jobject;
	}

	public Object get(String name) {
		if (mJsonObject.has(name)) {
			try {
				return mJsonObject.get(name);
			} catch (JSONException e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
	}

	public int getInt(String name) {
		if (mJsonObject.has(name)) {
			try {
				return mJsonObject.getInt(name);
			} catch (JSONException e) {
				e.printStackTrace();
				return 0;
			}
		} else {
			return 0;
		}
	}

	public long getLong(String name) {
		if (mJsonObject.has(name)) {
			try {
				return mJsonObject.getLong(name);
			} catch (JSONException e) {
				e.printStackTrace();
				return 0;
			}
		} else {
			return 0;
		}
	}

	public double getDouble(String name) {
		if (mJsonObject.has(name)) {
			try {
				return mJsonObject.getDouble(name);
			} catch (JSONException e) {
				e.printStackTrace();
				return 0;
			}
		} else {
			return 0;
		}
	}

	public boolean getBoolean(String name) {
		if (mJsonObject.has(name)) {
			try {
				return mJsonObject.getBoolean(name);
			} catch (JSONException e) {
				e.printStackTrace();
				return false;
			}
		} else {
			return false;
		}
	}

	public String getString(String name) {
		if (mJsonObject.has(name)) {
			try {
				return mJsonObject.getString(name);
			} catch (JSONException e) {
				e.printStackTrace();
				return "";
			}
		} else {
			return "";
		}
	}

	public MJSONArray getJSONArray(String name) {
		MJSONArray jsonArray = null;
		try {
			if (mJsonObject.has(name)) {
				jsonArray = new MJSONArray(mJsonObject.getJSONArray(name));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonArray;
	}

}

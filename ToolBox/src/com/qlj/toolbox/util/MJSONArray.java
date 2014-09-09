package com.qlj.toolbox.util;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * json集合类
 * @author qlj
 * @time 2014年9月1日上午9:57:28
 */
public class MJSONArray {
    JSONArray mJsonArray;


    public MJSONArray(JSONArray array) {
        mJsonArray = array;
    }


    public Object opt(int index) {
        JSONObject object = (JSONObject) mJsonArray.opt(index);
        MJSONObject jsonObject = new MJSONObject(object);
        return jsonObject;
    }


    public int length() {
        return mJsonArray.length();
    }

}

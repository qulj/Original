package com.qlj.toolbox.api;

public interface IResponseListener {

	public void doComplete(Object data, int code, String msg);
	
	public void doFail(int code, String msg);
	
}

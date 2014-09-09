package com.qlj.toolbox;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.Volley;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.http.AndroidHttpClient;

/**
 * 全局Application
 * 
 * @author qlj
 * @time 2014年9月1日上午11:01:29
 */
public class ToolBoxApplication extends Application {

	private static ToolBoxApplication instance;
	private static Context appContext;
	/*
	 * activity组件集合
	 */
	private List<Activity> activityList = new LinkedList<Activity>();
	/*
	 * 线程池对象
	 */
	private static ExecutorService cachedThreadPool;

	/**
	 * volley请求队列
	 */
	private static RequestQueue requestQueue;

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		appContext = this.getApplicationContext();
	}

	/**
	 * 全局Application
	 * 
	 * @return
	 */
	public static ToolBoxApplication getInstance() {
		return instance == null ? instance = new ToolBoxApplication() : instance;
	}

	/**
	 * 全局Context
	 * 
	 * @return
	 */
	public static Context getAppContext() {
		return appContext;
	}

	/**
	 * 添加activity组件到 list中
	 * 
	 * @param activity
	 */
	public void addActivity(Activity activity) {
		activityList.add(activity);
	}

	/**
	 * 获取消息队列
	 * 
	 * @return
	 */
	public static RequestQueue getRequestQueue() {
		return requestQueue == null ? requestQueue = Volley.newRequestQueue(appContext, new HttpClientStack(AndroidHttpClient.newInstance("qu"))) : requestQueue;
	}

	/**
	 * 线程池---创建一个无长度的线程池
	 * 
	 * @return
	 */
	public static ExecutorService getCachedExecutorService() {
		return cachedThreadPool == null ? cachedThreadPool = Executors.newCachedThreadPool() : cachedThreadPool;
	}

	/**
	 * 固定线程数的线程池
	 * 
	 * @return
	 */
	public static ExecutorService getFixedExecutorService() {
		return cachedThreadPool == null ? cachedThreadPool = Executors.newFixedThreadPool(5) : cachedThreadPool;
	}

	/**
	 * 创建一个使用单个 worker 线程的 Executor
	 * 
	 * @return
	 */
	public static ExecutorService getSingleExecutorService() {
		return cachedThreadPool == null ? cachedThreadPool = Executors.newSingleThreadExecutor() : cachedThreadPool;
	}

	/**
	 * 退出时finish所有activity
	 */
	public void exit() {
		try {
			for (Activity activity : activityList) {
				if (activity != null) {
					activity.finish();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}

	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
		System.gc();
	}
}

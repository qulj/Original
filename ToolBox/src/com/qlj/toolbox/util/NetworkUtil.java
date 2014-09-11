package com.qlj.toolbox.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.qlj.toolbox.ToolBoxApplication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络连接工具类
 * 
 * @author qlj
 * @time 2014年9月11日下午3:25:02
 */
public class NetworkUtil {

	private final static String TAG = "NetworkUtils";

	static Context mContext;

	public NetworkUtil(Context context) {
		mContext = context;
	}

	/**
	 * 检测网络连接
	 * 
	 * @param context
	 * @return 有网络连接返回true,否则false
	 */
	public static boolean checkNetWork() {
		boolean flag = false;
		try {
			ConnectivityManager connectivity = (ConnectivityManager) ToolBoxApplication.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity != null) {
				NetworkInfo info = connectivity.getActiveNetworkInfo();
				if (info != null && info.isConnected()) {
					if (info.getState() == NetworkInfo.State.CONNECTED) {
						flag = true;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 检测网络连接
	 * 
	 * @param context
	 * @return 有网络连接返回true,否则false
	 */
	public static boolean checkNetWork(Context context) {
		boolean flag = false;
		try {
			ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity != null) {
				NetworkInfo info = connectivity.getActiveNetworkInfo();
				if (info != null && info.isConnected()) {
					if (info.getState() == NetworkInfo.State.CONNECTED) {
						flag = true;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 监听 异常类型
	 * 
	 * @param error
	 *            volley中请求错误信息
	 * @return
	 */
	public static String errorInfo(VolleyError error) {
		String str = "";
		if (error.getClass().equals(NoConnectionError.class)) {
			str = "连接异常，请检查网络。";
		} else if (error.getClass().equals(NetworkError.class)) {
			str = "网络异常，请检查网络。";
		} else if (error.getClass().equals(TimeoutError.class)) {
			str = "请求超时，请检查网络。";
		} else if (error.getClass().equals(NetworkError.class)) {
			str = "网络异常，请检查网络。";
		}
		return str;
	}

	/**
	 * volley中拼接url
	 * 
	 * @param cmd
	 *            命令 .do
	 * @param keys
	 *            键数组
	 * @param values
	 *            值数组
	 * @return
	 */
	public static String jointURL(String cmd, String[] keys, String[] values) {
		StringBuffer url = new StringBuffer();
		StringBuffer param = new StringBuffer();
		for (int i = 0; i < values.length; i++) {
			param.append(keys[i]);
			param.append("=");
			param.append(values[i]);
			if (i != values.length - 1)
				param.append("&");
		}
		Logger.i("post params", CommonUtil.getApiUrl() + cmd + "?" + param.toString());
		url.append(CommonUtil.getApiUrl());
		url.append(cmd);
		url.append("?");
		url.append(param);

		return url.toString();
	}

	/**
	 * 基于 HttpClient 的请求（android的原始请求方式） 多参数，多值
	 * 
	 * @param uri
	 * @param key
	 * @param value
	 * @return
	 * @throws ClientProtocolException
	 * @throws ConnectException
	 * @throws IOException
	 * @throws Exception
	 */
	public static String post(String uri, String[] key, String[] value) throws ClientProtocolException, ConnectException, IOException, Exception {
		String strResult = null;
		HttpPost request = new HttpPost(uri);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		for (int i = 0; i < value.length; i++) {
			params.add(new BasicNameValuePair(key[i], value[i]));
		}
		request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 30000);
		HttpConnectionParams.setSoTimeout(httpParams, 30000);
		HttpClient client = new DefaultHttpClient(httpParams);
		HttpResponse response = client.execute(request);
		if (response.getStatusLine().getStatusCode() == 200) {
			strResult = EntityUtils.toString(response.getEntity());
		} else {
			strResult = String.valueOf(response.getStatusLine().getStatusCode());
		}
		return strResult;

	}

	/**
	 * 基于 HttpClient 的请求（android的原始请求方式）
	 * 
	 * 单参数 单值
	 * 
	 * @param uri
	 * @param value
	 * @return
	 * @throws ConnectTimeoutException
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws Exception
	 */
	public static String post(String uri, String value) throws ConnectTimeoutException, ClientProtocolException, IOException, Exception {
		String strResult = null;
		HttpPost request = new HttpPost(uri);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("key", value));

		request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 45000);
		HttpConnectionParams.setSoTimeout(httpParams, 30000);
		HttpClient client = new DefaultHttpClient(httpParams);
		HttpResponse response = client.execute(request);
		if (response.getStatusLine().getStatusCode() == 200) {
			strResult = EntityUtils.toString(response.getEntity());
		}
		return strResult;

	}

	private static byte[] getBytes(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] b = new byte[1024];
		int len = 0;
		while ((len = is.read(b, 0, 1024)) != -1) {
			baos.write(b, 0, len);
			baos.flush();
		}
		byte[] bytes = baos.toByteArray();
		return bytes;
	}

	public static byte[] getBytesFromServer(String urlStr) throws Exception {
		InputStream in = null;
		try {
			in = new java.net.URL(urlStr).openStream();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return getBytes(in);
	}

	public static String upload(String actionUrl, File targetFile, String customerID) throws IOException {
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(actionUrl);
		MultipartEntity entity = new MultipartEntity();
		try {
			entity.addPart("name", new StringBody(targetFile.getName()));
			entity.addPart("file", new FileBody(targetFile));
			entity.addPart("customerID", new StringBody(customerID));
			post.setEntity(entity);
			HttpResponse response = client.execute(post);
			Logger.d("HttpStatus: " + response.getStatusLine().getStatusCode());
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String result = EntityUtils.toString(response.getEntity());
				Logger.d("result url: " + result);
				return result;
			}
		} finally {
			entity.consumeContent();
			client.getConnectionManager().shutdown();
		}
		return null;
	}

	/**
	 * 下载网络 资源
	 * 
	 * @param urlStr
	 * @param path
	 * @param fileName
	 * @return -1:文件下载出错 0:文件下载成功 1:文件已经存在
	 */
	public static int downloadDataFile(String urlStr, String path, String fileName) {
		InputStream inputStream = null;
		try {
			FileUtil fileUtils = new FileUtil();

			if (fileUtils.isFileExist(path + fileName)) {
				return 1;
			} else {
				inputStream = getInputStreamFromURL(urlStr);
				if (inputStream == null) {
					return -1;
				}

				File resultFile = fileUtils.writeFromInput(path, fileName, inputStream);
				if (resultFile == null) {
					return -1;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	/**
	 * 根据URL得到输入流
	 * 
	 * @param urlStr
	 * @return
	 */
	public static InputStream getInputStreamFromURL(String urlStr) {
		HttpURLConnection urlConn = null;
		InputStream inputStream = null;
		try {
			URL url = new URL(urlStr);
			urlConn = (HttpURLConnection) url.openConnection();
			urlConn.setConnectTimeout(15000);
			inputStream = urlConn.getInputStream();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return inputStream;
	}
}

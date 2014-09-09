package com.qlj.toolbox.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.http.util.EncodingUtils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;

public class Until {
	public static final String DATETIME = "yyyyMMddHHmmss";

	public static boolean StrIsNull(String str) {
		if (str == null || str.equals("")) {
			return true;
		}
		return false;
	}

	/**
	 * 获取系统时间
	 * 
	 * @param dateFormatStr
	 *            日期格式字符串 如： yyyy-MM-dd
	 * @return 系统时间字符串
	 */
	public static String getSysDate(String dateFormatStr) {
		String systemDate = "";
		if (dateFormatStr != null) {
			Calendar date = Calendar.getInstance();
			SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatStr);
			systemDate = dateFormat.format(date.getTime()); // 系统时间
		}
		return systemDate;
	}

	public static byte[] BitmaptoBytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 90, baos);
		return baos.toByteArray();
	}

	public static String getSex(String sex) {
		if (sex.equals("1")) {
			return "女";
		} else if (sex.equals("0")) {
			return "男";
		} else {
			return sex;
		}
	}

	public static String getHunyin(String hunyin) {
		if (hunyin.equals("0")) {
			return "已婚";
		} else if (hunyin.equals("1")) {
			return "未婚";
		} else {
			return hunyin;
		}
	}

	public static String getDate(String date) {
		long time1 = Long.valueOf(date);
		long time2 = Calendar.getInstance().getTimeInMillis() / 1000;
		long time = time2 - time1;
		int i = (int) (time / (60 * 60 * 24));
		Date vDate = new Date(time1 * 1000);
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm"); // 打印年份
		String t = timeFormat.format(vDate);
		if (i == 0) {
			return "今天 " + t;
		}
		if (i == 1) {
			return "昨天 " + t;
		}
		if (i == 2) {
			return "前天 " + t;
		}
		if (i > 2) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm"); // 打印年份
			return dateFormat.format(vDate);
		}
		return "-";
	}

	public static void Log(Object pLog) {
		Log.i("sssss", pLog + "");
	}

	/**
	 * GPS是否可用
	 * @param pContext
	 * @return
	 */
	public static boolean isGpsEnable(Context pContext) {
		LocationManager locationManager = ((LocationManager) pContext.getSystemService(Context.LOCATION_SERVICE));
		return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}

	/**
	 * 打开GPS设置界面
	 * @param pContext
	 */
	public static void OpenGpsSetting(Context pContext) {
		Intent intent = new Intent();
		intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		try {
			pContext.startActivity(intent);
		} catch (ActivityNotFoundException ex) {
			intent.setAction(Settings.ACTION_SETTINGS);
			try {
				pContext.startActivity(intent);
			} catch (Exception e) {
			}
		}
	}

	public static void writeFileSdcard(Context pContext, String fileName, String message) {
		try {
			File vFile = new File(Config.getSDCardPath(pContext));
			if (!vFile.exists()) {
				vFile.mkdir();
			}
			FileOutputStream fout = new FileOutputStream(Config.getSDCardPath(pContext) + "/" + fileName);
			byte[] bytes = message.getBytes();
			fout.write(bytes);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String readFileSdcard(Context pContext, String fileName) {
		String res = "";
		try {
			FileInputStream fin = new FileInputStream(Config.getSDCardPath(pContext) + "/" + fileName);
			int length = fin.available();
			byte[] buffer = new byte[length];
			fin.read(buffer);
			res = EncodingUtils.getString(buffer, "UTF-8");
			fin.close();
		} catch (Exception e) {

			e.printStackTrace();
		}
		return res;

	}

	/**
	 * 根据url获取Bitmap对象
	 * 
	 * @param uri
	 * @return
	 */
	public static Bitmap getBitmapByUrl(Context pContext, String uri) {

		URLConnection url;
		Bitmap bitmap = null;
		String newuri;
		String iconFile = downloadFile(pContext, uri);

		if (iconFile == null)
			return null;

		if (iconFile.length() > 0) {
			newuri = Uri.fromFile(new File(Config.getSDCardPath(pContext) + "/" + iconFile)).toString();
		} else {
			newuri = uri;
		}
		try {
			url = new URL(newuri).openConnection();
			InputStream picStream = url.getInputStream();
			bitmap = BitmapFactory.decodeStream(picStream);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bitmap;
	}

	/**
	 * 下载文件
	 * 
	 * @param url
	 * @return
	 */
	public static String downloadFile(Context pContext, String url) {

		if (url == null || url == "")
			return null;

		String filename = url.substring(url.lastIndexOf("/") + 1);

		try {
			File downDir = new File(Config.getSDCardPath(pContext));
			downDir.mkdirs();

			File tryFile = new File(downDir, filename);
			if (tryFile.exists()) {
				if (tryFile.length() != 0) {
					return filename;
				} else {
					tryFile.delete();
				}
			}

			URL myURL = new URL(url);
			URLConnection conn = myURL.openConnection();
			conn.connect();
			InputStream is = conn.getInputStream();
			int fileSize = conn.getContentLength();

			if (fileSize <= 0)
				throw new RuntimeException("");

			File outputFile = new File(downDir, filename);
			FileOutputStream fos = new FileOutputStream(outputFile);

			if (is == null)
				throw new RuntimeException("stream is null");

			byte buf[] = new byte[1024];

			do {
				int numread = is.read(buf);
				if (numread == -1) {
					break;
				}
				fos.write(buf, 0, numread);

			} while (true);

		} catch (Exception ex) {
			ex.printStackTrace();
			return "";

		}
		return filename;
	}

	public static Bitmap getbitmap(String imageFile, int length) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inPreferredConfig = Bitmap.Config.RGB_565;
		opts.inJustDecodeBounds = true;

		BitmapFactory.decodeFile(imageFile, opts);
		int ins = computeSampleSize(opts, -1, length);
		opts.inSampleSize = ins;
		opts.inPurgeable = true;
		opts.inInputShareable = true;
		opts.inJustDecodeBounds = false;
		try {
			Bitmap bmp = BitmapFactory.decodeFile(imageFile, opts);
			return bmp;
		} catch (OutOfMemoryError err) {
			err.printStackTrace();
		}
		return null;
	}

	public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);

		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}

		return roundedSize;
	}

	private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;

		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));

		if (upperBound < lowerBound) {
			return lowerBound;
		}

		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

	public static String creatfile(Context pContext, Bitmap bm, String filename) {
		if (bm == null) {
			return "";
		}
		File f = new File(Config.getSDCardPath(pContext) + "/" + filename + ".jpg");
		try {
			FileOutputStream out = new FileOutputStream(f);
			if (bm.compress(Bitmap.CompressFormat.JPEG, 90, out)) {
				out.flush();
				out.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return f.getAbsolutePath();
	}

	public static void DownAPK(Context context, String url, Handler handler, String info_id) {

		int down_counter = 0;
		File outputFile = null;
		try {
			String filename = info_id + ".amr";
			File downDir = new File(Config.getSDCardPath(context) + "/RecordFiles/");
			downDir.mkdirs();

			File tryFile = new File(downDir, filename);
			if (tryFile.exists()) {
				handler.sendEmptyMessage(-3);
			}
			if (downing.contains(url)) {
				handler.sendEmptyMessage(-2);
			} else {
				downing.add(url);
			}
			outputFile = new File(downDir, filename);
			FileOutputStream fos = new FileOutputStream(outputFile);

			// 获取文件名
			URL myURL = new URL(url);
			URLConnection conn = myURL.openConnection();
			conn.connect();
			InputStream is = conn.getInputStream();
			int fileSize = conn.getContentLength();// 根据响应获取文件大小
			if (fileSize <= 0)
				throw new RuntimeException("无法获知文件大小 ");

			if (is == null)
				throw new RuntimeException("stream is null");

			// 把数据存入路径+文件名
			byte buf[] = new byte[1024];

			int downLoadFileSize = 0;

			do {
				// 循环读取
				int numread = is.read(buf);
				if (numread == -1) {
					break;
				}

				fos.write(buf, 0, numread);
				downLoadFileSize += numread;
				int percent = (int) ((double) downLoadFileSize / (double) fileSize * 100);

				down_counter++;
				if (down_counter % 50 == 0) {
					Message msg = new Message();
					msg.arg1 = percent;
					msg.what = 1;
					msg.obj = url;
					handler.sendMessage(msg);
				}
			} while (true);

			Message msg = new Message();
			msg.what = 0;
			msg.obj = url;
			handler.sendMessage(msg);
		} catch (Exception ex) {
			outputFile.delete();
			Log.i("sssss", "", ex);
			if (handler != null) {
				Message msg = new Message();
				msg.arg1 = -1;
				msg.obj = url;
				handler.sendMessage(msg);
			}
		}
		downing.remove(url);
	}

	public static List<String> downing = new ArrayList<String>();
}

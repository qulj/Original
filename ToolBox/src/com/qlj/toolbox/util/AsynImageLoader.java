package com.qlj.toolbox.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Hashtable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

/**
 * 图片加载一步任务
 * 
 * @author qlj
 * @time 2014年9月3日下午2:35:49
 */
public class AsynImageLoader {

	// 创建一个 容量为3的线程池
	private static ExecutorService executor = Executors.newFixedThreadPool(3);

	private static Hashtable<String, SoftReference<Bitmap>> cacheimg = new Hashtable<String, SoftReference<Bitmap>>();

	private static Handler handler = new Handler();

	/**
	 * 加载图片（用于集合类型，position是这张图片的位置）
	 * 
	 * @param pContext
	 * @param filepath
	 * @param position
	 * @param asyn
	 * @param callback
	 * @return
	 */
	public static Bitmap loadBitmap(final Context pContext, final String filepath, final int position, boolean asyn, final Handler callback) {
		if (Until.StrIsNull(filepath)) {
			return null;
		}
		SoftReference<Bitmap> soft = cacheimg.get(filepath);
		if (null != soft) {
			Bitmap ret = soft.get();
			if (null != ret && !ret.isRecycled()) {
				return ret;
			}
		}
		if (asyn) {
			executor.submit(new Runnable() {

				public void run() {
					Bitmap dr = Until.getBitmapByUrl(pContext, filepath);
					if (null != dr) {
						cacheimg.put(filepath, new SoftReference<Bitmap>(dr));
						callback.sendMessage(Message.obtain(callback, position, dr));
					}
				}
			});
		}
		return null;
	}

	/**
	 * 用于加载单张图片
	 * @param pContext
	 * @param imageUrl
	 * @param imageView
	 */
	public static void loadImage(final Context pContext, final String imageUrl, final ImageView imageView) {
		final SoftReference<Bitmap> image = cacheimg.get(imageUrl);
		if (image == null) {
			new Thread(new Runnable() {
				public void run() {

					final Bitmap dr = Until.getBitmapByUrl(pContext, imageUrl);
					if (dr == null) {
						return;
					}
					SoftReference<Bitmap> bitmap = new SoftReference<Bitmap>(dr);
					cacheimg.put(imageUrl, bitmap);
					handler.post(new Runnable() {
						public void run() {
							imageView.setImageBitmap(dr);
						}
					});

				}

			}).start();
		} else {
			handler.post(new Runnable() {
				public void run() {
					imageView.setImageBitmap(image.get());
				}
			});
		}

	}

	/**
	 * 在加载图片的时候转换为圆形图片
	 * @param pContext
	 * @param imageUrl
	 * @param imageView
	 */
	public static void loadRoundImage(final Context pContext, final String imageUrl, final ImageView imageView) {
		final SoftReference<Bitmap> image = cacheimg.get(imageUrl);
		if (image == null) {
			new Thread(new Runnable() {
				public void run() {

					final Bitmap dr = Until.getBitmapByUrl(pContext, imageUrl);
					if (dr == null) {
						return;
					}
					SoftReference<Bitmap> bitmap = new SoftReference<Bitmap>(dr);
					cacheimg.put(imageUrl, bitmap);
					handler.post(new Runnable() {
						public void run() {
							imageView.setImageBitmap(dr);
						}
					});

				}

			}).start();
		} else {
			handler.post(new Runnable() {
				public void run() {//处理成圆角
					imageView.setImageBitmap(BitmapUtil.toRoundBitmap(image.get()));
				}
			});
		}

	}

	public static void loadImageNolocal(final Context pContext, final String imageUrl, final ImageView imageView) {
		new Thread(new Runnable() {
			public void run() {
				URLConnection url;
				try {
					url = new URL(imageUrl).openConnection();
					InputStream picStream = url.getInputStream();
					BitmapFactory.Options options = new BitmapFactory.Options();
					options.inJustDecodeBounds = false;
					options.inSampleSize = 10;
					final Bitmap bitmap = BitmapFactory.decodeStream(picStream, null, options);
					handler.post(new Runnable() {
						public void run() {
							imageView.setImageBitmap(bitmap);
						}
					});
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}).start();

	}
}

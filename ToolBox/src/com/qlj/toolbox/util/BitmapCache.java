package com.qlj.toolbox.util;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.util.LruCache;
import com.android.volley.toolbox.ImageLoader.ImageCache;

/**
 * Volley图像缓存
 * 
 * @author qlj
 * @time 2014年9月3日上午10:44:48
 */
@SuppressLint("NewApi")
public class BitmapCache implements ImageCache {

	private LruCache<String, Bitmap> mCache;

	public BitmapCache() {
		int size = 10 * 1024 * 1024;
		mCache = new LruCache<String, Bitmap>(size) {
			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				// TODO Auto-generated method stub
				return bitmap.getRowBytes() * bitmap.getHeight();
			}
		};
	}

	@Override
	public Bitmap getBitmap(String url) {
		// TODO Auto-generated method stub
		return mCache.get(url);
	}

	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		mCache.put(url, bitmap);
	}

}
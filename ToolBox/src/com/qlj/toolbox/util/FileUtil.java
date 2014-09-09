package com.qlj.toolbox.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.util.EncodingUtils;

import android.os.Environment;
import android.util.Log;

/**
 * 客户端sdcard上的读写操作工具类
 * 
 * @author Administrator
 * 
 */
public class FileUtil {

	public static String mRoot;

	/**
	 * 判断SD卡是否存在
	 * 
	 * @return
	 */
	public static boolean existSDCard() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 创建目录
	 */
	public static void mkdir(String path) {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdir();
		}
	}

	private String SDPATH;

	// private int FILESIZE = 4 * 1024;
	private int FILESIZE = 1024;

	public String getSDPATH() {
		return SDPATH;
	}

	public FileUtil() {
		// 得到当前外部存储设备的目录( /SDCARD )
		SDPATH = Environment.getExternalStorageDirectory() + "/";
	}

	/**
	 * 在SD卡上创建文件
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public File createSDFile(String fileName) throws IOException {
		boolean res = false;
		// File file = new File(SDPATH + fileName);
		File file = new File(fileName);
		if (!file.exists()) {
			res = file.createNewFile();
		}
		return file;
	}

	/**
	 * 在SD卡上创建目录
	 * 
	 * @param dirName
	 * @return
	 */
	public File createSDDir(String dirName) {
		boolean res = false;
		File dir = new File(SDPATH + dirName);
		if (!dir.exists()) {
			res = dir.mkdir();
		}
		return dir;
	}

	/**
	 * 判断SD卡上的文件夹是否存在
	 * 
	 * @param fileName
	 * @return
	 */
	public boolean isFileExist(String fileName) {
		File file = new File(SDPATH + fileName);
		return file.exists();
	}

	/**
	 * 将一个InputStream里面的数据写入到SD卡中
	 * 
	 * @param path
	 * @param fileName
	 * @param input
	 * @return
	 */

	public File write2SDFromInput(String path, String fileName,
			InputStream input) {
		File file = null;
		OutputStream output = null;
		if (input == null) {
			return null;
		}
		try {
			createSDDir(path);
			file = createSDFile(path + fileName);
			output = new FileOutputStream(file);

			byte[] buffer = new byte[FILESIZE];
			int len;
			// while((input.read(buffer)) != -1){
			// output.write(buffer);
			// }
			while ((len = input.read(buffer)) > 0) {
				output.write(buffer, 0, len);
			}
			output.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	public File writeFromInput(String path, String fileName, InputStream input) {
		File file = null;
		OutputStream output = null;
		if (input == null) {
			return null;
		}
		try {
			boolean res = true;
			File dir = new File(path);
			if (!dir.exists()) {
				res = dir.mkdirs();
			}
			if (!res) {
				return null;
			}
			file = new File(path + fileName);
			if (!file.exists()) {
				res = file.createNewFile();
			}
			if (!res) {
				return null;
			}
			output = new FileOutputStream(file);

			byte[] buffer = new byte[FILESIZE];
			int len;

			while ((len = input.read(buffer)) > 0) {
				output.write(buffer, 0, len);
			}
			output.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	/**
	 * 获取项目在手机中创建的总文件夹
	 * 
	 * @return
	 */
	public static String getRoot() {
		if (mRoot == null) {
			File sdcardDir = Environment.getExternalStorageDirectory();
			if (CommonUtil.getModel().equals("ZTE_U930")) {
				sdcardDir = new File("sdcard-ext");
			}
			if (!sdcardDir.exists()) {
				return AppConstants.CACHE_PATH_PREFIX;
			}
			mRoot = sdcardDir.getAbsolutePath() + "/JanuBookingOnline";
			File rootFile = new File(mRoot);
			if (!rootFile.exists()) {
				rootFile.mkdirs();
			}
		}
		return mRoot;
	}

	/**
	 * 在工程目录下创建 图片文件夹，用于缓存下载图片
	 * 
	 * @return
	 */
	public static String getPicPath() {
		String root = getRoot() + "/pic";
		File picPath = new File(root);
		if (!picPath.exists()) {
			picPath.mkdirs();
			return root;
		} else {
			return root;
		}
	}
	
	public static String uploadFile(String uploadUrl, String srcPath) {
		String result = "";
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "******";
		try {
			URL url = new URL(uploadUrl);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setDoInput(true);
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setUseCaches(false);
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
			httpURLConnection.setRequestProperty("Charset", "UTF-8");
			httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

			DataOutputStream dos = new DataOutputStream(httpURLConnection.getOutputStream());
			dos.writeBytes(twoHyphens + boundary + end);
			dos.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\"" + srcPath.substring(srcPath.lastIndexOf("/") + 1) + "\"" + end);
			dos.writeBytes(end);

			FileInputStream fis = new FileInputStream(srcPath);
			byte[] buffer = new byte[8192]; // 8k
			int count = 0;
			while ((count = fis.read(buffer)) != -1) {
				dos.write(buffer, 0, count);
			}
			Log.e("", dos.size() + "----------");

			fis.close();

			dos.writeBytes(end);
			dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
			dos.flush();

			InputStream is = httpURLConnection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "utf-8");
			BufferedReader br = new BufferedReader(isr);
			result = br.readLine();

			dos.close();
			is.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// 写数据
	public static void writeFileSDCard(String path, String fileName, String writestr) throws IOException {
		try {
			if(!existSDCard()) {
				return;
			}
			File dir = new File(path);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			File file = new File(path + fileName);
			if(!file.exists()) {
				file.createNewFile();
			}
			
			FileOutputStream fout = new FileOutputStream(path + fileName);
			byte[] bytes = writestr.getBytes();
			fout.write(bytes);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 读数据
	public static String readFileSDCard(String filePath) throws IOException {
		String res = "";
		try {
			if(!existSDCard()) {
				return res;
			}
			
			File file = new File(filePath);
			if(!file.exists()) {
				return res;
			}
			FileInputStream fin = new FileInputStream(filePath);
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
	
}

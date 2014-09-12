package com.qlj.toolbox.data;

import java.util.ArrayList;

import com.qlj.toolbox.bean.Chat;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;

/**
 * 聊天
 * 
 * @comment ChatDataManager.java
 */
public class ChatDataManager {

	public interface ChatColumn {
		public static final String ID = "_id";
		public static final String CHAT_ID = "chat_id";
		public static final String CHAT_TIME = "chat_time";

		public static final String USER_ID = "user_id";
		public static final String MERCHANT_ID = "merchant_id";
		public static final String LOGO = "logo";
		public static final String REQ_OR_RES = "req_or_res";
		public static final String RES_SIZE = "res_size";
		public static final String TYPE_OF_CONTENT = "type_of_content";
	}

	/**
	 * 获取本地消息数据
	 * 
	 * @param resolver
	 * @return
	 */
	public static ArrayList<Chat> queryAllInfo(ContentResolver resolver, String userId, String merchantId) {
		ArrayList<Chat> infoList = new ArrayList<Chat>();

		String[] projection = { ChatColumn.ID, ChatColumn.CHAT_ID, ChatColumn.CHAT_TIME, ChatColumn.USER_ID, ChatColumn.MERCHANT_ID, ChatColumn.LOGO, ChatColumn.REQ_OR_RES, ChatColumn.RES_SIZE, ChatColumn.TYPE_OF_CONTENT };
		Cursor cursor = resolver.query(DataProvider.CHAT_URI, projection, ChatColumn.USER_ID + " == '" + userId + "' and " + ChatColumn.MERCHANT_ID + " == '" + merchantId + "'", null, null);
		try {
			if (cursor.moveToFirst()) {// 判断游标是否为空
				for (int i = 0; i < cursor.getCount(); i++) {
					cursor.moveToPosition(i);

					Chat chat = new Chat();

					chat.setChatId(cursor.getString(1));
					chat.setChatTime(cursor.getString(2));
					infoList.add(chat);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
		}
		return infoList;
	}

	/**
	 * 保存消息到本地
	 * 
	 * @param resolver
	 * @param info
	 */
	public static void saveInfo(ContentResolver resolver, Chat chat) {
		String[] projection = { ChatColumn.ID, ChatColumn.CHAT_ID, ChatColumn.CHAT_TIME, ChatColumn.USER_ID, ChatColumn.MERCHANT_ID, ChatColumn.LOGO, ChatColumn.REQ_OR_RES, ChatColumn.RES_SIZE, ChatColumn.TYPE_OF_CONTENT };

		Cursor cursor = resolver.query(DataProvider.CHAT_URI, projection, ChatColumn.CHAT_ID + " == '" + chat.getChatId() + "'", null, null);
		if (cursor != null && cursor.getCount() != 0) {
			cursor.close();
			resolver.delete(DataProvider.CHAT_URI, ChatColumn.CHAT_ID + " == '" + chat.getChatId() + "'", null);
		}
		ContentValues values = new ContentValues();
		values.put(ChatColumn.CHAT_ID, chat.getChatId());
		values.put(ChatColumn.CHAT_TIME, chat.getChatTime());
		values.put(ChatColumn.USER_ID, chat.getUserId());
		values.put(ChatColumn.MERCHANT_ID, chat.getMerchantId());
		values.put(ChatColumn.LOGO, chat.getLogo());
		values.put(ChatColumn.REQ_OR_RES, chat.getReqOrRes());
		values.put(ChatColumn.RES_SIZE, chat.getResSize());
		values.put(ChatColumn.TYPE_OF_CONTENT, chat.getTypeOfContent());
		resolver.insert(DataProvider.CHAT_URI, values);
	}

	/**
	 * 修改本地消息数据
	 * 
	 * @param resolver
	 * @param info
	 */
	public static void modifyInfo(ContentResolver resolver, Chat chat) {
		ContentValues values = new ContentValues();
		values.put(ChatColumn.CHAT_ID, chat.getChatId());
		values.put(ChatColumn.CHAT_TIME, chat.getChatTime());
		values.put(ChatColumn.USER_ID, chat.getUserId());
		values.put(ChatColumn.MERCHANT_ID, chat.getMerchantId());
		values.put(ChatColumn.LOGO, chat.getLogo());
		values.put(ChatColumn.REQ_OR_RES, chat.getReqOrRes());
		values.put(ChatColumn.RES_SIZE, chat.getResSize());
		values.put(ChatColumn.TYPE_OF_CONTENT, chat.getTypeOfContent());
		resolver.update(DataProvider.CHAT_URI, values, ChatColumn.CHAT_ID + " == '" + chat.getChatId() + "'", null);
	}

	/**
	 * 删除本地消息数据
	 * 
	 * @param resolver
	 * @param infoId
	 */
	public static void removeInfo(ContentResolver resolver, String infoId) {
		resolver.delete(DataProvider.CHAT_URI, ChatColumn.CHAT_ID + " == '" + infoId + "'", null);
	}

	/**
	 * 清空本地消息数据
	 * 
	 * @param resolver
	 */
	public static void clearAllInfo(ContentResolver resolver, String userId, String merchantId) {
		resolver.delete(DataProvider.CHAT_URI, ChatColumn.USER_ID + " == '" + userId + "' and " + ChatColumn.MERCHANT_ID + " == '" + merchantId + "'", null);
	}
}

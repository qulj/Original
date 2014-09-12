package com.qlj.toolbox.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库帮助类
 * 
 * @author Administrator
 * 
 */
public class DBHelper extends SQLiteOpenHelper {

	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "com.janu.lifecircle.db";

	public static final String T_USER = "t_user";// 用户
	public static final String T_COLLECTION = "t_collection";// 我的收藏
	public static final String T_MY_ATTENTION = "t_my_attention";// 我的关注
	public static final String T_SHOPPING_CART = "t_shopping_cart";// 购物车
	public static final String T_CHAT = "t_chat";// 聊一聊
	public static final String T_CHAT_CONTENT = "t_chat_content";// 聊一聊
	public static final String T_FOLLOW_MESSAGE = "t_follow_message";// 关注消息

	public static final String CREATE_T_USER = "create table " + T_USER + " (_id integer primary key autoincrement, tel text, userId text, nickName text, sex text, headPhoto text);";
	public static final String CREATE_T_COLLECTION = "create table " + T_COLLECTION + " (_id integer primary key autoincrement, infoId text, title text, intro text, createTime text, pic text, type text, userId text);";
	public static final String CREATE_T_ATTENTION = "create table " + T_MY_ATTENTION + " (_id integer primary key autoincrement, storeId text, title text, intro text, createTime text, pic text, addr text, userId text, myFocusID text, enterpriseName text, longitude text, latitude text);";
	public static final String CREATE_T_SHOPPING_CART = "create table " + T_SHOPPING_CART + " (_id integer primary key autoincrement, product_id text, product_name text, product_pic text, buy_count text, price text, store_id text, store_name text);";
	public static final String CREATE_T_CHAT = "create table " + T_CHAT + " (_id integer primary key autoincrement, chat_id text, chat_time text, user_id text, merchant_id text , logo text, req_or_res int, res_size int, type_of_content int);";
	public static final String CREATE_T_CHAT_CONTENT = "create table " + T_CHAT_CONTENT + " (_id integer primary key autoincrement, chat_id text, content_id text, content_title text, content_image text , content_intro text, content_date text, content_url text, type_of_business int);";
	public static final String CREATE_T_FOLLOW_MESSAGE = "create table " + T_FOLLOW_MESSAGE + " (_id integer primary key autoincrement, follow_id text, follow_time text, merchant_id text, merchant_name text , logo text, new_message text, last_message_time text);";

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		createTable(db, CREATE_T_USER);// 用户
		createTable(db, CREATE_T_COLLECTION);// 收藏
		createTable(db, CREATE_T_ATTENTION);// 关注
		createTable(db, CREATE_T_SHOPPING_CART);
		createTable(db, CREATE_T_CHAT);// 聊一聊
		createTable(db, CREATE_T_CHAT_CONTENT);
		createTable(db, CREATE_T_FOLLOW_MESSAGE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		if (oldVersion != DATABASE_VERSION) {
			dropTable(db, T_USER);
			dropTable(db, T_COLLECTION);
			dropTable(db, T_MY_ATTENTION);
			dropTable(db, T_SHOPPING_CART);
			dropTable(db, T_CHAT);
			dropTable(db, T_CHAT_CONTENT);
			dropTable(db, T_FOLLOW_MESSAGE);
			onCreate(db);
		}
	}

	public void createTable(SQLiteDatabase db, String sql) {
		db.execSQL(sql);
	}

	public void dropTable(SQLiteDatabase db, String tableName) {
		db.execSQL("DROP TABLE IF EXISTS " + tableName);
	}

}

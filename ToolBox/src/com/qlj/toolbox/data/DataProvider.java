package com.qlj.toolbox.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

/**
 * Sqlit提供器
 * @author Administrator
 *
 */
public class DataProvider extends ContentProvider {
	
	public static final String AUTHORITY = "com.qlj.toolbox.db.DataProvider";
	public static final String PARAMETER_NOTIFY = "notify";
	public static final Uri CHAT_URI = Uri.parse("content://" + AUTHORITY + "/t_chat?" + PARAMETER_NOTIFY + "=true");// 聊一聊
	public static final Uri CHAT_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/t_chat_content?" + PARAMETER_NOTIFY + "=true");// 聊一聊
	public static final Uri FOLLOW_MESSAGE_URI = Uri.parse("content://" + AUTHORITY + "/t_follow_message?" + PARAMETER_NOTIFY + "=true");// 聊一聊

	private SQLiteOpenHelper mOpenHelper;
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SqlArguments args = new SqlArguments(uri, selection, selectionArgs);

        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int count = db.delete(args.table, args.where, args.args);
        if (count > 0)
            sendNotify(uri);

        return count;
	}

	@Override
	public String getType(Uri uri) {
		SqlArguments args = new SqlArguments(uri, null, null);
        if (TextUtils.isEmpty(args.where)) {
            return "vnd.android.cursor.dir/" + args.table;
        }
        else {
            return "vnd.android.cursor.item/" + args.table;
        }
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SqlArguments args = new SqlArguments(uri);

        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final long rowId = db.insert(args.table, null, values);
        if (rowId <= 0)
            return null;

        uri = ContentUris.withAppendedId(uri, rowId);
        sendNotify(uri);

        return uri;
	}

	@Override
	public boolean onCreate() {
		mOpenHelper = new DBHelper(getContext());
        return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SqlArguments args = new SqlArguments(uri, selection, selectionArgs);
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(args.table);

        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        Cursor result = qb.query(db, projection, args.where, args.args, null,
            null, sortOrder);
        result.setNotificationUri(getContext().getContentResolver(), uri);

        return result;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SqlArguments args = new SqlArguments(uri, selection, selectionArgs);

        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int count = db.update(args.table, values, args.where, args.args);
        if (count > 0)
            sendNotify(uri);

        return count;
	}
	
	private void sendNotify(Uri uri) {
        String notify = uri.getQueryParameter(PARAMETER_NOTIFY);
        if (notify == null || "true".equals(notify)) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
    }
	
	static class SqlArguments {
        public final String table;
        public final String where;
        public final String[] args;


        SqlArguments(Uri url, String where, String[] args) {
            if (url.getPathSegments().size() == 1) {
                this.table = url.getPathSegments().get(0);
                this.where = where;
                this.args = args;
            }
            else if (url.getPathSegments().size() != 2) {
                throw new IllegalArgumentException("Invalid URI: " + url);
            }
            else if (!TextUtils.isEmpty(where)) {
                throw new UnsupportedOperationException(
                    "WHERE clause not supported: " + url);
            }
            else {
                this.table = url.getPathSegments().get(0);
                this.where = "_id=" + ContentUris.parseId(url);
                this.args = null;
            }
        }


        SqlArguments(Uri url) {
            if (url.getPathSegments().size() == 1) {
                table = url.getPathSegments().get(0);
                where = null;
                args = null;
            }
            else {
                throw new IllegalArgumentException("Invalid URI: " + url);
            }
        }
    }
	
	

}

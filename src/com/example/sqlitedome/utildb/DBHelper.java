package com.example.sqlitedome.utildb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	
	public static final String CREATE_BOOK = "create table Book(" +
			"id integer primary key autoincrement," +
			"name text," +
			"description text)";	
	
	private Context mContext;

	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		mContext = context;
		LogUtil.e("DBHelper构造函数");
		 
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		 
		db.execSQL(CREATE_BOOK);
		//Toast.makeText(mContext, "数据库Book创建成功，可以对它进行操作", Toast.LENGTH_SHORT).show();
		LogUtil.e("book表创建成功");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		 

	}
	public boolean deleteDatabase(String SQLName) {
		// TODO Auto-generated method stub
		return mContext.deleteDatabase(SQLName);
	}

}

package com.example.sqltest.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class GoodsImformation extends SQLiteOpenHelper {

	private Context mContext;
	
	public static final String CREATE_GOODS = "create table Goods ("
			+ "id integer primary key autoincrement,"
			+ "purchaser text,"
			+ "price integer,"
			+ "deposit integer,"
			+ "retainage integer,"
			+ "salesman text,"
			+ "date text)";
	//deposit 定金  
	

	public GoodsImformation(Context context, String name, 
							CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
		mContext = context;
	}


	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_GOODS);
		Toast.makeText(mContext, "第一次使用，创建本地数据库完成!", Toast.LENGTH_LONG).show();
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}

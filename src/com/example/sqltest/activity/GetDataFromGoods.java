package com.example.sqltest.activity;


import com.example.sqltest.R;
import com.example.sqltest.database.GoodsImformation;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class GetDataFromGoods extends Activity {
	
	 
	String purchaser;
	TextView show_pur,show_pri,show_re,show_de,show_sm,show_date;
	String id,price,retainage,deposit,salesman,date;
	int tag=0;Integer position;
	//类的初始化应放在onCreate中
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.getdatafromgoods);
		GoodsImformation dbhelper = new GoodsImformation(this, "Goods.db", null, 1);
		//控件实例化
		Button back = (Button) findViewById(R.id.back);
		Button modify = (Button) findViewById(R.id.modify);
		show_pur = (TextView) findViewById(R.id.purchaser_show);
		show_pri = (TextView) findViewById(R.id.price_show);
		show_re = (TextView) findViewById(R.id.retainage_show);
		show_de = (TextView) findViewById(R.id.deposit_show);
		show_sm = (TextView) findViewById(R.id.salesman_show);
		show_date = (TextView) findViewById(R.id.date_show);
		//遍历数据库 取出数据
		SQLiteDatabase db = dbhelper.getWritableDatabase();
		Cursor cursor = db.query("Goods", null, null, null, null, null, null);
		if(cursor.moveToFirst()) {
			Intent intent = getIntent();
			int pos = intent.getIntExtra("position", 10) + 1;
			position = new Integer(pos);
			do {
				id = cursor.getString(cursor.getColumnIndex("id"));
				if(position.toString().equals(id)){
					purchaser = cursor.getString(cursor.getColumnIndex("purchaser"));
					price = cursor.getString(cursor.getColumnIndex("price"));
					retainage = cursor.getString(cursor.getColumnIndex("retainage"));
					deposit = cursor.getString(cursor.getColumnIndex("deposit"));
					salesman = cursor.getString(cursor.getColumnIndex("salesman"));
					date = cursor.getString(cursor.getColumnIndex("date"));
					tag=1;
				}
//				Log.d("purchaser", purchaser);
//				Log.d("id", id);
			}while (cursor.moveToNext()&&tag==0);
		}
		cursor.close();
		//在textview控件中设置数值
		show_pur.setText(purchaser);
		show_pri.setText(price);
		show_re.setText(retainage);
		show_de.setText(deposit);
		show_sm.setText(salesman);
		show_date.setText(date);

		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		modify.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(GetDataFromGoods.this, ModifyData.class);
				intent.putExtra("purchaser", purchaser);
				intent.putExtra("price", price);
				intent.putExtra("retainage", retainage);
				intent.putExtra("deposit", deposit);
				intent.putExtra("salesman", salesman);
				intent.putExtra("date", date);
				intent.putExtra("position", position);Log.d("getposition", position+"");
				startActivity(intent);
			}
		});
	}
}

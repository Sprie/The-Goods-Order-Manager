package com.example.sqltest.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.sqltest.R;
import com.example.sqltest.database.GoodsImformation;


import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;


public class ShowDataInListView extends ListActivity {
	
	GoodsImformation dbHelper;
	
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.showdatainlistview);
		
		ListView listView = (ListView) findViewById(android.R.id.list);
		//数据库类的实例化
		dbHelper = new GoodsImformation(this, "Goods.db", null, 1);
		//创建一个SimpleAdater适配器
		SimpleAdapter adapter = new SimpleAdapter(this, getData(), 
				R.layout.listview, 
				new String[] { "purchaser", "salesman"}, 
				new int[] {R.id.listpurchaser, R.id.listsalesman});
		listView.setAdapter(adapter);
//		setListAdapter(adapter);
		//创建listview点击事件
		listView.setOnItemClickListener(new OnItemClickListener() {
			
			public void onItemClick(AdapterView<?> parent, View view, int position, long id){
				Log.d("msg", position+"");
				Intent intent = new Intent(ShowDataInListView.this, GetDataFromGoods.class);
				intent.putExtra("position", position);
				startActivity(intent);
			}
			
		});
	}
	
	private List<Map<String, Object>> getData() {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> map;
		String purchaser;
		String salesman;
		
		//遍历数据库 取出数据
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cursor = db.query("Goods", null, null, null, null, null, null);
		if(cursor.moveToFirst()) {
			do {
				//循坏开始必须实例化map对象，用于存储从数据库获取的值
				map = new HashMap<String, Object>();
				//获取数据库的值
				purchaser = cursor.getString(cursor.getColumnIndex("purchaser"));
				salesman= cursor.getString(cursor.getColumnIndex("salesman"));
				//将键与值绑定
				
				map.put("salesman", salesman);
				map.put("purchaser", purchaser);
				//添加进list列表
				list.add(map);
			}while (cursor.moveToNext());
		}
		cursor.close();
		return list;
	}
	
}

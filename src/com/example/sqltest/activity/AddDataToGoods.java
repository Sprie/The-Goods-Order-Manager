package com.example.sqltest.activity;


import java.util.Calendar;

import com.example.sqltest.R;
import com.example.sqltest.database.GoodsImformation;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.Toast;

public class AddDataToGoods extends Activity {

	private GoodsImformation database;
	private EditText purchaser,price,deposit,retainage,salesman;
	private DatePicker datepicker;
	private int year, month, day;
	private String date;
	private int now_year,now_month,now_day;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.adddatatogoods);
		
		purchaser = (EditText) findViewById(R.id.add_purchaser);
		price = (EditText) findViewById(R.id.add_price);
		deposit = (EditText) findViewById(R.id.add_deposit);
		retainage = (EditText) findViewById(R.id.add_retainage);
		salesman = (EditText) findViewById(R.id.add_salesman);
		datepicker = (DatePicker) findViewById(R.id.datepicker);
//		datepicker.updateDate(2016, 1, 1);
	
		Button sendData = (Button) findViewById(R.id.send_data);
		Button showData = (Button) findViewById(R.id.show_data);
		
		database = new GoodsImformation(this, "Goods.db", null, 1);
				
		//��ʼ��datepicker����ȡ�û�ѡ���date�����������Ĭ��ʱ������ڳ�ʼ��֮��
		datepicker.init(year, month, day, new OnDateChangedListener() {
			
			@Override
			public void onDateChanged(DatePicker view, int year, int month, int day) {
				// TODO Auto-generated method stub
				AddDataToGoods.this.year = year;
				AddDataToGoods.this.month = month;
				AddDataToGoods.this.day = day;
			}
		});
		//��ȡ��ǰʱ�䲢����ΪĬ��ʱ��
		Calendar c = Calendar.getInstance();
		now_year = c.get(Calendar.YEAR);Log.d("now year", now_year + "");
		now_month = c.get(Calendar.MONTH);Log.d("now month", now_month + "");
		now_day = c.get(Calendar.DAY_OF_MONTH);Log.d("now day", now_day + "");
		datepicker.updateDate(now_year, now_month, now_day);
		
		//��ȡ��editview�ؼ�����������ݲ����������ݿ�goods.db��
		sendData.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String pur = purchaser.getText().toString();
				String pri = price.getText().toString();
				String hm = deposit.getText().toString();
				String lm = retainage.getText().toString();
				String sm = salesman.getText().toString();
				date = year + "-" + month + "-" + day ;Log.d("date", date + "");

				SQLiteDatabase goods = database.getWritableDatabase();
				ContentValues values = new ContentValues();
				
				Log.d("message", pur);
				values.put("purchaser", pur);
				values.put("price", pri);
				values.put("deposit", hm);
				values.put("retainage", lm);
				values.put("salesman", sm);
				values.put("date", date);
				goods.insert("Goods", null, values);
				values.clear();
				
				Toast.makeText(AddDataToGoods.this, "��Ӷ����ɹ�", Toast.LENGTH_SHORT).show();
				
			}
		});
		//�򿪻getDataFromGoods
		showData.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(AddDataToGoods.this, ShowDataInListView.class);
				startActivity(intent);
			}
		});	
	}
	
	protected void onRestart() {
		super.onRestart();
		//���µ��ø�activtyʱ��տؼ��е�����
		purchaser.setText("");
		price.setText("");
		deposit.setText("");
		retainage.setText("");
		salesman.setText("");
		datepicker.updateDate(now_year, now_month, now_day);
		//ʹ�ؼ�purchaser��ȡ����
		purchaser.setFocusable(true);
		purchaser.setFocusableInTouchMode(true);
		purchaser.requestFocus();

		
	}
}

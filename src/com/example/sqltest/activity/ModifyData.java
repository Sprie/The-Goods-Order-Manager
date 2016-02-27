package com.example.sqltest.activity;

import java.util.regex.Pattern;

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
import android.widget.EditText;
import android.widget.Toast;
import android.widget.DatePicker.OnDateChangedListener;

public class ModifyData extends Activity {

	private SQLiteDatabase db;
	private GoodsImformation dbHelper;
	private EditText get_pur, get_pri, get_re, get_de, get_sm;
	private DatePicker get_date;
	private String purchaser,price, retainage, deposit, salesman, date;
	private int year, month, day;
	private Integer position;	//position值与数据库中的id相同
	private String pattern;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.modifydata);
		dbHelper = new GoodsImformation(this, "Goods.db", null, 1);
		
		//定义控件
		Button modify = (Button) findViewById(R.id.modify);
		Button back = (Button) findViewById(R.id.back);
		get_pur =  (EditText) findViewById(R.id.modify_purchaser);
		get_pri = (EditText) findViewById(R.id.modify_price);
		get_re = (EditText) findViewById(R.id.modify_retainage);
		get_de = (EditText) findViewById(R.id.modify_deposit);
		get_sm = (EditText) findViewById(R.id.modify_salesman);
		get_date =  (DatePicker) findViewById(R.id.datepicker);
		//获取上个Activty传来的数据
		Intent intent = getIntent();
		purchaser = intent.getStringExtra("purchaser");
		price = intent.getStringExtra("price");
		deposit = intent.getStringExtra("deposit");
		retainage = intent.getStringExtra("retainage");
		salesman = intent.getStringExtra("salesman");
		position = intent.getIntExtra("position", 10);
		date = intent.getStringExtra("date"); Log.d("date", date);
		//将上个活动传来的date数据解析为year, month, day3个string
		pattern = "-";
		Pattern pat = Pattern.compile(pattern);
		final String[] dt = pat.split(date);
		int get_year = Integer.parseInt(dt[0]);Log.d("getyear", get_year + "");
		int get_month = Integer.parseInt(dt[1]);Log.d("getmonth", get_month + "");
		int get_day = Integer.parseInt(dt[2]);Log.d("getday", get_day + "");
		//将数据设置到控件中
		get_pur.setText(purchaser);
		get_pri.setText(price);
		get_re.setText(retainage);
		get_de.setText(deposit);
		get_sm.setText(salesman);

		//初始化datepicker，获取用户选择的date，其余的设置默认时间必须在初始化之后
		get_date.init(year, month, day, new OnDateChangedListener() {
			
			@Override
			public void onDateChanged(DatePicker view, int year, int month, int day) {
				// TODO Auto-generated method stub
				ModifyData.this.year = year;
				ModifyData.this.month = month;
				ModifyData.this.day = day;
			}
		});
		get_date.updateDate(get_year, get_month, get_day);
		
		
		
		//将修改后的数据传回数据库
		modify.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				db = dbHelper.getWritableDatabase();
				ContentValues values = new ContentValues();
				
				String pur = get_pur.getText().toString();
				String pri = get_pri.getText().toString();
				String de = get_de.getText().toString();
				String re = get_re.getText().toString();
				String sm = get_sm.getText().toString();
				
				values.put("purchaser", pur);
				values.put("price", pri);
				values.put("deposit", de);
				values.put("retainage", re);
				values.put("salesman", sm);
				//获取时间信息

				String date = year + "-" + month + "-" + day ;Log.d("dateto", date);
				values.put("date", date);
				//更新数据
//				Log.d("position", position+"");
//				String[] p = {String.valueOf(position)};
//				String pos = position.toString();
//				Log.d("pos", String.valueOf(position));
				db.update("Goods", values, "id=?", new String[] {String.valueOf(position)});
				Toast.makeText(ModifyData.this, "数据修改成功！", Toast.LENGTH_SHORT).show();
				finish();
				
				Intent intent = new Intent(ModifyData.this, AddDataToGoods.class);
				startActivity(intent);
			}
		});
		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
	}
}

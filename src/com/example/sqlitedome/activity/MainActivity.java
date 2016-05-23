package com.example.sqlitedome.activity;

import java.util.ArrayList;
import com.example.sqlitedome.R;
import com.example.sqlitedome.utildb.DBUtil;
import com.example.sqlitedome.utildb.LogUtil;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


public class MainActivity extends Activity implements OnClickListener {
	
	
	private Button btnAdd;
	private Button btnUpdate;
	private Button btnDelete;
	private Button btnQuery;
	private Button btnAll;
	private Button btnSQLDelete;
	
	private EditText etId;
	private EditText etName;
	private EditText etDescription;
	
	private ListView listView;
	private ArrayAdapter<String> adapter;
	private ArrayList<String> list = new ArrayList<String>();
	
	private DBUtil dbUtil;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//³õÊ¼»¯
		btnAdd = (Button) findViewById(R.id.btn_add);
		btnUpdate = (Button) findViewById(R.id.btn_update);
		btnDelete = (Button) findViewById(R.id.btn_delete);
		btnQuery = (Button) findViewById(R.id.btn_query);
		btnAll = (Button) findViewById(R.id.btn_all);
		btnSQLDelete = (Button) findViewById(R.id.btn_sql);
		
		etId = (EditText) findViewById(R.id.edit_id);
		etName = (EditText) findViewById(R.id.edit_name);
		etDescription = (EditText) findViewById(R.id.edit_description);
		
		listView = (ListView) findViewById(R.id.list_view);
		
		dbUtil = DBUtil.getInstance(this);
		list = dbUtil.allDate();
		
		LogUtil.e("mian....list.size()Îª£º"+list.size());
		
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list);
		
		btnAdd.setOnClickListener(this);
		btnUpdate.setOnClickListener(this);
		btnDelete.setOnClickListener(this);
		btnQuery.setOnClickListener(this);
		btnAll.setOnClickListener(this);
		btnSQLDelete.setOnClickListener(this);
		
		listView.setAdapter(adapter);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_add:
			list.clear();
			dbUtil.addDate(etName.getText().toString(), etDescription.getText().toString());
			list = dbUtil.allDate();
			adapter.notifyDataSetChanged();
			break;
		case R.id.btn_update:
			list.clear();
			dbUtil.update(etId.getText().toString(), etName.getText().toString(), etDescription.getText().toString());
			list = dbUtil.allDate();
			adapter.notifyDataSetChanged();
			break;
		case R.id.btn_delete:
			list.clear();
			dbUtil.deleteDate(etId.getText().toString());
			list = dbUtil.allDate();
			adapter.notifyDataSetChanged();
			break;
		case R.id.btn_query:
			list.clear();
			dbUtil.queryDate(etId.getText().toString());
			list = dbUtil.queryDate(etId.getText().toString());
			adapter.notifyDataSetChanged();
			break;
		case R.id.btn_all:
			list.clear();
			list = dbUtil.allDate();
			adapter.notifyDataSetChanged();
			break;
		case R.id.btn_sql:
			dbUtil.dropData();
			list.clear();
			list = dbUtil.allDate();
			adapter.notifyDataSetChanged();
			break;
		default:
			break;
		}
		
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
	}
}

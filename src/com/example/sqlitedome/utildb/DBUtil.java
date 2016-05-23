package com.example.sqlitedome.utildb;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;


public class DBUtil {

	//���ݿ�����
	public static final String DB_NAME = "sql_book.db";
	//���ݿ�汾
	public static final int VERSION = 1;
	
	private  static DBUtil dbUtil;
	private SQLiteDatabase db;
	private Context mContext;
	private ArrayList<Book> list = new ArrayList<Book>(); 
	private ArrayList<String> dataList = new ArrayList<String>();
	private DBHelper dbHelper;

	
	/*
	 * �����췽��˽�л�����ֻ֤��һ��dbUtilʵ������
	 * */
	private DBUtil(Context context){
		dbHelper = new DBHelper(context,DB_NAME,null,VERSION);
		db = dbHelper.getWritableDatabase();
		mContext = context;
		//initDate();
		LogUtil.e("��ʼ�����ݿ�ɹ�");
		
	}
	/*
	 * ���Ⱪ¶��������ȡdbUtilʵ��,Ӧ������ʽ
	 * */
	public synchronized static DBUtil getInstance(Context context){
		if(dbUtil == null){
			dbUtil = new DBUtil(context);
		}
		return dbUtil;
		
	}
	/*
	 * ��ʼ�����ݿ�
	 * */
	public void initDate(){
	   
		Book book = new Book();
		book.setId(1);
		book.setName("book1");
		book.setDescription("description book1");
		ContentValues values = new ContentValues();
		values.put("name", book.getName());
		values.put("description", book.getDescription());
		LogUtil.e("��ʼ���Ƿ����ɹ���"+(db.insert("Book", null, values)));
		
	}
	/*
	 * ������ݵ����ݿ�
	 * */
	public void addDate(String getName,String getDescription){
		
		ContentValues values = new ContentValues();
		values.put("name", getName);
		values.put("description", getDescription);
		db.insert("Book", null, values);
		values.clear();//�Ա��������ݵĲ���
		Toast.makeText(mContext, "����ɹ�", Toast.LENGTH_SHORT).show();
	}
	/*
	 * �޸�����
	 * */
	public void update(String getId,String getName,String getDescription){
		
		try{
			ContentValues values = new ContentValues();
			values.put("name", getName);
			values.put("description", getDescription);
			db.update("Book", values, "id = "+getId, null);
			Toast.makeText(mContext, "�޸ĳɹ�", Toast.LENGTH_SHORT).show();
			
		}catch(Exception e){
			
		    Toast.makeText(mContext, "��������ȷ��id", Toast.LENGTH_SHORT).show();
	
		}
		/*
			String sql = "";
			sql = "update Book set name='"+getName+"',description='"+getDescription+"' where id = "+getId;
			db.execSQL(sql);
			Toast.makeText(mContext, "�޸����ݳɹ�", Toast.LENGTH_SHORT).show();
		*/
	}
	/*
	 * ɾ������
	 * */
	public void  deleteDate(String getId){
		try{
			/*String sql = "";
			sql = "delete from Book where id="+getId;
			db.execSQL(sql);*/
			db.delete("Book", "id = "+getId, null);
			Toast.makeText(mContext, "ɾ�����ݳɹ�", Toast.LENGTH_SHORT).show();
		}catch(Exception e){
			
			Toast.makeText(mContext, "��������ȷ��id", Toast.LENGTH_SHORT).show();
		}
		
	}
	/*
	 * ��ѯ��Ӧ����
	 * */
	public ArrayList<String> queryDate(String getId){
		
		if(dataList!=null){
			dataList.clear();
		}
//		LogUtil.e("��ӡgetId��"+getId);
//		LogUtil.e("��ӡgetId��"+(getId==null));
//		LogUtil.e("��ӡgetId��"+(getId==""));
//		LogUtil.e("��ӡgetId��"+(getId==" "));
		try{
			Cursor cursor = db.query("Book", null,  "id = "+getId, null, null, null, null);
			cursor.moveToFirst();
			Book book = new Book();
			book.setId(cursor.getInt(cursor.getColumnIndex("id")));
			book.setName(cursor.getString(cursor.getColumnIndex("name")));
			book.setDescription(cursor.getString(cursor.getColumnIndex("description")));
			
			dataList.add("id="+book.getId()+" name="+book.getName()
					+" description = "+book.getDescription());
			LogUtil.e("��ѯ������ "+dataList.size());
			return dataList;
		}catch(Exception e){
			
			Toast.makeText(mContext, "��������ȷ��id", Toast.LENGTH_SHORT).show();
			dataList.add("This is a listview!");
			return dataList;
		}

		
		
	}
	/*
	 * ��ѯ��������
	 * */
	public ArrayList<String> allDate(){
		if(dataList !=null){
			dataList.clear();
		}
		Cursor cursor = db.query( "Book", null, null, null, null, null, null);
		if(cursor.moveToFirst()){
			do{
				Book book = new Book();
				book.setId(cursor.getInt(cursor.getColumnIndex("id")));
				book.setName(cursor.getString(cursor.getColumnIndex("name")));
				book.setDescription(cursor.getString(cursor.getColumnIndex("description")));
				
				LogUtil.e("name= "+book.getName());
				LogUtil.e("description = "+book.getDescription());
				
				list.add(book);
				
			}while(cursor.moveToNext());
		}
		if(cursor !=null){
			cursor.close();
		}
		LogUtil.e("list.size()="+list.size()); 
		for(Book book:list){
			dataList.add("id="+book.getId()+" name="+book.getName()
					+" description = "+book.getDescription());
		}
		list.clear();
		if(dataList.size()==0){
			dataList.add("This is a listview!");
		}
		return dataList;
	}
	/*
	 * �������ݿ�
	 * */
	public void dropData(){
		
		
		/*//ɾ�����ݿ�
		 * dbHelper.deleteDatabase(DB_NAME);
		LogUtil.e("���ݿ� game over");*/
		
		//sql���ɾ�����е���������
		/*String sql = "delete from Book";
		db.execSQL(sql);*/
		
		//ɾ����������
		//db.delete("Book", null, null);
		
		/*ɾ���������ݲ�������id  ���� sqlite��û��truncate table table_name����
		String sql = "truncate table Book ";
		db.execSQL(sql);*/
		
		
		
		//���id�������⣬ɾ�����ٴ�������������⣺��ɾ���������ݣ���������id
		String sql = "DROP TABLE Book";
		db.execSQL(sql);
		db.execSQL(DBHelper.CREATE_BOOK);
		
		Toast.makeText(mContext, "ɾ�����ݳɹ�", Toast.LENGTH_SHORT).show();
	}
	
}

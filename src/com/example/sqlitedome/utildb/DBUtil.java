package com.example.sqlitedome.utildb;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;


public class DBUtil {

	//数据库名称
	public static final String DB_NAME = "sql_book.db";
	//数据库版本
	public static final int VERSION = 1;
	
	private  static DBUtil dbUtil;
	private SQLiteDatabase db;
	private Context mContext;
	private ArrayList<Book> list = new ArrayList<Book>(); 
	private ArrayList<String> dataList = new ArrayList<String>();
	private DBHelper dbHelper;

	
	/*
	 * 将构造方法私有化，保证只有一个dbUtil实例对象
	 * */
	private DBUtil(Context context){
		dbHelper = new DBHelper(context,DB_NAME,null,VERSION);
		db = dbHelper.getWritableDatabase();
		mContext = context;
		//initDate();
		LogUtil.e("初始化数据库成功");
		
	}
	/*
	 * 对外暴露方法，获取dbUtil实例,应用懒汉式
	 * */
	public synchronized static DBUtil getInstance(Context context){
		if(dbUtil == null){
			dbUtil = new DBUtil(context);
		}
		return dbUtil;
		
	}
	/*
	 * 初始化数据库
	 * */
	public void initDate(){
	   
		Book book = new Book();
		book.setId(1);
		book.setName("book1");
		book.setDescription("description book1");
		ContentValues values = new ContentValues();
		values.put("name", book.getName());
		values.put("description", book.getDescription());
		LogUtil.e("初始化是否插入成功："+(db.insert("Book", null, values)));
		
	}
	/*
	 * 添加数据到数据库
	 * */
	public void addDate(String getName,String getDescription){
		
		ContentValues values = new ContentValues();
		values.put("name", getName);
		values.put("description", getDescription);
		db.insert("Book", null, values);
		values.clear();//以便下条数据的插入
		Toast.makeText(mContext, "插入成功", Toast.LENGTH_SHORT).show();
	}
	/*
	 * 修改数据
	 * */
	public void update(String getId,String getName,String getDescription){
		
		try{
			ContentValues values = new ContentValues();
			values.put("name", getName);
			values.put("description", getDescription);
			db.update("Book", values, "id = "+getId, null);
			Toast.makeText(mContext, "修改成功", Toast.LENGTH_SHORT).show();
			
		}catch(Exception e){
			
		    Toast.makeText(mContext, "请输入正确的id", Toast.LENGTH_SHORT).show();
	
		}
		/*
			String sql = "";
			sql = "update Book set name='"+getName+"',description='"+getDescription+"' where id = "+getId;
			db.execSQL(sql);
			Toast.makeText(mContext, "修改数据成功", Toast.LENGTH_SHORT).show();
		*/
	}
	/*
	 * 删除数据
	 * */
	public void  deleteDate(String getId){
		try{
			/*String sql = "";
			sql = "delete from Book where id="+getId;
			db.execSQL(sql);*/
			db.delete("Book", "id = "+getId, null);
			Toast.makeText(mContext, "删除数据成功", Toast.LENGTH_SHORT).show();
		}catch(Exception e){
			
			Toast.makeText(mContext, "请输入正确的id", Toast.LENGTH_SHORT).show();
		}
		
	}
	/*
	 * 查询对应数据
	 * */
	public ArrayList<String> queryDate(String getId){
		
		if(dataList!=null){
			dataList.clear();
		}
//		LogUtil.e("打印getId："+getId);
//		LogUtil.e("打印getId："+(getId==null));
//		LogUtil.e("打印getId："+(getId==""));
//		LogUtil.e("打印getId："+(getId==" "));
		try{
			Cursor cursor = db.query("Book", null,  "id = "+getId, null, null, null, null);
			cursor.moveToFirst();
			Book book = new Book();
			book.setId(cursor.getInt(cursor.getColumnIndex("id")));
			book.setName(cursor.getString(cursor.getColumnIndex("name")));
			book.setDescription(cursor.getString(cursor.getColumnIndex("description")));
			
			dataList.add("id="+book.getId()+" name="+book.getName()
					+" description = "+book.getDescription());
			LogUtil.e("查询语句错误： "+dataList.size());
			return dataList;
		}catch(Exception e){
			
			Toast.makeText(mContext, "请输入正确的id", Toast.LENGTH_SHORT).show();
			dataList.add("This is a listview!");
			return dataList;
		}

		
		
	}
	/*
	 * 查询所有数据
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
	 * 销毁数据库
	 * */
	public void dropData(){
		
		
		/*//删除数据库
		 * dbHelper.deleteDatabase(DB_NAME);
		LogUtil.e("数据库 game over");*/
		
		//sql语句删除表中的所有数据
		/*String sql = "delete from Book";
		db.execSQL(sql);*/
		
		//删除表中数据
		//db.delete("Book", null, null);
		
		/*删除表中数据并且重置id  问题 sqlite中没有truncate table table_name命令
		String sql = "truncate table Book ";
		db.execSQL(sql);*/
		
		
		
		//解决id重置问题，删除表，再创建表，待解决问题：不删除表中数据，重新排列id
		String sql = "DROP TABLE Book";
		db.execSQL(sql);
		db.execSQL(DBHelper.CREATE_BOOK);
		
		Toast.makeText(mContext, "删除数据成功", Toast.LENGTH_SHORT).show();
	}
	
}

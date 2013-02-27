package net.mitrani.blackbook.DB;

import java.util.ArrayList;

import net.mitrani.blackbook.datatype.TaskItem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



public class DatabaseHandlerTask extends SQLiteOpenHelper
{

	// Database Version
	private static final int DATABASE_VERSION = 19;

	// Database Name
	private static final String DATABASE_NAME = "taskManager";

	// Contacts table name
	private static final String TABLE_CONTACTS = "tasks";

	// Contacts Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_TASKNAME = "name";
	private static final String KEY_TASKDIS = "discription";
	private static final String KEY_TASKCREATE = "creatdate";
	private static final String KEY_ENDTASK = "enddate";
	private static final String KEY_CONID = "conid";
	private static final String KEY_CHEACK = "ischeaked";
	
	private static DatabaseHandlerTask instance;

	private DatabaseHandlerTask(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public static DatabaseHandlerTask getInstance(Context context )
	{
		if (instance == null)
		{
			instance = new DatabaseHandlerTask(context);
		}
		return instance;
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," 
				+ KEY_TASKNAME + " TEXT,"
				+ KEY_TASKDIS + " TEXT," 
				+ KEY_TASKCREATE + " INTEGER," 
				+ KEY_ENDTASK + " INTEGER," 
				+ KEY_CONID + " TEXT," 
				+ KEY_CHEACK + " INTEGER"
				+ ")";
		db.execSQL(CREATE_CONTACTS_TABLE);
		Log.d("DB","created");
		
		 
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

		// Create tables again
		onCreate(db);

	}

	public void addTask(TaskItem item)
	{
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		
		values.put(KEY_TASKNAME, item.getTaskName()); // task Name
		values.put(KEY_TASKDIS, item.getTaskDescription()); // task description
		values.put(KEY_TASKCREATE, item.getTaskCreateDate()); 	// create date
		values.put(KEY_ENDTASK, item.getTaskEndDate());	// task end	 date
		values.put(KEY_CONID, item.getBelongTo());	// task belong to
		if(item.getToDelete())
			values.put(KEY_CHEACK, 1);
		else
			values.put(KEY_CHEACK, 0);
		
		// Inserting Row
		db.insert(TABLE_CONTACTS, null, values);
		db.close(); // Closing database connection
		Log.d("DB","task add " +getTaskCount());
		
		
	}

	/*make get all properties*/
	public TaskItem getTask(int id)
	{
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(	TABLE_CONTACTS,
									new String[]{ KEY_ID, KEY_TASKNAME, KEY_TASKDIS, KEY_TASKCREATE, KEY_ENDTASK, KEY_CONID, KEY_CHEACK },
									KEY_ID + "=?",
									new String[]{ String.valueOf(id) },
									null,
									null,
									null,
									null
								);
		if (cursor != null)
			cursor.moveToFirst();

		TaskItem item = new TaskItem(cursor.getString(1), cursor.getString(2));
		// return contact
		return item;
	}

	public ArrayList<TaskItem> getAllTask()
	{
		ArrayList<TaskItem> tasktList = new ArrayList<TaskItem>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS ;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst())
		{
			do
			{
				TaskItem item = new TaskItem();
				item.setId(Integer.parseInt(cursor.getString(0)));
				item.setTaskName(cursor.getString(1));
				item.setTaskDescription(cursor.getString(2));
				item.setTaskCreateDate(cursor.getLong(3));
				item.setTaskEndDate(cursor.getLong(4));
				item.setBlongTo(cursor.getString(5));
				if(cursor.getInt(6) == 1)
					item.setToDelete(true);
				
				// Adding contact to list
				tasktList.add(item);
				
			} while (cursor.moveToNext());
		}

		// return contact list
		Log.d("DB","all task");
		return tasktList;
	}
	
	public ArrayList<TaskItem> getAllTaskByName(String str)
	{
		ArrayList<TaskItem> tasktList = new ArrayList<TaskItem>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS + " WHERE " + KEY_CONID + "='" + str + "'";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst())
		{
			do
			{
				TaskItem item = new TaskItem();
				item.setId(Integer.parseInt(cursor.getString(0)));
				item.setTaskName(cursor.getString(1));
				item.setTaskDescription(cursor.getString(2));
				item.setTaskCreateDate(cursor.getLong(3));
				item.setTaskEndDate(cursor.getLong(4));
				item.setBlongTo(cursor.getString(5));
				if(cursor.getInt(6) == 1)
					item.setToDelete(true);
				// Adding contact to list
				tasktList.add(item);
				
			} while (cursor.moveToNext());
		}

		// return contact list
		Log.d("DB","all task");
		return tasktList;
	}

	public int getTaskCount()
	{
		int i=0;
		String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		while(cursor.moveToNext())
		{
			i++;
		} 
	
		cursor.close();

		// return count
		return i;
	}
	
	public void deleteTask(TaskItem item) 
	{
		Log.d("DB","del");
	    SQLiteDatabase db = this.getWritableDatabase();
	    db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
	            new String[] { String.valueOf(item.getId()) });
	    db.close();
	}
	
	public int updateTask(TaskItem item) 
	{
	    SQLiteDatabase db = this.getWritableDatabase();
	 
	    ContentValues values = new ContentValues();
	    values.put(KEY_TASKNAME, item.getTaskName()); // task Name
		values.put(KEY_TASKDIS, item.getTaskDescription()); // task discription
		values.put(KEY_TASKCREATE, item.getTaskCreateDate().toString()); 	// create date
		values.put(KEY_ENDTASK, item.getTaskEndDate().toString());	// task end	 date	
		values.put(KEY_CONID, item.getBelongTo().toString());	// task belong to	
	    // updating row
	    return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
	            new String[] { String.valueOf(item.getId()) });
	}

}

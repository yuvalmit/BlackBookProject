package net.mitrani.blackbook.DB;

import java.util.ArrayList;

import net.mitrani.blackbook.datatype.ContactItem;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandlerContact extends SQLiteOpenHelper
{

	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "ContactTaskManager";

	// Contacts table name
	private static final String TABLE_CONTACTS = "Contacts";

	// Contacts Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_CONNAME = "name";
	private static final String KEY_CONID = "discription";
	
	
	private static DatabaseHandlerContact instance;

	private DatabaseHandlerContact(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public static DatabaseHandlerContact getInstance(Context context)
	{
		if (instance == null)
		{
			instance = new DatabaseHandlerContact(context);
		}
		return instance;
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		Log.d("DB","create start");
		String CREATE_CONTACTS_TABLE = 
										"CREATE TABLE " 
										+ TABLE_CONTACTS 
										+ "("
										+ KEY_ID 
										+ " INTEGER PRIMARY KEY," 
										+ KEY_CONNAME 
										+ " TEXT,"
										+ KEY_CONID 
										+ " TEXT" 
										+ ")";
		db.execSQL(CREATE_CONTACTS_TABLE);
		Log.d("DB","create end");
		 
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		Log.d("DB","upgrade");
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

		// Create tables again
		onCreate(db);

	}

	public void addContact(ContactItem item)
	{
		Log.d("DB","add task");
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		
		values.put(KEY_CONNAME, item.getName()); // contact Name
		values.put(KEY_CONID, item.getLookUpKey()); // contact id
		
		// Inserting Row
		db.insert(TABLE_CONTACTS, null, values);
		db.close(); // Closing database connection
		
		
		
	}

	/*make get all properties*/
	public ContactItem getContact(int id)
	{
		Log.d("DB","get task");
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(	TABLE_CONTACTS,
									new String[]{ KEY_ID, KEY_CONNAME, KEY_CONID },
									KEY_ID + "=?",
									new String[]{ String.valueOf(id) },
									null,
									null,
									null,
									null
								);
		if (cursor != null)
			cursor.moveToFirst();

		ContactItem item = new ContactItem(cursor.getString(1));
		item.setLookUpKey(cursor.getString(2));
		// return contact
		return item;
	}

	public ArrayList<ContactItem> getAllContacts()
	{
		Log.d("DB","get all task");
		ArrayList<ContactItem> conList = new ArrayList<ContactItem>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst())
		{
			do
			{
				ContactItem item = new ContactItem(cursor.getString(1));
				item.setId(Integer.parseInt(cursor.getString(0)));
				item.setLookUpKey(cursor.getString(2));

				// Adding contact to list
				conList.add(item);
				
			} while (cursor.moveToNext());
		}

		// return contact list
		return conList;
	}

	public int getContactCount()
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
	
	public void deleteContact(ContactItem item) 
	{
	    SQLiteDatabase db = this.getWritableDatabase();
	    db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
	            new String[] { String.valueOf(item.getId()) });
	    db.close();
	}
	
	public int updateContact(ContactItem item) 
	{
	    SQLiteDatabase db = this.getWritableDatabase();
	 
	    ContentValues values = new ContentValues();
	    values.put(KEY_CONNAME, item.getName()); // contact Name
		values.put(KEY_CONID, item.getLookUpKey()); // contact id
		
	 
	    // updating row
	    return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
	            new String[] { String.valueOf(item.getId()) });
	}

}

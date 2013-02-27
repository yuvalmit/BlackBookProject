package net.mitrani.blackbook.control;

import android.content.Context;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.provider.ContactsContract;

public class AppManager
{  
	private static AppManager instance;

	private AppManager(){};
	
	public static AppManager getInstance()
	{
		if (instance == null)
			instance = new AppManager();
		return instance;
	}
	
	private Cursor baseQuery(Context context, String id)
	{
		Cursor cur = context.getContentResolver().query(
											ContactsContract.Contacts.CONTENT_URI,
											null,
											BaseColumns._ID + "=?",
											new String[]{id},
											null);
		cur.moveToFirst();
		return cur;
	}

	public String getNameByID(Context context, String id) 
	{
		Cursor deviceContacts = baseQuery( context,  id);
		int displyNameIndex = deviceContacts.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME); 
		return deviceContacts.getString(displyNameIndex);
	}
	
	
	
	

	
}

package net.mitrani.blackbook.datatype;

import java.util.ArrayList;


import net.mitrani.blackbook.DB.DatabaseHandlerContact;


import android.content.Context;

public class ContactsArray {
	
	private static ContactsArray instance ;
	private ArrayList<ContactItem> contactList = new ArrayList<ContactItem>();
	private static DatabaseHandlerContact db; 
	
	private ContactsArray() 
	{
		contactList =  db.getAllContacts();	
	};
	
	public static ContactsArray getInstance(Context context)
	{
		if (instance == null)
		{
			db = DatabaseHandlerContact.getInstance(context);
			instance = new ContactsArray();
		}
		return instance;
	}

	public void addContact(String name)
	{
		contactList.add(new ContactItem(name));
		db.addContact((new ContactItem(name)));
	}
	
	public ContactItem getContact(int pos)
	{
		return contactList.get(pos);
	}
	
	public void deleteContact(int pos)
	{
		db.deleteContact(getContact(pos));
		contactList.remove(pos);
	}
	
	public int getSize()
	{
		return contactList.size();
	}

	public boolean haveItem(ContactItem obj)
	{
		for(int i=0 ; i < getSize() ; i++)
			if(obj.getName().toString().equals(getContact(i).getName().toString()))
			{
				return true;
			}
		return false;
	}
	
	
}

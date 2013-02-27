package net.mitrani.blackbook;


import com.google.analytics.tracking.android.EasyTracker;

import net.mitrani.blackbook.control.AppManager;
import net.mitrani.blackbook.control.ContactsListAdapter;
import net.mitrani.blackbook.datatype.ContactItem;
import net.mitrani.blackbook.datatype.ContactsArray;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;

public class MainActivity extends Activity 
{
	private static final int CONTACT_PICKER_RESULT = 1001;
	private String _ID;

	@Override
	protected void onResume()
	{
		super.onResume();
		ContactsListAdapter adapter = new ContactsListAdapter(this, ContactsArray.getInstance(this));
		GridView gridView = (GridView) findViewById(R.id.main_contactsGridView);
		gridView.setAdapter(adapter);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ContactsListAdapter adapter = new ContactsListAdapter(this, ContactsArray.getInstance(this));
		GridView gridView = (GridView) findViewById(R.id.main_contactsGridView);
		Button addContact = (Button) findViewById(R.id.main_addContact);
		gridView.setAdapter(adapter);
		addContact.setBackgroundColor(Color.argb(100 , 0 , 204 , 0));

		addContact.setOnClickListener(addCon);
		gridView.setOnItemClickListener(conClick);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		if (resultCode == RESULT_OK) 
		{
			switch (requestCode) 
			{
			case CONTACT_PICKER_RESULT:
				Uri result = data.getData();
				String id = result.getLastPathSegment();
				set_ID(id);
				Context context = getApplicationContext();
				ContactsListAdapter adapter = new ContactsListAdapter(context, ContactsArray.getInstance(context));
				ContactItem obj = new ContactItem(AppManager.getInstance().getNameByID(this,get_ID()));
				obj.setLookUpKey(get_ID());
				adapter.addContact(obj);

				break;
			}
			
		} 
	     
	}
	
	
	OnClickListener addCon = new OnClickListener() 
	{
		
		@Override
		public void onClick(View v) 
		{
			Intent intent = new Intent(Intent.ACTION_PICK,Contacts.CONTENT_URI);            
            startActivityForResult(intent, CONTACT_PICKER_RESULT); 
			
		}
	};
	
	
	
	OnItemClickListener conClick = new OnItemClickListener()
	{

		@Override
		public void onItemClick(AdapterView<?> arg0, View v, int pos, long arg3)
		{
			Context context = v.getContext();
			ContactsListAdapter adapter = new ContactsListAdapter(context, ContactsArray.getInstance(context));
			Intent intent = new Intent(context,TaskListActivity.class);
			intent.putExtra("name", adapter.getItem(pos).getName());
			startActivity(intent);
			
		}
	};

	@Override
	protected void onStart()
	{
		super.onStart();
		EasyTracker.getInstance().activityStart(this);
	}

	@Override
	protected void onStop()
	{
		super.onStop();
		EasyTracker.getInstance().activityStop(this);
	}

	public String get_ID()
	{
		return _ID;
	}

	public void set_ID(String _ID)
	{
		this._ID = _ID;
	}
	
	
	
}

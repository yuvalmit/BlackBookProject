package net.mitrani.blackbook;

import com.google.analytics.tracking.android.EasyTracker;

import net.mitrani.blackbook.control.TaskListAdapter;
import net.mitrani.blackbook.datatype.TaskArray;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class TaskListActivity extends Activity
{

	public static int ACTIVIT_CODE = 12;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_list);
		Intent intent = getIntent();
		final TaskListAdapter adapter = new TaskListAdapter(this, TaskArray.getInstance(this));
		adapter.sortByName(intent.getStringExtra("name").toString());
		TextView name = (TextView) findViewById(R.id.task_list_name);
		Button addButton = (Button) findViewById(R.id.task_list_addButton);
		Button delButton = (Button) findViewById(R.id.task_list_deletebutton);
		final ListView lv1 = (ListView) findViewById(R.id.task_list_listview);
		addButton.setOnClickListener(addTask);
		delButton.setOnClickListener(delTask);
		addButton.setBackgroundColor(Color.argb(100 , 0 , 204 , 0));
		delButton.setBackgroundColor(Color.argb(100 , 0 , 204 , 0));
		name.setText(intent.getStringExtra("name").toString());
		lv1.setAdapter(adapter);
		lv1.setClickable(true);
		//lv1.setOnItemClickListener(conClick);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_task_list, menu);
		return true;
	}
	
	
	
	@Override
	public void onBackPressed()
	{
		super.onBackPressed();
		Intent intent = new Intent(getApplicationContext(),MainActivity.class);
		startActivity(intent);
	}



	OnClickListener addTask = new OnClickListener()
	{
		
		@Override
		public void onClick(View v)
		{
			TextView name = (TextView) findViewById(R.id.task_list_name);
			Intent intent = new Intent(getApplicationContext(),AddTaskActivity.class);
			intent.putExtra("name", name.getText().toString());
			startActivity(intent);
			
		}
	};
	
	OnClickListener delTask = new OnClickListener()
	{
		
		@Override
		public void onClick(View v)
		{
			TaskListAdapter adapter = new TaskListAdapter(getApplicationContext(), TaskArray.getInstance(getApplicationContext()));
			for(int i=0 ; i<adapter.getCount() ; i++)
			{
				if(adapter.getItem(i).getToDelete())
					adapter.delItem(i);
			}
			ListView lv1 = (ListView) findViewById(R.id.task_list_listview);
			lv1.setAdapter(adapter);
		}
	};
	
	OnItemClickListener conClick = new OnItemClickListener()
	{

		@Override
		public void onItemClick(AdapterView<?> arg0, View v, int pos, long arg3)
		{
			Log.d("juv","on item click");
			Context context = v.getContext();
			TaskListAdapter adapter = new TaskListAdapter(context, TaskArray.getInstance(context));
			Intent intent = new Intent(v.getContext(),EditTaskActivity.class);
			intent.putExtra("item", adapter.getItem(pos));
			startActivity(intent ); 
			
			
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

}

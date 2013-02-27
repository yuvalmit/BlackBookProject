package net.mitrani.blackbook;

import java.util.Calendar;

import com.google.analytics.tracking.android.EasyTracker;

import net.mitrani.blackbook.control.ReminderBroadCastReceiver;
import net.mitrani.blackbook.control.TaskListAdapter;
import net.mitrani.blackbook.datatype.TaskArray;
import net.mitrani.blackbook.datatype.TaskItem;
import net.mitrani.blackbook.dialog.DateDialogFragment;
import net.mitrani.blackbook.dialog.TimeDialogFragment;

import android.os.Bundle;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class AddTaskActivity extends FragmentActivity
{

	EditText addText;
	EditText addDis;
	DateDialogFragment endDate;
	TimeDialogFragment endTime;
	Button setAlarmBtn;
	TaskListAdapter adapter;
	CheckBox check;
	Button btnRandom;
	Calendar cal = Calendar.getInstance();
	private String contactName;
	static final int DATE_DIALOG_ID = 999;
	
	

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		View v = getLayoutInflater().inflate(R.layout.activity_add_task,null);
		setContentView(v);		
		Button addBT = (Button) findViewById(R.id.addTask_buttonAdd);
		addText = (EditText) findViewById(R.id.addTask_editTextTaskName);
		addDis = (EditText) findViewById(R.id.addTask_editTextTaskDisc);
		setAlarmBtn = (Button) findViewById(R.id.addTask_btnChangeDate);
		Button map = (Button) findViewById(R.id.add_task_mapbutton);
		
		//check = (CheckBox) findViewById(R.id.addTask_checkBox1);
		
		final Intent intent = getIntent();
		contactName = intent.getStringExtra("name").toString();
		adapter = new TaskListAdapter(this, TaskArray.getInstance(this));
		adapter.sortByName(contactName);
		map.setOnClickListener(startMap);
		addBT.setOnClickListener(addTask);
		setAlarmBtn.setOnClickListener(setAlarm);
		addBT.setBackgroundColor(Color.argb(100 , 0 , 204 , 0));
		setAlarmBtn.setBackgroundColor(Color.argb(100 , 0 , 204 , 0));
		map.setBackgroundColor(Color.argb(100 , 0 , 204 , 0));
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.activity_task_list, menu);
		return true;
	}
	
	OnClickListener setAlarm = new OnClickListener()
	{
		
		@Override
		public void onClick(View v)
		{
			endDate = new DateDialogFragment(getApplicationContext());
			endDate.show(getSupportFragmentManager(), "datePicker");
			endTime = new TimeDialogFragment(getApplicationContext());
			endTime.show(getSupportFragmentManager(), "timePicker");
			
		}
	};
	
	OnClickListener addTask = new OnClickListener()
	{
		
		
		@Override
		public void onClick(View v)
		{
			TaskItem obj = new TaskItem();
			obj.setTaskName(addText.getText().toString());
			obj.setTaskDescription(addDis.getText().toString());
			obj.setTaskEndDate(0);
			obj.setBlongTo(contactName);
			if (endDate != null)
			{
				Bundle bundleDate = endDate.getBundle();
				Bundle bundleTime = endTime.getBundle();
				cal.set(bundleDate.getInt("year"),
						bundleDate.getInt("month"),
						bundleDate.getInt("day"),
						bundleTime.getInt("hur"), 
						bundleTime.getInt("min"),0);
				obj.setTaskEndDate(cal.getTimeInMillis());
				alarm(obj);
			}
			
			
			adapter.addItem(obj);
			
	
			// return and recreate task list activity

			Intent intent = new Intent(v.getContext(),TaskListActivity.class);
			intent.putExtra("name", contactName);
			startActivity(intent);
			
		}
	};
	
	OnClickListener startMap = new OnClickListener() 
	{
		
		@Override
		public void onClick(View v) 
		{
			Intent intent = new Intent(v.getContext(),MapActivity.class);
			startActivity(intent);
			
		}
	};
	
	
	private void alarm(TaskItem obj)
	{
		Intent intent = new Intent(this, ReminderBroadCastReceiver.class);
		intent.putExtra("obj", obj);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		alarmManager.set(AlarmManager.RTC_WAKEUP, obj.getTaskEndDate(), pendingIntent);
		

	}
	
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

package net.mitrani.blackbook;

import java.util.Calendar;

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
import android.widget.TextView;

public class EditTaskActivity extends FragmentActivity
{
	private DateDialogFragment	endDate;
	private TimeDialogFragment	endTime;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_task);
		TaskItem item = (TaskItem) getIntent().getSerializableExtra("item");
		TextView name = (TextView)findViewById(R.id.edit_task_title);
		TextView dis = (TextView)findViewById(R.id.edit_task_dis);
		Button save = (Button)findViewById(R.id.edit_task_savebutton);
		Button cancel = (Button)findViewById(R.id.edit_task_cancelbutton);
		Button setAlarm = (Button)findViewById(R.id.Edit_task_setalarmbutton);
		name.setText(item.getTaskName().toString());
		dis.setText(item.getTaskDescription().toString());
		save.setOnClickListener(saveClick);
		cancel.setOnClickListener(cancelClick);
		setAlarm.setOnClickListener(setAlarmClick);
		
		save.setBackgroundColor(Color.argb(100 , 0 , 204 , 0));
		cancel.setBackgroundColor(Color.argb(100 , 0 , 204 , 0));
		setAlarm.setBackgroundColor(Color.argb(100 , 0 , 204 , 0));
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_edit_task, menu);
		return true;
	}
	
	
	OnClickListener saveClick = new OnClickListener()
	{
		
		@Override
		public void onClick(View v)
		{
			TaskListAdapter adapter = new TaskListAdapter(getApplicationContext(), TaskArray.getInstance(getApplicationContext()));
			TaskItem item = (TaskItem) getIntent().getSerializableExtra("item");
			TextView name = (TextView)findViewById(R.id.edit_task_title);
			TextView dis = (TextView)findViewById(R.id.edit_task_dis);
			item.setTaskName(name.getText().toString());
			item.setTaskDescription(dis.getText().toString());
			if (endDate != null)
			{
				Bundle bundleDate = endDate.getBundle();
				Bundle bundleTime = endTime.getBundle();
				Calendar cal = Calendar.getInstance();
				cal.set(bundleDate.getInt("year"),
						bundleDate.getInt("month"),
						bundleDate.getInt("day"),
						bundleTime.getInt("hur"), 
						bundleTime.getInt("min"),0);
				item.setTaskEndDate(cal.getTimeInMillis());
				alarm(item);
			}
			adapter.updateTask(item);
			Intent intent = new Intent(getApplicationContext(),TaskListActivity.class);
			intent.putExtra("name", item.getBelongTo().toString());
			startActivity(intent); 
			
		}
	};
	
	OnClickListener cancelClick = new OnClickListener()
	{
		
		@Override
		public void onClick(View v)
		{
			TaskItem item = (TaskItem) getIntent().getSerializableExtra("item");
			TextView name = (TextView)findViewById(R.id.edit_task_title);
			TextView dis = (TextView)findViewById(R.id.edit_task_dis);
			name.setText(item.getTaskName().toString());
			dis.setText(item.getTaskDescription().toString());
		}
	};
	
	OnClickListener setAlarmClick = new OnClickListener()
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
	
	private void alarm(TaskItem obj)
	{
		Intent intent = new Intent(this, ReminderBroadCastReceiver.class);
		intent.putExtra("obj", obj);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		alarmManager.set(AlarmManager.RTC_WAKEUP, obj.getTaskEndDate(), pendingIntent);
		

	}

}

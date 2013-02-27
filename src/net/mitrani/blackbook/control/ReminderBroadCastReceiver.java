package net.mitrani.blackbook.control;

import net.mitrani.blackbook.MainActivity;
import net.mitrani.blackbook.R;
import net.mitrani.blackbook.datatype.TaskItem;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class ReminderBroadCastReceiver extends BroadcastReceiver
{

	@Override
	public void onReceive(Context context, Intent intent)
	{
		Intent myIntent = new Intent(context, MainActivity.class);
		PendingIntent pendingintent = PendingIntent.getActivity(context, 0, myIntent, 0);
		TaskItem item = (TaskItem) intent.getSerializableExtra("obj");

		NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
		.setSmallIcon(R.drawable.ic_launcher)
		.setContentTitle(item.getTaskName().toString())
		.setContentText(item.getTaskDescription().toString());
		
		builder.setContentIntent(pendingintent);
		NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);  
	    manager.notify(6, builder.build());
		
	}

}

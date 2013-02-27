package net.mitrani.blackbook.control;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import net.mitrani.blackbook.EditTaskActivity;
import net.mitrani.blackbook.R;
import net.mitrani.blackbook.datatype.TaskArray;
import net.mitrani.blackbook.datatype.TaskItem;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;



public class TaskListAdapter extends BaseAdapter
{
	TaskArray myList;
	private LayoutInflater l_Inflater;
	private Context adapterContext;


	static class ViewHolder
	{
		TextView txt_taskName;
		TextView txt_taskDescription;
		TextView txt_taskDateCreate;
		TextView txt_taskDateEnd;
		CheckBox delTask;
	}
	
	
	public TaskListAdapter(Context context, TaskArray task_details)
	{
		adapterContext = context;
		myList = task_details;
		l_Inflater = LayoutInflater.from(adapterContext);

	}

	public void addItem(TaskItem obj)
	{
		myList.addItem(obj);
	}

	@Override
	public int getCount()
	{
		return myList.getSize();
	}

	@Override
	public TaskItem getItem(int index)
	{
		return myList.getItem(index);
	}

	@Override
	public long getItemId(int index)
	{
		return index;
	}
	
	public void sortByName(String name)
	{
		myList.setAllTaskByName(name);
	}
	
	public void delItem(int index)
	{
		myList.delItem(index);
		notifyDataSetChanged();
	}
	
	public void updateTask(TaskItem item)
	{
		myList.updateItem(item);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		
			final ViewHolder holder;
			if (convertView == null)
			{
				convertView = l_Inflater.inflate(R.layout.task_list_item, null);
				holder = new ViewHolder();
				holder.txt_taskName = (TextView) convertView.findViewById(R.id.task_item_name);
				holder.txt_taskDescription = (TextView) convertView.findViewById(R.id.task_item_dis);
				holder.txt_taskDateEnd = (TextView) convertView.findViewById(R.id.task_item_enddate);
				holder.delTask = (CheckBox) convertView.findViewById(R.id.task_item_checkBox1);
				holder.delTask.setOnClickListener(new OnClickListener()
				{
					
					@Override
					public void onClick(View v)
					{
						int pos = (Integer) v.getTag();
						if(holder.delTask.isChecked())
							myList.getItem(pos).setDelTask();
						else
							myList.getItem(pos).unSetDelTask();
						notifyDataSetChanged();
						
						
					}
				});
				holder.txt_taskName.setOnClickListener(editTask);
				convertView.setTag(holder);
				
			} 
			else
			{
				holder = (ViewHolder) convertView.getTag();
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
			holder.txt_taskName.setText(myList.getItem(position).getTaskName());
			if(myList.getItem(position).getTaskEndDate() > Calendar.getInstance().getTimeInMillis())
				holder.txt_taskDateEnd.setText(sdf.format(new Date( myList.getItem(position).getTaskEndDate())));
			holder.txt_taskDescription.setText(myList.getItem(position).getTaskDescription());
			holder.delTask.setTag(position);
			holder.txt_taskName.setTag(position);
			holder.delTask.setChecked(myList.getItem(position).getToDelete());
			//make color gradient
			int col = position*(255/getCount()/2);
			convertView.setBackgroundColor(Color.argb(100 , col , 204 , col));
			return convertView;
				
		
	}
	
	OnClickListener editTask = new OnClickListener()
	{
		
		@Override
		public void onClick(View v)
		{
			Context context = v.getContext();
			int pos = (Integer) v.getTag();
			TaskListAdapter adapter = new TaskListAdapter(context, TaskArray.getInstance(context));
			Intent intent = new Intent(v.getContext(),EditTaskActivity.class);
			intent.putExtra("item", adapter.getItem(pos));
			context.startActivity(intent); 
			
		}
	};
	
	
	
		

	
	
	
	

}
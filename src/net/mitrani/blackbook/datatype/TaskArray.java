package net.mitrani.blackbook.datatype;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import net.mitrani.blackbook.DB.DatabaseHandlerTask;
import android.content.Context;



public class TaskArray
{
	private static TaskArray instance ;
	private ArrayList<TaskItem> theList = new ArrayList<TaskItem>();
	private static DatabaseHandlerTask db; 


	
	private TaskArray()
	{
		theList = db.getAllTask();
	}

	public static TaskArray getInstance(Context context)
	{
		if(instance == null)
		{
			db = DatabaseHandlerTask.getInstance(context);
			instance = new TaskArray();
		}
		return instance;
	}
	

	public void setAllTaskByName(String name)
	{
		theList = db.getAllTaskByName(name);
	}
	
	public void addItem(String name, String dis)
	{
		theList.add(new TaskItem(name,dis));
		db.addTask(new TaskItem(name,dis));
	}
	
	public void addItem(TaskItem obj)
	{
		theList.add(new TaskItem(obj));
		db.addTask(new TaskItem(obj));

	}
	public void addItemDB(String name, String dis)
	{
		
		db.addTask(new TaskItem(name,dis));
	}
	
	public TaskItem getItem(int index)
	{
		return theList.get(index);
		
	}
	
	public void delItem(int index)
	{
		
		 db.deleteTask(getItem(index));
		 theList.remove(index);
	}
	
	public void updateItem(TaskItem item)
	{
		db.updateTask(item);
	}
	
	public int getSize()
	{
		return theList.size();
	}
	
	public void sortByDate(final int dir)//2-new to old, 1-old to new, default old to new
	{ 
		Collections.sort(theList, new Comparator<TaskItem>() 
			{
			  @Override
			public int compare(TaskItem o1, TaskItem o2) 
			  {
				  switch(dir)
				  {
				  	case 1:
				  		return o2.getTaskCreateDate().compareTo(o1.getTaskCreateDate());
				  	case 2:
				  		return o1.getTaskCreateDate().compareTo(o2.getTaskCreateDate());
				  	default :
				  		return o2.getTaskCreateDate().compareTo(o1.getTaskCreateDate());
				  }
			  }
			});
		
	}
	
}
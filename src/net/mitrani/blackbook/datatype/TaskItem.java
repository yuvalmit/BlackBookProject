package net.mitrani.blackbook.datatype;

import java.io.Serializable;


public class TaskItem implements Comparable<TaskItem> , Serializable
{
	

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	private int id;
	private String taskName;
	private String taskDescription;
	private Long taskCreateDate = (long) 0;
	private Long taskEndDate = (long) 0 ;
	private String belongTo ;
	private Boolean toDelete ;


	
	
	public TaskItem( String name, String dis)
	{	
		setTaskName(name);
		setTaskDescription(dis);
		setTaskCreateDate();
		setTaskEndDate();
		setToDelete(false);
		
	}
	public TaskItem(TaskItem obj)
	{	
		setTaskName(obj.getTaskName());
		setTaskDescription(obj.getTaskDescription());
		setTaskCreateDate();
		setTaskEndDate(obj.getTaskEndDate());
		setBlongTo(obj.getBelongTo());
		setToDelete(false);
		
	}
	public TaskItem() 
	{
		setToDelete(false);
	}
	public String getTaskName()
	{
		return taskName;
	}
	public void setTaskName(String taskName)
	{
		this.taskName = taskName;
	}
	public String getTaskDescription()
	{
		return taskDescription;
	}
	public void setTaskDescription(String taskDescription)
	{
		this.taskDescription = taskDescription;
	}
	public Long getTaskCreateDate() 
	{
		return taskCreateDate;
	}
	public void setTaskCreateDate()
	{
		
		this.taskCreateDate = System.currentTimeMillis();
		
	}
	public void setTaskCreateDate(long timeInMilli)
	{
		
		this.taskCreateDate = timeInMilli;
		
	}
	public Long getTaskEndDate()
	{
		return taskEndDate;
	}
	public void setTaskEndDate()
	{
		this.taskEndDate =  System.currentTimeMillis();
	}
	public void setTaskEndDate(long timeInMilli)
	{
		
		this.taskEndDate = timeInMilli;
	}
	@Override
	public int compareTo(TaskItem obj) 
	{
		if (getTaskCreateDate() == 0 || obj.getTaskCreateDate() == 0)
		      return 0;
		
		return getTaskCreateDate().compareTo(obj.getTaskCreateDate());
	}
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getBelongTo()
	{
		return belongTo;
	}
	public void setBlongTo(String belongTo)
	{
		this.belongTo = belongTo;
	}
	public void setDelTask()
	{
		this.setToDelete(true);
	}
	public void unSetDelTask()
	{
		this.setToDelete(false);
	}
	public Boolean getToDelete()
	{
		return toDelete;
	}
	public void setToDelete(Boolean toDelete)
	{
		this.toDelete = toDelete;
	}
	
}

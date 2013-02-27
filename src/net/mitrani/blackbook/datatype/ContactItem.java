package net.mitrani.blackbook.datatype;

public class ContactItem 
{
	
	private int id;
	private String cName;
	private String lookUpKey;
	
	public ContactItem(String cName) 
	{
		super();
		this.cName = cName;
	}

	public String getName() 
	{
		return cName;
	}

	public void setName(String cName) 
	{
		this.cName = cName;
	}

	public String getLookUpKey()
	{
		return lookUpKey;
	}

	public void setLookUpKey(String lookUpKey)
	{
		this.lookUpKey = lookUpKey;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	
	

}

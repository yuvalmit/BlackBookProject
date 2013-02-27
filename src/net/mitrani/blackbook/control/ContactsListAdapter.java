package net.mitrani.blackbook.control;


import net.mitrani.blackbook.R;
import net.mitrani.blackbook.datatype.ContactItem;
import net.mitrani.blackbook.datatype.ContactsArray;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactsListAdapter extends BaseAdapter 
{
	ContactsArray conList;
	private LayoutInflater l_Inflater;
	private Context _context;
	
	static class ViewHolder
	{
		TextView txt_conName;
		ImageView img_con;
	}
	
	public ContactsListAdapter(Context context, ContactsArray con_details)
	{
		this._context = context;
		conList = con_details;
		l_Inflater = LayoutInflater.from(_context);
		
	}

	public void addContact(ContactItem obj)
	{
		if(conList.haveItem(obj));
			conList.addContact(obj.getName());
	}
	
	@Override
	public int getCount() 
	{
		return conList.getSize();
	}

	@Override
	public ContactItem getItem(int pos) 
	{
		return conList.getContact(pos);
	}

	@Override
	public long getItemId(int pos) 
	{
		return pos;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) 
	{
		final ViewHolder holder;
		
		if (convertView == null)
		{
			convertView = l_Inflater.inflate(R.layout.contacts_grid_item, null);
			holder = new ViewHolder();
			holder.txt_conName = (TextView) convertView.findViewById(R.id.con_name);
			holder.img_con = (ImageView) convertView.findViewById(R.id.con_ImageView);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.txt_conName.setText(conList.getContact(position).getName());
		holder.img_con.setImageResource(R.drawable.contactimage);
		return convertView;
	}

}

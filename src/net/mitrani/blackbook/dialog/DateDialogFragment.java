package net.mitrani.blackbook.dialog;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.Toast;

public class DateDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener
{
	Bundle bund = new Bundle();
	DatePickerDialog date;
	Context context;
	
	public DateDialogFragment(){}
	public DateDialogFragment(Context context)
	{
		this.context = context;	
	}
	
	
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) 
    {
    	final Calendar c = Calendar.getInstance();
		int year = c.get( Calendar.YEAR );
		int month = c.get( Calendar.MONTH );
		int day = c.get( Calendar.DAY_OF_MONTH );
        date = new DatePickerDialog(getActivity(), this, year, month, day);
        date.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener()
		{
			
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.cancel();
				
			}
		});
        date.setCancelable(true);
        return date;
    }
	
	
	public Bundle getBundle()
	{
		return bund;
	}
	
	@Override
	public void onDateSet(DatePicker view, int year, int month, int day)
	{
		bund.putInt("year", year);
		bund.putInt("month", month);
		bund.putInt("day", day);
		bund.putString("date",
				"Set Date : " + Integer.toString( day ) + " / " + Integer.toString( month ) + " / "
						+ Integer.toString( year ) );
		
		Toast.makeText(context, bund.getString("date"), Toast.LENGTH_SHORT).show();
		
	}
	
	
	
	
}

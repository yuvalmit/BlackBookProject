package net.mitrani.blackbook.dialog;

import java.util.Calendar;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.TimePicker;
import android.widget.Toast;

public class TimeDialogFragment extends DialogFragment implements OnTimeSetListener
{
	Bundle bund = new Bundle();
	TimePickerDialog time;
	Context context;
	
	public TimeDialogFragment(){}
	public TimeDialogFragment(Context context)
	{
		this.context = context;	
	}
	
	
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) 
    {
    	final Calendar c = Calendar.getInstance();
		int hur = c.get( Calendar.HOUR );
		int min = c.get( Calendar.MINUTE );
        time = new TimePickerDialog(getActivity(), this, hur, min, false);
        time.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener()
		{
			
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.cancel();
				
			}
		});
        time.setCancelable(true);
        return time;
    }
	
	
	public Bundle getBundle()
	{
		return bund;
	}
	
	
	@Override
	public void onTimeSet(TimePicker view, int hur, int min)
	{
		bund.putInt("hur", hur);
		bund.putInt("min", min);

		Toast.makeText(context, hur+" : "+min, Toast.LENGTH_SHORT).show();
		
	}
	
	
	
	
}

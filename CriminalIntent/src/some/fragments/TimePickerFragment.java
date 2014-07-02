/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package some.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TimePicker;
import bignerdranch.android.criminalintent.R;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author jorgwel
 */
public class TimePickerFragment extends DialogFragment {
    
    private static final String TAG = "TimePickerFragment";

    public static final String EXTRA_CURRENT_TIME =
        "com.bignerdranch.android.criminalintent.currenttime";

    private String currentTime;
    
    public static TimePickerFragment newInstance(String time) {
        Bundle args = new Bundle();
//        args.putSerializable(EXTRA_DATE, date);
        args.putSerializable(EXTRA_CURRENT_TIME, time);

        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);

        return fragment;
    }
    
    private void sendResult(int resultCode) {
        
        if (getTargetFragment() == null)
            return;

        Intent i = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_CURRENT_TIME, currentTime);
        i.putExtras(bundle);

        //Enviando el parámetro al fragmento target
        getTargetFragment()
            .onActivityResult(getTargetRequestCode(), resultCode, i);
        
    }
    
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        
        currentTime = (String)getArguments().getSerializable(EXTRA_CURRENT_TIME);

        Integer hour = Integer.parseInt(currentTime.split(":")[0]);
        Integer minute = Integer.parseInt(currentTime.split(":")[1]);
        
        View calendarView = getActivity().getLayoutInflater()
        .inflate(R.layout.dialog_time, null);
        
        TimePicker timePicker = (TimePicker)calendarView.findViewById(R.id.dialog_time_timePicker);
        timePicker.setCurrentHour(hour);
        timePicker.setCurrentMinute(minute);
        
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {

            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                String newTime = hourOfDay + ":" + minute;
                getArguments().putSerializable(EXTRA_CURRENT_TIME, newTime);
                currentTime = newTime;
                Log.i(TAG, "Nueva hora: " + newTime);
            }
            
        });
       
        
        return new AlertDialog.Builder(getActivity())
            .setView(calendarView)
            .setTitle(R.string.time_picker_title)
            .setPositiveButton(
                android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i(TAG, "Enviando resultado de Time");
                        sendResult(Activity.RESULT_OK);
                    }
                }
            )
            .create();
    }
}

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
import bignerdranch.android.criminalintent.R;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author jorgwel
 */
public class DatePickerFragment extends DialogFragment {
    
    private static final String TAG = "DatePickerFragment";
    
    public static final String EXTRA_DATE =
        "com.bignerdranch.android.criminalintent.date";

    private Date mDate;

    public static DatePickerFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_DATE, date);

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);

        return fragment;
    }
    
    private void sendResult(int resultCode) {
        
        if (getTargetFragment() == null)
            return;

        Intent i = new Intent();
        i.putExtra(EXTRA_DATE, mDate);

        //Enviando el parámetro al fragmento target
        getTargetFragment()
            .onActivityResult(getTargetRequestCode(), resultCode, i);
        
    }
    
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        
        mDate = (Date)getArguments().getSerializable(EXTRA_DATE);

        // Create a Calendar to get the year, month, and day
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

    
        
        
        View calendarView = getActivity().getLayoutInflater()
        .inflate(R.layout.dialog_date, null);
        
        DatePicker datePicker = (DatePicker)calendarView.findViewById(R.id.dialog_date_datePicker);
        datePicker.init(year, month, day, new OnDateChangedListener() {
            public void onDateChanged(DatePicker view, int year, int month, int day) {
                // Translate year, month, day into a Date object using a calendar
                mDate = new GregorianCalendar(year, month, day).getTime();

                // Update argument to preserve selected value on rotation
                getArguments().putSerializable(EXTRA_DATE, mDate);
                Log.i(TAG, "Seteando nueva fecha: " + mDate);
            }
        });
        
        return new AlertDialog.Builder(getActivity())
            .setView(calendarView)
            .setTitle(R.string.date_picker_title)
//            .setPositiveButton(android.R.string.ok, null)
            .setPositiveButton(
                android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        sendResult(Activity.RESULT_OK);
                    }
                }
            )
            .create();
    }
}

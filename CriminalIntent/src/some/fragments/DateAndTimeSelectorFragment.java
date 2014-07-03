/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package some.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import bignerdranch.android.criminalintent.Crime;
import bignerdranch.android.criminalintent.CrimeFragment;
import bignerdranch.android.criminalintent.CrimeLab;
import bignerdranch.android.criminalintent.R;
import java.util.UUID;

/**
 *
 * @author jorgwel
 */
public class DateAndTimeSelectorFragment extends DialogFragment {
    
    private static final String TAG = "DateAndTimeSelectorFragment";
    
    private static final String DIALOG_DATE = "date";
    private static final String DIALOG_TIME = "time";
    
    public static final String EXTRA_CRIME = "crimeId";
    
    
    public static final int REQUEST_DATE = 0;
    public static final int REQUEST_TIME = 1;
    public static final  int REQUEST_CRIME_FRAGMENT = 2;
    private Button mDateButton;
    private Button mTimeButton;
    
//    private Crime currentCrime;
    private UUID currentCrimeId;
    
    public static DateAndTimeSelectorFragment newInstance(UUID crimeId, CrimeFragment crimeFragment) {
        
        Bundle args = new Bundle();   
        
        args.putSerializable(EXTRA_CRIME, crimeId);
        
        DateAndTimeSelectorFragment fragment = new DateAndTimeSelectorFragment();
        fragment.setArguments(args);
        
        fragment.setTargetFragment(crimeFragment, REQUEST_CRIME_FRAGMENT);

        return fragment;
    }
    
//    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
//        
//        Log.i(TAG, "Ejecutando onCreateVew en fragment date y time");
//        
//        View v = inflater.inflate(R.layout.dialog_date_and_time, parent, false);
//        Log.i(TAG, "1");
//        currentCrimeId = (UUID)getArguments().getSerializable(EXTRA_CRIME);
//        Log.i(TAG, "2");
//        final Crime crime = CrimeLab.get(getActivity()).getCrime(currentCrimeId);
//        Log.i(TAG, "3");
//        mDateButton = (Button)v.findViewById(R.id.crime_date);       
//        Log.i(TAG, "4");
//        mDateButton.setText(DateFormat.format("EEEE, dd MMMM, yyyy", crime.getDate()));
//        Log.i(TAG, "5");
//        
//        mDateButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                
//                Log.i(TAG, "Ejecutando onClick de dialogDate");
//                
//                FragmentManager fm = getActivity().getSupportFragmentManager();
//
//                DatePickerFragment dialog = DatePickerFragment.newInstance(crime.getDate());
//
//                dialog.show(fm, DIALOG_DATE);
//                
//            }
//        });
//        
//        Log.i(TAG, "6");
//        
//        mTimeButton = (Button) v.findViewById(R.id.crime_time);
//        Log.i(TAG, "7");
//        mTimeButton.setText(crime.getTime());
//        Log.i(TAG, "8");
//        mTimeButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                
//                Log.i(TAG, "OnClick, enviando como parámetro el tiempo: " + crime.getTime());
//                
//                FragmentManager fm = getActivity().getSupportFragmentManager();
//                
//                TimePickerFragment dialog = TimePickerFragment.newInstance(crime.getTime());
//
//                dialog.show(fm, DIALOG_TIME);
//            }
//        });
//        Log.i(TAG, "9");
//        
//        return v;
//    }
//    
//    private void sendResult(int resultCode) {
//        
//        if (getTargetFragment() == null)
//            return;
//
//        Intent i = new Intent();
//    
//
//        //Enviando el parámetro al fragmento target
//        getTargetFragment()
//            .onActivityResult(getTargetRequestCode(), resultCode, i);
//        
//    }
    
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.i(TAG, "Creando diálogo fecha y tiempo");
        View calendarView = getActivity().getLayoutInflater().inflate(R.layout.dialog_date_and_time, null);

        currentCrimeId = (UUID)getArguments().getSerializable(EXTRA_CRIME);
        final Crime crime = CrimeLab.get(getActivity()).getCrime(currentCrimeId);
        mDateButton = (Button)calendarView.findViewById(R.id.crime_date);       
        mDateButton.setText(DateFormat.format("EEEE, dd MMMM, yyyy", crime.getDate()));
        
        mDateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                
                Log.i(TAG, "Ejecutando onClick de dialogDate");
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(crime.getDate());
                dialog.setTargetFragment(getTargetFragment(), REQUEST_DATE);
                dialog.show(fm, DIALOG_DATE);
                getDialog().hide();
                
                
            }
        });
        
        mTimeButton = (Button) calendarView.findViewById(R.id.crime_time);
        mTimeButton.setText(crime.getTime());
        mTimeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                
                Log.i(TAG, "OnClick, enviando como parámetro el tiempo: " + crime.getTime());
                FragmentManager fm = getActivity().getSupportFragmentManager();
                TimePickerFragment dialog = TimePickerFragment.newInstance(crime.getTime());
                dialog.setTargetFragment(getTargetFragment(), REQUEST_TIME);
                dialog.show(fm, DIALOG_TIME);
                getDialog().hide();
                
                
            }
        });

        return new AlertDialog.Builder(getActivity())
            .setView(calendarView)
            .setTitle(R.string.button_text_select_dialog)
            .create();
    }
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        Log.i(TAG, "Creando diálogo fecha y tiempo");
//        View calendarView = getActivity().getLayoutInflater()
//        .inflate(R.layout.dialog_date_and_time, null);
//
//        return new AlertDialog.Builder(getActivity())
//            .setView(calendarView)
//            .setTitle(R.string.button_text_select_dialog)
//            .create();
//    }
    
    
        @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode != Activity.RESULT_OK){
//            return;
//        }
//        
//        if (requestCode == REQUEST_DATE) {
//            Date date = (Date)data
//                .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
//            Log.i(TAG, "Seteando FECHA:" + date);
//            mCrime.setDate(date);
//            mDateButton.setText(mCrime.getDate().toString());
//        } else {
//            String time = (String)data
//                .getSerializableExtra(TimePickerFragment.EXTRA_CURRENT_TIME);
//            Log.i(TAG, "Seteando HORA:" + time);
//            mCrime.setTime(time);
//            mTimeButton.setText(time);
//        }
    }
    
}

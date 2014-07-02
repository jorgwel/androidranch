package bignerdranch.android.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import some.fragments.DatePickerFragment;
import some.fragments.TimePickerFragment;

public class CrimeFragment extends Fragment{
    
    private static final String TAG = "CrimeFragment";
    
    public static final String EXTRA_CRIME_ID = "com.bignerdranch.android.criminalintent.crime_id";
    
    //Constante para mostrar fragmento de fecha en dialogo
    private static final String DIALOG_DATE = "date";
    
    //Código de target
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;
    
    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private Button mTimeButton;
    private CheckBox mSolvedCheckBox;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        UUID crimeId = (UUID)getActivity().getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        UUID crimeId = (UUID)getArguments().getSerializable(EXTRA_CRIME_ID);


        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
//        mCrime = new Crime();
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, parent, false);
        
        mTitleField = (EditText)v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(
                    CharSequence c, int start, int before, int count) {
                mCrime.setTitle(c.toString());
            }

            public void beforeTextChanged(
                    CharSequence c, int start, int count, int after) {
                // This space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // This one too
            }
        });
        
        mDateButton = (Button)v.findViewById(R.id.crime_date);        
        mDateButton.setText(DateFormat.format("EEEE, dd MMMM, yyyy", mCrime.getDate()));
//        mDateButton.setEnabled(false);
        mDateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fm = getActivity()
                        .getSupportFragmentManager();
//                DatePickerFragment dialog = new DatePickerFragment();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getDate());
                
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);

                dialog.show(fm, DIALOG_DATE);
            }
        });
        
        mTimeButton = (Button) v.findViewById(R.id.crime_time);
        
//        final String hour = mCrime.getTime().split(":")[0];
//        final String minute = mCrime.getTime().split(":")[1];
        mTimeButton.setText(mCrime.getTime());
        mTimeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                
                Log.i(TAG, "OnClick, enviando como parámetro el tiempo: " + mCrime.getTime());
                
                FragmentManager fm = getActivity()
                        .getSupportFragmentManager();
                TimePickerFragment dialog = TimePickerFragment.newInstance(mCrime.getTime());
                
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_TIME);

                dialog.show(fm, DIALOG_DATE);
            }
        });
        
        mSolvedCheckBox = (CheckBox)v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Set the crime's solved property
                mCrime.setSolved(isChecked);
            }
        });
        
        return v;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK){
            return;
        }
        
        if (requestCode == REQUEST_DATE) {
            Date date = (Date)data
                .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            Log.i(TAG, "Seteando FECHA:" + date);
            mCrime.setDate(date);
            mDateButton.setText(mCrime.getDate().toString());
        } else {
            String time = (String)data
                .getSerializableExtra(TimePickerFragment.EXTRA_CURRENT_TIME);
            Log.i(TAG, "Seteando HORA:" + time);
            mCrime.setTime(time);
            mTimeButton.setText(time);
        }
    }
    
    
    
    public static CrimeFragment newInstance(UUID crimeId) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_CRIME_ID, crimeId);

        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);

        return fragment;
    }
    
}

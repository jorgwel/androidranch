package com.example.geoquiz;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends Activity {

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mCheatButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
    private TextView mQuestionTextView;
//    private boolean mIsCheater;
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
//    private static final String KEY_IS_CHEATER = "isCheater";
    public static final String CURRENT_INDEX = "currentIndex";
    public static final String CHEATED_VALUES = "cheatedValues";

    private TrueFalse[] mQuestionBank = new TrueFalse[]{
        new TrueFalse(R.string.question_oceans, true),
        new TrueFalse(R.string.question_mideast, false),
        new TrueFalse(R.string.question_africa, false),
        new TrueFalse(R.string.question_americas, true),
        new TrueFalse(R.string.question_asia, true)};
    
    private boolean[] mCheatedQuestionsBank = new boolean[]{
        false, false, false, false, false};

    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate(Bundle) called");

        setContentView(R.layout.activity_quiz);
        
        Log.d(TAG, "La versión del SDK actual es: " + Build.VERSION.SDK_INT);
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            ActionBar actionBar = getActionBar();
            actionBar.setSubtitle("Bodies of Water");
        }
        
        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(TAG, "Botón TRUE clickeado...");
                checkAnswer(true);
            }
        });
        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(TAG, "Botón FALSE clickeado...");
                checkAnswer(false);
            }
        });

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                updateQuestion(true);
            }
        });

        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mIsCheater = false;
                updateQuestion(true);
            }
        });

        mPrevButton = (ImageButton) findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuestion(false);
            }
        });

        mCheatButton = (Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Log.d(TAG, "Presionando botón Cheat");
                Intent i = new Intent(QuizActivity.this, CheatActivity.class);
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
                i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue); 
                i.putExtra(CURRENT_INDEX, mCurrentIndex);
                startActivityForResult(i, 0);
            }
        });
        
        if (savedInstanceState != null) {
            Log.d(TAG, "Ya había un valor guardado, recuperando");
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
//            mIsCheater = savedInstanceState.getBoolean(KEY_IS_CHEATER, false);
            mCheatedQuestionsBank = savedInstanceState.getBooleanArray(CHEATED_VALUES);
            Log.d(TAG, "mCheatedQuestionsBank: " + mCheatedQuestionsBank);
            mCheatedQuestionsBank[mCurrentIndex] = false;//On Rotate, clears "I'm a cheater DATA"
            mNextButton.setEnabled(true);
            mPrevButton.setEnabled(true);
            
        }

        updateQuestion(null);

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putBooleanArray(CHEATED_VALUES, mCheatedQuestionsBank);
    }

    private void updateQuestion(Boolean isForward) {
        if (isForward == null) {
            Log.d(TAG, "No se modificó el mCurrentIndex");
        } else if (isForward == true) {
            mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
            if(mCheatedQuestionsBank[mCurrentIndex])
                mNextButton.setEnabled(false);
            Log.d(TAG, "NEXT mCurrentIndex: " + mCurrentIndex);
        } else if (isForward == false) {
            mCurrentIndex = mCurrentIndex - 1;
            if (mCurrentIndex == -1) {
                mCurrentIndex = mQuestionBank.length - 1;
            }
            if(mCheatedQuestionsBank[mCurrentIndex])
                mPrevButton.setEnabled(false);
            Log.d(TAG, "PREV mCurrentIndex: " + mCurrentIndex);
        }

        int question = mQuestionBank[mCurrentIndex].getQuestion();
        mQuestionTextView.setText(question);

    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();

        int messageResId = 0;
        if (mCheatedQuestionsBank[mCurrentIndex]) {
            messageResId = R.string.judgment_toast;
        } else {
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.quiz, menu);
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
          return;
        }
        boolean isCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
        int indiceDePregunta = data.getIntExtra(CURRENT_INDEX, 0);
        mCheatedQuestionsBank[indiceDePregunta] = isCheater;
        
    }


}

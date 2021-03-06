/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 *
 * @author jorgwel
 */
public class CheatActivity extends Activity {
    private static final String TAG = "CheatActivity";
    private static final String KEY_HAS_CHEATED = "hasCheated";
    private static final String KEY_ANSWER_TRUE = "answerIsTrue";
    
    public static final String EXTRA_ANSWER_IS_TRUE = "com.bignerdranch.android.geoquiz.answer_is_true";
    public static final String EXTRA_ANSWER_SHOWN = "com.bignerdranch.android.geoquiz.answer_shown";

    
    private boolean mAnswerIsTrue;
    private boolean hasCheated;
    
    private int mCurrentQuestionIndex;

    private TextView mAnswerTextView;    
    private Button mShowAnswer;
    
    private TextView mBuildVersionTextView;

    
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        //Recibe el Intent enviado desde QuizActivity
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
        mCurrentQuestionIndex = getIntent().getIntExtra(QuizActivity.CURRENT_INDEX, 0);

        mAnswerTextView = (TextView)findViewById(R.id.answerTextView);

        mShowAnswer = (Button)findViewById(R.id.showAnswerButton);
        
        mBuildVersionTextView = (TextView) findViewById(R.id.buildVersion);
        
        mBuildVersionTextView.setText("Versión de android: " + Build.VERSION.SDK_INT);
        
        setAnswerShownResult(false);
        mShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarResultado();
            }

        });
        
        if(savedInstanceState != null) {
            Log.d(TAG, "Recuperando info de hasCheated");
            hasCheated = savedInstanceState.getBoolean(KEY_HAS_CHEATED);
//            setAnswerShownResult(hasCheated);
            setAnswerShownResult(false);//On Rotate, clears "I'm a cheater DATA"
            mAnswerIsTrue = savedInstanceState.getBoolean(KEY_ANSWER_TRUE);
            Log.d(TAG, "Re iniciando. hasCheated: " + hasCheated);
            if(hasCheated){
                mostrarResultado();
            }
        }
        
        // ToDo add your GUI initialization code here        
    }
    
    private void setAnswerShownResult(boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        data.putExtra(QuizActivity.CURRENT_INDEX, mCurrentQuestionIndex);
        setResult(RESULT_OK, data);
        hasCheated = isAnswerShown;
    }
    
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putBoolean(KEY_HAS_CHEATED, hasCheated);
        savedInstanceState.putBoolean(KEY_ANSWER_TRUE, mAnswerIsTrue);
        
    }

    
    private void mostrarResultado() {
        if (mAnswerIsTrue) {
            mAnswerTextView.setText(R.string.true_button);
        } else {
            mAnswerTextView.setText(R.string.false_button);
        }
        setAnswerShownResult(true);
        
    }
    
    private void ocultarResultado() {
        mAnswerTextView.setText("");
    }
    
}

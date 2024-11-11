package com.youngsophomore.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.util.Pair;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.youngsophomore.R;
import com.youngsophomore.data.CollectionsStorage;
import com.youngsophomore.data.StatParam;
import com.youngsophomore.data.Training;
import com.youngsophomore.fragments.FinishDialogFragment;
import com.youngsophomore.fragments.StopwatchFragment;
import com.youngsophomore.helpers.TrainHelper;

import java.util.ArrayList;
import java.util.Arrays;

public class WordsTrainingActivity extends AppCompatActivity implements
        FinishDialogFragment.FinishDialogListener {
    private static final String DEBUG_TAG = "Gestures";
    private static final String STOPWATCH_FRAGMENT_TAG = "stopwatch_fragment_tag";
    int curWordShowInd = 0, curWordSeqInd = 0, curPaletteSize = TrainHelper.Words.INIT_PAL_SIZE;
    private int mistakesAmount = 0;
    private int trainingDurationSec = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pretrain_sequence_layout);
        SharedPreferences sharedPreferences =
                getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        int wordsCollectionPosition = sharedPreferences.getInt(getString(R.string.saved_words_collection_position_key), 0);;
        int wordShowTime = sharedPreferences.getInt(getString(R.string.saved_word_show_time_key), 2);

        TextView tvCountdown = findViewById(R.id.tv_countdown);
        TextView tvCurWordNum = findViewById(R.id.tv_cur_elem_num);
        CountDownTimer countDownTimer = new CountDownTimer(3000 + 200, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                
                tvCountdown.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                ArrayList<String> wordsCollectionsTitles = CollectionsStorage.getCollectionsTitles(
                        sharedPreferences, getString(R.string.words_collections_titles_key)
                );
                
                String wordsCollectionTitle = wordsCollectionsTitles.get(wordsCollectionPosition);
                String strWordsCollection = sharedPreferences.getString(wordsCollectionTitle, "");
                
                String[] splittedWordsCollection = strWordsCollection.split(" ");
                ArrayList<String> wordsCollection = new ArrayList<>(Arrays.asList(splittedWordsCollection));
                
                if (wordsCollection.size() < curPaletteSize) curPaletteSize = wordsCollection.size();
                tvCountdown.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        getResources().getDimension(R.dimen.w_training_seq_text_size));
                CountDownTimer pretrainSequenceTimer = new CountDownTimer(
                        wordsCollection.size() * wordShowTime * 1000 + 200, wordShowTime * 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        if(curWordShowInd < wordsCollection.size()){
                            tvCountdown.setText(wordsCollection.get(curWordShowInd));
                            ++curWordShowInd;
                            tvCurWordNum.setText(String.valueOf(curWordShowInd));
                            
                        }
                    }

                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public void onFinish() {
                        ArrayList<String> curWordPalette = TrainHelper.Words.generateWordsPalette(
                                wordsCollection, curWordSeqInd, curPaletteSize
                        );
                        setContentView(R.layout.activity_words_training);
                        ConstraintLayout clParent = findViewById(R.id.cst_lt_parent);
                        ConstraintLayout clWordsPal = findViewById(R.id.cst_lt_words_pal);
                        TextView tvWordsSeq = findViewById(R.id.tv_words_seq);
                        tvWordsSeq.setMovementMethod(new ScrollingMovementMethod());
                        String curStrWordsCollection = "";
                        tvWordsSeq.setText(curStrWordsCollection);

                        Bundle bundle = new Bundle();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction()
                                .setReorderingAllowed(true)
                                .add(R.id.frt_cnt_v_stopwatch, StopwatchFragment.class, bundle, STOPWATCH_FRAGMENT_TAG)
                                .commit();
                        // Fill palette with right amount of words
                        ArrayList<Button> btnWordPal = new ArrayList<>();
                        for(int i = 0; i < clWordsPal.getChildCount() - 2; ++i){
                            btnWordPal.add((Button) clWordsPal.getChildAt(i));
                            if (i < curPaletteSize)
                                btnWordPal.get(i).setText(curWordPalette.get(i));
                            else
                                btnWordPal.get(i).setVisibility(View.GONE);
                            int elevPx = getResources().getDimensionPixelSize(R.dimen.btn_stats_elev);
                            btnWordPal.get(i).setElevation(elevPx);
                        }
                        for (int i = 0; i < btnWordPal.size(); ++i){
                            int finalI = i;
                            btnWordPal.get(i).setOnTouchListener(new View.OnTouchListener() {
                                @Override
                                public boolean onTouch(View view, MotionEvent event) {
                                    int action = event.getAction();
                                    int elevPx = getResources().getDimensionPixelSize(R.dimen.btn_stats_elev);
                                    switch(action) {
                                        case (MotionEvent.ACTION_DOWN):
                                            
                                            btnWordPal.get(finalI).setElevation(0);
                                            return true;
                                        case (MotionEvent.ACTION_MOVE):
                                            
                                            btnWordPal.get(finalI).setElevation(elevPx);
                                            return false;
                                        case (MotionEvent.ACTION_UP):
                                            
                                            btnWordPal.get(finalI).setElevation(elevPx);
                                            if(btnWordPal.get(finalI).getText() == wordsCollection.get(curWordSeqInd)){
                                                
                                                tvWordsSeq.setText(tvWordsSeq.getText() + " " + wordsCollection.get(curWordSeqInd));
                                                ++curWordSeqInd;
                                                if (curWordSeqInd + curPaletteSize > wordsCollection.size())
                                                    --curPaletteSize;
                                                ArrayList<String> curWordPalette = TrainHelper.Words.generateWordsPalette(
                                                        wordsCollection, curWordSeqInd, curPaletteSize
                                                );
                                                for(int j = 0; j < clWordsPal.getChildCount() - 2; ++j){
                                                    if (j < curPaletteSize)
                                                        btnWordPal.get(j).setText(curWordPalette.get(j));
                                                    else
                                                        btnWordPal.get(j).setVisibility(View.GONE);
                                                }
                                            }
                                            else {
                                                
                                                ++mistakesAmount;
                                                clParent.setBackgroundColor(
                                                        getResources().getColor(R.color.seq_training_wrong_choice
                                                        ));
                                                new Handler().postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        clParent.setBackgroundColor(
                                                                getResources().getColor(R.color.white_blue)
                                                        );
                                                    }
                                                }, 60);
                                            }
                                            if(curWordSeqInd == wordsCollection.size()){
                                                
                                                StopwatchFragment stopwatchFragment =
                                                        (StopwatchFragment) fragmentManager.findFragmentByTag(STOPWATCH_FRAGMENT_TAG);
                                                trainingDurationSec = stopwatchFragment.getDecisecond() / 10;
                                                stopwatchFragment.finishStopwatch();
                                                TrainHelper.updateStatParams(sharedPreferences,
                                                        new Pair<>(
                                                                TrainHelper.getStatParamKey(Training.WRD, StatParam.TOTNUMMOVES, wordsCollection.size(), wordShowTime),
                                                                mistakesAmount
                                                        ),
                                                        new Pair<>(
                                                                TrainHelper.getStatParamKey(Training.WRD, StatParam.TOTNUMTIME, wordsCollection.size(), wordShowTime),
                                                                trainingDurationSec
                                                        ),
                                                        new Pair<>(
                                                                TrainHelper.getStatParamKey(Training.WRD, StatParam.TOTNUMTRAINS, wordsCollection.size(), wordShowTime),
                                                                1
                                                        ));
                                                DialogFragment finishFragment = new FinishDialogFragment(
                                                        trainingDurationSec + " с.",
                                                        getResources().getString(R.string.seq_train_mistakes_amount),
                                                        String.valueOf(mistakesAmount)
                                                );
                                                finishFragment.show(getSupportFragmentManager(), "FinishDialogFragment");
                                            }
                                            return true;
                                        default:
                                            return false;
                                    }
                                }
                            });
                        }

                    }
                }.start();
            }
        }.start();

    }

    @Override
    public void onFinishPosClick(DialogFragment dialog) {
        onBackPressed();
    }
}
package com.youngsophomore.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

import com.youngsophomore.R;
import com.youngsophomore.data.CollectionsStorage;
import com.youngsophomore.fragments.StopwatchFragment;
import com.youngsophomore.helpers.TrainHelper;

import java.util.ArrayList;
import java.util.Arrays;

public class WordsTrainingActivity extends AppCompatActivity {
    private static final String DEBUG_TAG = "Gestures";
    private static final String STOPWATCH_FRAGMENT_TAG = "stopwatch_fragment_tag";
    int curWordShowInd = 0, curWordSeqInd = 0, curPaletteSize = TrainHelper.Words.INIT_PAL_SIZE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(DEBUG_TAG, "before setContentView(R.layout.pretrain_sequence_layout)");
        setContentView(R.layout.pretrain_sequence_layout);
        Log.d(DEBUG_TAG, "after setContentView(R.layout.pretrain_sequence_layout)");
        SharedPreferences sharedPreferences =
                getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        int wordsCollectionPosition = sharedPreferences.getInt(getString(R.string.saved_words_collection_position_key), 0);;
        int wordShowTime = sharedPreferences.getInt(getString(R.string.saved_word_show_time_key), 2);
        Log.d(DEBUG_TAG, "wordsCollectionPosition and wordShowTime = " +
                wordsCollectionPosition + " " +
                wordShowTime);

        TextView tvCountdown = findViewById(R.id.tv_countdown);
        TextView tvCurWordNum = findViewById(R.id.tv_cur_elem_num);
        CountDownTimer countDownTimer = new CountDownTimer(3000 + 200, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d(DEBUG_TAG, "onTick: millisUntilFinished = " + millisUntilFinished);
                tvCountdown.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                // get string with all titles from sharedpref
                // split it and get unique title by the wordsCollectionPosition
                ArrayList<String> wordsCollectionsTitles = CollectionsStorage.getCollectionsTitles(
                        sharedPreferences, getString(R.string.words_collections_titles_key)
                );
                Log.d(DEBUG_TAG, wordsCollectionsTitles.toString());
                String wordsCollectionTitle = wordsCollectionsTitles.get(wordsCollectionPosition);
                // get words collection from sharedpref via title
                String strWordsCollection = sharedPreferences.getString(wordsCollectionTitle, "");
                Log.d(DEBUG_TAG, strWordsCollection);
                String[] splittedWordsCollection = strWordsCollection.split(" ");
                ArrayList<String> wordsCollection = new ArrayList<>(Arrays.asList(splittedWordsCollection));
                Log.d(DEBUG_TAG, wordsCollection.toString());
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
                            Log.d(DEBUG_TAG, "curWordInd = " + curWordShowInd);
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
                        // Запуск секундомера
                        Bundle bundle = new Bundle();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction()
                                .setReorderingAllowed(true)
                                .add(R.id.frgt_view, StopwatchFragment.class, bundle, STOPWATCH_FRAGMENT_TAG)
                                .commit();
                        //Log.d(DEBUG_TAG, "clWords.getChildCount() = " + clWords.getChildCount());
                        // Fill palette with right amount of words
                        ArrayList<Button> btnWordPal = new ArrayList<>();
                        for(int i = 0; i < clWordsPal.getChildCount() - 2; ++i){
                            btnWordPal.add((Button) clWordsPal.getChildAt(i));
                            btnWordPal.get(i).setText(curWordPalette.get(i));
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
                                            Log.d(DEBUG_TAG, "cvWordPal.get(i) onTouch. Action was DOWN");
                                            btnWordPal.get(finalI).setElevation(0);
                                            return true;
                                        case (MotionEvent.ACTION_MOVE):
                                            Log.d(DEBUG_TAG, "btnWordPal.get(i) onTouch. Action was MOVE");
                                            btnWordPal.get(finalI).setElevation(elevPx);
                                            return false;
                                        case (MotionEvent.ACTION_UP):
                                            Log.d(DEBUG_TAG, "btnWordPal.get(i) onTouch. Action was UP");
                                            btnWordPal.get(finalI).setElevation(elevPx);
                                            if(btnWordPal.get(finalI).getText() == wordsCollection.get(curWordSeqInd)){
                                                Log.d(DEBUG_TAG, "YOU CHOSE CORRECT WORD");
                                                tvWordsSeq.setText(tvWordsSeq.getText() + " " + wordsCollection.get(curWordSeqInd));
                                                ++curWordSeqInd;
                                                if (curWordSeqInd + curPaletteSize > wordsCollection.size())
                                                    --curPaletteSize;
                                                ArrayList<String> curWordPalette = TrainHelper.Words.generateWordsPalette(
                                                        wordsCollection, curWordSeqInd, curPaletteSize
                                                );
                                                for(int j = 0; j < clWordsPal.getChildCount() - 2; ++j){
                                                    Log.d(DEBUG_TAG, "j=" + j);
                                                    if (j < curPaletteSize)
                                                        btnWordPal.get(j).setText(curWordPalette.get(j));
                                                    else
                                                        btnWordPal.get(j).setVisibility(View.GONE);
                                                }
                                            }
                                            else {
                                                Log.d(DEBUG_TAG, "YOU CHOSE WRONG WORD");
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
                                                // Add 1 to mistakes amount
                                            }
                                            if(curWordSeqInd == wordsCollection.size()){
                                                Log.d(DEBUG_TAG, "ALL COLORS CHOSEN CORRECT");
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
}
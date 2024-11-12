package com.youngsophomore.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.youngsophomore.R;
import com.youngsophomore.data.StatParam;
import com.youngsophomore.data.Training;
import com.youngsophomore.fragments.FinishDialogFragment;
import com.youngsophomore.fragments.StopwatchFragment;
import com.youngsophomore.helpers.TrainHelper;

import java.util.ArrayList;

public class ColorsTrainingActivity extends AppCompatActivity implements
        FinishDialogFragment.FinishDialogListener {
    private static final String DEBUG_TAG = "Gestures";
    private static final String STOPWATCH_FRAGMENT_TAG = "stopwatch_fragment_tag";
    int curColorShowInd = 0, curColorSeqInd = 0;
    private int mistakesAmount = 0;
    private int trainingDurationSec = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pretrain_sequence_layout);

        SharedPreferences sharedPreferences =
                getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        int colorsAmount = sharedPreferences.getInt(getString(R.string.saved_colors_amount_key), 6);
        int distinctColorsAmount = sharedPreferences.getInt(getString(R.string.saved_distinct_colors_amount_key), 2);
        int colorShowTime = sharedPreferences.getInt(getString(R.string.saved_color_show_time_key), 2);

        TextView tvCountdown = findViewById(R.id.tv_countdown);
        CountDownTimer countDownTimer = new CountDownTimer(3000 + 200, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvCountdown.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                ArrayList<Integer> palette = TrainHelper.Colors.generatePalette(distinctColorsAmount);
                ArrayList<Integer> colorSeq = TrainHelper.Colors.generateColors(colorsAmount, palette);

                ConstraintLayout constraintLayout = findViewById(R.id.cst_lt_pretrain);
                constraintLayout.removeView(findViewById(R.id.tv_countdown));
                ImageView ivCurColor = new ImageView(getApplicationContext());
                ivCurColor.setId(View.generateViewId());
                ivCurColor.setBackgroundColor(getResources().getColor(colorSeq.get(curColorShowInd)));
                ConstraintLayout.LayoutParams ivCurColorParams = new ConstraintLayout.LayoutParams(
                        0,
                        0
                );
                ivCurColor.setLayoutParams(ivCurColorParams);
                constraintLayout.addView(ivCurColor);
                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(constraintLayout);
                constraintSet.connect(ivCurColor.getId(), ConstraintSet.TOP,
                        ConstraintSet.PARENT_ID, ConstraintSet.TOP);
                constraintSet.connect(ivCurColor.getId(), ConstraintSet.BOTTOM,
                        ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
                constraintSet.connect(ivCurColor.getId(), ConstraintSet.START,
                        ConstraintSet.PARENT_ID, ConstraintSet.START);
                constraintSet.connect(ivCurColor.getId(), ConstraintSet.END,
                        ConstraintSet.PARENT_ID, ConstraintSet.END);
                constraintSet.setDimensionRatio(ivCurColor.getId(), "1:1");
                constraintSet.applyTo(constraintLayout);

                TextView tvCurColorNum = findViewById(R.id.tv_cur_elem_num);
                CountDownTimer pretrainSequenceTimer = new CountDownTimer(
                        colorsAmount * colorShowTime * 1000 + 200, colorShowTime * 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        if(curColorShowInd < colorSeq.size()){
                            ivCurColor.setBackgroundColor(getResources().getColor(colorSeq.get(curColorShowInd)));
                            ++curColorShowInd;
                            tvCurColorNum.setText(String.valueOf(curColorShowInd));
                        }
                    }

                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public void onFinish() {
                        setContentView(R.layout.activity_colors_training);
                        Bundle bundle = new Bundle();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction()
                                .setReorderingAllowed(true)
                                .add(R.id.frt_cnt_v_stopwatch_clr, StopwatchFragment.class, bundle, STOPWATCH_FRAGMENT_TAG)
                                .commit();
                        ConstraintLayout cstLtClr = findViewById(R.id.cst_lt_clr);
                        // Make all ImageView background white_blue
                        // Fill palette with right amount of colors
                        ArrayList<ImageView> ivColorSeq = new ArrayList<>();
                        ArrayList<CardView> cvColorPal = new ArrayList<>();
                        for(int i = 1; i < 40; ++i){
                            if(i <= 24){
                                ivColorSeq.add((ImageView) cstLtClr.getChildAt(i));
                            }
                            else {
                                CardView cvCurColorPal = (CardView) cstLtClr.getChildAt(i);
                                if(i - 25 < distinctColorsAmount){
                                    cvColorPal.add(cvCurColorPal);
                                }
                                else{
                                    cvCurColorPal.setVisibility(View.GONE);
                                }

                            }
                        }
                        for(ImageView iv : ivColorSeq){
                            iv.setVisibility(View.INVISIBLE);
                        }
                        for (int i = 0; i < cvColorPal.size(); ++i){
                            cvColorPal.get(i).getChildAt(0).setBackgroundTintList(
                                    ColorStateList.valueOf(getResources().getColor(palette.get(i))));
                            int finalI = i;
                            cvColorPal.get(i).getChildAt(0).setOnTouchListener(new View.OnTouchListener() {
                                @Override
                                public boolean onTouch(View view, MotionEvent event) {
                                    int action = event.getAction();
                                    switch(action) {
                                        case (MotionEvent.ACTION_DOWN):
                                            cvColorPal.get(finalI).setCardElevation(0);
                                            return true;
                                        case (MotionEvent.ACTION_UP):
                                            int elevPx = getResources().getDimensionPixelSize(R.dimen.btn_stats_elev);
                                            cvColorPal.get(finalI).setCardElevation(elevPx);
                                            if(palette.get(finalI).intValue() == colorSeq.get(curColorSeqInd).intValue()){
                                                ivColorSeq.get(curColorSeqInd).setVisibility(View.VISIBLE);
                                                ivColorSeq.get(curColorSeqInd).setBackgroundColor(
                                                        getResources().getColor(colorSeq.get(curColorSeqInd))
                                                );
                                                ++curColorSeqInd;
                                            }
                                            else {
                                                ++mistakesAmount;
                                                cstLtClr.setBackgroundColor(
                                                        getResources().getColor(R.color.seq_training_wrong_choice
                                                ));
                                                new Handler().postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        cstLtClr.setBackgroundColor(
                                                                getResources().getColor(R.color.white_blue)
                                                        );
                                                    }
                                                }, 60);
                                            }
                                            if(curColorSeqInd == colorsAmount){
                                                StopwatchFragment stopwatchFragment =
                                                        (StopwatchFragment) fragmentManager.findFragmentByTag(STOPWATCH_FRAGMENT_TAG);
                                                trainingDurationSec = stopwatchFragment.getDecisecond() / 10;
                                                stopwatchFragment.finishStopwatch();
                                                TrainHelper.updateStatParams(sharedPreferences,
                                                        new Pair<>(
                                                                TrainHelper.getStatParamKey(Training.CLR, StatParam.TOTNUMMOVES, colorsAmount, distinctColorsAmount, colorShowTime),
                                                                mistakesAmount
                                                        ),
                                                        new Pair<>(
                                                                TrainHelper.getStatParamKey(Training.CLR, StatParam.TOTNUMTIME, colorsAmount, distinctColorsAmount, colorShowTime),
                                                                trainingDurationSec
                                                        ),
                                                        new Pair<>(
                                                                TrainHelper.getStatParamKey(Training.CLR, StatParam.TOTNUMTRAINS, colorsAmount, distinctColorsAmount, colorShowTime),
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
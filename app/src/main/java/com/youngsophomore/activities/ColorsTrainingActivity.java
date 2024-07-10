package com.youngsophomore.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Guideline;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

import com.youngsophomore.R;
import com.youngsophomore.fragments.StopwatchFragment;
import com.youngsophomore.helpers.TrainHelper;

import java.util.ArrayList;

public class ColorsTrainingActivity extends AppCompatActivity {
    private static final String DEBUG_TAG = "Gestures";
    private static final String STOPWATCH_FRAGMENT_TAG = "stopwatch_fragment_tag";
    int curColorShowInd = 0, curColorSeqInd = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Отображение обратного отсчёта перед тренировкой
        setContentView(R.layout.pretrain_sequence_layout);
        // Загрузка данных из сохранённых настроек

        SharedPreferences sharedPreferences =
                getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        int colorsAmount = sharedPreferences.getInt(getString(R.string.saved_colors_amount_key), 6);
        int distinctColorsAmount = sharedPreferences.getInt(getString(R.string.saved_distinct_colors_amount_key), 2);
        int colorShowTime = sharedPreferences.getInt(getString(R.string.saved_color_show_time_key), 2);
        Log.d(DEBUG_TAG, "saved colorsAmount = " + colorsAmount +
                ", distinctColorsAmount = " + distinctColorsAmount + ", colorShowTime = " + colorShowTime);

        TextView tvCountdown = findViewById(R.id.tv_countdown);
        CountDownTimer countDownTimer = new CountDownTimer(3000 + 200, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d(DEBUG_TAG, "onTick: millisUntilFinished = " + millisUntilFinished);
                tvCountdown.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                ArrayList<Integer> palette = TrainHelper.Colors.generatePalette(distinctColorsAmount);
                ArrayList<Integer> colorSeq = TrainHelper.Colors.generateColors(colorsAmount, palette);

                ConstraintLayout constraintLayout = findViewById(R.id.cnstrnt_lyt_pretrain);
                constraintLayout.removeView(findViewById(R.id.tv_countdown));
                ImageView ivCurColor = new ImageView(getApplicationContext());
                ivCurColor.setId(View.generateViewId());
                ivCurColor.setBackgroundColor(getResources().getColor(colorSeq.get(curColorShowInd)));
                //++curColorShowInd;

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
                            Log.d(DEBUG_TAG, "curColorInd = " + curColorShowInd);
                        }
                    }

                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public void onFinish() {
                        setContentView(R.layout.activity_colors_training);
                        ConstraintLayout clColors = findViewById(R.id.cnstrnt_lyt_colors);
                        // Запуск секундомера
                        Bundle bundle = new Bundle();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction()
                                .setReorderingAllowed(true)
                                .add(R.id.frgt_view, StopwatchFragment.class, bundle, STOPWATCH_FRAGMENT_TAG)
                                .commit();
                        Log.d(DEBUG_TAG, "clColors.getChildCount() = " + clColors.getChildCount());
                        // Make all ImageView background white_blue
                        // Fill palette with right amount of colors
                        ArrayList<ImageView> ivColorSeq = new ArrayList<>();
                        ArrayList<CardView> cvColorPal = new ArrayList<>();
                        for(int i = 1; i < 40; ++i){
                            if(i <= 24){
                                ivColorSeq.add((ImageView) clColors.getChildAt(i));
                            }
                            else {
                                CardView cvCurColorPal = (CardView) clColors.getChildAt(i);
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
                                            Log.d(DEBUG_TAG, "cvColorPal.get(i) onTouch. Action was DOWN");
                                            cvColorPal.get(finalI).setCardElevation(0);
                                            return true;
                                        case (MotionEvent.ACTION_UP):
                                            int elevPx = getResources().getDimensionPixelSize(R.dimen.btn_stats_elev);
                                            Log.d(DEBUG_TAG, "cvColorPal.get(i) onTouch. Action was UP");
                                            cvColorPal.get(finalI).setCardElevation(elevPx);
                                            if(palette.get(finalI).intValue() == colorSeq.get(curColorSeqInd).intValue()){
                                                ivColorSeq.get(curColorSeqInd).setVisibility(View.VISIBLE);
                                                ivColorSeq.get(curColorSeqInd).setBackgroundColor(
                                                        getResources().getColor(colorSeq.get(curColorSeqInd))
                                                );
                                                ++curColorSeqInd;
                                                Log.d(DEBUG_TAG, "YOU CHOSE CORRECT COLOR");
                                            }
                                            else {
                                                Log.d(DEBUG_TAG, "YOU CHOSE WRONG COLOR");
                                                clColors.setBackgroundColor(
                                                        getResources().getColor(R.color.seq_training_wrong_choice
                                                ));
                                                new Handler().postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        clColors.setBackgroundColor(
                                                                getResources().getColor(R.color.white_blue)
                                                        );
                                                    }
                                                }, 60);
                                                // Add 1 to mistakes amount
                                            }
                                            if(curColorSeqInd == colorsAmount){
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
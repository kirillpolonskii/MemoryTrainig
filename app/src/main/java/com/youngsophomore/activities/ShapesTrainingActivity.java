package com.youngsophomore.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
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

public class ShapesTrainingActivity extends AppCompatActivity implements
        FinishDialogFragment.FinishDialogListener {
    private static final String DEBUG_TAG = "Gestures";
    private static final String STOPWATCH_FRAGMENT_TAG = "stopwatch_fragment_tag";
    int curShapeShowInd = 0, curShapeSeqInd = 0;
    private int mistakesAmount = 0;
    private int trainingDurationSec = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.pretrain_sequence_layout);

        SharedPreferences sharedPreferences =
                getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        int shapesAmount = sharedPreferences.getInt(getString(R.string.saved_shapes_amount_key), 4);
        int distinctShapesAmount = sharedPreferences.getInt(getString(R.string.saved_distinct_shapes_amount_key), 2);
        int shapeShowTime = sharedPreferences.getInt(getString(R.string.saved_shape_show_time_key), 2);

        TextView tvCountdown = findViewById(R.id.tv_countdown);
        CountDownTimer countDownTimer = new CountDownTimer(3000 + 200, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                
                tvCountdown.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                ArrayList<Integer> paletteShp = TrainHelper.Shapes.generatePalette(distinctShapesAmount);
                ArrayList<Integer> shapesSeq = TrainHelper.Shapes.generateShapesSequence(shapesAmount, paletteShp);
                // Replace TextView for countdown with ImageView for sequence of shapes
                ConstraintLayout constraintLayout = findViewById(R.id.cst_lt_pretrain);
                constraintLayout.removeView(findViewById(R.id.tv_countdown));
                ImageView ivCurShape = new ImageView(getApplicationContext());
                ivCurShape.setId(View.generateViewId());
                ivCurShape.setBackgroundResource(shapesSeq.get(curShapeShowInd));
                ConstraintLayout.LayoutParams ivCurShapeParams = new ConstraintLayout.LayoutParams(
                        0,
                        0
                );
                ivCurShapeParams.setMarginStart(20);
                ivCurShapeParams.setMarginEnd(20);
                ivCurShape.setLayoutParams(ivCurShapeParams);
                constraintLayout.addView(ivCurShape);
                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(constraintLayout);
                constraintSet.connect(ivCurShape.getId(), ConstraintSet.TOP,
                        ConstraintSet.PARENT_ID, ConstraintSet.TOP);
                constraintSet.connect(ivCurShape.getId(), ConstraintSet.BOTTOM,
                        ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
                constraintSet.connect(ivCurShape.getId(), ConstraintSet.START,
                        ConstraintSet.PARENT_ID, ConstraintSet.START);
                constraintSet.connect(ivCurShape.getId(), ConstraintSet.END,
                        ConstraintSet.PARENT_ID, ConstraintSet.END);
                constraintSet.setDimensionRatio(ivCurShape.getId(), "1:1");
                constraintSet.applyTo(constraintLayout);

                TextView tvCurShapeNum = findViewById(R.id.tv_cur_elem_num);
                CountDownTimer pretrainSequenceTimer = new CountDownTimer(
                        shapesAmount * shapeShowTime * 1000 + 200, shapeShowTime * 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        if(curShapeShowInd < shapesSeq.size()){
                            ivCurShape.setBackgroundResource(shapesSeq.get(curShapeShowInd));
                            ++curShapeShowInd;
                            tvCurShapeNum.setText(String.valueOf(curShapeShowInd));
                        }
                    }

                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public void onFinish() {
                        setContentView(R.layout.activity_shapes_training);
                        ConstraintLayout clShapes = findViewById(R.id.cst_lt_parent_shp);
                        Bundle bundle = new Bundle();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction()
                                .setReorderingAllowed(true)
                                .add(R.id.frt_cnt_v_stopwatch_shp, StopwatchFragment.class, bundle, STOPWATCH_FRAGMENT_TAG)
                                .commit();
                        
                        // Fill palette with right amount of shapes
                        ArrayList<ImageView> ivShapesSeq = new ArrayList<>();
                        ArrayList<ImageButton> btnShapesSet = new ArrayList<>();
                        for(int i = 1; i < 34; ++i){
                            if(i <= 24){
                                ivShapesSeq.add((ImageView) clShapes.getChildAt(i));
                            }
                            else {
                                ImageButton btnCurShape = (ImageButton) clShapes.getChildAt(i);
                                if(i - 25 < distinctShapesAmount){
                                    btnShapesSet.add(btnCurShape);
                                }
                                else{
                                    btnCurShape.setVisibility(View.GONE);
                                }

                            }
                        }
                        for(ImageView iv : ivShapesSeq){
                            iv.setVisibility(View.INVISIBLE);
                        }
                        for (int i = 0; i < btnShapesSet.size(); ++i){
                            btnShapesSet.get(i).setImageResource(paletteShp.get(i));
                            int finalI = i;
                            btnShapesSet.get(i).setOnTouchListener(new View.OnTouchListener() {
                                @Override
                                public boolean onTouch(View view, MotionEvent event) {
                                    int action = event.getAction();
                                    switch(action) {
                                        case (MotionEvent.ACTION_DOWN):
                                            btnShapesSet.get(finalI).setElevation(0);
                                            return true;
                                        case (MotionEvent.ACTION_UP):
                                            int elevPx = getResources().getDimensionPixelSize(R.dimen.btn_stats_elev);
                                            btnShapesSet.get(finalI).setElevation(elevPx);
                                            // TODO: добавить условие проверки индекса < размера
                                            if(curShapeSeqInd < shapesAmount && paletteShp.get(finalI).intValue() == shapesSeq.get(curShapeSeqInd).intValue()){
                                                ivShapesSeq.get(curShapeSeqInd).setVisibility(View.VISIBLE);
                                                ivShapesSeq.get(curShapeSeqInd).setBackgroundResource(
                                                        shapesSeq.get(curShapeSeqInd)
                                                );
                                                ++curShapeSeqInd;
                                                
                                            }
                                            else {
                                                
                                                ++mistakesAmount;
                                                clShapes.setBackgroundColor(
                                                        getResources().getColor(R.color.seq_training_wrong_choice
                                                        ));
                                                new Handler().postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        clShapes.setBackgroundColor(
                                                                getResources().getColor(R.color.white_blue)
                                                        );
                                                    }
                                                }, 60);
                                            }
                                            if(curShapeSeqInd == shapesAmount){
                                                StopwatchFragment stopwatchFragment =
                                                        (StopwatchFragment) fragmentManager.findFragmentByTag(STOPWATCH_FRAGMENT_TAG);
                                                trainingDurationSec = stopwatchFragment.getDecisecond() / 10;
                                                stopwatchFragment.finishStopwatch();
                                                TrainHelper.updateStatParams(sharedPreferences,
                                                        new Pair<>(
                                                                TrainHelper.getStatParamKey(Training.SHP, StatParam.TOTNUMMOVES, shapesAmount, distinctShapesAmount, shapeShowTime),
                                                                mistakesAmount
                                                        ),
                                                        new Pair<>(
                                                                TrainHelper.getStatParamKey(Training.SHP, StatParam.TOTNUMTIME, shapesAmount, distinctShapesAmount, shapeShowTime),
                                                                trainingDurationSec
                                                        ),
                                                        new Pair<>(
                                                                TrainHelper.getStatParamKey(Training.SHP, StatParam.TOTNUMTRAINS, shapesAmount, distinctShapesAmount, shapeShowTime),
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
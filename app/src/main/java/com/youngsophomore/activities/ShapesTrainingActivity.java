package com.youngsophomore.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

import com.youngsophomore.R;
import com.youngsophomore.fragments.StopwatchFragment;
import com.youngsophomore.helpers.TrainHelper;

import java.util.ArrayList;

public class ShapesTrainingActivity extends AppCompatActivity {
    private static final String DEBUG_TAG = "Gestures";
    private static final String STOPWATCH_FRAGMENT_TAG = "stopwatch_fragment_tag";
    int curShapeShowInd = 0, curShapeSeqInd = 0;
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
        Log.d(DEBUG_TAG, "saved shapesAmount = " + shapesAmount +
                ", distinctShapesAmount = " + distinctShapesAmount + ", shapeShowTime = " + shapeShowTime);

        TextView tvCountdown = findViewById(R.id.tv_countdown);
        CountDownTimer countDownTimer = new CountDownTimer(3000 + 200, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d(DEBUG_TAG, "onTick: millisUntilFinished = " + millisUntilFinished);
                tvCountdown.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                ArrayList<Integer> shapesSet = TrainHelper.Shapes.generateShapesSet(distinctShapesAmount);
                ArrayList<Integer> shapesSeq = TrainHelper.Shapes.generateShapesSequence(shapesAmount, shapesSet);

                ConstraintLayout constraintLayout = findViewById(R.id.cnstrnt_lyt_pretrain);
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
                            // Log.d(DEBUG_TAG, "curShapeShowInd = " + curShapeShowInd);
                        }
                    }

                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public void onFinish() {
                        setContentView(R.layout.activity_shapes_training);
                        ConstraintLayout clShapes = findViewById(R.id.cnstrnt_lyt_shapes);
                        // Запуск секундомера
                        Bundle bundle = new Bundle();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction()
                                .setReorderingAllowed(true)
                                .add(R.id.frgt_view, StopwatchFragment.class, bundle, STOPWATCH_FRAGMENT_TAG)
                                .commit();
                        Log.d(DEBUG_TAG, "clShapes.getChildCount() = " + clShapes.getChildCount());
                        // Make all ImageView background white_blue
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
                            Log.d(DEBUG_TAG, "i = " + i + "btnShapesSet.size() = " + btnShapesSet.size());
                            btnShapesSet.get(i).setImageResource(shapesSet.get(i));
                            int finalI = i;
                            btnShapesSet.get(i).setOnTouchListener(new View.OnTouchListener() {
                                @Override
                                public boolean onTouch(View view, MotionEvent event) {
                                    int action = event.getAction();
                                    switch(action) {
                                        case (MotionEvent.ACTION_DOWN):
                                            Log.d(DEBUG_TAG, "cvShapePal.get(i) onTouch. Action was DOWN");
                                            btnShapesSet.get(finalI).setElevation(0);
                                            return true;
                                        case (MotionEvent.ACTION_UP):
                                            int elevPx = getResources().getDimensionPixelSize(R.dimen.btn_stats_elev);
                                            Log.d(DEBUG_TAG, "cvShapePal.get(i) onTouch. Action was UP");
                                            btnShapesSet.get(finalI).setElevation(elevPx);
                                            if(shapesSet.get(finalI).intValue() == shapesSeq.get(curShapeSeqInd).intValue()){
                                                ivShapesSeq.get(curShapeSeqInd).setVisibility(View.VISIBLE);
                                                ivShapesSeq.get(curShapeSeqInd).setBackgroundResource(
                                                        shapesSeq.get(curShapeSeqInd)
                                                );
                                                ++curShapeSeqInd;
                                                Log.d(DEBUG_TAG, "YOU CHOSE CORRECT SHAPE");
                                            }
                                            else {
                                                Log.d(DEBUG_TAG, "YOU CHOSE WRONG SHAPE");
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
                                                // Add 1 to mistakes amount
                                            }
                                            if(curShapeSeqInd == shapesAmount){
                                                Log.d(DEBUG_TAG, "ALL SHAPES CHOSEN CORRECT");
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
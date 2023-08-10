package com.youngsophomore.viewgroups;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.motion.widget.MotionScene;

import com.addisonelliott.segmentedbutton.SegmentedButtonGroup;
import com.youngsophomore.R;

import java.util.Arrays;

public class MyMotionLayout extends MotionLayout implements MotionLayout.TransitionListener {
    private static final String DEBUG_TAG = "Gestures";

    private ImageButton btnInfo;
    private SegmentedButtonGroup segmentedButtonGroup;
    private ImageButton btnStats;
    private ImageButton btnMahjongSettings;
    private Button btnMahjongTraining;
    private Button btnColorsTraining;
    private Button btnFiguresTraining;
    private Button btnWordsTraining;
    private Button btnPhrasesTraining;
    private Button btnDetailsTraining;
    public MyMotionLayout(@NonNull Context context) {
        super(context);
        Log.d(DEBUG_TAG, "In MyMotionLayout(Context context)");
    }

    public MyMotionLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Log.d(DEBUG_TAG, "In MyMotionLayout(Context context, AttributeSet attrs)");

    }

    public MyMotionLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.d(DEBUG_TAG, "In MyMotionLayout(Context context, AttributeSet attrs, defStyleAttr)");
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent motionEvent){
        int action = motionEvent.getAction();
        btnInfo = findViewById(R.id.btn_info);
        btnStats = findViewById(R.id.btn_stats);
        segmentedButtonGroup = findViewById(R.id.sgbtn_enru);
        btnMahjongSettings = findViewById(R.id.btn_mahjong_settings);

        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                Log.d(DEBUG_TAG, "onInterceptTouchEvent. Action was DOWN");
                if(btnStats != null && touchEventInsideTargetView(btnStats, motionEvent) ||
                    btnInfo != null && touchEventInsideTargetView(btnInfo, motionEvent) ||
                    segmentedButtonGroup != null && touchEventInsideTargetView(segmentedButtonGroup, motionEvent) ||
                    btnMahjongSettings != null && touchEventInsideTargetView(btnMahjongSettings, motionEvent)){
                    Log.d(DEBUG_TAG, "onInterceptTouchEvent. Action was DOWN. return false");

                    return false;

                }
                else{
                    Log.d(DEBUG_TAG, "onInterceptTouchEvent. btn_info is null");
                }
                return super.onTouchEvent(motionEvent);
                //return false;
            case (MotionEvent.ACTION_MOVE):
                Log.d(DEBUG_TAG, "onInterceptTouchEvent. Action was MOVE");
                return super.onInterceptTouchEvent(motionEvent);
                //return false;
            case (MotionEvent.ACTION_UP):
                Log.d(DEBUG_TAG, "onInterceptTouchEvent. Action was UP");
                return super.onInterceptTouchEvent(motionEvent);
                //return false;
            case (MotionEvent.ACTION_CANCEL):
                Log.d(DEBUG_TAG, "onInterceptTouchEvent. Action was CANCEL");
                return false;
            case (MotionEvent.ACTION_BUTTON_PRESS):
                Log.d(DEBUG_TAG, "onInterceptTouchEvent. Action was PRESSED");
                return onTouchEvent(motionEvent);
            //return true;
            case (MotionEvent.ACTION_OUTSIDE):
                Log.d(DEBUG_TAG, "onInterceptTouchEvent. Movement occurred outside bounds " +
                        "of current screen element");
                return false;
            default:
                return false;
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){
        int action = motionEvent.getAction();
        MyMotionLayout myMotionLayout = findViewById(R.id.motion_layout);
        btnMahjongTraining = findViewById(R.id.btn_mahjong_training);
        btnColorsTraining = findViewById(R.id.btn_colors_training);
        btnFiguresTraining = findViewById(R.id.btn_figures_training);
        btnWordsTraining = findViewById(R.id.btn_words_training);
        btnPhrasesTraining = findViewById(R.id.btn_phrases_training);
        btnDetailsTraining = findViewById(R.id.btn_details_training);
        Button btnWithEvent = new Button(getContext());

        boolean eventInTrainingBtn = false;
        boolean setInBaseState = false;
        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                Log.d(DEBUG_TAG, "onTouchEvent in MyMotionLayout. Action was DOWN");
                if(touchEventInsideTargetView(btnMahjongTraining, motionEvent))
                    btnWithEvent = btnMahjongTraining;
                if(touchEventInsideTargetView(btnColorsTraining, motionEvent))
                    btnWithEvent = btnColorsTraining;
                if(touchEventInsideTargetView(btnFiguresTraining, motionEvent))
                    btnWithEvent = btnFiguresTraining;
                if(touchEventInsideTargetView(btnWordsTraining, motionEvent))
                    btnWithEvent = btnWordsTraining;
                if(touchEventInsideTargetView(btnPhrasesTraining, motionEvent))
                    btnWithEvent = btnPhrasesTraining;
                if(touchEventInsideTargetView(btnDetailsTraining, motionEvent))
                    btnWithEvent = btnDetailsTraining;

                return super.onTouchEvent(motionEvent);
            case (MotionEvent.ACTION_MOVE):
                Log.d(DEBUG_TAG, "onTouchEvent in MyMotionLayout. Action was MOVE");
                if(!eventBtnCorrespondsWithState(btnWithEvent, myMotionLayout.getCurrentState()) ||
                        myMotionLayout.getCurrentState() != -1){
                    myMotionLayout.transitionToState(R.id.base_state);
                }
                return super.onTouchEvent(motionEvent);
            case (MotionEvent.ACTION_UP):
                Log.d(DEBUG_TAG, "onTouchEvent in MyMotionLayout. Action was UP");
                Log.d(DEBUG_TAG, "onTouchEvent in MyMotionLayout. Is state == mahjong_end " + (myMotionLayout.getCurrentState() == R.id.mahjong_end));
                return super.onTouchEvent(motionEvent);
            case (MotionEvent.ACTION_CANCEL):
                Log.d(DEBUG_TAG, "onTouchEvent in MyMotionLayout. Action was CANCEL");
                return false;
            case (MotionEvent.ACTION_OUTSIDE):
                Log.d(DEBUG_TAG, "onTouchEvent in MyMotionLayout. Movement occurred outside bounds " +
                        "of current screen element");
                return false;
            default:
                return false;
        }
    }

    private boolean eventBtnCorrespondsWithState(Button eventBtn, int curState){
        return (eventBtn == btnMahjongTraining && curState == R.id.mahjong_end) ||
                (eventBtn == btnColorsTraining && curState == R.id.colors_end) ||
                (eventBtn == btnFiguresTraining && curState == R.id.figures_end) ||
                (eventBtn == btnWordsTraining && curState == R.id.words_end) ||
                (eventBtn == btnPhrasesTraining && curState == R.id.phrases_end) ||
                (eventBtn == btnDetailsTraining && curState == R.id.details_end)
                || (curState == -1)
                ;
    }


    private boolean touchEventInsideTargetView(View v, MotionEvent ev) {
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        int viewX = location[0];
        int viewY = location[1];
        float evX = ev.getRawX();
        float evY = ev.getRawY();
        int viewWidth = v.getWidth();
        int viewHeight = v.getHeight();
//        Log.d(DEBUG_TAG, "touchEventInsideTargetView -- view: " + Arrays.toString(location) +
//                                v.getWidth() + " " + v.getHeight());
//        Log.d(DEBUG_TAG, "touchEventInsideTargetView -- event: " +
//                                ev.getX() + " " + ev.getY() + " " + ev.getRawX() + " " + ev.getRawY());
        return evX >= viewX && evX <= viewX + viewWidth &&
                evY >= viewY && evY <= viewY + viewHeight;

    }


    @Override
    public void onTransitionStarted(MotionLayout motionLayout, int startId, int endId) {
        Log.d(DEBUG_TAG, "onTransitionStarted.");
    }

    @Override
    public void onTransitionChange(MotionLayout motionLayout, int startId, int endId, float progress) {
        Log.d(DEBUG_TAG, "onTransitionChange.");
    }

    @Override
    public void onTransitionCompleted(MotionLayout motionLayout, int currentId) {
        Log.d(DEBUG_TAG, "onTransitionCompleted. Is state == mahjong_end" + (currentId == R.id.mahjong_end));
    }

    @Override
    public void onTransitionTrigger(MotionLayout motionLayout, int triggerId, boolean positive, float progress) {
        Log.d(DEBUG_TAG, "onTransitionTrigger.");
    }
}


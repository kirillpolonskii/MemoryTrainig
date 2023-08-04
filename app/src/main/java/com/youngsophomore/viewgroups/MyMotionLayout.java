package com.youngsophomore.viewgroups;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.MotionLayout;

import com.addisonelliott.segmentedbutton.SegmentedButtonGroup;
import com.youngsophomore.R;

public class MyMotionLayout extends MotionLayout {
    private static final String DEBUG_TAG = "Gestures";

    private ImageButton btn_info;
    private SegmentedButtonGroup segmentedButtonGroup;
    private ImageButton btn_stats;
    public MyMotionLayout(@NonNull Context context) {
        super(context);
        Log.d(DEBUG_TAG, "In MyMotionLayout(Context context)");
    }

    public MyMotionLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Log.d(DEBUG_TAG, "In MyMotionLayout(Context context, AttributeSet attrs)");
        btn_info = findViewById(R.id.btn_info);
        btn_stats = findViewById(R.id.btn_stats);
    }

    public MyMotionLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.d(DEBUG_TAG, "In MyMotionLayout(Context context, AttributeSet attrs, defStyleAttr)");
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent motionEvent){
        int action = motionEvent.getAction();

        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                Log.d(DEBUG_TAG, "onInterceptTouchEvent. Action was DOWN");
                if(touchEventInsideTargetView(btn_info, motionEvent)){
                    return false;
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
        switch (action) {
            case (MotionEvent.ACTION_BUTTON_PRESS):
                Log.d(DEBUG_TAG, "onTouchEvent in MyMotionLayout. Action was PRESSED");
                return super.onTouchEvent(motionEvent);
                // return true;
            case (MotionEvent.ACTION_DOWN):
                Log.d(DEBUG_TAG, "onTouchEvent in MyMotionLayout. Action was DOWN");
                return super.onTouchEvent(motionEvent);
            case (MotionEvent.ACTION_MOVE):
                Log.d(DEBUG_TAG, "onTouchEvent in MyMotionLayout. Action was MOVE");
                return super.onTouchEvent(motionEvent);
            case (MotionEvent.ACTION_UP):
                Log.d(DEBUG_TAG, "onTouchEvent in MyMotionLayout. Action was UP");
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
    private boolean touchEventInsideTargetView(View v, MotionEvent ev) {
        return ev.getX() > v.getLeft() && ev.getX() < v.getRight() &&
                ev.getY() > v.getBottom() && ev.getY() < v.getTop();
    }
}


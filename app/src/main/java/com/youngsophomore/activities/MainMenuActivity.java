package com.youngsophomore.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.core.view.GestureDetectorCompat;
import androidx.core.view.MotionEventCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.addisonelliott.segmentedbutton.SegmentedButton;
import com.addisonelliott.segmentedbutton.SegmentedButtonGroup;
import com.youngsophomore.R;

public class MainMenuActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    private static final String DEBUG_TAG = "Gestures";
    private GestureDetectorCompat gestureDetector;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Log.d(DEBUG_TAG, "IN onCreate()");
        gestureDetector = new GestureDetectorCompat(this, this);
        ImageButton btnStats = findViewById(R.id.btn_stats);
        btnStats.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        Log.d(DEBUG_TAG, "btnStats onTouch. Action was DOWN");
                        view.setElevation(0);
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        Log.d(DEBUG_TAG, "btnStats onTouch. Action was MOVE");
                        return true;
                    case (MotionEvent.ACTION_UP):
                        int elevPx = getResources().getDimensionPixelSize(R.dimen.btn_stats_elev);
                        Log.d(DEBUG_TAG, "btnStats onTouch. Action was UP. open statistics" +
                                ", R.dimen.btn_stats_elev = " + R.dimen.btn_stats_elev +
                                ", elev = " + elevPx);
                        view.setElevation(elevPx);
                        return true;
                    case (MotionEvent.ACTION_CANCEL):
                        Log.d(DEBUG_TAG, "btnStats onTouch. Action was CANCEL");
                        return true;
                    case (MotionEvent.ACTION_OUTSIDE):
                        Log.d(DEBUG_TAG, "btnStats onTouch. Movement occurred outside bounds " +
                                "of current screen element");
                        return true;
                    default:
                        return false;
                }
            }
        });
        ImageButton btnInfo = findViewById(R.id.btn_info);
        btnInfo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        Log.d(DEBUG_TAG, "btnInfo onTouch. Action was DOWN");
                        view.setElevation(0);
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        Log.d(DEBUG_TAG, "btnInfo onTouch. Action was MOVE");
                        return true;
                    case (MotionEvent.ACTION_UP):
                        int elevPx = getResources().getDimensionPixelSize(R.dimen.btn_info_elev);
                        Log.d(DEBUG_TAG, "btnInfo onTouch. Action was UP. open statistics" +
                                ", R.dimen.btn_info_elev = " + R.dimen.btn_info_elev +
                                ", elev = " + elevPx);
                        view.setElevation(elevPx);
                        return true;
                    case (MotionEvent.ACTION_CANCEL):
                        Log.d(DEBUG_TAG, "btnInfo onTouch. Action was CANCEL");
                        return true;
                    case (MotionEvent.ACTION_OUTSIDE):
                        Log.d(DEBUG_TAG, "btnInfo onTouch. Movement occurred outside bounds " +
                                "of current screen element");
                        return true;
                    default:
                        return false;
                }
            }
        });
        SegmentedButtonGroup sgBtnGroup = findViewById(R.id.sgbtn_enru);
        sgBtnGroup.setOnPositionChangedListener(new SegmentedButtonGroup.OnPositionChangedListener() {
            @Override
            public void onPositionChanged(final int position) {
                // Handle stuff here
                Log.d(DEBUG_TAG, "setOnPositionChangedListener: position = " + position);
                //sgBtnGroup.setElevation(0);
            }
        });
        /*sgBtnGroup.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        Log.d(DEBUG_TAG, "sgBtnGroup onTouch. Action was DOWN");
                        view.setElevation(0);
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        Log.d(DEBUG_TAG, "sgBtnGroup onTouch. Action was MOVE");
                        return true;
                    case (MotionEvent.ACTION_UP):
                        int elevPx = getResources().getDimensionPixelSize(R.dimen.sgbtn_elev);
                        Log.d(DEBUG_TAG, "sgBtnGroup onTouch. Action was UP. open statistics" +
                                ", R.dimen.sgbtn_elev = " + R.dimen.sgbtn_elev +
                                ", elev = " + elevPx);
                        view.setElevation(elevPx);
                        return true;
                    case (MotionEvent.ACTION_CANCEL):
                        Log.d(DEBUG_TAG, "sgBtnGroup onTouch. Action was CANCEL");
                        return true;
                    case (MotionEvent.ACTION_OUTSIDE):
                        Log.d(DEBUG_TAG, "sgBtnGroup onTouch. Movement occurred outside bounds " +
                                "of current screen element");
                        return true;
                    default:
                        return false;
                }
            }
        });*/
        SegmentedButton sgBtnEn = sgBtnGroup.getButton(0);
        sgBtnEn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        Log.d(DEBUG_TAG, "sgBtnEn onTouch. Action was DOWN");
                        sgBtnGroup.setElevation(0);
                        //sgBtnGroup.onTouchEvent(motionEvent);
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        Log.d(DEBUG_TAG, "sgBtnEn onTouch. Action was MOVE");
                        return true;
                    case (MotionEvent.ACTION_UP):
                        int elevPx = getResources().getDimensionPixelSize(R.dimen.sgbtn_elev);
                        Log.d(DEBUG_TAG, "sgBtnEn onTouch. Action was UP. open statistics" +
                                ", R.dimen.sgbtn_elev = " + R.dimen.sgbtn_elev +
                                ", elev = " + elevPx);
                        //sgBtnGroup.onTouchEvent(motionEvent);
                        sgBtnGroup.setElevation(elevPx);

                        return true;
                    case (MotionEvent.ACTION_CANCEL):
                        Log.d(DEBUG_TAG, "sgBtnEn onTouch. Action was CANCEL");
                        return true;
                    case (MotionEvent.ACTION_OUTSIDE):
                        Log.d(DEBUG_TAG, "sgBtnEn onTouch. Movement occurred outside bounds " +
                                "of current screen element");
                        return true;
                    default:
                        return false;
                }
            }
        });
        SegmentedButton sgBtnRu = sgBtnGroup.getButton(1);
        sgBtnRu.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        Log.d(DEBUG_TAG, "sgBtnRu onTouch. Action was DOWN");
                        sgBtnGroup.setElevation(0);
                        //sgBtnGroup.onTouchEvent(motionEvent);
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        Log.d(DEBUG_TAG, "sgBtnRu onTouch. Action was MOVE");
                        return true;
                    case (MotionEvent.ACTION_UP):
                        int elevPx = getResources().getDimensionPixelSize(R.dimen.sgbtn_elev);
                        Log.d(DEBUG_TAG, "sgBtnRu onTouch. Action was UP. open statistics" +
                                ", R.dimen.sgbtn_elev = " + R.dimen.sgbtn_elev +
                                ", elev = " + elevPx);
                        //sgBtnGroup.onTouchEvent(motionEvent);
                        sgBtnGroup.setElevation(elevPx);

                        return true;
                    case (MotionEvent.ACTION_CANCEL):
                        Log.d(DEBUG_TAG, "sgBtnRu onTouch. Action was CANCEL");
                        return true;
                    case (MotionEvent.ACTION_OUTSIDE):
                        Log.d(DEBUG_TAG, "sgBtnRu onTouch. Movement occurred outside bounds " +
                                "of current screen element");
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        int action = event.getAction();
//        switch(action) {
//            case (MotionEvent.ACTION_DOWN) :
//                Log.d(DEBUG_TAG,"Action was DOWN");
//                return true;
//            case (MotionEvent.ACTION_MOVE) :
//                Log.d(DEBUG_TAG,"Action was MOVE");
//                return true;
//            case (MotionEvent.ACTION_UP) :
//                Log.d(DEBUG_TAG,"Action was UP");
//                return true;
//            case (MotionEvent.ACTION_CANCEL) :
//                Log.d(DEBUG_TAG,"Action was CANCEL");
//                return true;
//            case (MotionEvent.ACTION_OUTSIDE) :
//                Log.d(DEBUG_TAG,"Movement occurred outside bounds " +
//                        "of current screen element");
//                return true;
//            default :
//                return super.onTouchEvent(event);
//        }
        if (this.gestureDetector.onTouchEvent(event)) {
            return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(@NonNull MotionEvent motionEvent) {
        Log.d(DEBUG_TAG,"onDown: " + motionEvent.toString());
        return true;
    }

    @Override
    public void onShowPress(@NonNull MotionEvent motionEvent) {
        Log.d(DEBUG_TAG, "onShowPress: " + motionEvent.toString());
    }

    @Override
    public boolean onSingleTapUp(@NonNull MotionEvent motionEvent) {
        Log.d(DEBUG_TAG, "onSingleTapUp: " + motionEvent.toString());
        return true;
    }

    @Override
    public boolean onScroll(@Nullable MotionEvent motionEvent, @NonNull MotionEvent motionEvent1, float v, float v1) {
        Log.d(DEBUG_TAG, "onScroll: " + motionEvent.toString() + motionEvent1.toString());
        return true;
    }

    @Override
    public void onLongPress(@NonNull MotionEvent motionEvent) {
        Log.d(DEBUG_TAG, "onLongPress: " + motionEvent.toString());
    }

    @Override
    public boolean onFling(@Nullable MotionEvent motionEvent, @NonNull MotionEvent motionEvent1, float v, float v1) {
        Log.d(DEBUG_TAG, "onFling: " + motionEvent.toString() + motionEvent1.toString());
        return true;
    }
    public int dpToPx(int dp){
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
    public float convertDpToPixel(float dp, Context context){
        return dp * ((float) context.getResources().getDisplayMetrics().density / DisplayMetrics.DENSITY_DEFAULT);
    }
}
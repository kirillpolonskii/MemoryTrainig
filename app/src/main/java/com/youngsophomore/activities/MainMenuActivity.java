package com.youngsophomore.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.core.view.GestureDetectorCompat;
import androidx.core.view.MotionEventCompat;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

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
        btnStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence text = "OPEN STATISTICS";
                Toast toast = Toast.makeText(MainMenuActivity.this, text, Toast.LENGTH_LONG);
                toast.show();
                Log.d(DEBUG_TAG, "open statistics");
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
}
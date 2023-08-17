package com.youngsophomore.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.NumberPicker;

import com.addisonelliott.segmentedbutton.SegmentedButton;
import com.addisonelliott.segmentedbutton.SegmentedButtonGroup;
import com.youngsophomore.R;

public class ShapesSettingsActivity extends AppCompatActivity {
    private static final String DEBUG_TAG = "Gestures";
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shapes_settings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.tbr_shapes_settings_title));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NumberPicker pckrShapesAmount = findViewById(R.id.pckr_shapes_amount);
        NumberPicker pckrDistinctShapesAmount = findViewById(R.id.pckr_distinct_shapes_amount);
        pckrShapesAmount.setMinValue(4);
        pckrShapesAmount.setMaxValue(25);
        pckrDistinctShapesAmount.setMinValue(2);
        pckrDistinctShapesAmount.setMaxValue(4);
        pckrShapesAmount.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if(newVal < 10){
                    pckrDistinctShapesAmount.setMaxValue(newVal);
                }
                else{
                    pckrDistinctShapesAmount.setMaxValue(9);
                }
            }
        });

        NumberPicker pckrShapeShowTime = findViewById(R.id.pckr_shape_show_time);
        pckrShapeShowTime.setMinValue(1);
        pckrShapeShowTime.setMaxValue(6);

        SegmentedButtonGroup sgBtnGroupMaxRepeat = findViewById(R.id.sgbtn_max_repeat_shapes_amount);

        SharedPreferences sharedPreferences =
                getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        int shapesAmount = sharedPreferences.getInt(getString(R.string.saved_shapes_amount_key), 4);
        int distinctShapesAmount = sharedPreferences.getInt(getString(R.string.saved_distinct_shapes_amount_key), 2);
        int maxRepeatShapesAmount = sharedPreferences.getInt(getString(R.string.saved_max_repeat_shapes_amount_key), 1);
        int shapeShowTime = sharedPreferences.getInt(getString(R.string.saved_shape_show_time_key), 2);

        pckrShapesAmount.setValue(shapesAmount);
        pckrDistinctShapesAmount.setValue(distinctShapesAmount);
        sgBtnGroupMaxRepeat.setPosition(maxRepeatShapesAmount, false);
        pckrShapeShowTime.setValue(shapeShowTime);

        SegmentedButton sgBtnMaxRepeat0 = sgBtnGroupMaxRepeat.getButton(0);
        sgBtnMaxRepeat0.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        Log.d(DEBUG_TAG, "sgBtnMaxRepeat0 onTouch. Action was DOWN");
                        sgBtnGroupMaxRepeat.setElevation(0);
                        //sgBtnGroup.onTouchEvent(motionEvent);
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        Log.d(DEBUG_TAG, "sgBtnMaxRepeat0 onTouch. Action was MOVE");
                        return true;
                    case (MotionEvent.ACTION_UP):
                        int elevPx = getResources().getDimensionPixelSize(R.dimen.sgbtn_bones_elev);
                        Log.d(DEBUG_TAG, "sgBtnMaxRepeat0 onTouch. Action was UP");
                        //sgBtnGroup.onTouchEvent(motionEvent);
                        sgBtnGroupMaxRepeat.setElevation(elevPx);

                        return true;
                    default:
                        return false;
                }
            }
        });
        SegmentedButton sgBtnMaxRepeat1 = sgBtnGroupMaxRepeat.getButton(1);
        sgBtnMaxRepeat1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        Log.d(DEBUG_TAG, "sgBtnMaxRepeat1 onTouch. Action was DOWN");
                        sgBtnGroupMaxRepeat.setElevation(0);
                        //sgBtnGroup.onTouchEvent(motionEvent);
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        Log.d(DEBUG_TAG, "sgBtnMaxRepeat1 onTouch. Action was MOVE");
                        return true;
                    case (MotionEvent.ACTION_UP):
                        int elevPx = getResources().getDimensionPixelSize(R.dimen.sgbtn_bones_elev);
                        Log.d(DEBUG_TAG, "sgBtnMaxRepeat1 onTouch. Action was UP");
                        //sgBtnGroup.onTouchEvent(motionEvent);
                        sgBtnGroupMaxRepeat.setElevation(elevPx);

                        return true;
                    default:
                        return false;
                }
            }
        });
        SegmentedButton sgBtnMaxRepeat2 = sgBtnGroupMaxRepeat.getButton(2);
        sgBtnMaxRepeat2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        Log.d(DEBUG_TAG, "sgBtnMaxRepeat2 onTouch. Action was DOWN");
                        sgBtnGroupMaxRepeat.setElevation(0);
                        //sgBtnGroup.onTouchEvent(motionEvent);
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        Log.d(DEBUG_TAG, "sgBtnMaxRepeat2 onTouch. Action was MOVE");
                        return true;
                    case (MotionEvent.ACTION_UP):
                        int elevPx = getResources().getDimensionPixelSize(R.dimen.sgbtn_bones_elev);
                        Log.d(DEBUG_TAG, "sgBtnMaxRepeat2 onTouch. Action was UP");
                        //sgBtnGroup.onTouchEvent(motionEvent);
                        sgBtnGroupMaxRepeat.setElevation(elevPx);

                        return true;
                    default:
                        return false;
                }
            }
        });
        SegmentedButton sgBtnMaxRepeat3 = sgBtnGroupMaxRepeat.getButton(3);
        sgBtnMaxRepeat3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        Log.d(DEBUG_TAG, "sgBtnMaxRepeat3 onTouch. Action was DOWN");
                        sgBtnGroupMaxRepeat.setElevation(0);
                        //sgBtnGroup.onTouchEvent(motionEvent);
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        Log.d(DEBUG_TAG, "sgBtnMaxRepeat3 onTouch. Action was MOVE");
                        return true;
                    case (MotionEvent.ACTION_UP):
                        int elevPx = getResources().getDimensionPixelSize(R.dimen.sgbtn_bones_elev);
                        Log.d(DEBUG_TAG, "sgBtnMaxRepeat3 onTouch. Action was UP");
                        //sgBtnGroup.onTouchEvent(motionEvent);
                        sgBtnGroupMaxRepeat.setElevation(elevPx);

                        return true;
                    default:
                        return false;
                }
            }
        });
        SegmentedButton sgBtnMaxRepeat4 = sgBtnGroupMaxRepeat.getButton(4);
        sgBtnMaxRepeat4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        Log.d(DEBUG_TAG, "sgBtnMaxRepeat4 onTouch. Action was DOWN");
                        sgBtnGroupMaxRepeat.setElevation(0);
                        //sgBtnGroup.onTouchEvent(motionEvent);
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        Log.d(DEBUG_TAG, "sgBtnMaxRepeat4 onTouch. Action was MOVE");
                        return true;
                    case (MotionEvent.ACTION_UP):
                        int elevPx = getResources().getDimensionPixelSize(R.dimen.sgbtn_bones_elev);
                        Log.d(DEBUG_TAG, "sgBtnMaxRepeat4 onTouch. Action was UP");
                        //sgBtnGroup.onTouchEvent(motionEvent);
                        sgBtnGroupMaxRepeat.setElevation(elevPx);

                        return true;
                    default:
                        return false;
                }
            }
        });


        ImageButton btnSaveSettings = findViewById(R.id.btn_save);
        btnSaveSettings.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = event.getAction();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        Log.d(DEBUG_TAG, "btnSave onTouch. Action was DOWN");
                        view.setElevation(0);
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        Log.d(DEBUG_TAG, "btnSave onTouch. Action was MOVE");
                        return true;
                    case (MotionEvent.ACTION_UP):
                        int elevPx = getResources().getDimensionPixelSize(R.dimen.btn_stats_elev);
                        Log.d(DEBUG_TAG, "btnSave onTouch. Action was UP. open info");
                        view.setElevation(elevPx);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt(getString(R.string.saved_shapes_amount_key),
                                pckrShapesAmount.getValue());
                        editor.putInt(getString(R.string.saved_distinct_shapes_amount_key),
                                pckrDistinctShapesAmount.getValue());
                        editor.putInt(getString(R.string.saved_max_repeat_shapes_amount_key),
                                sgBtnGroupMaxRepeat.getPosition());
                        editor.putInt(getString(R.string.saved_shape_show_time_key),
                                pckrShapeShowTime.getValue());
                        editor.apply();
                        onBackPressed();
                        return true;
                    case (MotionEvent.ACTION_CANCEL):
                        Log.d(DEBUG_TAG, "btnSave onTouch. Action was CANCEL");
                        return true;
                    case (MotionEvent.ACTION_OUTSIDE):
                        Log.d(DEBUG_TAG, "btnSave onTouch. Movement occurred outside bounds " +
                                "of current screen element");
                        return true;
                    default:
                        return false;
                }

            }

        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
}
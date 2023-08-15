package com.youngsophomore.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.NumberPicker;

import com.addisonelliott.segmentedbutton.SegmentedButton;
import com.addisonelliott.segmentedbutton.SegmentedButtonGroup;
import com.youngsophomore.R;

public class MahjongSettingsActivity extends AppCompatActivity {
    private static final String DEBUG_TAG = "Gestures";
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mahjong_settings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Стартовые настройки: ");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NumberPicker numberPicker = findViewById(R.id.number_picker);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);
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
                        Log.d(DEBUG_TAG, "btnSave onTouch. Action was UP. open info" +
                                ", R.dimen.btn_info_elev = " + R.dimen.btn_info_elev +
                                ", elev = " + elevPx);
                        view.setElevation(elevPx);
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

        ImageButton btnSavePlaySettings = findViewById(R.id.btn_save_and_play);
        btnSavePlaySettings.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = event.getAction();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        Log.d(DEBUG_TAG, "btnSavePlaySettings onTouch. Action was DOWN");
                        view.setElevation(0);
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        Log.d(DEBUG_TAG, "btnSavePlaySettings onTouch. Action was MOVE");
                        return true;
                    case (MotionEvent.ACTION_UP):
                        int elevPx = getResources().getDimensionPixelSize(R.dimen.btn_stats_elev);
                        Log.d(DEBUG_TAG, "btnSavePlaySettings onTouch. Action was UP. open info" +
                                ", R.dimen.btn_info_elev = " + R.dimen.btn_info_elev +
                                ", elev = " + elevPx);
                        view.setElevation(elevPx);
                        onBackPressed();
                        return true;
                    case (MotionEvent.ACTION_CANCEL):
                        Log.d(DEBUG_TAG, "btnSavePlaySettings onTouch. Action was CANCEL");
                        return true;
                    case (MotionEvent.ACTION_OUTSIDE):
                        Log.d(DEBUG_TAG, "btnSavePlaySettings onTouch. Movement occurred outside bounds " +
                                "of current screen element");
                        return true;
                    default:
                        return false;
                }

            }

        });

        SegmentedButtonGroup sgBtnGroupBones = findViewById(R.id.sgbtn_bones_amount);
        sgBtnGroupBones.setOnPositionChangedListener(new SegmentedButtonGroup.OnPositionChangedListener() {
            @Override
            public void onPositionChanged(final int position) {
                // Handle stuff here
                Log.d(DEBUG_TAG, "setOnPositionChangedListener: position = " + position);
                //sgBtnGroup.setElevation(0);
            }
        });
        SegmentedButton sgBtnBonesAmount12 = sgBtnGroupBones.getButton(0);
        sgBtnBonesAmount12.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        Log.d(DEBUG_TAG, "sgBtnBonesAmount12 onTouch. Action was DOWN");
                        sgBtnGroupBones.setElevation(0);
                        //sgBtnGroup.onTouchEvent(motionEvent);
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        Log.d(DEBUG_TAG, "sgBtnBonesAmount12 onTouch. Action was MOVE");
                        return true;
                    case (MotionEvent.ACTION_UP):
                        int elevPx = getResources().getDimensionPixelSize(R.dimen.sgbtn_bones_elev);
                        Log.d(DEBUG_TAG, "sgBtnBonesAmount12 onTouch. Action was UP. open statistics" +
                                ", R.dimen.sgbtn_elev = " + R.dimen.sgbtn_elev +
                                ", elev = " + elevPx);
                        //sgBtnGroup.onTouchEvent(motionEvent);
                        sgBtnGroupBones.setElevation(elevPx);

                        return true;
                    case (MotionEvent.ACTION_CANCEL):
                        Log.d(DEBUG_TAG, "sgBtnBonesAmount12 onTouch. Action was CANCEL");
                        return true;
                    case (MotionEvent.ACTION_OUTSIDE):
                        Log.d(DEBUG_TAG, "sgBtnBonesAmount12 onTouch. Movement occurred outside bounds " +
                                "of current screen element");
                        return true;
                    default:
                        return false;
                }
            }
        });
        SegmentedButton sgBtnBonesAmount24 = sgBtnGroupBones.getButton(1);
        sgBtnBonesAmount24.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        Log.d(DEBUG_TAG, "sgBtnBonesAmount24 onTouch. Action was DOWN");
                        sgBtnGroupBones.setElevation(0);
                        //sgBtnGroup.onTouchEvent(motionEvent);
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        Log.d(DEBUG_TAG, "sgBtnBonesAmount24 onTouch. Action was MOVE");
                        return true;
                    case (MotionEvent.ACTION_UP):
                        int elevPx = getResources().getDimensionPixelSize(R.dimen.sgbtn_bones_elev);
                        Log.d(DEBUG_TAG, "sgBtnBonesAmount24 onTouch. Action was UP. open statistics" +
                                ", R.dimen.sgbtn_elev = " + R.dimen.sgbtn_elev +
                                ", elev = " + elevPx);
                        sgBtnGroupBones.setElevation(elevPx);

                        return true;
                    case (MotionEvent.ACTION_CANCEL):
                        Log.d(DEBUG_TAG, "sgBtnBonesAmount24 onTouch. Action was CANCEL");
                        return true;
                    case (MotionEvent.ACTION_OUTSIDE):
                        Log.d(DEBUG_TAG, "sgBtnBonesAmount24 onTouch. Movement occurred outside bounds " +
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
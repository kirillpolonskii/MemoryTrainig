package com.youngsophomore.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.NumberPicker;

import com.addisonelliott.segmentedbutton.SegmentedButton;
import com.addisonelliott.segmentedbutton.SegmentedButtonGroup;
import com.youngsophomore.R;
import com.youngsophomore.fragments.InfoDialogFragment;

public class ColorsSettingsActivity extends AppCompatActivity {
    private static final String DEBUG_TAG = "Gestures";
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colors_settings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.tbr_colors_settings_title));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NumberPicker pckrColorsAmount = findViewById(R.id.pckr_colors_amount);
        NumberPicker pckrDistinctColorsAmount = findViewById(R.id.pckr_distinct_colors_amount);
        pckrColorsAmount.setMinValue(4);
        pckrColorsAmount.setMaxValue(25);
        pckrDistinctColorsAmount.setMinValue(2);
        pckrDistinctColorsAmount.setMaxValue(4);
        pckrColorsAmount.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if(newVal < 16){
                    pckrDistinctColorsAmount.setMaxValue(newVal);
                }
                else{
                    pckrDistinctColorsAmount.setMaxValue(15);
                }
            }
        });

        NumberPicker pckrColorShowTime = findViewById(R.id.pckr_color_show_time);
        pckrColorShowTime.setMinValue(1);
        pckrColorShowTime.setMaxValue(6);

        SegmentedButtonGroup sgBtnGroupMaxRepeat = findViewById(R.id.sgbtn_max_repeat_colors_amount);

        SharedPreferences sharedPreferences =
                getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        int colorsAmount = sharedPreferences.getInt(getString(R.string.saved_colors_amount_key), 4);
        int distinctColorsAmount = sharedPreferences.getInt(getString(R.string.saved_distinct_colors_amount_key), 2);
        int maxRepeatColorsAmount = sharedPreferences.getInt(getString(R.string.saved_max_repeat_colors_amount_key), 1);
        int colorShowTime = sharedPreferences.getInt(getString(R.string.saved_color_show_time_key), 2);

        pckrColorsAmount.setValue(colorsAmount);
        pckrDistinctColorsAmount.setValue(distinctColorsAmount);
        sgBtnGroupMaxRepeat.setPosition(maxRepeatColorsAmount, false);
        pckrColorShowTime.setValue(colorShowTime);

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
                        editor.putInt(getString(R.string.saved_colors_amount_key),
                                pckrColorsAmount.getValue());
                        editor.putInt(getString(R.string.saved_distinct_colors_amount_key),
                                pckrDistinctColorsAmount.getValue());
                        editor.putInt(getString(R.string.saved_max_repeat_colors_amount_key),
                                sgBtnGroupMaxRepeat.getPosition());
                        editor.putInt(getString(R.string.saved_color_show_time_key),
                                pckrColorShowTime.getValue());
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.mi_btn_info) {
            Log.d(DEBUG_TAG, "info button in ColorsSettingsActivity");
            showInfoDialog(R.layout.fragment_mahjong_settings_info);
        }
        return true;
    }
    public void showInfoDialog(int layoutResource) {
        DialogFragment newFragment = new InfoDialogFragment(layoutResource);
        newFragment.show(getSupportFragmentManager(), "InfoDialogFragment");
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
}
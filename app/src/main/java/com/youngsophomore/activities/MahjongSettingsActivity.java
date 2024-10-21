package com.youngsophomore.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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

public class MahjongSettingsActivity extends AppCompatActivity {
    private static final String DEBUG_TAG = "Gestures";
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mahjong_settings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.tbr_mahjong_settings_title));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        SharedPreferences sharedPreferences =
                getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        int mahjongRememberTime = sharedPreferences.getInt(getString(R.string.saved_mahjong_remember_time_key), 2);
        int mahjongTilesAmount = sharedPreferences.getInt(getString(R.string.saved_mahjong_tiles_amount_key), 0);
        int mahjongEqualTilesAmount = sharedPreferences.getInt(getString(R.string.saved_mahjong_equal_tiles_amount_key), 0);
        NumberPicker numberPicker = findViewById(R.id.number_picker);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);
        numberPicker.setValue(mahjongRememberTime);

        SegmentedButtonGroup sgBtnGroupTiles = findViewById(R.id.sgbtn_tiles_amount);
        sgBtnGroupTiles.setPosition(mahjongTilesAmount, false);
        SegmentedButton sgBtnTilesAmount12 = sgBtnGroupTiles.getButton(0);

        sgBtnTilesAmount12.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        Log.d(DEBUG_TAG, "sgBtnTilesAmount12 onTouch. Action was DOWN");
                        sgBtnGroupTiles.setElevation(0);
                        //sgBtnGroup.onTouchEvent(motionEvent);
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        Log.d(DEBUG_TAG, "sgBtnTilesAmount12 onTouch. Action was MOVE");
                        return true;
                    case (MotionEvent.ACTION_UP):
                        int elevPx = getResources().getDimensionPixelSize(R.dimen.sgbtn_tiles_elev);
                        Log.d(DEBUG_TAG, "sgBtnTilesAmount12 onTouch. Action was UP");
                        //sgBtnGroup.onTouchEvent(motionEvent);
                        sgBtnGroupTiles.setElevation(elevPx);

                        return true;
                    default:
                        return false;
                }
            }
        });
        SegmentedButton sgBtnTilesAmount24 = sgBtnGroupTiles.getButton(1);
        sgBtnTilesAmount24.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        Log.d(DEBUG_TAG, "sgBtnTilesAmount24 onTouch. Action was DOWN");
                        sgBtnGroupTiles.setElevation(0);
                        //sgBtnGroup.onTouchEvent(motionEvent);
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        Log.d(DEBUG_TAG, "sgBtnTilesAmount24 onTouch. Action was MOVE");
                        return true;
                    case (MotionEvent.ACTION_UP):
                        int elevPx = getResources().getDimensionPixelSize(R.dimen.sgbtn_tiles_elev);
                        Log.d(DEBUG_TAG, "sgBtnTilesAmount24 onTouch. Action was UP. open statistics" +
                                ", R.dimen.sgbtn_elev = " + R.dimen.sgbtn_elev +
                                ", elev = " + elevPx);
                        sgBtnGroupTiles.setElevation(elevPx);

                        return true;
                    case (MotionEvent.ACTION_CANCEL):
                        Log.d(DEBUG_TAG, "sgBtnTilesAmount24 onTouch. Action was CANCEL");
                        return true;
                    case (MotionEvent.ACTION_OUTSIDE):
                        Log.d(DEBUG_TAG, "sgBtnTilesAmount24 onTouch. Movement occurred outside bounds " +
                                "of current screen element");
                        return true;
                    default:
                        return false;
                }
            }
        });

        SegmentedButtonGroup sgBtnGroupEqualTiles = findViewById(R.id.sgbtn_equal_tiles_amount);
        sgBtnGroupEqualTiles.setPosition(mahjongEqualTilesAmount, false);
        SegmentedButton sgBtnEqualTilesAmount2 = sgBtnGroupEqualTiles.getButton(0);
        sgBtnEqualTilesAmount2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        Log.d(DEBUG_TAG, "sgBtnEqualTilesAmount2 onTouch. Action was DOWN");
                        sgBtnGroupEqualTiles.setElevation(0);
                        //sgBtnGroup.onTouchEvent(motionEvent);
                        return true;
                    case (MotionEvent.ACTION_UP):
                        int elevPx = getResources().getDimensionPixelSize(R.dimen.sgbtn_tiles_elev);
                        Log.d(DEBUG_TAG, "sgBtnEqualTilesAmount2 onTouch. Action was UP");
                        //sgBtnGroup.onTouchEvent(motionEvent);
                        sgBtnGroupEqualTiles.setElevation(elevPx);
                        return true;
                    default:
                        return false;
                }
            }
        });
        SegmentedButton sgBtnEqualTilesAmount3 = sgBtnGroupEqualTiles.getButton(1);
        sgBtnEqualTilesAmount3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        Log.d(DEBUG_TAG, "sgBtnEqualTilesAmount3 onTouch. Action was DOWN");
                        sgBtnGroupEqualTiles.setElevation(0);
                        //sgBtnGroup.onTouchEvent(motionEvent);
                        return true;
                    case (MotionEvent.ACTION_UP):
                        int elevPx = getResources().getDimensionPixelSize(R.dimen.sgbtn_tiles_elev);
                        Log.d(DEBUG_TAG, "sgBtnEqualTilesAmount3 onTouch. Action was UP");
                        //sgBtnGroup.onTouchEvent(motionEvent);
                        sgBtnGroupEqualTiles.setElevation(elevPx);
                        return true;
                    default:
                        return false;
                }
            }
        });
        SegmentedButton sgBtnEqualTilesAmount4 = sgBtnGroupEqualTiles.getButton(2);
        sgBtnEqualTilesAmount4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        Log.d(DEBUG_TAG, "sgBtnEqualTilesAmount4 onTouch. Action was DOWN");
                        sgBtnGroupEqualTiles.setElevation(0);
                        //sgBtnGroup.onTouchEvent(motionEvent);
                        return true;
                    case (MotionEvent.ACTION_UP):
                        int elevPx = getResources().getDimensionPixelSize(R.dimen.sgbtn_tiles_elev);
                        Log.d(DEBUG_TAG, "sgBtnEqualTilesAmount4 onTouch. Action was UP");
                        //sgBtnGroup.onTouchEvent(motionEvent);
                        sgBtnGroupEqualTiles.setElevation(elevPx);
                        return true;
                    default:
                        return false;
                }
            }
        });
        SegmentedButton sgBtnEqualTilesAmount6 = sgBtnGroupEqualTiles.getButton(3);
        sgBtnEqualTilesAmount6.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        Log.d(DEBUG_TAG, "sgBtnEqualTilesAmount6 onTouch. Action was DOWN");
                        sgBtnGroupEqualTiles.setElevation(0);
                        //sgBtnGroup.onTouchEvent(motionEvent);
                        return true;
                    case (MotionEvent.ACTION_UP):
                        int elevPx = getResources().getDimensionPixelSize(R.dimen.sgbtn_tiles_elev);
                        Log.d(DEBUG_TAG, "sgBtnEqualTilesAmount6 onTouch. Action was UP");
                        //sgBtnGroup.onTouchEvent(motionEvent);
                        sgBtnGroupEqualTiles.setElevation(elevPx);
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
                        editor.putInt(getString(R.string.saved_mahjong_remember_time_key),
                                numberPicker.getValue());
                        editor.putInt(getString(R.string.saved_mahjong_tiles_amount_key),
                                sgBtnGroupTiles.getPosition());
                        editor.putInt(getString(R.string.saved_mahjong_equal_tiles_amount_key),
                                sgBtnGroupEqualTiles.getPosition());
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

        ImageButton btnPlayWSettings = findViewById(R.id.btn_save_and_play);
        btnPlayWSettings.setOnTouchListener(new View.OnTouchListener() {
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
                        Log.d(DEBUG_TAG, "btnPlayWSettings onTouch. Action was UP. open info" +
                                ", R.dimen.btn_info_elev = " + R.dimen.btn_info_elev +
                                ", elev = " + elevPx);
                        view.setElevation(elevPx);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt(getString(R.string.saved_mahjong_remember_time_key),
                                numberPicker.getValue());
                        editor.putInt(getString(R.string.saved_mahjong_tiles_amount_key),
                                sgBtnGroupTiles.getPosition());
                        editor.putInt(getString(R.string.saved_mahjong_equal_tiles_amount_key),
                                sgBtnGroupEqualTiles.getPosition());
                        editor.apply();
                        Intent intent = new Intent(getApplicationContext(), MahjongTrainingActivity.class);
                        startActivity(intent);
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.mi_btn_info) {
            Log.d(DEBUG_TAG, "info button in MahjongSettingsActivity");
            showInfoDialog(R.layout.fragment_mahjong_settings_info);
            return true;
        }
        return false;
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
package com.youngsophomore.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
        int mhjTilesAmount = sharedPreferences.getInt(getString(R.string.saved_mahjong_tiles_amount_key), 0);
        int mhjEqualTilesAmount = sharedPreferences.getInt(getString(R.string.saved_mahjong_equal_tiles_amount_key), 0);
        int mhjShowTime = sharedPreferences.getInt(getString(R.string.saved_mahjong_remember_time_key), 2);

        SegmentedButtonGroup sgBtnGrTilesAmount = findViewById(R.id.sg_btn_gr_tiles_amount);
        SegmentedButtonGroup sgBtnGrEqualTilesAmount = findViewById(R.id.sg_btn_gr_equal_tiles_amount);
        NumberPicker mhjNumPckShowTime = findViewById(R.id.num_pck_show_time_mhj);
        ImageButton btnSaveSettings = findViewById(R.id.btn_save_settings_mhj);
        ImageButton btnPlayWSettings = findViewById(R.id.btn_play_w_settings_mhj);

        sgBtnGrTilesAmount.setPosition(mhjTilesAmount, false);
        for(int i = 0; i < sgBtnGrTilesAmount.getButtons().size(); ++i){
            SegmentedButton sgBtnTilesAmount = sgBtnGrTilesAmount.getButton(i);
            sgBtnTilesAmount.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    int action = motionEvent.getAction();
                    switch(action) {
                        case (MotionEvent.ACTION_DOWN):
                            sgBtnGrTilesAmount.setElevation(0);
                            return true;
                        case (MotionEvent.ACTION_MOVE):
                            return true;
                        case (MotionEvent.ACTION_UP):
                            int elevPx = getResources().getDimensionPixelSize(R.dimen.sgbtn_tiles_elev);
                            sgBtnGrTilesAmount.setElevation(elevPx);
                            return true;
                        default:
                            return false;
                    }
                }
            });
        }

        sgBtnGrEqualTilesAmount.setPosition(mhjEqualTilesAmount, false);
        for(int i = 0; i < sgBtnGrEqualTilesAmount.getButtons().size(); ++i){
            SegmentedButton sgBtnEqualTilesAmount = sgBtnGrEqualTilesAmount.getButton(i);
            sgBtnEqualTilesAmount.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    int action = motionEvent.getAction();
                    switch(action) {
                        case (MotionEvent.ACTION_DOWN):
                            sgBtnGrEqualTilesAmount.setElevation(0);
                            return true;
                        case (MotionEvent.ACTION_UP):
                            int elevPx = getResources().getDimensionPixelSize(R.dimen.sgbtn_tiles_elev);
                            sgBtnGrEqualTilesAmount.setElevation(elevPx);
                            return true;
                        default:
                            return false;
                    }
                }
            });
        }

        mhjNumPckShowTime.setMinValue(1);
        mhjNumPckShowTime.setMaxValue(10);
        mhjNumPckShowTime.setValue(mhjShowTime);

        btnSaveSettings.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = event.getAction();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        view.setElevation(0);
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        return true;
                    case (MotionEvent.ACTION_UP):
                        int elevPx = getResources().getDimensionPixelSize(R.dimen.btn_stats_elev);
                        view.setElevation(elevPx);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt(getString(R.string.saved_mahjong_remember_time_key),
                                mhjNumPckShowTime.getValue());
                        editor.putInt(getString(R.string.saved_mahjong_tiles_amount_key),
                                sgBtnGrTilesAmount.getPosition());
                        editor.putInt(getString(R.string.saved_mahjong_equal_tiles_amount_key),
                                sgBtnGrEqualTilesAmount.getPosition());
                        editor.apply();
                        onBackPressed();
                        return true;
                    default:
                        return false;
                }

            }
        });

        btnPlayWSettings.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = event.getAction();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        view.setElevation(0);
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        return true;
                    case (MotionEvent.ACTION_UP):
                        int elevPx = getResources().getDimensionPixelSize(R.dimen.btn_stats_elev);
                        view.setElevation(elevPx);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt(getString(R.string.saved_mahjong_remember_time_key),
                                mhjNumPckShowTime.getValue());
                        editor.putInt(getString(R.string.saved_mahjong_tiles_amount_key),
                                sgBtnGrTilesAmount.getPosition());
                        editor.putInt(getString(R.string.saved_mahjong_equal_tiles_amount_key),
                                sgBtnGrEqualTilesAmount.getPosition());
                        editor.apply();
                        Intent intent = new Intent(getApplicationContext(), MahjongTrainingActivity.class);
                        startActivity(intent);
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
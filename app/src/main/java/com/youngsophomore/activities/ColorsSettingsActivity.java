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

        SharedPreferences sharedPreferences =
                getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        int colorsAmount = sharedPreferences.getInt(getString(R.string.saved_colors_amount_key), 6);
        int distinctColorsAmount = sharedPreferences.getInt(getString(R.string.saved_distinct_colors_amount_key), 2);
        int colorShowTime = sharedPreferences.getInt(getString(R.string.saved_color_show_time_key), 2);

        NumberPicker pckrColorsAmount = findViewById(R.id.num_pck_clr_amount);
        NumberPicker pckrDistinctColorsAmount = findViewById(R.id.num_pck_distinct_clr_amount);
        NumberPicker pckrColorShowTime = findViewById(R.id.num_pck_clr_show_time);
        ImageButton btnSaveSettings = findViewById(R.id.btn_save_settings_clr);
        ImageButton btnPlayWSettings = findViewById(R.id.btn_play_w_settings_clr);

        pckrColorsAmount.setMinValue(6);
        pckrColorsAmount.setMaxValue(24);
        pckrColorsAmount.setValue(colorsAmount);
        pckrDistinctColorsAmount.setMinValue(2);
        pckrDistinctColorsAmount.setMaxValue(15);
        pckrDistinctColorsAmount.setValue(distinctColorsAmount);
        pckrColorShowTime.setMinValue(1);
        pckrColorShowTime.setMaxValue(6);
        pckrColorShowTime.setValue(colorShowTime);

        btnSaveSettings.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = event.getAction();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        view.setElevation(0);
                        return true;
                    case (MotionEvent.ACTION_UP):
                        int elevPx = getResources().getDimensionPixelSize(R.dimen.btn_stats_elev);
                        view.setElevation(elevPx);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt(getString(R.string.saved_colors_amount_key),
                                pckrColorsAmount.getValue());
                        editor.putInt(getString(R.string.saved_distinct_colors_amount_key),
                                pckrDistinctColorsAmount.getValue());
                        editor.putInt(getString(R.string.saved_color_show_time_key),
                                pckrColorShowTime.getValue());
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
                    case (MotionEvent.ACTION_UP):
                        int elevPx = getResources().getDimensionPixelSize(R.dimen.btn_stats_elev);
                        view.setElevation(elevPx);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt(getString(R.string.saved_colors_amount_key),
                                pckrColorsAmount.getValue());
                        editor.putInt(getString(R.string.saved_distinct_colors_amount_key),
                                pckrDistinctColorsAmount.getValue());
                        editor.putInt(getString(R.string.saved_color_show_time_key),
                                pckrColorShowTime.getValue());
                        editor.apply();
                        Intent intent = new Intent(getApplicationContext(), ColorsTrainingActivity.class);
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
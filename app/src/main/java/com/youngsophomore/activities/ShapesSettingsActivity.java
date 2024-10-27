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

        SharedPreferences sharedPreferences =
                getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        int shapesAmount = sharedPreferences.getInt(getString(R.string.saved_shapes_amount_key), 4);
        int distinctShapesAmount = sharedPreferences.getInt(getString(R.string.saved_distinct_shapes_amount_key), 2);
        int shapeShowTime = sharedPreferences.getInt(getString(R.string.saved_shape_show_time_key), 2);

        NumberPicker pckrShapesAmount = findViewById(R.id.num_pck_shp_amount);
        NumberPicker pckrDistinctShapesAmount = findViewById(R.id.num_pck_distinct_shp_amount);
        NumberPicker pckrShapeShowTime = findViewById(R.id.num_pck_shape_show_time);
        pckrShapesAmount.setMinValue(4);
        pckrShapesAmount.setMaxValue(24);
        pckrDistinctShapesAmount.setMinValue(2);
        pckrDistinctShapesAmount.setMaxValue(9);
        pckrShapeShowTime.setMinValue(1);
        pckrShapeShowTime.setMaxValue(6);

        pckrShapesAmount.setValue(shapesAmount);
        pckrDistinctShapesAmount.setValue(distinctShapesAmount);
        pckrShapeShowTime.setValue(shapeShowTime);

        ImageButton btnSaveSettings = findViewById(R.id.btn_save_settings_shp);
        ImageButton btnPlayWSettings = findViewById(R.id.btn_play_w_settings_shp);
        btnSaveSettings.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = event.getAction();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        Log.d(DEBUG_TAG, "btnSave onTouch. Action was DOWN");
                        view.setElevation(0);
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
                        editor.putInt(getString(R.string.saved_shape_show_time_key),
                                pckrShapeShowTime.getValue());
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
                        Log.d(DEBUG_TAG, "btnSavePlaySettings onTouch. Action was DOWN");
                        view.setElevation(0);
                        return true;
                    case (MotionEvent.ACTION_UP):
                        int elevPx = getResources().getDimensionPixelSize(R.dimen.btn_stats_elev);
                        Log.d(DEBUG_TAG, "btnSavePlaySettings onTouch. Action was UP. open info" +
                                ", R.dimen.btn_info_elev = " + R.dimen.btn_info_elev +
                                ", elev = " + elevPx);
                        view.setElevation(elevPx);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt(getString(R.string.saved_shapes_amount_key),
                                pckrShapesAmount.getValue());
                        editor.putInt(getString(R.string.saved_distinct_shapes_amount_key),
                                pckrDistinctShapesAmount.getValue());
                        editor.putInt(getString(R.string.saved_shape_show_time_key),
                                pckrShapeShowTime.getValue());
                        editor.apply();
                        Intent intent = new Intent(getApplicationContext(), ShapesTrainingActivity.class);
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
            Log.d(DEBUG_TAG, "info button in ShapesSettingsActivity");
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
package com.youngsophomore.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.Spinner;

import com.youngsophomore.R;
import com.youngsophomore.data.CollectionsStorage;

public class DetailsSettingsActivity extends AppCompatActivity {
    private static final String DEBUG_TAG = "Gestures";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_settings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.tbr_details_settings_title));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences sharedPreferences =
                getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        int imagesCollectionPosition = sharedPreferences.getInt(getString(R.string.saved_images_collection_position_key), 0);
        int imageShowTime = sharedPreferences.getInt(getString(R.string.saved_image_show_time_key), 2);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.custom_spinner_item, CollectionsStorage.getQuestionsCollectionsTitles());
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        Spinner sprImagesCollection = findViewById(R.id.spr_image);
        sprImagesCollection.setAdapter(adapter);
        sprImagesCollection.setSelection(imagesCollectionPosition);

        NumberPicker pckrImageShowTime = findViewById(R.id.pckr_image_show_time);
        pckrImageShowTime.setMinValue(1);
        pckrImageShowTime.setMaxValue(10);
        pckrImageShowTime.setValue(imageShowTime);

        ImageButton btnAddImage = findViewById(R.id.btn_add_image);
        ImageButton btnSaveDetailsSettings = findViewById(R.id.btn_save_details_settings);
        ImageButton btnPlayDetailsWSettings = findViewById(R.id.btn_play_details_w_settings);

        btnAddImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = event.getAction();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        Log.d(DEBUG_TAG, "btnAddImage onTouch. Action was DOWN");
                        view.setElevation(0);
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        Log.d(DEBUG_TAG, "btnAddImage onTouch. Action was MOVE");
                        return true;
                    case (MotionEvent.ACTION_UP):
                        int elevPx = getResources().getDimensionPixelSize(R.dimen.btn_stats_elev);
                        Log.d(DEBUG_TAG, "btnAddImage onTouch. Action was UP");
                        view.setElevation(elevPx);
                        Intent intent = new Intent(DetailsSettingsActivity.this, AddImageActivity.class);
                        startActivity(intent);
                        return true;
                    default:
                        return false;
                }
            }
        });

        btnSaveDetailsSettings.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = event.getAction();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        Log.d(DEBUG_TAG, "btnSaveDetailsSettings onTouch. Action was DOWN");
                        view.setElevation(0);
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        Log.d(DEBUG_TAG, "btnSaveDetailsSettings onTouch. Action was MOVE");
                        return true;
                    case (MotionEvent.ACTION_UP):
                        int elevPx = getResources().getDimensionPixelSize(R.dimen.btn_stats_elev);
                        Log.d(DEBUG_TAG, "btnSaveDetailsSettings onTouch. Action was UP");
                        view.setElevation(elevPx);

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt(getString(R.string.saved_images_collection_position_key),
                                sprImagesCollection.getSelectedItemPosition());
                        editor.putInt(getString(R.string.saved_image_show_time_key),
                                pckrImageShowTime.getValue());
                        editor.apply();
                        onBackPressed();
                        return true;
                    default:
                        return false;
                }
            }
        });

        btnPlayDetailsWSettings.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = event.getAction();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        Log.d(DEBUG_TAG, "btnPlayDetailsWSettings onTouch. Action was DOWN");
                        view.setElevation(0);
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        Log.d(DEBUG_TAG, "btnPlayDetailsWSettings onTouch. Action was MOVE");
                        return true;
                    case (MotionEvent.ACTION_UP):
                        int elevPx = getResources().getDimensionPixelSize(R.dimen.btn_stats_elev);
                        Log.d(DEBUG_TAG, "btnPlayDetailsWSettings onTouch. Action was UP");
                        view.setElevation(elevPx);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt(getString(R.string.saved_images_collection_position_key),
                                sprImagesCollection.getSelectedItemPosition());
                        editor.putInt(getString(R.string.saved_image_show_time_key),
                                pckrImageShowTime.getValue());
                        editor.apply();
                        onBackPressed();
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
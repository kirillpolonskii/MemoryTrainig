package com.youngsophomore.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

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

public class PhrasesSettingsActivity extends AppCompatActivity {
    private static final String DEBUG_TAG = "Gestures";
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phrases_settings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.tbr_phrases_settings_title));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.custom_spinner_item, CollectionsStorage.getPhrasesCollectionsTitles());
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        Spinner sprPhrasesCollection = findViewById(R.id.spr_phrases_collection);
        sprPhrasesCollection.setAdapter(adapter);

        SharedPreferences sharedPreferences =
                getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        int phrasesCollectionPosition = sharedPreferences.getInt(getString(R.string.saved_phrases_collection_position_key), 0);
        int phraseShowTime = sharedPreferences.getInt(getString(R.string.saved_phrase_show_time_key), 2);

        sprPhrasesCollection.setSelection(phrasesCollectionPosition);

        NumberPicker pckrPhraseShowTime = findViewById(R.id.pckr_phrase_show_time);
        pckrPhraseShowTime.setMinValue(1);
        pckrPhraseShowTime.setMaxValue(6);
        pckrPhraseShowTime.setValue(phraseShowTime);

        ImageButton btnAddPhrasesCollection = findViewById(R.id.btn_add_phrases_collection);
        ImageButton btnSavePhrasesSettings = findViewById(R.id.btn_save_phrases_settings);
        ImageButton btnPlayPhrasesWSettings = findViewById(R.id.btn_play_phrases_w_settings);

        btnAddPhrasesCollection.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = event.getAction();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        Log.d(DEBUG_TAG, "btnAddPhrasesCollection onTouch. Action was DOWN");
                        view.setElevation(0);
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        Log.d(DEBUG_TAG, "btnAddPhrasesCollection onTouch. Action was MOVE");
                        return true;
                    case (MotionEvent.ACTION_UP):
                        int elevPx = getResources().getDimensionPixelSize(R.dimen.btn_stats_elev);
                        Log.d(DEBUG_TAG, "btnAddPhrasesCollection onTouch. Action was UP");
                        view.setElevation(elevPx);
                        Intent intent = new Intent(PhrasesSettingsActivity.this, AddPhrasesCollectionActivity.class);
                        startActivity(intent);
                        return true;
                    default:
                        return false;
                }
            }
        });

        btnSavePhrasesSettings.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = event.getAction();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        Log.d(DEBUG_TAG, "btnSavePhrasesSettings onTouch. Action was DOWN");
                        view.setElevation(0);
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        Log.d(DEBUG_TAG, "btnSavePhrasesSettings onTouch. Action was MOVE");
                        return true;
                    case (MotionEvent.ACTION_UP):
                        int elevPx = getResources().getDimensionPixelSize(R.dimen.btn_stats_elev);
                        Log.d(DEBUG_TAG, "btnSavePhrasesSettings onTouch. Action was UP");
                        view.setElevation(elevPx);

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt(getString(R.string.saved_phrases_collection_position_key),
                                sprPhrasesCollection.getSelectedItemPosition());
                        editor.putInt(getString(R.string.saved_phrase_show_time_key),
                                pckrPhraseShowTime.getValue());
                        editor.apply();
                        onBackPressed();
                        return true;
                    default:
                        return false;
                }
            }
        });

        btnPlayPhrasesWSettings.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = event.getAction();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        Log.d(DEBUG_TAG, "btnSavePhrasesSettingsAndPlay onTouch. Action was DOWN");
                        view.setElevation(0);
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        Log.d(DEBUG_TAG, "btnSavePhrasesSettingsAndPlay onTouch. Action was MOVE");
                        return true;
                    case (MotionEvent.ACTION_UP):
                        int elevPx = getResources().getDimensionPixelSize(R.dimen.btn_stats_elev);
                        Log.d(DEBUG_TAG, "btnSavePhrasesSettingsAndPlay onTouch. Action was UP");
                        view.setElevation(elevPx);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt(getString(R.string.saved_phrases_collection_position_key),
                                sprPhrasesCollection.getSelectedItemPosition());
                        editor.putInt(getString(R.string.saved_phrase_show_time_key),
                                pckrPhraseShowTime.getValue());
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
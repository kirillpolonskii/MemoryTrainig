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
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.youngsophomore.R;
import com.youngsophomore.data.CollectionsStorage;
import com.youngsophomore.fragments.DeleteCollectionDialogFragment;
import com.youngsophomore.fragments.InfoDialogFragment;

import java.util.ArrayList;

public class PhrasesSettingsActivity extends AppCompatActivity
        implements DeleteCollectionDialogFragment.DeleteCollectionDialogListener{
    SharedPreferences sharedPreferences;
    ArrayAdapter<String> adapter;
    Spinner sprPhrasesCollection;
    int phrasesCollectionPosition;
    ArrayList<String> phrasesCollectionsTitles;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phrases_settings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.tbr_phrases_settings_title));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferences =
                getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        phrasesCollectionPosition = sharedPreferences.getInt(getString(R.string.saved_phrases_collection_position_key), 0);
        int phraseShowTime = sharedPreferences.getInt(getString(R.string.saved_phrase_show_time_key), 2);

        sprPhrasesCollection = findViewById(R.id.spr_phr_collection);
        NumberPicker pckrPhraseShowTime = findViewById(R.id.num_pck_phr_show_time);
        ImageButton btnAddPhrasesCollection = findViewById(R.id.btn_add_phr_collection);
        ImageButton btnSaveSettings = findViewById(R.id.btn_save_phr_settings);
        ImageButton btnPlayWSettings = findViewById(R.id.btn_play_w_settings_phr);

        phrasesCollectionsTitles = CollectionsStorage.getCollectionsTitles(
                sharedPreferences, getString(R.string.phrases_collections_titles_key));
        adapter = new ArrayAdapter<>(this,
                R.layout.item_spinner, phrasesCollectionsTitles);
        adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
        sprPhrasesCollection.setAdapter(adapter);
        sprPhrasesCollection.setSelection(phrasesCollectionPosition);
        sprPhrasesCollection.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(adapter.getCount() > 1){
                    showDeleteCollectionDialog();
                }
                else{
                    Toast.makeText(getApplicationContext(), getString(R.string.msg_forbid_delete_collection),
                            Toast.LENGTH_LONG).show();
                }
                return true;
            }
        });

        pckrPhraseShowTime.setMinValue(1);
        pckrPhraseShowTime.setMaxValue(6);
        pckrPhraseShowTime.setValue(phraseShowTime);

        btnAddPhrasesCollection.setOnTouchListener(new View.OnTouchListener() {
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
                        Intent intent = new Intent(PhrasesSettingsActivity.this, AddPhrasesCollectionActivity.class);
                        startActivity(intent);
                        return true;
                    default:
                        return false;
                }
            }
        });

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
                        editor.putInt(getString(R.string.saved_phrases_collection_position_key),
                                sprPhrasesCollection.getSelectedItemPosition());
                        editor.putInt(getString(R.string.saved_phrase_show_time_key),
                                pckrPhraseShowTime.getValue());
                        editor.apply();
                        Intent intent = new Intent(getApplicationContext(), PhrasesTrainingActivity.class);
                        startActivity(intent);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        phrasesCollectionsTitles.clear();
        phrasesCollectionsTitles.addAll(CollectionsStorage.getCollectionsTitles(
                        sharedPreferences, getString(R.string.phrases_collections_titles_key)));

        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.mi_btn_info) {
            
            showInfoDialog(R.layout.fragment_phrases_settings_info);
            return true;
        }
        return false;
    }
    public void showInfoDialog(int layoutResource) {
        DialogFragment newFragment = new InfoDialogFragment(layoutResource);
        newFragment.show(getSupportFragmentManager(), "InfoDialogFragment");
    }
    public void showDeleteCollectionDialog(){
        DialogFragment newFragment = new DeleteCollectionDialogFragment();
        newFragment.show(getSupportFragmentManager(), "DeleteCollectionDialogFragment");
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    @Override
    public void onDeleteCollectionPosClick(DialogFragment dialog) {
        CollectionsStorage.deletePhrasesCollection(
                (String) sprPhrasesCollection.getSelectedItem(),
                getString(R.string.phrases_collections_titles_key),
                getString(R.string.saved_phrases_collection_position_key),
                sharedPreferences, getApplicationContext());
        adapter.remove((String) sprPhrasesCollection.getSelectedItem());
        adapter.notifyDataSetChanged();
        sprPhrasesCollection.setSelection(0);
    }

    @Override
    public void onDeleteCollectionNegClick(DialogFragment dialog) {}
}
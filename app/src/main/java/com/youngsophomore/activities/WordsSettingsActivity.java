package com.youngsophomore.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.youngsophomore.R;
import com.youngsophomore.data.CollectionsStorage;
import com.youngsophomore.fragments.AddWordsCollectionFragment;
import com.youngsophomore.fragments.DisplayWordsSettingsFragment;
import com.youngsophomore.fragments.InfoDialogFragment;
import com.youngsophomore.helpers.PrepHelper;

public class WordsSettingsActivity extends AppCompatActivity {
    private final String DISPLAY_SETTINGS_FRAGMENT_TAG = "display_words_settings_fragment";
    private final String ADD_COLLECTION_FRAGMENT_TAG = "add_collection_fragment";
    private static final String DEBUG_TAG = "Gestures";
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words_settings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.tbr_words_settings_title));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.frgt_view, DisplayWordsSettingsFragment.class, null, DISPLAY_SETTINGS_FRAGMENT_TAG)
                .commit();

        ImageButton btnAddWordsCollection = findViewById(R.id.btn_add_words_collection);
        ImageButton btnSaveWordsSettings = findViewById(R.id.btn_save_words_settings);
        ImageButton btnSaveWordsSettingsAndPlay = findViewById(R.id.btn_save_words_settings_and_play);
        ImageButton btnConfirmWordsCollection = findViewById(R.id.btn_confirm_words_collection);

        PrepHelper.deactivateBtn(btnConfirmWordsCollection);

        btnAddWordsCollection.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = event.getAction();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        Log.d(DEBUG_TAG, "btnAddWordsCollection onTouch. Action was DOWN");
                        //view.setElevation(0);
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        Log.d(DEBUG_TAG, "btnAddWordsCollection onTouch. Action was MOVE");
                        return true;
                    case (MotionEvent.ACTION_UP):
                        int elevPx = getResources().getDimensionPixelSize(R.dimen.btn_stats_elev);
                        Log.d(DEBUG_TAG, "btnAddWordsCollection onTouch. Action was UP");

                        PrepHelper.deactivateBtn(btnAddWordsCollection);
                        PrepHelper.deactivateBtn(btnSaveWordsSettings);
                        PrepHelper.deactivateBtn(btnSaveWordsSettingsAndPlay);
                        PrepHelper.activateBtn(btnConfirmWordsCollection, elevPx);

                        fragmentManager.beginTransaction()
                                .replace(R.id.frgt_view, AddWordsCollectionFragment.class, null, ADD_COLLECTION_FRAGMENT_TAG)
                                .setReorderingAllowed(true)
                                .addToBackStack("transaction_add_words_collection_fragment")
                                .commit();
                        toolbar.setTitle(R.string.tbr_words_add_collection_title);
                        return true;
                    default:
                        return false;
                }
            }
        });

        btnSaveWordsSettings.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = event.getAction();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        Log.d(DEBUG_TAG, "btnSaveWordsSettings onTouch. Action was DOWN");
                        view.setElevation(0);
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        Log.d(DEBUG_TAG, "btnSaveWordsSettings onTouch. Action was MOVE");
                        return true;
                    case (MotionEvent.ACTION_UP):
                        int elevPx = getResources().getDimensionPixelSize(R.dimen.btn_stats_elev);
                        Log.d(DEBUG_TAG, "btnSaveWordsSettings onTouch. Action was UP");
                        view.setElevation(elevPx);
                        Fragment displayWordsCollectionsFragment = fragmentManager.findFragmentByTag(DISPLAY_SETTINGS_FRAGMENT_TAG);
                        Spinner sprWordsCollection = displayWordsCollectionsFragment.getView()
                                .findViewById(R.id.spr_words_collection);
                        NumberPicker pckrWordShowTime = displayWordsCollectionsFragment.getView()
                                .findViewById(R.id.pckr_word_show_time);

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt(getString(R.string.saved_words_collection_position_key),
                                sprWordsCollection.getSelectedItemPosition());
                        editor.putInt(getString(R.string.saved_word_show_time_key),
                                pckrWordShowTime.getValue());

                        editor.apply();
                        onBackPressed();
                        return true;
                    default:
                        return false;
                }
            }
        });

        btnSaveWordsSettingsAndPlay.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = event.getAction();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        Log.d(DEBUG_TAG, "btnSaveWordsSettingsAndPlay onTouch. Action was DOWN");
                        view.setElevation(0);
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        Log.d(DEBUG_TAG, "btnSaveWordsSettingsAndPlay onTouch. Action was MOVE");
                        return true;
                    case (MotionEvent.ACTION_UP):
                        int elevPx = getResources().getDimensionPixelSize(R.dimen.btn_stats_elev);
                        Log.d(DEBUG_TAG, "btnSaveWordsSettingsAndPlay onTouch. Action was UP");
                        view.setElevation(elevPx);
                        /*SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt(getString(R.string.saved_shapes_amount_key),
                                pckrShapesAmount.getValue());
                        editor.putInt(getString(R.string.saved_distinct_shapes_amount_key),
                                pckrDistinctShapesAmount.getValue());
                        editor.putInt(getString(R.string.saved_max_repeat_shapes_amount_key),
                                sgBtnGroupMaxRepeat.getPosition());
                        editor.putInt(getString(R.string.saved_shape_show_time_key),
                                pckrShapeShowTime.getValue());
                        editor.apply();*/
                        return true;
                    default:
                        return false;
                }
            }
        });

        btnConfirmWordsCollection.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = event.getAction();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        Log.d(DEBUG_TAG, "btnConfirmWordsCollection onTouch. Action was DOWN");
                        view.setElevation(0);
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        Log.d(DEBUG_TAG, "btnConfirmWordsCollection onTouch. Action was MOVE");
                        return true;
                    case (MotionEvent.ACTION_UP):
                        int elevPx = getResources().getDimensionPixelSize(R.dimen.btn_stats_elev);
                        Log.d(DEBUG_TAG, "btnConfirmWordsCollection onTouch. Action was UP");
                        //view.setElevation(elevPx);

                        PrepHelper.activateBtn(btnAddWordsCollection, elevPx);
                        PrepHelper.activateBtn(btnSaveWordsSettings, elevPx);
                        PrepHelper.activateBtn(btnSaveWordsSettingsAndPlay, elevPx);
                        PrepHelper.deactivateBtn(btnConfirmWordsCollection);

                        Fragment addWordsCollectionFragment = fragmentManager.findFragmentByTag(ADD_COLLECTION_FRAGMENT_TAG);
                        EditText etWordsCollectionTitle = addWordsCollectionFragment.getView()
                                .findViewById(R.id.et_words_collection_title);
                        EditText etWordsCollection = addWordsCollectionFragment.getView()
                                .findViewById(R.id.et_words_collection);
                        // исправить логику проверки названия на уникальность
                        String strWordsCollectionsTitles =
                                sharedPreferences.getString(getString(R.string.words_collections_titles_key), "");
                        String wordsCollectionTitle = etWordsCollectionTitle.getText().toString();
                        if(PrepHelper.isCollectionTitleUnique(strWordsCollectionsTitles, wordsCollectionTitle)){
                            CollectionsStorage.saveWordsCollection(
                                    wordsCollectionTitle,
                                    etWordsCollection.getText().toString(),
                                    strWordsCollectionsTitles,
                                    sharedPreferences,
                                    getString(R.string.words_collections_titles_key));
                        }
                        else{
                            Toast.makeText(getApplicationContext(), getString(R.string.msg_collection_title_not_unique),
                                    Toast.LENGTH_LONG).show();
                        }


                        /*String newCollection = etWordsCollection.getText().toString();
                        Log.d(DEBUG_TAG, newCollection);
                        String[] splittedNewCollection = newCollection.split(" ");
                        for(String el : splittedNewCollection){
                            Log.d(DEBUG_TAG, el + "|");
                        }
                        ArrayList<String> newCollectionArray = new ArrayList<>(Arrays.asList(splittedNewCollection));
                        Log.d(DEBUG_TAG, newCollectionArray.toString());*/
                        toolbar.setTitle(R.string.tbr_words_settings_title);
                        onBackPressed();
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
            Log.d(DEBUG_TAG, "info button in WordsSettingsActivity");
            showInfoDialog(R.layout.fragment_words_settings_info);
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
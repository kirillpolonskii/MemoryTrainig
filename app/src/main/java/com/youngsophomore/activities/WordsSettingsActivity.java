package com.youngsophomore.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Intent;
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
    private static final String DEBUG_TAG = "Gestures";
    private final String DISPLAY_SETTINGS_FRAGMENT_TAG = "display_words_settings_fragment";
    private final String ADD_COLLECTION_FRAGMENT_TAG = "add_collection_fragment";
    Toolbar toolbar;
    FragmentManager fragmentManager;
    Spinner sprWordsCollection;
    NumberPicker pckrWordShowTime;
    int elevPx;
    ImageButton btnSaveSettings;
    ImageButton btnPlayWSettings;
    ImageButton btnAddWordsCollection;
    ImageButton btnConfirmWordsCollection;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words_settings);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.tbr_words_settings_title));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        elevPx = getResources().getDimensionPixelSize(R.dimen.btn_stats_elev);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.frt_cnt_v_wrd, DisplayWordsSettingsFragment.class, null, DISPLAY_SETTINGS_FRAGMENT_TAG)
                .commit();

        btnSaveSettings = findViewById(R.id.btn_save_wrd_settings);
        btnPlayWSettings = findViewById(R.id.btn_play_w_settings_wrd);
        btnAddWordsCollection = findViewById(R.id.btn_add_wrd_collection);
        btnConfirmWordsCollection = findViewById(R.id.btn_confirm_wrd_collection);

        PrepHelper.deactivateBtn(btnConfirmWordsCollection);
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
                        
                        view.setElevation(elevPx);
                        Fragment displayWordsCollectionsFragment = fragmentManager.findFragmentByTag(DISPLAY_SETTINGS_FRAGMENT_TAG);
                        sprWordsCollection = displayWordsCollectionsFragment.getView()
                                .findViewById(R.id.spr_wrd_collection);
                        pckrWordShowTime = displayWordsCollectionsFragment.getView()
                                .findViewById(R.id.num_pck_wrd_show_time);

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
                        
                        view.setElevation(elevPx);
                        Fragment displayWordsCollectionsFragment = fragmentManager.findFragmentByTag(DISPLAY_SETTINGS_FRAGMENT_TAG);
                        sprWordsCollection = displayWordsCollectionsFragment.getView()
                                .findViewById(R.id.spr_wrd_collection);
                        pckrWordShowTime = displayWordsCollectionsFragment.getView()
                                .findViewById(R.id.num_pck_wrd_show_time);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt(getString(R.string.saved_words_collection_position_key),
                                sprWordsCollection.getSelectedItemPosition());
                        editor.putInt(getString(R.string.saved_word_show_time_key),
                                pckrWordShowTime.getValue());

                        editor.apply();
                        Intent intent = new Intent(getApplicationContext(), WordsTrainingActivity.class);
                        startActivity(intent);
                        return true;
                    default:
                        return false;
                }
            }
        });

        btnAddWordsCollection.setOnTouchListener(new View.OnTouchListener() {
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
                        

                        PrepHelper.deactivateBtn(btnAddWordsCollection);
                        PrepHelper.deactivateBtn(btnSaveSettings);
                        PrepHelper.deactivateBtn(btnPlayWSettings);
                        PrepHelper.activateBtn(btnConfirmWordsCollection, elevPx);

                        fragmentManager.beginTransaction()
                                .replace(R.id.frt_cnt_v_wrd, AddWordsCollectionFragment.class, null, ADD_COLLECTION_FRAGMENT_TAG)
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

        btnConfirmWordsCollection.setOnTouchListener(new View.OnTouchListener() {
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
                        
                        Fragment addWordsCollectionFragment = fragmentManager.findFragmentByTag(ADD_COLLECTION_FRAGMENT_TAG);
                        EditText etWordsCollectionTitle = addWordsCollectionFragment.getView()
                                .findViewById(R.id.et_wrd_collection_title);
                        EditText etWordsCollection = addWordsCollectionFragment.getView()
                                .findViewById(R.id.et_wrd_collection);
                        String strWordsCollectionsTitles =
                                sharedPreferences.getString(getString(R.string.words_collections_titles_key), "");
                        String wordsCollectionTitle = etWordsCollectionTitle.getText().toString();
                        if (wordsCollectionTitle.equals("") || etWordsCollection.getText().toString().equals("")){
                            onBackPressed();
                            toolbar.setTitle(R.string.tbr_words_settings_title);
                            PrepHelper.activateBtn(btnAddWordsCollection, elevPx);
                            PrepHelper.activateBtn(btnSaveSettings, elevPx);
                            PrepHelper.activateBtn(btnPlayWSettings, elevPx);
                            PrepHelper.deactivateBtn(btnConfirmWordsCollection);
                        }
                        else{
                            if(PrepHelper.isCollectionTitleUnique(strWordsCollectionsTitles, wordsCollectionTitle)){
                                CollectionsStorage.saveWordsCollection(
                                        wordsCollectionTitle,
                                        etWordsCollection.getText().toString(),
                                        strWordsCollectionsTitles,
                                        sharedPreferences,
                                        getString(R.string.words_collections_titles_key));
                                onBackPressed();
                                toolbar.setTitle(R.string.tbr_words_settings_title);
                                PrepHelper.activateBtn(btnAddWordsCollection, elevPx);
                                PrepHelper.activateBtn(btnSaveSettings, elevPx);
                                PrepHelper.activateBtn(btnPlayWSettings, elevPx);
                                PrepHelper.deactivateBtn(btnConfirmWordsCollection);
                            }
                            else{
                                Toast.makeText(getApplicationContext(), getString(R.string.msg_collection_title_not_unique),
                                        Toast.LENGTH_LONG).show();
                            }
                        }
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

    @Override
    public void onBackPressed() {
        
        AddWordsCollectionFragment addWordsCollectionFragment =
                (AddWordsCollectionFragment) fragmentManager.findFragmentByTag(ADD_COLLECTION_FRAGMENT_TAG);
        DisplayWordsSettingsFragment displayWordsSettingsFragment =
                (DisplayWordsSettingsFragment) fragmentManager.findFragmentByTag(DISPLAY_SETTINGS_FRAGMENT_TAG);
        
        if(addWordsCollectionFragment != null && addWordsCollectionFragment.isVisible()){
            toolbar.setTitle(R.string.tbr_words_settings_title);
            PrepHelper.activateBtn(btnSaveSettings, elevPx);
            PrepHelper.activateBtn(btnPlayWSettings, elevPx);
            PrepHelper.activateBtn(btnAddWordsCollection, elevPx);
            PrepHelper.deactivateBtn(btnConfirmWordsCollection);
        }
        super.onBackPressed();
    }
}
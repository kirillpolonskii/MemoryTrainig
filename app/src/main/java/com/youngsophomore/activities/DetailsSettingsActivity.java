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

public class DetailsSettingsActivity extends AppCompatActivity
        implements DeleteCollectionDialogFragment.DeleteCollectionDialogListener{
    ArrayList<String> questionsCollectionsTitles;
    SharedPreferences sharedPreferences;
    ArrayAdapter<String> adapter;
    Spinner sprImagesCollection;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_settings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.tbr_details_settings_title));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferences =
                getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        int imagesCollectionPosition = sharedPreferences.getInt(getString(R.string.saved_images_collection_position_key), 0);
        int imageShowTime = sharedPreferences.getInt(getString(R.string.saved_image_show_time_key), 2);

        sprImagesCollection = findViewById(R.id.spr_image);
        NumberPicker pckrImageShowTime = findViewById(R.id.num_pck_image_show_time);
        ImageButton btnAddImage = findViewById(R.id.btn_add_image);
        ImageButton btnSaveDetailsSettings = findViewById(R.id.btn_save_settings_det);
        ImageButton btnPlayDetailsWSettings = findViewById(R.id.btn_play_w_settings_det);

        questionsCollectionsTitles = CollectionsStorage.getCollectionsTitles(sharedPreferences,
                getString(R.string.questions_collections_titles_key));
        adapter = new ArrayAdapter<>(this,
                R.layout.item_spinner, questionsCollectionsTitles);
        adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
        sprImagesCollection.setAdapter(adapter);
        sprImagesCollection.setSelection(imagesCollectionPosition);
        sprImagesCollection.setOnLongClickListener(new View.OnLongClickListener() {
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

        pckrImageShowTime.setMinValue(1);
        pckrImageShowTime.setMaxValue(10);
        pckrImageShowTime.setValue(imageShowTime);

        btnSaveDetailsSettings.setOnTouchListener(new View.OnTouchListener() {
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
                        view.setElevation(0);
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        return true;
                    case (MotionEvent.ACTION_UP):
                        int elevPx = getResources().getDimensionPixelSize(R.dimen.btn_stats_elev);
                        view.setElevation(elevPx);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt(getString(R.string.saved_images_collection_position_key),
                                sprImagesCollection.getSelectedItemPosition());
                        editor.putInt(getString(R.string.saved_image_show_time_key),
                                pckrImageShowTime.getValue());
                        editor.apply();
                        Intent intent = new Intent(getApplicationContext(), DetailsTrainingActivity.class);
                        startActivity(intent);
                        return true;
                    default:
                        return false;
                }
            }
        });

        btnAddImage.setOnTouchListener(new View.OnTouchListener() {
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
                        Intent intent = new Intent(DetailsSettingsActivity.this, AddImageActivity.class);
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
        questionsCollectionsTitles.clear();
        questionsCollectionsTitles.addAll(CollectionsStorage.getCollectionsTitles(sharedPreferences,
                getString(R.string.questions_collections_titles_key)));

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
            showInfoDialog(R.layout.fragment_details_settings_info);
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
        CollectionsStorage.deleteQuestionsCollection((String) sprImagesCollection.getSelectedItem(),
                getString(R.string.questions_collections_titles_key),
                getString(R.string.saved_images_collection_position_key),
                sharedPreferences, getApplicationContext());
        adapter.remove((String) sprImagesCollection.getSelectedItem());
        adapter.notifyDataSetChanged();
        sprImagesCollection.setSelection(0);
    }

    @Override
    public void onDeleteCollectionNegClick(DialogFragment dialog) {

    }
}
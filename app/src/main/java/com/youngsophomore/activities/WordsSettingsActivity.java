package com.youngsophomore.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.youngsophomore.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class WordsSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words_settings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.tbr_words_settings_title));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayList<String> strArray = new ArrayList<>();
        strArray.add("One");
        strArray.add("Two");
        strArray.add("Three");
        strArray.add("Four");
        strArray.add("Five");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, strArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sprWordsCollection = findViewById(R.id.spr_words_collection);
        sprWordsCollection.setAdapter(adapter);


    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
}
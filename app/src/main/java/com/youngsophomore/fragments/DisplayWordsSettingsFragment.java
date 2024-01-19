package com.youngsophomore.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Spinner;

import com.youngsophomore.R;
import com.youngsophomore.data.CollectionsStorage;

public class DisplayWordsSettingsFragment extends Fragment /*implements AdapterView.OnItemLongClickListener*/ {

    private static final String DEBUG_TAG = "Gestures";
    public DisplayWordsSettingsFragment() {
        // Required empty public constructor
    }


    public static DisplayWordsSettingsFragment newInstance(String param1, String param2) {

        return new DisplayWordsSettingsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragment = inflater.inflate(R.layout.fragment_display_words_settings, container, false);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(fragment.getContext(),
                R.layout.custom_spinner_item, CollectionsStorage.getWordsCollectionsTitles());
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        Spinner sprWordsCollection = fragment.findViewById(R.id.spr_words_collection);
        sprWordsCollection.setAdapter(adapter);
        sprWordsCollection.setLongClickable(true);
        sprWordsCollection.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(DEBUG_TAG, "in DisplayWordsSettingsFragment: long click on " + position);
                return true;
            }
        });

        SharedPreferences sharedPreferences =
                getContext().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        int wordsCollectionPosition = sharedPreferences.getInt(getString(R.string.saved_words_collection_position_key), 0);
        Log.d(DEBUG_TAG, String.valueOf(wordsCollectionPosition));
        int wordShowTime = sharedPreferences.getInt(getString(R.string.saved_word_show_time_key), 2);

        sprWordsCollection.setSelection(wordsCollectionPosition);

        NumberPicker pckrWordShowTime = fragment.findViewById(R.id.pckr_word_show_time);
        pckrWordShowTime.setMinValue(1);
        pckrWordShowTime.setMaxValue(6);
        pckrWordShowTime.setValue(wordShowTime);
        return fragment;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

}
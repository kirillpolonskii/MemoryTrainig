package com.youngsophomore.fragments;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.youngsophomore.R;

public class AddWordsCollectionFragment extends Fragment {
    private static final String DEBUG_TAG = "Gestures";
    public AddWordsCollectionFragment() {
        // Required empty public constructor
    }

    public static AddWordsCollectionFragment newInstance(String param1, String param2) {
        Log.d(DEBUG_TAG, "AddWordsCollectionFragment in AddWordsCollectionFragment");

        return new AddWordsCollectionFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(DEBUG_TAG, "onCreate in AddWordsCollectionFragment");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(DEBUG_TAG, "onCreateView in AddWordsCollectionFragment");
        View fragment = inflater.inflate(R.layout.fragment_add_words_collection, container, false);

        return fragment;
    }
}
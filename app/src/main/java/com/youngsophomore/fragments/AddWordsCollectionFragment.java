package com.youngsophomore.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.youngsophomore.R;

public class AddWordsCollectionFragment extends Fragment {
    public AddWordsCollectionFragment() {
        // Required empty public constructor
    }

    public static AddWordsCollectionFragment newInstance(String param1, String param2) {
        return new AddWordsCollectionFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        
        View fragment = inflater.inflate(R.layout.fragment_add_words_collection, container, false);
        return fragment;
    }
}
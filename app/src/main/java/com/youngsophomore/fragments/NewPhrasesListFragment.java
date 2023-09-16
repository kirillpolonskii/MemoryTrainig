package com.youngsophomore.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.color.MaterialColors;
import com.google.android.material.divider.MaterialDividerItemDecoration;
import com.youngsophomore.R;
import com.youngsophomore.adapters.PhrasesAdapter;

import java.util.ArrayList;

public class NewPhrasesListFragment extends Fragment {
    private static final String DEBUG_TAG = "Gestures";
    private ArrayList<CharSequence> newPhrasesCollectionCharS;
    private ArrayList<String> newPhrasesCollection;


    public NewPhrasesListFragment() {
        Log.d(DEBUG_TAG, "in NewPhrasesListFragment() of NewPhrasesListFragment");
        // Required empty public constructor
    }

    public static NewPhrasesListFragment newInstance() {
        Log.d(DEBUG_TAG, "in newInstance() of NewPhrasesListFragment");
        return new NewPhrasesListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        newPhrasesCollectionCharS = getArguments().getCharSequenceArrayList(getString(R.string.new_phrases_collection_key));
        Log.d(DEBUG_TAG, "in onCreate() of NewPhrasesListFragment");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(DEBUG_TAG, "in onCreateView() of NewPhrasesListFragment");
        return inflater.inflate(R.layout.fragment_new_phrases_list, container, false);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        Log.d(DEBUG_TAG, "in onViewCreated() of NewPhrasesListFragment");
        newPhrasesCollection = new ArrayList<>();
        for(CharSequence phraseCharS: newPhrasesCollectionCharS){
            newPhrasesCollection.add(String.valueOf(phraseCharS));
        }
        PhrasesAdapter phrasesAdapter = new PhrasesAdapter(newPhrasesCollection);
        RecyclerView rvNewPhrasesCollection = view.findViewById(R.id.rv_new_phrases_collection);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvNewPhrasesCollection.setLayoutManager(layoutManager);
        rvNewPhrasesCollection.setAdapter(phrasesAdapter);
        MaterialDividerItemDecoration dividerItemDecoration = new MaterialDividerItemDecoration(rvNewPhrasesCollection.getContext(),
                layoutManager.getOrientation());
        dividerItemDecoration.setDividerColorResource(getContext(), R.color.blue);
        rvNewPhrasesCollection.addItemDecoration(dividerItemDecoration);
    }
    @Override
    public void onStart() {
        super.onStart();
        Log.d(DEBUG_TAG, "in onStart() of NewPhrasesListFragment");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(DEBUG_TAG, "in onResume() of NewPhrasesListFragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(DEBUG_TAG, "in onPause() of NewPhrasesListFragment");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(DEBUG_TAG, "in onStop() of NewPhrasesListFragment");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(DEBUG_TAG, "in onDestroy() of NewPhrasesListFragment");
    }
}
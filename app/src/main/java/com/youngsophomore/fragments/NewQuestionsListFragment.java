package com.youngsophomore.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.divider.MaterialDividerItemDecoration;
import com.youngsophomore.R;
import com.youngsophomore.adapters.QuestionsAdapter;
import com.youngsophomore.data.Question;

import java.util.ArrayList;

public class NewQuestionsListFragment extends Fragment {
    private static final String DEBUG_TAG = "Gestures";
    private ArrayList<Question> newQuestionsCollection;


    public NewQuestionsListFragment() {
        Log.d(DEBUG_TAG, "in NewQuestionsListFragment() of NewQuestionsListFragment");
        // Required empty public constructor
    }

    public static NewQuestionsListFragment newInstance() {
        Log.d(DEBUG_TAG, "in newInstance() of NewQuestionsListFragment");
        NewQuestionsListFragment fragment = new NewQuestionsListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(DEBUG_TAG, "in onCreate() of NewQuestionsListFragment");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            newQuestionsCollection = getArguments().getParcelableArrayList(getString(R.string.new_questions_collection_key));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(DEBUG_TAG, "in onCreateView() of NewQuestionsListFragment");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_questions_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        Log.d(DEBUG_TAG, "in onViewCreated() of NewPhrasesListFragment");

        QuestionsAdapter questionsAdapter = new QuestionsAdapter(newQuestionsCollection);
        RecyclerView rvNewQuestionsCollection = view.findViewById(R.id.rv_new_questions_collection);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvNewQuestionsCollection.setLayoutManager(layoutManager);
        rvNewQuestionsCollection.setAdapter(questionsAdapter);
        MaterialDividerItemDecoration dividerItemDecoration =
                new MaterialDividerItemDecoration(rvNewQuestionsCollection.getContext(),
                    layoutManager.getOrientation());
        dividerItemDecoration.setDividerColorResource(getContext(), R.color.blue);
        rvNewQuestionsCollection.addItemDecoration(dividerItemDecoration);
    }
}
package com.youngsophomore.fragments;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
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
import com.youngsophomore.interfaces.RecyclerViewClickListener;

import java.util.ArrayList;

public class NewQuestionsListFragment extends Fragment
        implements RecyclerViewClickListener,
        DeleteQuestionDialogFragment.DeleteQuestionDialogListener {
    private static final String DEBUG_TAG = "Gestures";
    private ArrayList<Question> newQuestionsCollection;
    QuestionsAdapter questionsAdapter;

    public NewQuestionsListFragment() {
        
    }

    public static NewQuestionsListFragment newInstance() {
        
        NewQuestionsListFragment fragment = new NewQuestionsListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            newQuestionsCollection = getArguments().getParcelableArrayList(getString(R.string.new_questions_collection_key));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        
        return inflater.inflate(R.layout.fragment_new_questions_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        

        questionsAdapter = new QuestionsAdapter(newQuestionsCollection, this);
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
    public void showDeleteQuestionDialog(int position){
        DialogFragment newFragment = new DeleteQuestionDialogFragment(position);
        newFragment.show(getChildFragmentManager(), "DeleteQuestionDialogFragment");
    }
    @Override
    public void onItemLongClick(int position) {
        showDeleteQuestionDialog(position);
    }

    @Override
    public void onDeleteQuestionPosClick(DialogFragment dialog, int position) {
        newQuestionsCollection.remove(position);
        questionsAdapter.notifyItemRemoved(position);
    }

    @Override
    public void onDeleteQuestionNegClick(DialogFragment dialog) {

    }
}
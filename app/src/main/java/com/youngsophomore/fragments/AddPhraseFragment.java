package com.youngsophomore.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.youngsophomore.R;

public class AddPhraseFragment extends Fragment {
    private static final String DEBUG_TAG = "Gestures";
    private EditText etNewPhrase;


    public AddPhraseFragment() {
        // Required empty public constructor
    }

    public static AddPhraseFragment newInstance(String param1, String param2) {
        return new AddPhraseFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_phrase, container, false);
        etNewPhrase = view.findViewById(R.id.et_new_phrase);
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(DEBUG_TAG, "in onStop() of NewPhrasesListFragment");
        Bundle result = new Bundle();
        result.putCharSequence(getString(R.string.new_phrase_key), etNewPhrase.getText().toString());
        getParentFragmentManager().setFragmentResult("requestKey", result);
    }
}
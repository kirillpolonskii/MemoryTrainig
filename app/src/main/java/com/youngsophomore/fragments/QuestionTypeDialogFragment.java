package com.youngsophomore.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.youngsophomore.R;

public class QuestionTypeDialogFragment extends DialogFragment {
    private static final String DEBUG_TAG = "Gestures";
    public interface QuestionTypeDialogListener {
        public void onQuestionTypePosClick(DialogFragment dialog);
        public void onQuestionTypeNegClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    QuestionTypeDialogListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(DEBUG_TAG, "in onAttach() of QuestionTypeDialogFragment");
        Log.d(DEBUG_TAG, "context: " + context.toString());
        // Verify that the host activity implements the callback interface
        listener = (QuestionTypeDialogListener) getParentFragment();
        /*try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (QuestionTypeDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(getParentFragment().toString()
                    + " must implement QuestionTypeDialogListener");
        }*/
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d(DEBUG_TAG, "in onCreateDialog() of QuestionTypeDialogFragment");

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.question_type_dialog_title)
                .setMessage(R.string.question_type_dialog_message)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onQuestionTypePosClick(QuestionTypeDialogFragment.this);
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onQuestionTypeNegClick(QuestionTypeDialogFragment.this);
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    /*public QuestionTypeDialogFragment() {
        // Required empty public constructor
    }

    public static QuestionTypeDialogFragment newInstance(String param1, String param2) {
        QuestionTypeDialogFragment fragment = new QuestionTypeDialogFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_question_type_dialog, container, false);
    }*/
}
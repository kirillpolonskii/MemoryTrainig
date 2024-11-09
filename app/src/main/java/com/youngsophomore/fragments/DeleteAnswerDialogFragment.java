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

public class DeleteAnswerDialogFragment extends DialogFragment {
    private static final String DEBUG_TAG = "Gestures";
    private int deletedPosition;
    public interface DeleteAnswerDialogListener {
        public void onDeleteAnswerPosClick(DialogFragment dialog, int position);
        public void onDeleteAnswerNegClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    DeleteAnswerDialogListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(DEBUG_TAG, "in onAttach() of DeleteAnswerDialogFragment");
        Log.d(DEBUG_TAG, "context: " + context.toString());
        // Verify that the host activity implements the callback interface
        //listener = (DeleteAnswerDialogListener) getParentFragment();
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (DeleteAnswerDialogListener) getParentFragment();
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(getParentFragment().toString()
                    + " must implement DeleteAnswerDialogListener");
        }
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d(DEBUG_TAG, "in onCreateDialog() of DeleteAnswerDialogFragment");

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.delete_answer_dialog_title)
                .setMessage(R.string.delete_answer_dialog_message)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDeleteAnswerPosClick(DeleteAnswerDialogFragment.this, deletedPosition);
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDeleteAnswerNegClick(DeleteAnswerDialogFragment.this);
                    }
                });
        // Create the AlertDialog object and return it
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    public DeleteAnswerDialogFragment(int position) {
        Log.d(DEBUG_TAG, "in DeleteAnswerDialogFragment()");
        deletedPosition = position;
    }

    /*public static DeleteAnswerDialogFragment newInstance(String param1, String param2) {
        DeleteAnswerDialogFragment fragment = new DeleteAnswerDialogFragment();
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
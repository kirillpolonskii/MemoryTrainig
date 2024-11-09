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

public class DeleteQuestionDialogFragment extends DialogFragment {

    private static final String DEBUG_TAG = "Gestures";
    private int deletedPosition;
    public interface DeleteQuestionDialogListener {
        public void onDeleteQuestionPosClick(DialogFragment dialog, int position);
        public void onDeleteQuestionNegClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    DeleteQuestionDialogFragment.DeleteQuestionDialogListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(DEBUG_TAG, "in onAttach() of DeleteQuestionDialogFragment");
        Log.d(DEBUG_TAG, "context: " + context.toString());
        // Verify that the host activity implements the callback interface
        //listener = (DeleteQuestionDialogListener) getParentFragment();
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (DeleteQuestionDialogFragment.DeleteQuestionDialogListener) getParentFragment();
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(getParentFragment().toString()
                    + " must implement DeleteQuestionDialogListener");
        }
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d(DEBUG_TAG, "in onCreateDialog() of DeleteQuestionDialogFragment");

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.delete_question_dialog_title)
                .setMessage(R.string.delete_question_dialog_message)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDeleteQuestionPosClick(DeleteQuestionDialogFragment.this, deletedPosition);
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDeleteQuestionNegClick(DeleteQuestionDialogFragment.this);
                    }
                });
        // Create the AlertDialog object and return it
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    public DeleteQuestionDialogFragment(int position) {
        Log.d(DEBUG_TAG, "in DeleteQuestionDialogFragment()");
        deletedPosition = position;
    }

}
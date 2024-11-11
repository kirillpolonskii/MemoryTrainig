package com.youngsophomore.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import android.util.Log;

import com.youngsophomore.R;

public class DeleteQuestionDialogFragment extends DialogFragment {

    private static final String DEBUG_TAG = "Gestures";
    private int deletedPosition;
    public interface DeleteQuestionDialogListener {
        void onDeleteQuestionPosClick(DialogFragment dialog, int position);
        void onDeleteQuestionNegClick(DialogFragment dialog);
    }

    DeleteQuestionDialogFragment.DeleteQuestionDialogListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        
        
        try {
            listener = (DeleteQuestionDialogFragment.DeleteQuestionDialogListener) getParentFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(getParentFragment().toString()
                    + " must implement DeleteQuestionDialogListener");
        }
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        
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
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    public DeleteQuestionDialogFragment(int position) {
        
        deletedPosition = position;
    }

}
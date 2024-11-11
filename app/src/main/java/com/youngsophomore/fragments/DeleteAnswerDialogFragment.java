package com.youngsophomore.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import android.util.Log;

import com.youngsophomore.R;

public class DeleteAnswerDialogFragment extends DialogFragment {
    private static final String DEBUG_TAG = "Gestures";
    private int deletedPosition;
    public interface DeleteAnswerDialogListener {
        void onDeleteAnswerPosClick(DialogFragment dialog, int position);
        void onDeleteAnswerNegClick(DialogFragment dialog);
    }

    DeleteAnswerDialogListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(DEBUG_TAG, "in onAttach() of DeleteAnswerDialogFragment");
        Log.d(DEBUG_TAG, "context: " + context.toString());
        try {
            listener = (DeleteAnswerDialogListener) getParentFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(getParentFragment().toString()
                    + " must implement DeleteAnswerDialogListener");
        }
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d(DEBUG_TAG, "in onCreateDialog() of DeleteAnswerDialogFragment");

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
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    public DeleteAnswerDialogFragment(int position) {
        Log.d(DEBUG_TAG, "in DeleteAnswerDialogFragment()");
        deletedPosition = position;
    }
}
package com.youngsophomore.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import android.util.Log;

import com.youngsophomore.R;

public class CorrectAnswerDialogFragment extends DialogFragment {
    public interface CorrectAnswerDialogListener {
        void onCorrectAnswerPosClick(DialogFragment dialog);
        void onCorrectAnswerNegClick(DialogFragment dialog);
    }

    CorrectAnswerDialogListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        
        
        try {
            listener = (CorrectAnswerDialogListener) getParentFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(getParentFragment().toString()
                    + " must implement CorrectAnswerDialogListener");
        }
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.correct_answer_dialog_title)
                .setMessage(R.string.correct_answer_dialog_message)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onCorrectAnswerPosClick(CorrectAnswerDialogFragment.this);
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onCorrectAnswerNegClick(CorrectAnswerDialogFragment.this);
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }
}
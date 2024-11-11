package com.youngsophomore.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import android.util.Log;

import com.youngsophomore.R;

public class QuestionTypeDialogFragment extends DialogFragment {
    private static final String DEBUG_TAG = "Gestures";
    public interface QuestionTypeDialogListener {
        void onQuestionTypePosClick(DialogFragment dialog);
        void onQuestionTypeNegClick(DialogFragment dialog);
    }

    QuestionTypeDialogListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(DEBUG_TAG, "in onAttach() of QuestionTypeDialogFragment");
        listener = (QuestionTypeDialogListener) getParentFragment();
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
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
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }
}
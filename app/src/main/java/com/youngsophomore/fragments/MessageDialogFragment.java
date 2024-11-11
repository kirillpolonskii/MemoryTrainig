package com.youngsophomore.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import android.util.Log;

import com.youngsophomore.R;

public class MessageDialogFragment extends DialogFragment {
    private static final String DEBUG_TAG = "Gestures";
    private String title;
    private String message;
    public MessageDialogFragment(String title, String message){
        this.title = title;
        this.message = message;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MessageDialogFragment.this.getDialog().cancel();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }
}
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

public class MessageDialogFragment extends DialogFragment {
    private static final String DEBUG_TAG = "Gestures";
    private String title;
    private String message;
    public MessageDialogFragment(String title, String message){
        this.title = title;
        this.message = message;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d(DEBUG_TAG, "in onCreateDialog() of MessageDialogFragment");

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MessageDialogFragment.this.getDialog().cancel();
                    }
                });
        // Create the AlertDialog object and return it
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }
}
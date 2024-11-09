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

public class DeleteCollectionDialogFragment extends DialogFragment {

    private static final String DEBUG_TAG = "Gestures";
    public interface DeleteCollectionDialogListener {
        public void onDeleteCollectionPosClick(DialogFragment dialog);
        public void onDeleteCollectionNegClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    DeleteCollectionDialogFragment.DeleteCollectionDialogListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(DEBUG_TAG, "in onAttach() of DeleteCollectionDialogFragment");
        Log.d(DEBUG_TAG, "context: " + context.toString());
        // Verify that the host activity implements the callback interface
        //listener = (DeleteCollectionDialogFragment) getParentFragment();
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (DeleteCollectionDialogFragment.DeleteCollectionDialogListener) getParentFragment();
            if(listener == null) { // this Fragment is attached directly to an Activity
                listener = (DeleteCollectionDialogFragment.DeleteCollectionDialogListener) getActivity();
            }
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(getParentFragment().toString()
                    + " must implement DeleteCollectionDialogFragment");
        }
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d(DEBUG_TAG, "in onCreateDialog() of DeleteCollectionDialogFragment");

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Material_Light_Dialog_NoActionBar_MinWidth);
        builder.setTitle(R.string.delete_collection_dialog_title)
                .setMessage(R.string.delete_collection_dialog_message)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDeleteCollectionPosClick(DeleteCollectionDialogFragment.this);
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDeleteCollectionNegClick(DeleteCollectionDialogFragment.this);
                    }
                });
        // Create the AlertDialog object and return it
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    public DeleteCollectionDialogFragment() {
        Log.d(DEBUG_TAG, "in DeleteCollectionDialogFragment()");
    }
}
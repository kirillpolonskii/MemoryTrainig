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
        void onDeleteCollectionPosClick(DialogFragment dialog);
        void onDeleteCollectionNegClick(DialogFragment dialog);
    }

    DeleteCollectionDialogFragment.DeleteCollectionDialogListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        
        
        try {
            listener = (DeleteCollectionDialogFragment.DeleteCollectionDialogListener) getParentFragment();
            if(listener == null) {
                listener = (DeleteCollectionDialogFragment.DeleteCollectionDialogListener) getActivity();
            }
        } catch (ClassCastException e) {
            throw new ClassCastException(getParentFragment().toString()
                    + " must implement DeleteCollectionDialogFragment");
        }
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),
                android.R.style.Theme_Material_Light_Dialog_NoActionBar_MinWidth);
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
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    public DeleteCollectionDialogFragment() {
        
    }
}
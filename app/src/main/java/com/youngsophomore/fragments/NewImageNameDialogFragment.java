package com.youngsophomore.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.youngsophomore.R;

public class NewImageNameDialogFragment extends DialogFragment {
    private static final String DEBUG_TAG = "Gestures";
    public interface NewImageNameDialogListener {
        void onNewImageNamePosClick(DialogFragment dialog, String newImageName);
    }

    NewImageNameDialogListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        
        
        try {
            listener = (NewImageNameDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getParentFragment().toString()
                    + " must implement NewImageNameDialogListener");
        }
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),
                android.R.style.Theme_Material_Light_Dialog_NoActionBar_MinWidth);
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.fragment_new_image_name_dialog, null);
        EditText etNewImageName = view.findViewById(R.id.et_new_image);
        builder.setView(view)
                .setTitle(R.string.new_image_dialog_title)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onNewImageNamePosClick(NewImageNameDialogFragment.this,
                                etNewImageName.getText().toString());
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }
}
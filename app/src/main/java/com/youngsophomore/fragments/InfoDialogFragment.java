package com.youngsophomore.fragments;

import android.animation.LayoutTransition;
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
import android.widget.EditText;

import com.youngsophomore.R;

public class InfoDialogFragment extends DialogFragment {
    private static final String DEBUG_TAG = "Gestures";
    private int layoutResource;

    // Use this instance of the interface to deliver action events

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d(DEBUG_TAG, "in onCreateDialog() of InfoDialogFragment");
        // Use the Builder class for convenient dialog construction
        //AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Material_Light_Dialog_NoActionBar);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Material_Light_Dialog_NoActionBar_MinWidth);
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(layoutResource, null);
        EditText etNewImageName = view.findViewById(R.id.et_new_image);
        builder.setView(view)
                .setTitle(R.string.instruction)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    public InfoDialogFragment(int layoutResource) {
        this.layoutResource = layoutResource;
    }

    /*public static InfoDialogFragment newInstance(String param1, String param2) {
        InfoDialogFragment fragment = new InfoDialogFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_question_type_dialog, container, false);
    }*/
}
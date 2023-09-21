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

public class NewImageNameDialogFragment extends DialogFragment {
    private static final String DEBUG_TAG = "Gestures";
    public interface NewImageNameDialogListener {
        public void onNewImageNamePosClick(DialogFragment dialog, String newImageName);
    }

    // Use this instance of the interface to deliver action events
    NewImageNameDialogListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(DEBUG_TAG, "in onAttach() of NewImageNameDialogFragment");
        Log.d(DEBUG_TAG, "context: " + context.toString());
        // Verify that the host activity implements the callback interface
        //listener = (NewImageNameDialogListener) context;
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (NewImageNameDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(getParentFragment().toString()
                    + " must implement NewImageNameDialogListener");
        }
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d(DEBUG_TAG, "in onCreateDialog() of NewImageNameDialogFragment");
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
        // Create the AlertDialog object and return it
        return builder.create();
    }

    /*public NewImageNameDialogFragment() {
        // Required empty public constructor
    }

    public static NewImageNameDialogFragment newInstance(String param1, String param2) {
        NewImageNameDialogFragment fragment = new NewImageNameDialogFragment();
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
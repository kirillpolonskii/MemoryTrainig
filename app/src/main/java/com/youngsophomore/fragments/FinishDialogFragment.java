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
import android.widget.EditText;
import android.widget.TextView;

import com.youngsophomore.R;


public class FinishDialogFragment extends DialogFragment {

    private static final String DEBUG_TAG = "Gestures";
    private String trainingDurSec, amountText, amount;
    public interface FinishDialogListener {
        public void onFinishPosClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    FinishDialogFragment.FinishDialogListener listener;

    public FinishDialogFragment(String trainingDurSec, String amountText, String amount){
        this.trainingDurSec = trainingDurSec;
        this.amountText = amountText;
        this.amount = amount;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(DEBUG_TAG, "in onAttach() of FinishDialogFragment");
        Log.d(DEBUG_TAG, "context: " + context.toString());
        // Verify that the host activity implements the callback interface
        //listener = (FinishDialogListener) context;
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (FinishDialogFragment.FinishDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(getParentFragment().toString()
                    + " must implement FinishDialogListener");
        }
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d(DEBUG_TAG, "in onCreateDialog() of FinishDialogFragment");
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Material_Light_Dialog_NoActionBar_MinWidth);
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.fragment_finish_dialog, null);
        TextView tvTrainingDurSec = view.findViewById(R.id.tv_training_dur_sec);
        TextView tvAmountText = view.findViewById(R.id.tv_amount_text);
        TextView tvAmount = view.findViewById(R.id.tv_amount);
        tvTrainingDurSec.setText(trainingDurSec);
        tvAmountText.setText(amountText);
        tvAmount.setText(amount);
        builder.setView(view)
                .setTitle(R.string.finish_dialog_title)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onFinishPosClick(FinishDialogFragment.this);
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
package com.youngsophomore.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.youngsophomore.R;


public class FinishDialogFragment extends DialogFragment {

    private String trainingDurSec, amountText, amount;
    public interface FinishDialogListener {
        void onFinishPosClick(DialogFragment dialog);
    }

    FinishDialogFragment.FinishDialogListener listener;

    public FinishDialogFragment(String trainingDurSec, String amountText, String amount){
        this.trainingDurSec = trainingDurSec;
        this.amountText = amountText;
        this.amount = amount;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (FinishDialogFragment.FinishDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getParentFragment().toString()
                    + " must implement FinishDialogListener");
        }
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
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
        return builder.create();
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
        listener.onFinishPosClick(FinishDialogFragment.this);
    }
}
package com.youngsophomore.adapters;

import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.youngsophomore.R;
import com.youngsophomore.interfaces.RecyclerViewClickListener;

import java.util.ArrayList;

public class AnswersAdapter extends RecyclerView.Adapter<AnswersAdapter.ViewHolder> {
    private RecyclerViewClickListener rvClickListener;
    private static final String DEBUG_TAG = "Gestures";

    private ArrayList<String> answers;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ViewHolder(View view, RecyclerViewClickListener rvClickListener) {
            super(view);
            // Define click listener for the ViewHolder's View
            textView = (TextView) view.findViewById(R.id.textView);
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(rvClickListener != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            rvClickListener.onItemLongClick(pos);
                        }
                    }
                    return true;
                }

            });
        }

        public TextView getTextView() {
            return textView;
        }
    }


    public AnswersAdapter(ArrayList<String> dataSet, RecyclerViewClickListener rvClickListener) {
        Log.d(DEBUG_TAG, "In AnswersAdapter()");
        answers = dataSet;
        this.rvClickListener = rvClickListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Log.d(DEBUG_TAG, "AnswersAdapter: In onCreateViewHolder()");
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.text_row_item, viewGroup, false);

        return new ViewHolder(view, rvClickListener);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(DEBUG_TAG, "In onBindViewHolder()");
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTextView().setText(answers.get(position));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        Log.d(DEBUG_TAG, "In getItemCount()");
        return answers.size();
    }
}


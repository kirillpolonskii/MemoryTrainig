package com.youngsophomore.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.youngsophomore.R;
import com.youngsophomore.interfaces.RecyclerViewClickListener;

import java.util.ArrayList;

public class AnswersAdapter extends RecyclerView.Adapter<AnswersAdapter.ViewHolder> {
    private static final String DEBUG_TAG = "Gestures";
    private RecyclerViewClickListener rvClickListener;

    private ArrayList<String> answers;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ViewHolder(View view, RecyclerViewClickListener rvClickListener) {
            super(view);
            textView = view.findViewById(R.id.textView);
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

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.text_row_item, viewGroup, false);

        return new ViewHolder(view, rvClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getTextView().setText(answers.get(position));
    }

    @Override
    public int getItemCount() {
        return answers.size();
    }
}


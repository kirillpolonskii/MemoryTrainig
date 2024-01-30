package com.youngsophomore.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.youngsophomore.R;
import com.youngsophomore.data.Question;
import com.youngsophomore.interfaces.RecyclerViewClickListener;

import java.util.ArrayList;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.ViewHolder> {

    private static final String DEBUG_TAG = "Gestures";
    private ArrayList<Question> localQuestions;
    private RecyclerViewClickListener rvClickListener;

    public QuestionsAdapter(ArrayList<Question> questions, RecyclerViewClickListener rvClickListener) {
        Log.d(DEBUG_TAG, "In QuestionsAdapter()");
        localQuestions = questions;
        this.rvClickListener = rvClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvQuestionText;
        private final TextView tvAnswers;

        public ViewHolder(View view, RecyclerViewClickListener rvClickListener) {
            super(view);

            tvQuestionText = (TextView) view.findViewById(R.id.tv_question_text);
            tvAnswers = (TextView) view.findViewById(R.id.tv_answers);
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

        public TextView getTVQuestionText() {
            return tvQuestionText;
        }
        public TextView getTVAnswers() {
            return tvAnswers;
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Log.d(DEBUG_TAG, "QuestionsAdapter: In onCreateViewHolder()");
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.new_question_list_item, viewGroup, false);

        return new QuestionsAdapter.ViewHolder(view, rvClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Log.d(DEBUG_TAG, "QuestionsAdapter: In onBindViewHolder()");
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTVQuestionText().setText(localQuestions.get(position).getQuestionText());
        viewHolder.getTVAnswers().setText(localQuestions.get(position).getAnswersInOneString(true));
    }

    @Override
    public int getItemCount() {
        Log.d(DEBUG_TAG, "QuestionsAdapter: In getItemCount()");
        return localQuestions.size();
    }
}

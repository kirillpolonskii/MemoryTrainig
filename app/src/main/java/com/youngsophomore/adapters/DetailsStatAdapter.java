package com.youngsophomore.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.youngsophomore.R;

import java.util.ArrayList;

public class DetailsStatAdapter extends RecyclerView.Adapter<DetailsStatAdapter.ViewHolder> {
    ArrayList<String> questionsCollectionsTitles;
    ArrayList<String> questionsCollectionsSecsMoves;

    public DetailsStatAdapter(ArrayList<String> questionsCollectionsTitles,
                              ArrayList<String> questionsCollectionsSecsMoves){
        this.questionsCollectionsTitles = questionsCollectionsTitles;
        this.questionsCollectionsSecsMoves = questionsCollectionsSecsMoves;
    }

    @NonNull
    @Override
    public DetailsStatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_stat_imgs_collection, parent, false);

        return new DetailsStatAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsStatAdapter.ViewHolder holder, int position) {
        holder.getTvCollectTitle().setText(questionsCollectionsTitles.get(position));
        holder.getTvAvTimeMoves().setText(questionsCollectionsSecsMoves.get(position));
    }

    @Override
    public int getItemCount() {
        return questionsCollectionsSecsMoves.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvCollectTitle;
        private final TextView tvAvTimeMoves;

        public ViewHolder(View view) {
            super(view);
            tvCollectTitle = view.findViewById(R.id.tv_collect_title);
            tvAvTimeMoves = view.findViewById(R.id.tv_collect_av_time_moves);

        }

        public TextView getTvCollectTitle() {
            return tvCollectTitle;
        }
        public TextView getTvAvTimeMoves() {
            return tvAvTimeMoves;
        }
    }
}

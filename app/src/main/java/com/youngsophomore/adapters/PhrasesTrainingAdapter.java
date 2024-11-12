package com.youngsophomore.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.youngsophomore.R;

import java.util.ArrayList;

public class PhrasesTrainingAdapter extends RecyclerView.Adapter<PhrasesTrainingAdapter.ViewHolder> {
    private static final String DEBUG_TAG = "Gestures";
    public PhraseTrainingListener phraseTrainingListener;
    private ArrayList<String> localDataSet;
    public ArrayList<Integer> indicesPerm;
    int colorFocused, colorChosen;
    private Button elA;
    private int elAPos, elBPos;
    private boolean isElASelected = false;
    private float elevPx;
    private int movesAmount;
    public interface PhraseTrainingListener {
        void onFinishTraining(int movesAmount);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final Button btnCurPhrase;

        public ViewHolder(View view) {
            super(view);
            
            btnCurPhrase = view.findViewById(R.id.btn_cur_phrase);

        }

        public Button getButton() {
            return btnCurPhrase;
        }
    }

    public PhrasesTrainingAdapter(ArrayList<String> dataSet, ArrayList<Integer> indicesPerm,
                                  float elevPx, int colorFocused, int colorChosen, Context context) {
        
        localDataSet = dataSet;
        this.indicesPerm = indicesPerm;
        this.colorFocused = colorFocused;
        this.colorChosen = colorChosen;
        this.elevPx = 2 * elevPx;
        this.phraseTrainingListener = (PhraseTrainingListener) context;
    }

    @NonNull
    @Override
    public PhrasesTrainingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.phrase_training_row_el, parent, false);

        return new PhrasesTrainingAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhrasesTrainingAdapter.ViewHolder holder, int position) {
        holder.getButton().setText(localDataSet.get(position));
        
        holder.getButton().setElevation(0);
        holder.getButton().setBackgroundColor(colorFocused);
        holder.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isElASelected){
                    isElASelected = true;
                    v.setElevation(elevPx);
                    elA = (Button) v;
                    elA.setBackgroundColor(colorChosen);
                    
                    elAPos = holder.getAdapterPosition();
                }
                else if (elAPos != holder.getAdapterPosition()){
                    ++movesAmount;
                    elBPos = holder.getAdapterPosition();
                    String temp = localDataSet.get(elAPos);
                    localDataSet.set(elAPos, localDataSet.get(elBPos));
                    localDataSet.set(elBPos, temp);
                    Integer tempInd = indicesPerm.get(elAPos);
                    indicesPerm.set(elAPos, indicesPerm.get(elBPos));
                    indicesPerm.set(elBPos, tempInd);

                    elAPos = elBPos = 0;
                    elA = null;
                    isElASelected = false;
                    notifyDataSetChanged();
                    if (phrasesInRightOrder()){
                        
                        phraseTrainingListener.onFinishTraining(movesAmount);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        
        return localDataSet.size();
    }

    private boolean phrasesInRightOrder(){
        
        for(int i = 1; i < indicesPerm.size(); ++i){
            if(indicesPerm.get(i) - indicesPerm.get(i - 1) != 1){
                return false;
            }
        }
        return true;
    }
}

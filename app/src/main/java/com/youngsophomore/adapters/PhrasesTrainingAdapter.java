package com.youngsophomore.adapters;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.youngsophomore.R;

import java.util.ArrayList;
import java.util.Objects;

public class PhrasesTrainingAdapter extends RecyclerView.Adapter<PhrasesTrainingAdapter.ViewHolder> {
    private static final String DEBUG_TAG = "Gestures";

    private ArrayList<String> localDataSet;
    public ArrayList<Integer> indicesPerm;
    int colorFocused, colorChosen;
    private Button elA;
    private Button elB;
    private int elAPos, elBPos;
    private boolean isElASelected = false;
    private float elevPx;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final Button btnCurPhrase;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            Log.d(DEBUG_TAG, "In public ViewHolder()");
            btnCurPhrase = view.findViewById(R.id.btn_cur_phrase);

        }

        public Button getButton() {
            return btnCurPhrase;
        }
    }

    public PhrasesTrainingAdapter(ArrayList<String> dataSet, ArrayList<Integer> indicesPerm,
                                  float elevPx, int colorFocused, int colorChosen) {
        Log.d(DEBUG_TAG, "In PhrasesAdapter()");
        localDataSet = dataSet;
        this.indicesPerm = indicesPerm;
        this.colorFocused = colorFocused;
        this.colorChosen = colorChosen;
        this.elevPx = 2 * elevPx;
    }

    @NonNull
    @Override
    public PhrasesTrainingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(DEBUG_TAG, "In onCreateViewHolder()");
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.phrase_training_row_el, parent, false);

        return new PhrasesTrainingAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhrasesTrainingAdapter.ViewHolder holder, int position) {
        Log.d(DEBUG_TAG, "In onBindViewHolder()");
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        holder.getButton().setText(localDataSet.get(position));
        Log.d(DEBUG_TAG, "In onBindViewHolder(): elevation = " + holder.getButton().getElevation());
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
                    Log.d(DEBUG_TAG, "In onClick(): " + elA);
                    elAPos = holder.getAdapterPosition();
                }
                else if (elAPos != holder.getAdapterPosition()){
                    elBPos = holder.getAdapterPosition();
                    String temp = localDataSet.get(elAPos);
                    localDataSet.set(elAPos, localDataSet.get(elBPos));
                    localDataSet.set(elBPos, temp);
                    Integer tempInd = indicesPerm.get(elAPos);
                    indicesPerm.set(elAPos, indicesPerm.get(elBPos));
                    indicesPerm.set(elBPos, tempInd);
                    elB = (Button) v;
                    //elA.setElevation(0);
                    //elB.setElevation(0);
                    Log.d(DEBUG_TAG, "In onClick(): " + elB);
                    elAPos = elBPos = 0;
                    elA = null;
                    elB = null;
                    isElASelected = false;
                    notifyDataSetChanged();
                    if (phrasesInRightOrder()){
                        Log.d(DEBUG_TAG, "YOU ORDERED PHRASES CORRECTLY");
                        // show finish dialog
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d(DEBUG_TAG, "In getItemCount()");
        return localDataSet.size();
    }

    private boolean phrasesInRightOrder(){
        Log.d(DEBUG_TAG, "Entered phrasesInRightOrder()");
        for(int i = 1; i < indicesPerm.size(); ++i){
            if(indicesPerm.get(i) - indicesPerm.get(i - 1) != 1){
                return false;
            }
        }
        return true;
    }
}

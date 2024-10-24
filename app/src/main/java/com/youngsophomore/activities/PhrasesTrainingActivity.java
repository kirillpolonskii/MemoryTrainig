package com.youngsophomore.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.youngsophomore.R;
import com.youngsophomore.adapters.PhrasesTrainingAdapter;
import com.youngsophomore.data.CollectionsStorage;
import com.youngsophomore.fragments.FinishDialogFragment;
import com.youngsophomore.fragments.StopwatchFragment;
import com.youngsophomore.helpers.TrainHelper;

import java.util.ArrayList;

public class PhrasesTrainingActivity extends AppCompatActivity implements
        FinishDialogFragment.FinishDialogListener,
        PhrasesTrainingAdapter.PhraseTrainingListener {
    private static final String DEBUG_TAG = "Gestures";
    private static final String STOPWATCH_FRAGMENT_TAG = "stopwatch_fragment_tag";
    int curPhraseShowInd = 0;
    FragmentManager fragmentManager;
    private int movesAmount = 0;
    private int trainingDurationSec = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pretrain_sequence_layout);
        SharedPreferences sharedPreferences =
                getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        int phrasesCollectionPosition = sharedPreferences.getInt(getString(R.string.saved_phrases_collection_position_key), 0);;
        int phraseShowTime = sharedPreferences.getInt(getString(R.string.saved_phrase_show_time_key), 2);
        Log.d(DEBUG_TAG, "phrasesCollectionPosition and phraseShowTime = " +
                phrasesCollectionPosition + " " +
                phraseShowTime);
        ArrayList<String> phrasesCollectionsTitles = CollectionsStorage.getCollectionsTitles(
                sharedPreferences, getString(R.string.phrases_collections_titles_key)
        );
        ArrayList<String> origPhrasesCollection = CollectionsStorage.getPhrasesCollection(
                phrasesCollectionsTitles.get(phrasesCollectionPosition),
                getApplicationContext().getExternalFilesDir(null).getAbsolutePath() + "/phrases"
        );
        ArrayList<Integer> indicesPerm = TrainHelper.getRandomIndicesPerm(0, origPhrasesCollection.size());
        ArrayList<String> phrasesCollection = TrainHelper.Phrases.generatePhrasesList(origPhrasesCollection, indicesPerm);
        int elevPx = getResources().getDimensionPixelSize(R.dimen.btn_stats_elev);
        PhrasesTrainingAdapter phrasesAdapter = new PhrasesTrainingAdapter(
                phrasesCollection, indicesPerm, elevPx,
                getResources().getColor(R.color.light_blue),
                getResources().getColor(R.color.medium_blue),
                this);

        TextView tvCountdown = findViewById(R.id.tv_countdown);
        TextView tvCurPhraseNum = findViewById(R.id.tv_cur_elem_num);
        CountDownTimer countDownTimer = new CountDownTimer(3000 + 200, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d(DEBUG_TAG, "onTick: millisUntilFinished = " + millisUntilFinished);
                tvCountdown.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                // get string with all titles from sharedpref
                // split it and get unique title by the phrasesCollectionPosition

                Log.d(DEBUG_TAG, phrasesCollectionsTitles.toString());
                // get phrases collection from /phrases via title

                // String[] splittedPhrasesCollection = strPhrasesCollection.split(" ");
                ArrayList<String> origPhrasesCollection = CollectionsStorage.getPhrasesCollection(
                        phrasesCollectionsTitles.get(phrasesCollectionPosition),
                        getApplicationContext().getExternalFilesDir(null).getAbsolutePath() + "/phrases"
                );
                ArrayList<Integer> indicesPerm = TrainHelper.getRandomIndicesPerm(0, origPhrasesCollection.size());
                ArrayList<String> phrasesCollection = TrainHelper.Phrases.generatePhrasesList(origPhrasesCollection, indicesPerm);
                Log.d(DEBUG_TAG, phrasesCollection.toString());
                tvCountdown.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        getResources().getDimension(R.dimen.w_training_seq_text_size));
                CountDownTimer pretrainSequenceTimer = new CountDownTimer(
                        phrasesCollection.size() * phraseShowTime * 1000 + 200, phraseShowTime * 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        if(curPhraseShowInd < phrasesCollection.size()){
                            tvCountdown.setText(origPhrasesCollection.get(curPhraseShowInd));
                            ++curPhraseShowInd;
                            tvCurPhraseNum.setText(String.valueOf(curPhraseShowInd));
                            Log.d(DEBUG_TAG, "curPhraseInd = " + curPhraseShowInd);
                        }
                    }

                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public void onFinish() {
                        setContentView(R.layout.activity_phrases_training);


                        RecyclerView rvPhrasesCollection = findViewById(R.id.rv_phrases_collection);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                        rvPhrasesCollection.setLayoutManager(layoutManager);
                        rvPhrasesCollection.setAdapter(phrasesAdapter);

                        // Запуск секундомера
                        Bundle bundle = new Bundle();
                        fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction()
                                .setReorderingAllowed(true)
                                .add(R.id.frgt_view, StopwatchFragment.class, bundle, STOPWATCH_FRAGMENT_TAG)
                                .commit();
                    }
                }.start();
            }
        }.start();

    }

    @Override
    public void onFinishTraining(int movesAmount) {
        this.movesAmount = movesAmount;
        StopwatchFragment stopwatchFragment =
                (StopwatchFragment) fragmentManager.findFragmentByTag(STOPWATCH_FRAGMENT_TAG);
        trainingDurationSec = stopwatchFragment.getDecisecond() / 10;
        stopwatchFragment.finishStopwatch();
        DialogFragment finishFragment = new FinishDialogFragment(
                trainingDurationSec + " с.",
                getResources().getString(R.string.phr_train_moves_amount),
                String.valueOf(movesAmount)
        );
        finishFragment.show(getSupportFragmentManager(), "FinishDialogFragment");
    }

    @Override
    public void onFinishPosClick(DialogFragment dialog) {
        onBackPressed();
    }

}
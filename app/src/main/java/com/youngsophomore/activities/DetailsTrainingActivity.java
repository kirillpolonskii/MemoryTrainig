package com.youngsophomore.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.youngsophomore.R;
import com.youngsophomore.data.CollectionsStorage;
import com.youngsophomore.data.Question;
import com.youngsophomore.data.StatParam;
import com.youngsophomore.data.Training;
import com.youngsophomore.fragments.FinishDialogFragment;
import com.youngsophomore.fragments.StopwatchFragment;
import com.youngsophomore.helpers.TrainHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

public class DetailsTrainingActivity extends AppCompatActivity implements
        FinishDialogFragment.FinishDialogListener {
    private static final String DEBUG_TAG = "Gestures";
    private static final String STOPWATCH_FRAGMENT_TAG = "stopwatch_fragment_tag";
    int curQuestionInd = 0;
    ArrayList<Question> questionsCollection;
    TextView tvQuestionText;
    RadioGroup rgSingleAnswer;
    ArrayList<RadioButton> rBtnSingleAnswers;
    ArrayList<CheckBox> ckBxMultAnswers;
    private final int ANSWERS_NUM = 8;
    HashSet<Integer> answersIndices;
    private int singleMistakesAmount = 0;
    private int multipMistakesAmount = 0;
    private int trainingDurationSec = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pretrain_sequence_layout);
        SharedPreferences sharedPreferences =
                getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        int questionsCollectionPosition = sharedPreferences.getInt(getString(R.string.saved_images_collection_position_key), 0);
        int imageShowTime = sharedPreferences.getInt(getString(R.string.saved_image_show_time_key), 2);
        
        ArrayList<String> questionsCollectionsTitles = CollectionsStorage.getCollectionsTitles(
                sharedPreferences, getString(R.string.questions_collections_titles_key)
        );
        String questionsCollectionTitle = questionsCollectionsTitles.get(questionsCollectionPosition);

        File questionsDir = new File(getExternalFilesDir(null).getAbsolutePath()
                + "/details" + "/" + questionsCollectionTitle);
        if (!questionsDir.exists()) {
            Log.d(DEBUG_TAG, "DetailsTraining: " + questionsDir + " DOESN'T EXIST");
        }
        File[] fullQuestionsFiles = questionsDir.listFiles();
        int questionsAmount = Math.min(fullQuestionsFiles.length, 10);
        ArrayList<Integer> questionNums = TrainHelper.getRandomNumsInRange(questionsAmount, 0, fullQuestionsFiles.length);
        ArrayList<File> questionsFiles = new ArrayList<>();
        for (int i = 0; i < 10 && i < fullQuestionsFiles.length; ++i){
            questionsFiles.add(fullQuestionsFiles[questionNums.get(i)]);
        }
        questionsCollection = TrainHelper.Details.parseQuestionsFiles(questionsFiles);

        TextView tvCountdown = findViewById(R.id.tv_countdown);
        TextView tvCurPhraseNum = findViewById(R.id.tv_cur_elem_num);
        TextView tvPretrainTip = findViewById(R.id.tv_pretrain_tip);
        tvPretrainTip.setText(getString(R.string.tv_pretrain_tip_det));
        CountDownTimer countDownTimer = new CountDownTimer(3000 + 200, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d(DEBUG_TAG, "onTick: millisUntilFinished = " + millisUntilFinished);
                tvCountdown.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                answersIndices = new HashSet<>();
                rBtnSingleAnswers = new ArrayList<>();
                ckBxMultAnswers = new ArrayList<>();
                Uri imageUri = Uri.parse(sharedPreferences.getString(questionsCollectionTitle, ""));

                ConstraintLayout cntLytPretrain = findViewById(R.id.cst_lt_pretrain);
                cntLytPretrain.removeView(tvCountdown);
                cntLytPretrain.removeView(tvPretrainTip);
                ImageView ivCollectionImage = new ImageView(getApplicationContext());
                ivCollectionImage.setId(View.generateViewId());
                ConstraintLayout.LayoutParams ivCurColorParams = new ConstraintLayout.LayoutParams(
                        0,
                        0
                );
                ivCollectionImage.setLayoutParams(ivCurColorParams);
                cntLytPretrain.addView(ivCollectionImage);
                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(cntLytPretrain);
                constraintSet.connect(ivCollectionImage.getId(), ConstraintSet.TOP,
                        ConstraintSet.PARENT_ID, ConstraintSet.TOP);
                constraintSet.connect(ivCollectionImage.getId(), ConstraintSet.BOTTOM,
                        ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
                constraintSet.connect(ivCollectionImage.getId(), ConstraintSet.START,
                        ConstraintSet.PARENT_ID, ConstraintSet.START);
                constraintSet.connect(ivCollectionImage.getId(), ConstraintSet.END,
                        ConstraintSet.PARENT_ID, ConstraintSet.END);
                constraintSet.setDimensionRatio(ivCollectionImage.getId(), "1:1");
                constraintSet.applyTo(cntLytPretrain);
                ivCollectionImage.setImageURI(imageUri);

                CountDownTimer pretrainSequenceTimer = new CountDownTimer(
                        imageShowTime * 1000 + 200,  1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        tvCurPhraseNum.setText(String.valueOf(millisUntilFinished / 1000));
                    }

                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public void onFinish() {
                        setContentView(R.layout.activity_details_training);
                        ConstraintLayout cstLtParent = findViewById(R.id.cst_lt_details_parent);
                        tvQuestionText = findViewById(R.id.tv_question_text);
                        rgSingleAnswer = findViewById(R.id.rg_single_answer);
                        rBtnSingleAnswers.add(findViewById(R.id.r_btn_answer_1));
                        rBtnSingleAnswers.add(findViewById(R.id.r_btn_answer_2));
                        rBtnSingleAnswers.add(findViewById(R.id.r_btn_answer_3));
                        rBtnSingleAnswers.add(findViewById(R.id.r_btn_answer_4));
                        rBtnSingleAnswers.add(findViewById(R.id.r_btn_answer_5));
                        rBtnSingleAnswers.add(findViewById(R.id.r_btn_answer_6));
                        rBtnSingleAnswers.add(findViewById(R.id.r_btn_answer_7));
                        rBtnSingleAnswers.add(findViewById(R.id.r_btn_answer_8));
                        ckBxMultAnswers.add(findViewById(R.id.ck_bx_answer_1));
                        ckBxMultAnswers.add(findViewById(R.id.ck_bx_answer_2));
                        ckBxMultAnswers.add(findViewById(R.id.ck_bx_answer_3));
                        ckBxMultAnswers.add(findViewById(R.id.ck_bx_answer_4));
                        ckBxMultAnswers.add(findViewById(R.id.ck_bx_answer_5));
                        ckBxMultAnswers.add(findViewById(R.id.ck_bx_answer_6));
                        ckBxMultAnswers.add(findViewById(R.id.ck_bx_answer_7));
                        ckBxMultAnswers.add(findViewById(R.id.ck_bx_answer_8));
                        rgSingleAnswer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup group, int checkedId) {
                                if (checkedId == R.id.r_btn_answer_1){
                                    answersIndices.clear();
                                    answersIndices.add(1);
                                }
                                else if (checkedId == R.id.r_btn_answer_2){
                                    answersIndices.clear();
                                    answersIndices.add(2);
                                }
                                else if (checkedId == R.id.r_btn_answer_3){
                                    answersIndices.clear();
                                    answersIndices.add(3);
                                }
                                else if (checkedId == R.id.r_btn_answer_4){
                                    answersIndices.clear();
                                    answersIndices.add(4);
                                }
                                else if (checkedId == R.id.r_btn_answer_5){
                                    answersIndices.clear();
                                    answersIndices.add(5);
                                }
                                else if (checkedId == R.id.r_btn_answer_6){
                                    answersIndices.clear();
                                    answersIndices.add(6);
                                }
                                else if (checkedId == R.id.r_btn_answer_7){
                                    answersIndices.clear();
                                    answersIndices.add(7);
                                }
                                else if (checkedId == R.id.r_btn_answer_8){
                                    answersIndices.clear();
                                    answersIndices.add(8);
                                }
                            }
                        });
                        for(int i = 0; i < ckBxMultAnswers.size(); ++i){
                            int finalI = i;
                            ckBxMultAnswers.get(i).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                    if (isChecked) {
                                        answersIndices.add(finalI + 1);
                                    } else {
                                        answersIndices.remove(finalI + 1);
                                    }
                                }
                            });
                        }

                        Bundle bundle = new Bundle();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction()
                                .setReorderingAllowed(true)
                                .add(R.id.frt_cnt_v_stopwatch, StopwatchFragment.class, bundle, STOPWATCH_FRAGMENT_TAG)
                                .commit();

                        showNextQuestion();

                        tvQuestionText.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (questionsCollection.get(curQuestionInd).getCorrAnswersIndices().equals(
                                        answersIndices
                                )){
                                    Log.d(DEBUG_TAG, "YOU CHOSE RIGHT ANSWERS");
                                    ++curQuestionInd;
                                    if (curQuestionInd < questionsCollection.size()){
                                        showNextQuestion();
                                    }
                                    else {
                                        Log.d(DEBUG_TAG, "ALL QUESTIONS ARE ANSWERED");
                                        StopwatchFragment stopwatchFragment =
                                                (StopwatchFragment) fragmentManager.findFragmentByTag(STOPWATCH_FRAGMENT_TAG);
                                        trainingDurationSec = stopwatchFragment.getDecisecond() / 10;
                                        stopwatchFragment.finishStopwatch();
                                        TrainHelper.updateStatParams(sharedPreferences,
                                                new Pair<>(
                                                        TrainHelper.getStatParamKey(Training.DET, StatParam.TOTNUMSINGANS, questionsCollectionTitle),
                                                        singleMistakesAmount
                                                ),
                                                new Pair<>(
                                                        TrainHelper.getStatParamKey(Training.DET, StatParam.TOTNUMMULTANS, questionsCollectionTitle),
                                                        multipMistakesAmount
                                                ),
                                                new Pair<>(
                                                        TrainHelper.getStatParamKey(Training.DET, StatParam.TOTNUMTIME, questionsCollectionTitle),
                                                        trainingDurationSec
                                                ),
                                                new Pair<>(
                                                        TrainHelper.getStatParamKey(Training.DET, StatParam.TOTNUMTRAINS, questionsCollectionTitle),
                                                        1
                                                ));
                                        DialogFragment finishFragment = new FinishDialogFragment(
                                                trainingDurationSec + " с.",
                                                getResources().getString(R.string.det_train_mistakes_amount),
                                                singleMistakesAmount + " / " + multipMistakesAmount
                                        );
                                        finishFragment.show(getSupportFragmentManager(), "FinishDialogFragment");
                                    }
                                }
                                else{
                                    Log.d(DEBUG_TAG, "YOU CHOSE WRONG ANSWERS");
                                    if (questionsCollection.get(curQuestionInd).isSingleAnswer()){
                                        ++singleMistakesAmount;
                                    }
                                    else{
                                        ++multipMistakesAmount;
                                    }
                                    cstLtParent.setBackgroundColor(
                                            getResources().getColor(R.color.seq_training_wrong_choice
                                            ));
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            cstLtParent.setBackgroundColor(
                                                    getResources().getColor(R.color.white_blue)
                                            );
                                        }
                                    }, 60);
                                }
                            }
                        });

                    }
                }.start();
            }
        }.start();
    }

    private void showNextQuestion(){
        answersIndices.clear();
        Question curQuestion = questionsCollection.get(curQuestionInd);
        changeAnswersLayout(curQuestion.isSingleAnswer(), curQuestion.getAnswers().size());
        tvQuestionText.setText(curQuestion.getQuestionText());
        for(int i = 0; i < curQuestion.getAnswers().size(); ++i){
            if(curQuestion.isSingleAnswer()){
                rBtnSingleAnswers.get(i).setText(
                        curQuestion.getAnswers().get(i).substring(0, curQuestion.getAnswers().get(i).length() - 1));
            }
            else{
                ckBxMultAnswers.get(i).setText(
                        curQuestion.getAnswers().get(i).substring(0, curQuestion.getAnswers().get(i).length() - 1));
            }
        }
    }
    private void changeAnswersLayout(boolean hasSingleAnswer, int answersNum){
        if (hasSingleAnswer){
            rgSingleAnswer.setVisibility(View.VISIBLE);
            for(int i = 0; i < ANSWERS_NUM; ++i){
                if(i < answersNum){
                    rBtnSingleAnswers.get(i).setVisibility(View.VISIBLE);
                }
                else{
                    rBtnSingleAnswers.get(i).setVisibility(View.GONE);
                }
            }
            for(int i = 0; i < ANSWERS_NUM; ++i){
                ckBxMultAnswers.get(i).setVisibility(View.GONE);
            }
        }
        else{
            rgSingleAnswer.setVisibility(View.GONE);
            for(int i = 0; i < ANSWERS_NUM; ++i){
                if(i < answersNum){
                    ckBxMultAnswers.get(i).setVisibility(View.VISIBLE);
                }
                else{
                    ckBxMultAnswers.get(i).setVisibility(View.GONE);
                }
            }
        }
    }


    @Override
    public void onFinishPosClick(DialogFragment dialog) {
        onBackPressed();
    }
}
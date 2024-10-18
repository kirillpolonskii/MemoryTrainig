package com.youngsophomore.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.youngsophomore.R;
import com.youngsophomore.adapters.PhrasesTrainingAdapter;
import com.youngsophomore.data.CollectionsStorage;
import com.youngsophomore.data.Question;
import com.youngsophomore.fragments.StopwatchFragment;
import com.youngsophomore.helpers.TrainHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashSet;

public class DetailsTrainingActivity extends AppCompatActivity {
    private static final String DEBUG_TAG = "Gestures";
    private static final String STOPWATCH_FRAGMENT_TAG = "stopwatch_fragment_tag";
    int curQuestionInd = 0;
    ArrayList<Question> questionsCollection;
    TextView tvQuestionText;
    RadioGroup rgSingleAnswer;
    ArrayList<RadioButton> rBtnSingleAnswers;
    ArrayList<CheckBox> ckBxMultAnswers;
    private final int ANSWERS_NUM = 8;
    HashSet<Integer> corrAnswersIndices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pretrain_sequence_layout);
        SharedPreferences sharedPreferences =
                getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        // достать позицию названия коллекции и время показа картинки
        int questionsCollectionPosition = sharedPreferences.getInt(getString(R.string.saved_images_collection_position_key), 0);
        int imageShowTime = sharedPreferences.getInt(getString(R.string.saved_image_show_time_key), 2);
        Log.d(DEBUG_TAG, "phrasesCollectionPosition and phraseShowTime = " +
                questionsCollectionPosition + " " +
                imageShowTime);
        // get string with all titles from sharedpref
        // split it and get unique title
        ArrayList<String> questionsCollectionsTitles = CollectionsStorage.getCollectionsTitles(
                sharedPreferences, getString(R.string.phrases_collections_titles_key)
        );
        String questionsCollectionTitle = questionsCollectionsTitles.get(questionsCollectionPosition);
        // сгенерировать 10 рандомных чисел от 0 до размера коллекции, преобразовать их
        // в названия файлов, загрузить строки из файлов и распарсить их в объекты Question

        File questionsDir = new File(getExternalFilesDir(null).getAbsolutePath()
                + "/details" + "/" + questionsCollectionTitle);
        if (!questionsDir.exists()) {
            Log.d(DEBUG_TAG, "DetailsTraining: " + questionsDir + " DOESN'T EXIST");
        }
        File[] fullQuestionsFiles = questionsDir.listFiles();
        int questionsAmount = (fullQuestionsFiles.length > 10) ? 10: fullQuestionsFiles.length;
        ArrayList<Integer> questionNums = TrainHelper.getRandomNumsInRange(questionsAmount, 0, fullQuestionsFiles.length);
        ArrayList<File> questionsFiles = new ArrayList<>();
        for (int i = 0; i < 10 && i < fullQuestionsFiles.length; ++i){
            questionsFiles.add(fullQuestionsFiles[questionNums.get(i)]);
        }
        questionsCollection = TrainHelper.Details.parseQuestionsFiles(questionsFiles);

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
                corrAnswersIndices = new HashSet<>();
                rBtnSingleAnswers = new ArrayList<>();
                ckBxMultAnswers = new ArrayList<>();
                Uri imageUri = Uri.parse(sharedPreferences.getString(questionsCollectionTitle, ""));
                // Log.d(DEBUG_TAG, phrasesCollectionsTitles.toString());
                // заменить textview на imageview и показать картинку
                ConstraintLayout cntLytPretrain = findViewById(R.id.cnstrnt_lyt_pretrain);
                cntLytPretrain.removeView(tvCountdown);
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
                                    corrAnswersIndices.clear();
                                    corrAnswersIndices.add(1);
                                }
                                else if (checkedId == R.id.r_btn_answer_2){
                                    corrAnswersIndices.clear();
                                    corrAnswersIndices.add(2);
                                }
                                else if (checkedId == R.id.r_btn_answer_3){
                                    corrAnswersIndices.clear();
                                    corrAnswersIndices.add(3);
                                }
                                else if (checkedId == R.id.r_btn_answer_4){
                                    corrAnswersIndices.clear();
                                    corrAnswersIndices.add(4);
                                }
                                else if (checkedId == R.id.r_btn_answer_5){
                                    corrAnswersIndices.clear();
                                    corrAnswersIndices.add(5);
                                }
                                else if (checkedId == R.id.r_btn_answer_6){
                                    corrAnswersIndices.clear();
                                    corrAnswersIndices.add(6);
                                }
                                else if (checkedId == R.id.r_btn_answer_7){
                                    corrAnswersIndices.clear();
                                    corrAnswersIndices.add(7);
                                }
                                else if (checkedId == R.id.r_btn_answer_8){
                                    corrAnswersIndices.clear();
                                    corrAnswersIndices.add(8);
                                }
                            }
                        });
                        ckBxMultAnswers.get(0).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked) {
                                    corrAnswersIndices.add(1);
                                } else {
                                    corrAnswersIndices.remove(1);
                                }
                            }
                        });
                        ckBxMultAnswers.get(1).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked) {
                                    corrAnswersIndices.add(2);
                                } else {
                                    corrAnswersIndices.remove(2);
                                }
                            }
                        });
                        ckBxMultAnswers.get(2).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked) {
                                    corrAnswersIndices.add(3);
                                } else {
                                    corrAnswersIndices.remove(3);
                                }
                            }
                        });
                        ckBxMultAnswers.get(3).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked) {
                                    corrAnswersIndices.add(4);
                                } else {
                                    corrAnswersIndices.remove(4);
                                }
                            }
                        });
                        ckBxMultAnswers.get(4).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked) {
                                    corrAnswersIndices.add(5);
                                } else {
                                    corrAnswersIndices.remove(5);
                                }
                            }
                        });
                        ckBxMultAnswers.get(5).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked) {
                                    corrAnswersIndices.add(6);
                                } else {
                                    corrAnswersIndices.remove(6);
                                }
                            }
                        });
                        ckBxMultAnswers.get(6).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked) {
                                    corrAnswersIndices.add(7);
                                } else {
                                    corrAnswersIndices.remove(7);
                                }
                            }
                        });
                        ckBxMultAnswers.get(7).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked) {
                                    corrAnswersIndices.add(8);
                                } else {
                                    corrAnswersIndices.remove(8);
                                }
                            }
                        });

                        // Запуск секундомера
                        Bundle bundle = new Bundle();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction()
                                .setReorderingAllowed(true)
                                .add(R.id.frgt_view, StopwatchFragment.class, bundle, STOPWATCH_FRAGMENT_TAG)
                                .commit();
                        // методом nextQuestion() заполнить textview текущим вопросом, сделать видимыми
                        // только нужные radiobutton или checkbox и заполнить их ответами
                        showNextQuestion();
                        // добавить на textview вопроса слушатель и при нажатии осуществлять проверку
                        // правильности ответа, и если он правильный, вызывать nextQuestion, а если нет,
                        // подсвечивать задний фон красным
                        tvQuestionText.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (questionsCollection.get(curQuestionInd).getCorrAnswersIndices().equals(
                                        corrAnswersIndices
                                )){
                                    Log.d(DEBUG_TAG, "YOU CHOSE RIGHT ANSWERS");
                                    ++curQuestionInd;
                                    if (curQuestionInd < questionsCollection.size()){
                                        showNextQuestion();
                                    }
                                    else {
                                        Log.d(DEBUG_TAG, "ALL QUESTIONS ARE ANSWERED");
                                    }
                                }
                                else{
                                    Log.d(DEBUG_TAG, "YOU CHOSE WRONG ANSWERS");
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
        corrAnswersIndices.clear();
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


}
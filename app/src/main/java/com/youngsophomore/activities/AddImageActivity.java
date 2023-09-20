package com.youngsophomore.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.youngsophomore.R;
import com.youngsophomore.data.CollectionsStorage;
import com.youngsophomore.data.Question;
import com.youngsophomore.fragments.AddPhraseFragment;
import com.youngsophomore.fragments.AddQuestionFragment;
import com.youngsophomore.fragments.NewPhrasesListFragment;
import com.youngsophomore.fragments.NewQuestionsListFragment;

import java.util.ArrayList;

public class AddImageActivity extends AppCompatActivity {
    private final String NEW_QUESTIONS_FRAGMENT_TAG = "new_questions_fragment";
    private final String ADD_QUESTION_FRAGMENT_TAG = "add_question_fragment";
    private static final String DEBUG_TAG = "Gestures";
    private ArrayList<Question> newQuestions;
    String newQuestionsCollectionTitle;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_image);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.tbr_details_add_image_title));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = new Bundle();
        newQuestions = new ArrayList<>();
        bundle.putParcelableArrayList(getString(R.string.new_questions_collection_key), newQuestions);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.frgt_view, NewQuestionsListFragment.class, bundle, NEW_QUESTIONS_FRAGMENT_TAG)
                .commit();

        ImageButton btnAddQuestion = findViewById(R.id.btn_add_question);
        ImageButton btnConfirmQuestion = findViewById(R.id.btn_confirm_question);
        ImageButton btnConfirmQuestionsCollection = findViewById(R.id.btn_confirm_questions_collection);

        deactivateBtn(btnConfirmQuestion);

        btnAddQuestion.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = event.getAction();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        Log.d(DEBUG_TAG, "btnAddQuestion onTouch. Action was DOWN");
                        view.setElevation(0);
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        Log.d(DEBUG_TAG, "btnAddQuestion onTouch. Action was MOVE");
                        return true;
                    case (MotionEvent.ACTION_UP):
                        int elevPx = getResources().getDimensionPixelSize(R.dimen.btn_stats_elev);
                        Log.d(DEBUG_TAG, "btnAddQuestion onTouch. Action was UP");
                        view.setElevation(elevPx);

                        deactivateBtn(btnAddQuestion);
                        deactivateBtn(btnConfirmQuestionsCollection);
                        activateBtn(btnConfirmQuestion, elevPx);

                        fragmentManager.beginTransaction()
                                .replace(R.id.frgt_view, AddQuestionFragment.class, null, ADD_QUESTION_FRAGMENT_TAG)
                                .setReorderingAllowed(true)
                                .addToBackStack("transaction_add_questions_collection_fragment")
                                .commit();

                        /*SharedPreferences.Editor editor = sharedPreferences.edit();*/
                        return true;
                    default:
                        return false;
                }
            }
        });

        btnConfirmQuestion.setOnTouchListener(new View.OnTouchListener() {
            // return to the NewQuestionsListFragment
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = event.getAction();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        Log.d(DEBUG_TAG, "btnConfirmQuestion onTouch. Action was DOWN");
                        view.setElevation(0);
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        Log.d(DEBUG_TAG, "btnConfirmQuestion onTouch. Action was MOVE");
                        return true;
                    case (MotionEvent.ACTION_UP):
                        int elevPx = getResources().getDimensionPixelSize(R.dimen.btn_stats_elev);
                        Log.d(DEBUG_TAG, "btnConfirmQuestion onTouch. Action was UP");
                        view.setElevation(elevPx);
                        activateBtn(btnAddQuestion, elevPx);
                        activateBtn(btnConfirmQuestionsCollection, elevPx);
                        deactivateBtn(btnConfirmQuestion);
                        /*
                        * Какие данные есть на фрагменте: текст вопроса, список ответов на него в
                        * формате "<текст ответа> +/-".
                        * Сперва нужно достать из фрагмента вопрос, который на уровне фрагмента
                        * уже будет заполнен (текст вопроса, коллекция ответов, ответы в одной строке)
                        * Для обновления коллекции на уровне активности нужно получить из фрагмента вопрос
                        * в объекте Bundle.
                        * */

                        AddQuestionFragment newQuestionFragment =
                                (AddQuestionFragment) fragmentManager.findFragmentByTag(ADD_QUESTION_FRAGMENT_TAG);
                        Question newQuestion = newQuestionFragment.getQuestion();
                        newQuestion.putAnswersInOneString();
                        newQuestions.add(newQuestion);
                        bundle.putParcelableArrayList(getString(R.string.new_questions_collection_key), newQuestions);
                        fragmentManager.beginTransaction()
                                .replace(R.id.frgt_view, NewQuestionsListFragment.class, bundle, NEW_QUESTIONS_FRAGMENT_TAG)
                                .setReorderingAllowed(true)
                                .commit();
                        fragmentManager.popBackStack();
                        /*SharedPreferences.Editor editor = sharedPreferences.edit();*/
                        return true;
                    default:
                        return false;
                }
            }
        });
        btnConfirmQuestionsCollection.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = event.getAction();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        Log.d(DEBUG_TAG, "btnConfirmQuestionsCollection onTouch. Action was DOWN");
                        view.setElevation(0);
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        Log.d(DEBUG_TAG, "btnConfirmQuestionsCollection onTouch. Action was MOVE");
                        return true;
                    case (MotionEvent.ACTION_UP):
                        int elevPx = getResources().getDimensionPixelSize(R.dimen.btn_stats_elev);
                        Log.d(DEBUG_TAG, "btnConfirmQuestionsCollection onTouch. Action was UP");
                        view.setElevation(elevPx);

                        newQuestionsCollectionTitle = "test1";
                        CollectionsStorage.addQuestionsCollections(newQuestionsCollectionTitle, newQuestions);
                        onBackPressed();
                        /*SharedPreferences.Editor editor = sharedPreferences.edit();*/
                        return true;
                    default:
                        return false;
                }
            }
        });

    }

    private void activateBtn(ImageButton btn, int elevPx){
        btn.setEnabled(true);
        btn.setAlpha(1f);
        btn.setElevation(elevPx);
    }
    private void deactivateBtn(ImageButton btn){
        btn.setEnabled(false);
        btn.setAlpha(0.5f);
        btn.setElevation(0);
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
}
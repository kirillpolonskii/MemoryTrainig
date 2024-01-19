package com.youngsophomore.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.youngsophomore.R;
import com.youngsophomore.data.CollectionsStorage;
import com.youngsophomore.data.Question;
import com.youngsophomore.fragments.AddPhraseFragment;
import com.youngsophomore.fragments.AddQuestionFragment;
import com.youngsophomore.fragments.CorrectAnswerDialogFragment;
import com.youngsophomore.fragments.InfoDialogFragment;
import com.youngsophomore.fragments.NewImageNameDialogFragment;
import com.youngsophomore.fragments.NewPhrasesListFragment;
import com.youngsophomore.fragments.NewQuestionsListFragment;
import com.youngsophomore.helpers.PrepHelper;

import java.util.ArrayList;

public class AddImageActivity extends AppCompatActivity implements
        NewImageNameDialogFragment.NewImageNameDialogListener {
    private final String NEW_QUESTIONS_FRAGMENT_TAG = "new_questions_fragment";
    private final String ADD_QUESTION_FRAGMENT_TAG = "add_question_fragment";
    private static final String DEBUG_TAG = "Gestures";
    private ArrayList<Question> newQuestions;
    String newQuestionsCollectionTitle;
    Uri imageUri;
    public final int NEW_IMAGE_REQUEST_CODE = 20;
    TextView tvNewImageName;
    int tvNewImageNameId;

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

        PrepHelper.deactivateBtn(btnConfirmQuestion);

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

                        PrepHelper.deactivateBtn(btnAddQuestion);
                        PrepHelper.deactivateBtn(btnConfirmQuestionsCollection);
                        PrepHelper.activateBtn(btnConfirmQuestion, elevPx);

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
                        PrepHelper.activateBtn(btnAddQuestion, elevPx);
                        PrepHelper.activateBtn(btnConfirmQuestionsCollection, elevPx);
                        PrepHelper.deactivateBtn(btnConfirmQuestion);
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


                        CollectionsStorage.addQuestionsCollections(newQuestionsCollectionTitle, imageUri, newQuestions);
                        onBackPressed();
                        /*SharedPreferences.Editor editor = sharedPreferences.edit();*/
                        return true;
                    default:
                        return false;
                }
            }
        });
        ImageButton btnNewImage = findViewById(R.id.btn_new_image);
        btnNewImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = event.getAction();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        Log.d(DEBUG_TAG, "btnNewImage onTouch. Action was DOWN");
                        view.setElevation(0);
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        Log.d(DEBUG_TAG, "btnNewImage onTouch. Action was MOVE");
                        return true;
                    case (MotionEvent.ACTION_UP):
                        int elevPx = getResources().getDimensionPixelSize(R.dimen.btn_stats_elev);
                        Log.d(DEBUG_TAG, "btnNewImage onTouch. Action was UP");
                        view.setElevation(elevPx);
                        openFileChooser();

                        showNewImageNameDialog();
                        
                        /*SharedPreferences.Editor editor = sharedPreferences.edit();*/
                        return true;
                    default:
                        return false;
                }
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        if(requestCode == NEW_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK && intent != null){
            imageUri = intent.getData();
            Log.d(DEBUG_TAG, "Uri from file picker = " + imageUri);
        }
    }

    public void openFileChooser(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, NEW_IMAGE_REQUEST_CODE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.mi_btn_info) {
            Log.d(DEBUG_TAG, "info button in ShapesSettingsActivity");
            showInfoDialog(R.layout.fragment_add_image_info);
            return true;
        }
        return false;
    }
    public void showInfoDialog(int layoutResource) {
        DialogFragment newFragment = new InfoDialogFragment(layoutResource);
        newFragment.show(getSupportFragmentManager(), "InfoDialogFragment");
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
    public void showNewImageNameDialog() {
        DialogFragment newFragment = new NewImageNameDialogFragment();
        newFragment.show(getSupportFragmentManager(), "NewImageNameDialogFragment");
    }

    @Override
    public void onNewImageNamePosClick(DialogFragment dialog, String newImageName) {
        newQuestionsCollectionTitle = newImageName;
        tvNewImageName = new TextView(AddImageActivity.this, null, 0, R.style.SettingsTextViewStyle);
        tvNewImageNameId = View.generateViewId();
        tvNewImageName.setId(tvNewImageNameId);
        tvNewImageName.setText(newQuestionsCollectionTitle);
        ConstraintLayout.LayoutParams tvNewImageNameParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        tvNewImageName.setLayoutParams(tvNewImageNameParams);

        ConstraintLayout clAddImage = findViewById(R.id.cl_add_image);
        clAddImage.removeView(clAddImage.findViewById(R.id.btn_new_image));

        //((ViewGroup) view.getParent()).removeView(etNewQuestion);

        //etNewAnswer.setActivated(true);
        //etNewAnswer.setImportantForAutofill(View.IMPORTANT_FOR_AUTOFILL_NO);

        clAddImage.addView(tvNewImageName);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(clAddImage);
        constraintSet.connect(tvNewImageNameId, ConstraintSet.TOP,
                R.id.tv_new_image, ConstraintSet.TOP);
        constraintSet.connect(tvNewImageNameId, ConstraintSet.BOTTOM,
                R.id.tv_new_image, ConstraintSet.BOTTOM);
        constraintSet.connect(tvNewImageNameId, ConstraintSet.START,
                R.id.tv_new_image, ConstraintSet.END);
        //constraintSet.constrainHeight(etNewAnswerId, ConstraintSet.MATCH_CONSTRAINT_PERCENT);
        //constraintSet.constrainDefaultHeight(etNewAnswerId, ConstraintSet.MATCH_CONSTRAINT_PERCENT);
        constraintSet.applyTo(clAddImage);
        

    }
}
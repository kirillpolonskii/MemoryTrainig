package com.youngsophomore.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.youngsophomore.R;
import com.youngsophomore.data.CollectionsStorage;
import com.youngsophomore.data.Question;
import com.youngsophomore.fragments.AddPhraseFragment;
import com.youngsophomore.fragments.AddQuestionFragment;
import com.youngsophomore.fragments.InfoDialogFragment;
import com.youngsophomore.fragments.NewImageNameDialogFragment;
import com.youngsophomore.fragments.NewPhrasesListFragment;
import com.youngsophomore.fragments.NewQuestionsListFragment;
import com.youngsophomore.helpers.PrepHelper;

import java.util.ArrayList;

public class AddImageActivity extends AppCompatActivity implements
        NewImageNameDialogFragment.NewImageNameDialogListener {
    private static final String DEBUG_TAG = "Gestures";
    private final String NEW_QUESTIONS_FRAGMENT_TAG = "new_questions_fragment";
    private final String ADD_QUESTION_FRAGMENT_TAG = "add_question_fragment";
    SharedPreferences sharedPreferences;
    FragmentManager fragmentManager;
    ImageButton btnAddQuestion;
    ImageButton btnConfirmQuestion;
    ImageButton btnConfirmQuestionsCollection;
    int elevPx;
    private ArrayList<Question> newQuestions;
    String newQuestionsCollectionTitle;
    Uri imageUri = null;
    public final int NEW_IMAGE_REQUEST_CODE = 20;
    TextView tvNewImage;
    ImageButton btnNewImage;
    FrameLayout frLtRemindImg;
    ImageView ivRemindImg;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_image);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.tbr_details_add_image_title));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sharedPreferences = getSharedPreferences(
                getString(R.string.preference_file_key), MODE_PRIVATE);

        Bundle bundle = new Bundle();
        newQuestions = new ArrayList<>();
        bundle.putParcelableArrayList(getString(R.string.new_questions_collection_key), newQuestions);
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.frt_cnt_v_questions, NewQuestionsListFragment.class, bundle, NEW_QUESTIONS_FRAGMENT_TAG)
                .commit();

        btnNewImage = findViewById(R.id.btn_new_image);
        btnAddQuestion = findViewById(R.id.btn_add_question);
        btnConfirmQuestion = findViewById(R.id.btn_confirm_question);
        btnConfirmQuestionsCollection = findViewById(R.id.btn_confirm_questions_collection);
        tvNewImage = findViewById(R.id.tv_new_image);
        frLtRemindImg = findViewById(R.id.fr_lt_remind_img);
        ivRemindImg = findViewById(R.id.iv_remind_img);
        elevPx = getResources().getDimensionPixelSize(R.dimen.btn_stats_elev);

        PrepHelper.deactivateBtn(btnConfirmQuestion);
        PrepHelper.deactivateBtn(btnAddQuestion);
        btnNewImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = event.getAction();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        view.setElevation(0);
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        return true;
                    case (MotionEvent.ACTION_UP):
                        view.setElevation(elevPx);
                        openFileChooser();
                        return true;
                    default:
                        return false;
                }
            }
        });

        btnAddQuestion.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = event.getAction();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        view.setElevation(0);
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        return true;
                    case (MotionEvent.ACTION_UP):
                        view.setElevation(elevPx);

                        PrepHelper.deactivateBtn(btnAddQuestion);
                        PrepHelper.deactivateBtn(btnConfirmQuestionsCollection);
                        PrepHelper.activateBtn(btnConfirmQuestion, elevPx);
                        Bundle addQuestionFragmentBundle = new Bundle();
                        addQuestionFragmentBundle.putString(getString(R.string.chosen_img_key), imageUri.toString());
                        fragmentManager.beginTransaction()
                                .replace(R.id.frt_cnt_v_questions, AddQuestionFragment.class, addQuestionFragmentBundle, ADD_QUESTION_FRAGMENT_TAG)
                                .setReorderingAllowed(true)
                                .addToBackStack("transaction_add_questions_collection_fragment")
                                .commit();
                        return true;
                    default:
                        return false;
                }
            }
        });
        btnConfirmQuestion.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = event.getAction();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        view.setElevation(0);
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        return true;
                    case (MotionEvent.ACTION_UP):
                        view.setElevation(elevPx);
                        PrepHelper.activateBtn(btnAddQuestion, elevPx);
                        PrepHelper.activateBtn(btnConfirmQuestionsCollection, elevPx);
                        PrepHelper.deactivateBtn(btnConfirmQuestion);

                        AddQuestionFragment newQuestionFragment =
                                (AddQuestionFragment) fragmentManager.findFragmentByTag(ADD_QUESTION_FRAGMENT_TAG);
                        Question newQuestion = newQuestionFragment.getQuestion();
                        if(!newQuestion.getQuestionText().equals("")){
                            newQuestion.putAnswersInOneString();
                            newQuestions.add(newQuestion);
                        }
                        bundle.putParcelableArrayList(getString(R.string.new_questions_collection_key), newQuestions);
                        fragmentManager.beginTransaction()
                                .replace(R.id.frt_cnt_v_questions, NewQuestionsListFragment.class, bundle, NEW_QUESTIONS_FRAGMENT_TAG)
                                .setReorderingAllowed(true)
                                .commit();
                        fragmentManager.popBackStack();
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
                        view.setElevation(0);
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        return true;
                    case (MotionEvent.ACTION_UP):
                        view.setElevation(elevPx);
                        if (imageUri != null && !newQuestions.isEmpty()){
                            CollectionsStorage.saveQuestionsCollections(newQuestionsCollectionTitle, imageUri,
                                    newQuestions, getString(R.string.questions_collections_titles_key),
                                    sharedPreferences, getApplicationContext());

                        }
                        onBackPressed();
                        return true;
                    default:
                        return false;
                }
            }
        });

        frLtRemindImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frLtRemindImg.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        if(requestCode == NEW_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK && intent != null){
            imageUri = intent.getData();
            ivRemindImg.setImageURI(imageUri);
            showNewImageNameDialog();
            PrepHelper.activateBtn(btnAddQuestion, elevPx);
        }

    }

    public void openFileChooser(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
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

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onNewImageNamePosClick(DialogFragment dialog, String newImageName) {
        String strQuestionsCollectionsTitles =
                sharedPreferences.getString(getString(R.string.questions_collections_titles_key), "");
        if(PrepHelper.isCollectionTitleUnique(strQuestionsCollectionsTitles, newImageName)){
            newQuestionsCollectionTitle = newImageName;
            tvNewImage.setText(newQuestionsCollectionTitle + ":");
            btnNewImage.setImageResource(R.drawable.outline_image_48);
            btnNewImage.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    int action = event.getAction();
                    switch(action) {
                        case (MotionEvent.ACTION_DOWN):
                            view.setElevation(0);
                            return true;
                        case (MotionEvent.ACTION_MOVE):
                            return true;
                        case (MotionEvent.ACTION_UP):
                            int elevPx = getResources().getDimensionPixelSize(R.dimen.btn_stats_elev);
                            view.setElevation(elevPx);
                            frLtRemindImg.setVisibility(View.VISIBLE);
                            return true;
                        default:
                            return false;
                    }
                }
            });

        }
        else{
            Toast.makeText(getApplicationContext(), getString(R.string.msg_collection_title_not_unique),
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        if(frLtRemindImg.getVisibility() == View.VISIBLE){
            frLtRemindImg.setVisibility(View.GONE);
        }
        else{
            AddQuestionFragment addQuestionFragment =
                    (AddQuestionFragment) fragmentManager.findFragmentByTag(ADD_QUESTION_FRAGMENT_TAG);
            if(addQuestionFragment != null && addQuestionFragment.isVisible()){
                PrepHelper.activateBtn(btnAddQuestion, elevPx);
                PrepHelper.activateBtn(btnConfirmQuestionsCollection, elevPx);
                PrepHelper.deactivateBtn(btnConfirmQuestion);
            }
            super.onBackPressed();
        }
    }
}
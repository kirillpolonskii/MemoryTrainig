package com.youngsophomore.fragments;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.media.Image;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.material.divider.MaterialDividerItemDecoration;
import com.youngsophomore.R;
import com.youngsophomore.adapters.AnswersAdapter;
import com.youngsophomore.adapters.QuestionsAdapter;
import com.youngsophomore.data.Question;
import com.youngsophomore.interfaces.RecyclerViewClickListener;

import java.util.ArrayList;

public class AddQuestionFragment extends Fragment
        implements QuestionTypeDialogFragment.QuestionTypeDialogListener,
                    CorrectAnswerDialogFragment.CorrectAnswerDialogListener,
        DeleteAnswerDialogFragment.DeleteAnswerDialogListener,
                    RecyclerViewClickListener {
    private static final String DEBUG_TAG = "Gestures";
    //private ArrayList<String> answers;
    EditText etNewQuestion;
    EditText etNewAnswer;

    Question question;
    int etNewQuestionId;
    int etNewAnswerId;
    private boolean haveCorrectAnswer;
    AnswersAdapter answersAdapter;


    public AddQuestionFragment() {
        Log.d(DEBUG_TAG, "in AddQuestionFragment() of AddQuestionFragment");

        // Required empty public constructor
    }

    public static AddQuestionFragment newInstance(String param1, String param2) {
        Log.d(DEBUG_TAG, "in newInstance() of AddQuestionFragment");
        AddQuestionFragment fragment = new AddQuestionFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(DEBUG_TAG, "in onCreate() of AddQuestionFragment");
        super.onCreate(savedInstanceState);
        question = new Question();
        if (getArguments() != null) {
            
        }
    }
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(DEBUG_TAG, "in onCreateView() of AddQuestionFragment");
        if (container instanceof FragmentContainerView){
            Log.d(DEBUG_TAG, "container IS instance of FragmentContainerView");
        }
        else{
            Log.d(DEBUG_TAG, container.toString());
        }
        View view = inflater.inflate(R.layout.fragment_add_question, container, false);
        if (view instanceof ConstraintLayout){
            Log.d(DEBUG_TAG, "view IS instance of ConstraintLayout");
        }
        else{
            Log.d(DEBUG_TAG, container.toString());
        }
        if (view.getId() == view.findViewById(R.id.constr_layout_add_question).getId()){
            Log.d(DEBUG_TAG, "view.getId() == view.findViewById(R.id.constr_layout_add_question).getId()");
        }
        else{
            Log.d(DEBUG_TAG, "view.getId() != view.findViewById(R.id.constr_layout_add_question).getId()");
        }
        etNewQuestion = view.findViewById(R.id.et_new_question);
        etNewAnswer = new EditText(getContext());
        etNewAnswerId = View.generateViewId();
        etNewAnswer.setId(etNewAnswerId);
        etNewAnswer.setHint(getString(R.string.et_answer_hint));
        etNewAnswer.setGravity(Gravity.TOP);
        etNewAnswer.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        //etNewAnswer.setTextSize(getResources().getDimension(R.dimen.settings_text_size));
        etNewAnswer.setTextSize(22);
        etNewAnswer.setTextColor(getResources().getColor(R.color.blue));
        etNewAnswer.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.blue)));
        //etNewAnswer.setText(getResources().getString(R.string.new_questions_collection_key));
        ConstraintLayout.LayoutParams etNewAnswerParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                0
        );
        etNewAnswer.setLayoutParams(etNewAnswerParams);

        ImageButton btnAddAnswer = view.findViewById(R.id.btn_add_answer);
        ImageButton btnConfirmAnswer = view.findViewById(R.id.btn_confirm_answer);
        btnAddAnswer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        Log.d(DEBUG_TAG, "btnAddAnswer onTouch. Action was DOWN");
                        v.setElevation(0);
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        Log.d(DEBUG_TAG, "btnAddAnswer onTouch. Action was MOVE");
                        return true;
                    case (MotionEvent.ACTION_UP):
                        int elevPx = getResources().getDimensionPixelSize(R.dimen.btn_stats_elev);
                        Log.d(DEBUG_TAG, "btnAddAnswer onTouch. Action was UP");
                        v.setElevation(elevPx);

                        question.setQuestionText(etNewQuestion.getText().toString());
                        Log.d(DEBUG_TAG, "view.getChildCount() bef removeView = " +
                                ((ConstraintLayout) view).getChildCount());
                        ((ViewGroup) view).removeView(etNewQuestion);
                        Log.d(DEBUG_TAG, "view.getChildCount() aft removeView = " +
                                ((ConstraintLayout) view).getChildCount());
                        //((ViewGroup) view.getParent()).removeView(etNewQuestion);

                        //etNewAnswer.setActivated(true);
                        //etNewAnswer.setImportantForAutofill(View.IMPORTANT_FOR_AUTOFILL_NO);

                        ((ViewGroup) view).addView(etNewAnswer);
                        ConstraintSet constraintSet = new ConstraintSet();
                        constraintSet.clone((ConstraintLayout) view);
                        constraintSet.connect(etNewAnswerId, ConstraintSet.TOP,
                                R.id.constr_layout_add_question, ConstraintSet.TOP);
                        constraintSet.connect(R.id.rv_answers_collection, ConstraintSet.TOP,
                                etNewAnswerId, ConstraintSet.BOTTOM);
                        //constraintSet.constrainHeight(etNewAnswerId, ConstraintSet.MATCH_CONSTRAINT_PERCENT);
                        //constraintSet.constrainDefaultHeight(etNewAnswerId, ConstraintSet.MATCH_CONSTRAINT_PERCENT);
                        constraintSet.constrainPercentHeight(etNewAnswerId, 0.4f);
                        constraintSet.applyTo((ConstraintLayout) view);
                        return true;
                    default:
                        return false;
                }
            }
        });

        btnConfirmAnswer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        Log.d(DEBUG_TAG, "btnConfirmAnswer onTouch. Action was DOWN");
                        v.setElevation(0);
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        Log.d(DEBUG_TAG, "btnConfirmAnswer onTouch. Action was MOVE");
                        return true;
                    case (MotionEvent.ACTION_UP):
                        showCorrectAnswerDialog();
                        int elevPx = getResources().getDimensionPixelSize(R.dimen.btn_stats_elev);
                        Log.d(DEBUG_TAG, "btnConfirmAnswer onTouch. Action was UP");
                        v.setElevation(elevPx);
                        
                        Log.d(DEBUG_TAG, "view.getChildCount() bef removeView = " +
                                ((ConstraintLayout) view).getChildCount());
                        ((ViewGroup) view).removeView(etNewAnswer);
                        Log.d(DEBUG_TAG, "view.getChildCount() aft removeView = " +
                                ((ConstraintLayout) view).getChildCount());

                        ((ViewGroup) view).addView(etNewQuestion);
                        etNewQuestion.setText(question.getQuestionText());
                        ConstraintSet constraintSet = new ConstraintSet();
                        constraintSet.clone((ConstraintLayout) view);
                        constraintSet.connect(R.id.et_new_question, ConstraintSet.TOP,
                                R.id.constr_layout_add_question, ConstraintSet.TOP);
                        constraintSet.connect(R.id.rv_answers_collection, ConstraintSet.TOP,
                                R.id.et_new_question, ConstraintSet.BOTTOM);
                        //constraintSet.constrainHeight(etNewAnswerId, ConstraintSet.MATCH_CONSTRAINT_PERCENT);
                        //constraintSet.constrainDefaultHeight(etNewAnswerId, ConstraintSet.MATCH_CONSTRAINT_PERCENT);
                        constraintSet.constrainPercentHeight(R.id.et_new_question, 0.4f);
                        constraintSet.applyTo((ConstraintLayout) view);
                        return true;
                    default:
                        return false;
                }
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(DEBUG_TAG, "in onViewCreated() of AddQuestionFragment");



        answersAdapter = new AnswersAdapter(question.getAnswers(), this);
        RecyclerView rvAnswers = view.findViewById(R.id.rv_answers_collection);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvAnswers.setLayoutManager(layoutManager);
        rvAnswers.setAdapter(answersAdapter);
        MaterialDividerItemDecoration dividerItemDecoration =
                new MaterialDividerItemDecoration(rvAnswers.getContext(),
                        layoutManager.getOrientation());
        dividerItemDecoration.setDividerColorResource(getContext(), R.color.blue);
        rvAnswers.addItemDecoration(dividerItemDecoration);
        showQuestionTypeDialog();
    }

    public void showQuestionTypeDialog() {
        DialogFragment newFragment = new QuestionTypeDialogFragment();
        //newFragment.setTargetFragment(this, 1);
        newFragment.show(getChildFragmentManager(), "QuestionTypeDialogFragment");
        //newFragment.show(getFragmentManager(), "QuestionTypeDialogFragment");
    }
    public void showCorrectAnswerDialog() {
        DialogFragment newFragment = new CorrectAnswerDialogFragment();
        newFragment.show(getChildFragmentManager(), "CorrectAnswerDialogFragment");
    }
    public void showMessageDialog(String title, String message) {
        DialogFragment newFragment = new MessageDialogFragment(title, message);
        newFragment.show(getChildFragmentManager(), "CorrectAnswerDialogFragment");
    }
    public void showDeleteAnswerDialog(int position){
        DialogFragment newFragment = new DeleteAnswerDialogFragment(position);
        newFragment.show(getChildFragmentManager(), "CorrectAnswerDialogFragment");
    }

    public Question getQuestion(){
        return question;
    }

    @Override
    public void onQuestionTypePosClick(DialogFragment dialog) {
        question.setSingleAnswer(true);
    }

    @Override
    public void onQuestionTypeNegClick(DialogFragment dialog) {
        question.setSingleAnswer(false);
    }

    @Override
    public void onCorrectAnswerPosClick(DialogFragment dialog) {

        if(question.isSingleAnswer() && haveCorrectAnswer){
            showMessageDialog(getResources().getString(R.string.msg_dlg_too_many_correct_answers_title),
                    getResources().getString(R.string.msg_dlg_too_many_correct_answers_message));
        }
        else{
            // добавить вопрос в коллекцию и обновить RecyclerView
            String newAnswer = etNewAnswer.getText().toString() + " +";
            question.addAnswerToCollection(newAnswer);
            answersAdapter.notifyDataSetChanged();
            haveCorrectAnswer = false;
        }
        etNewAnswer.setText("");
        haveCorrectAnswer = true;
    }
    @Override
    public void onCorrectAnswerNegClick(DialogFragment dialog) {
        String newAnswer = etNewAnswer.getText().toString() + " -";
        question.addAnswerToCollection(newAnswer);
        answersAdapter.notifyDataSetChanged();
        etNewAnswer.setText("");
    }

    @Override
    public void onItemLongClick(int position) {
        showDeleteAnswerDialog(position);
    }

    @Override
    public void onDeleteAnswerPosClick(DialogFragment dialog, int position) {
        question.removeAnswerFromCollection(position);
        answersAdapter.notifyItemRemoved(position);
    }

    @Override
    public void onDeleteAnswerNegClick(DialogFragment dialog) {

    }
}
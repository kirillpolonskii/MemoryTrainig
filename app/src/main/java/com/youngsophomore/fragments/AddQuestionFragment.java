package com.youngsophomore.fragments;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.net.Uri;
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
import com.youngsophomore.data.Question;
import com.youngsophomore.helpers.PrepHelper;
import com.youngsophomore.interfaces.RecyclerViewClickListener;

public class AddQuestionFragment extends Fragment
        implements QuestionTypeDialogFragment.QuestionTypeDialogListener,
                    CorrectAnswerDialogFragment.CorrectAnswerDialogListener,
        DeleteAnswerDialogFragment.DeleteAnswerDialogListener,
                    RecyclerViewClickListener {
    private static final String DEBUG_TAG = "Gestures";
    EditText etNewQuestion;
    EditText etNewAnswer;
    Question question;
    int etNewAnswerId;
    private boolean haveCorrectAnswer;
    AnswersAdapter answersAdapter;
    Uri imageUri;

    public AddQuestionFragment() {
        // Required empty public constructor
    }

    public static AddQuestionFragment newInstance(String param1, String param2) {
        
        AddQuestionFragment fragment = new AddQuestionFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        question = new Question();
        question.setQuestionText("");
        if (getArguments() != null) {
            imageUri = Uri.parse(getArguments().getString(getString(R.string.chosen_img_key)));
        }
    }
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        
        if (container instanceof FragmentContainerView){
            
        }
        else{
            
        }
        View view = inflater.inflate(R.layout.fragment_add_question, container, false);
        if (view instanceof ConstraintLayout){
            
        }
        else{
            
        }
        if (view.getId() == view.findViewById(R.id.constr_layout_add_question).getId()){
            
        }
        else{
            
        }
        etNewQuestion = view.findViewById(R.id.et_new_question);
        etNewAnswer = new EditText(getContext());
        etNewAnswerId = View.generateViewId();
        etNewAnswer.setId(etNewAnswerId);
        etNewAnswer.setHint(getString(R.string.et_answer_hint));
        etNewAnswer.setGravity(Gravity.TOP);
        etNewAnswer.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        etNewAnswer.setTextSize(22);
        etNewAnswer.setTextColor(getResources().getColor(R.color.blue));
        etNewAnswer.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.blue)));

        ConstraintLayout.LayoutParams etNewAnswerParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                0
        );
        etNewAnswer.setLayoutParams(etNewAnswerParams);

        ImageButton btnAddAnswer = view.findViewById(R.id.btn_add_answer);
        ImageButton btnConfirmAnswer = view.findViewById(R.id.btn_confirm_answer);
        int elevPx = getResources().getDimensionPixelSize(R.dimen.btn_stats_elev);
        PrepHelper.deactivateBtn(btnConfirmAnswer);
        btnAddAnswer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        
                        v.setElevation(0);
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        
                        return true;
                    case (MotionEvent.ACTION_UP):
                        
                        PrepHelper.deactivateBtn(btnAddAnswer);
                        PrepHelper.activateBtn(btnConfirmAnswer, elevPx);

                        question.setQuestionText(etNewQuestion.getText().toString());
                        ((ViewGroup) view).removeView(etNewQuestion);
                        

                        ((ViewGroup) view).addView(etNewAnswer);
                        ConstraintSet constraintSet = new ConstraintSet();
                        constraintSet.clone((ConstraintLayout) view);
                        constraintSet.connect(etNewAnswerId, ConstraintSet.TOP,
                                R.id.constr_layout_add_question, ConstraintSet.TOP);
                        constraintSet.connect(R.id.rv_answers_collection, ConstraintSet.TOP,
                                etNewAnswerId, ConstraintSet.BOTTOM);
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
                        
                        v.setElevation(0);
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        
                        return true;
                    case (MotionEvent.ACTION_UP):
                        if (!etNewAnswer.getText().toString().equals("")){
                            showCorrectAnswerDialog();
                        }
                        PrepHelper.deactivateBtn(btnConfirmAnswer);
                        PrepHelper.activateBtn(btnAddAnswer, elevPx);
                        
                        //v.setElevation(elevPx);
                        
                        ((ViewGroup) view).removeView(etNewAnswer);
                        ((ViewGroup) view).addView(etNewQuestion);
                        etNewQuestion.setText(question.getQuestionText());
                        ConstraintSet constraintSet = new ConstraintSet();
                        constraintSet.clone((ConstraintLayout) view);
                        constraintSet.connect(R.id.et_new_question, ConstraintSet.TOP,
                                R.id.constr_layout_add_question, ConstraintSet.TOP);
                        constraintSet.connect(R.id.rv_answers_collection, ConstraintSet.TOP,
                                R.id.et_new_question, ConstraintSet.BOTTOM);
                        constraintSet.constrainPercentHeight(R.id.et_new_question, 0.4f);
                        constraintSet.applyTo((ConstraintLayout) view);
                        return true;
                    default:
                        return false;
                }
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        

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
        newFragment.show(getChildFragmentManager(), "QuestionTypeDialogFragment");
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
        newFragment.show(getChildFragmentManager(), "DeleteAnswerDialogFragment");
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
            String newAnswer = etNewAnswer.getText().toString() + " +";
            if (!etNewAnswer.getText().toString().equals("")){
                question.addAnswerToCollection(newAnswer);
                answersAdapter.notifyDataSetChanged();
                haveCorrectAnswer = false;
            }
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
package com.youngsophomore.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Question implements Parcelable {
    private String questionText;
    private ArrayList<String> answers;
    private String answersInOneString;
    private boolean isSingleAnswer;

    public Question(){
        this.answers = new ArrayList<>();
    }

    public Question(String questionText, ArrayList<String> answers, String answersInOneString){
        this.questionText = questionText;
        this.answers = answers;
        this.answersInOneString = answersInOneString;
    }

    protected Question(Parcel in) {
        questionText = in.readString();
        answers = in.createStringArrayList();
        answersInOneString = in.readString();
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public String getQuestionText() {
        return questionText;
    }
    public ArrayList<String> getAnswers() {
        return answers;
    }
    public String getAnswersInOneString() {
        return answersInOneString;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }
    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }
    public void setAnswersInOneString(String answersInOneString) {
        this.answersInOneString = answersInOneString;
    }
    public void addAnswerToCollection(String newAnswer){
        answers.add(newAnswer);
    }
    public void removeAnswerFromCollection(int position){
        answers.remove(position);
    }
    public void setSingleAnswer(boolean isSingleAnswer){
        this.isSingleAnswer = isSingleAnswer;
    }
    public boolean isSingleAnswer(){
        return isSingleAnswer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(questionText);
        dest.writeStringList(answers);
        dest.writeString(answersInOneString);
    }
}

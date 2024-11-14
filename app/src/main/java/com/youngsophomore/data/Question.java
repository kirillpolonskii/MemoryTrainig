package com.youngsophomore.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class Question implements Parcelable {
    private String questionText;
    private ArrayList<String> answers; // last char in answer: '+' or '-'
    private String answersInOneString;
    private boolean isSingleAnswer;
    private HashSet<Integer> corrAnswersIndices;

    public Question(){
        this.answers = new ArrayList<>();
        answersInOneString = "";
    }

    public Question(String questionText, String answersInOneString){
        this.questionText = questionText;
        this.answers = new ArrayList<>();
        this.answersInOneString = answersInOneString;
        this.corrAnswersIndices = new HashSet<>();
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
    public String getAnswersInOneString(boolean forListDisplay) {
        if(forListDisplay){
            return answersInOneString.replace('|', '\n');
        }
        else{
            return answersInOneString;
        }

    }

    public HashSet<Integer> getCorrAnswersIndices(){
        return corrAnswersIndices;
    }
    public void putAnswersInOneString(){
        for(int i = 0; i < answers.size() - 1; ++i){
            answersInOneString += answers.get(i) + "|";
        }
        answersInOneString += answers.get(answers.size() - 1);
    }
    public void parseAnswersFromString(){
        int corrAnswersNum = 0;
        String[] answersSplitted = answersInOneString.split("\\|");
        for(int i = 0; i < answersSplitted.length; ++i){
            answers.add(answersSplitted[i]);
            if (answersSplitted[i].charAt(answersSplitted[i].length() - 1) == '+')
                ++corrAnswersNum;
        }
        if (corrAnswersNum == 1) isSingleAnswer = true;
        Collections.shuffle(answers);
        for(int i = 0; i < answers.size(); ++i){
            if (answers.get(i).charAt(answers.get(i).length() - 1) == '+'){
                corrAnswersIndices.add(i + 1);
            }
        }
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }
    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
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

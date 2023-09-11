package com.youngsophomore.data;

import java.util.ArrayList;

public class Question {
    private String questionText;
    private ArrayList<String> answers;
    private String answersInOneString;

    public Question(String questionText, ArrayList<String> answers, String answersInOneString){
        this.questionText = questionText;
        this.answers = answers;
        this.answersInOneString = answersInOneString;
    }

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
}

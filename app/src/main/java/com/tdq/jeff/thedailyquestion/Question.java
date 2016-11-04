package com.tdq.jeff.thedailyquestion;

import java.io.Serializable;

/**
 * Created by Jeff on 10/23/2016.
 */

public class Question implements Serializable{
    private String question;
    private String answerFormat;
    private String questionTimeHour;
    private String questionTimeMinute;
    private String questionAmPM = "AM";
    private String questionID;

    public Question(){

    }
    public Question(String Q, String AF){
        question = Q;
        answerFormat = AF;
    }

    //getters
    public String getQuestion(){
        return question;
    }
    public String getQuestionTimeHour() { return questionTimeHour; }
    public String getQuestionTimeMinute() { return questionTimeMinute; }
    public String getQuestionID() { return questionID; }
    public Integer getQuestionIDasInt() { return Integer.parseInt(questionID); }
    public String getAnswerFormat() { return answerFormat; }


    //setters
    public void setQuestion(String q){
        question = q;
    }
    public void setAnswerFormat(String a){
        answerFormat = a;
    }

    public void setQuestionTimeHour(String h){
        questionTimeHour = h;
    }
    public void setQuestionTimeMinute(String m){
        if (m == "0"){
            m = "00";
        }
        questionTimeMinute = m;
    }
    public void setQuestionID(Object ID) {
        assert (ID instanceof String || ID instanceof Integer);
        if (ID instanceof String) {
            questionID = String.valueOf(ID);
        } else {
            questionID = ID.toString();
        }
    }
}

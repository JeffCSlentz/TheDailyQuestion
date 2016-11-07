package com.tdq.jeff.thedailyquestion;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Jeff on 10/24/2016.
 */

public class PrefsAccessor extends AppCompatActivity {
    Context mContext;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    Gson gson = new Gson();


    public PrefsAccessor(Context mContext){
        this.mContext = mContext;
        sharedPref = mContext.getSharedPreferences("Questions", MODE_PRIVATE);
        editor = sharedPref.edit();
    }

    public Set<String> getAllQuestions(){
        Set<String> questions = new TreeSet<>();
        return sharedPref.getStringSet("questions", questions);
    }

    public void setAllQuestions(Set<String> questions){
        editor.putStringSet("questions", questions);
        editor.commit();
    }

    public void saveQuestion(Question q){

        //If the question is new, give it the highest question ID possible
        if (q.getQuestionIDasInt() == -1){
            q = saveNewQuestion(q);
        }

        q.setQuestionTimeHour(getGlobalTimeHour());
        q.setQuestionTimeMinute(getGlobalTimeMinute());

        //Convert Question q to a json string.
        String json = gson.toJson(q);

        //Sharedpreferences stores each question as a json string by the id q0, q1, q2 etc.
        Log.i("Saved Question ID... ", q.getQuestionID());
        editor.putString("q"+ q.getQuestionID(), json);

        editor.commit();
    }

    private Question saveNewQuestion(Question q){
        String tempQuestionID;

        //grab a set of all the questions
        Set<String> questions = new TreeSet<>();
        questions = getAllQuestions();

        //Convert it to an arraylist
        ArrayList<String> questionList = new ArrayList<>(questions);
        Collections.sort(questionList);

        if (questions.isEmpty()){
            tempQuestionID = "0";
            questions.add(tempQuestionID);
            q.setQuestionID(tempQuestionID);
        }
        else{
            //Set the question ID as the highest questionID already used + 1
            q.setQuestionID(questionList.size());
            questionList.add(q.getQuestionID());
            questions = new TreeSet<>(questionList);
        }


        setAllQuestions(questions);
        return q;
    }


    public Question loadQuestion(String qID) throws Exception {
        Gson gson = new Gson();
        String json = sharedPref.getString("q"+qID, null);
        Question q =  gson.fromJson(json, Question.class);

        if (q.equals(null)){
            Exception e = new Exception("Question not loaded");
            throw e;
        }
        return q;
    }

    public int getNumberOfQuestions(){
        return getAllQuestions().size();
    }


    // TODO: THIS WILL NEED TO DELETE ANSWER DATA ONCE I GET THERE
    public void deleteQuestion(String qID){
        Integer numQuestions = this.getNumberOfQuestions();

        Question q = null;
        try{
            q = loadQuestion(qID);
        }
        catch(Exception e){
                System.out.println("Load Failed in PrefsAccessor.deleteQuestion: " + e.getMessage());
        }

        //Cancel the alarm
        Alarm alarm = new Alarm(mContext);
        alarm.cancelAlarm(q);

        //Remove this question from any notifications
        NotifyReceiver nr = new NotifyReceiver();
        nr.removeNotification(mContext, qID);

        //Remove the requested entry
        editor.remove("q"+ qID);

        Set<String> questions = new TreeSet<>();
        questions = getAllQuestions();
        questions.remove(qID);
        setAllQuestions(questions);


        editor.commit();
    }

    public void setGlobalTime(String hour, String minute){
        editor.putString("qTimeHour", hour);
        editor.putString("qTimeMinute", minute);
        editor.commit();
    }

    public String getGlobalTimeHour(){
        return sharedPref.getString("qTimeHour", "19");
    }

    public String getGlobalTimeMinute(){
        return sharedPref.getString("qTimeMinute", "00");
    }

    public void addqNotification(Question q){
        Set<String> notificationIDs = new TreeSet<>();
        notificationIDs = sharedPref.getStringSet("qNotifications", notificationIDs);

        notificationIDs.add(q.getQuestionID() );

        editor.putStringSet("qNotifications", notificationIDs);
        editor.commit();
    }

    public Set<String> getqNotifications(){
        Set<String> notificationIDs = new TreeSet<>();
        return sharedPref.getStringSet("qNotifications", notificationIDs);
    }

    public void removeqNotification(String qID){
        Set<String> notificationIDs = new TreeSet<>();
        notificationIDs = sharedPref.getStringSet("qNotifications", notificationIDs);
        notificationIDs.remove(qID);
    }

}

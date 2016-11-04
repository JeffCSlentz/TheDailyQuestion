package com.tdq.jeff.thedailyquestion;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;

/**
 * Created by Jeff on 10/24/2016.
 */

public class PrefsAccessor extends AppCompatActivity {
    Context mContext;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    public PrefsAccessor(Context mContext){
        this.mContext = mContext;
        sharedPref = mContext.getSharedPreferences("Questions", MODE_PRIVATE);
        editor = sharedPref.edit();
    }

    public void saveQuestion(Question q){

        //If the question is new, give it the highest question ID possible
        if (q.getQuestionIDasInt() == -1){
            int numQuestions = sharedPref.getInt("numQs", 0);
            numQuestions++;
            editor.putInt("numQs", numQuestions);

            q.setQuestionID(numQuestions - 1);
        }

        //Convert Question q to a json string.
        Gson gson = new Gson();
        String json = gson.toJson(q);

        //Sharedpreferences stores each question as a json string by the id q0, q1, q2 etc.
        Log.i("Saved Question ID... ", q.getQuestionID());
        editor.putString("q"+ q.getQuestionID(), json);

        editor.commit();
    }

    public Question loadQuestion(String qID){
        Gson gson = new Gson();
        String json = sharedPref.getString("q"+qID, "null");
        Question q =  gson.fromJson(json, Question.class);
        return q;
    }

    public int getNumberOfQuestions(){
        return sharedPref.getInt("numQs", 0);
    }


    // TODO: THIS WILL NEED TO DELETE ANSWER DATA ONCE I GET THERE
    public void deleteQuestion(String qID){
        Integer numQuestions = this.getNumberOfQuestions();

        //Remove the requested entry
        editor.remove("q"+ qID);

        //For each entry higher than the one deleted, decrement their question ID.
        for(Integer i = Integer.parseInt(qID); i < (numQuestions - 1); i++ ){
            Question q  = loadQuestion(String.valueOf(i+1));
            q.setQuestionID(String.valueOf(i));
            saveQuestion(q);
        }

        //Remove the last question from the list (it's now a duplicate) and decrement the number of questions.
        editor.remove("q" + String.valueOf(numQuestions - 1));
        editor.putInt("numQs", (numQuestions - 1));

        editor.commit();
    }
}

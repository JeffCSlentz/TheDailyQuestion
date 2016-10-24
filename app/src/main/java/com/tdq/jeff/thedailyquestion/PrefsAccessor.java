package com.tdq.jeff.thedailyquestion;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;

/**
 * Created by Jeff on 10/24/2016.
 */

public class PrefsAccessor extends AppCompatActivity {

    public static void saveQuestion(Context c, Question q, int qNum){

        Gson gson = new Gson();
        String json = gson.toJson(q);

        SharedPreferences sharedPref = c.getSharedPreferences("Questions", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        if (qNum == -1){
            int numQuestions = sharedPref.getInt("numQs", 0);
            numQuestions++;
            editor.putInt("numQs", numQuestions);
            qNum = numQuestions - 1;
        }

        editor.putString("q"+ qNum, json);

        editor.commit();

        return;

    }

    public static Question loadQuestion(Context c, int qNum){
        SharedPreferences sharedPref = c.getSharedPreferences("Questions", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPref.getString("q"+qNum, "null");
        Question q =  gson.fromJson(json, Question.class);
        return q;
    }

    public static int getNumberOfQuestions(Context c){
        SharedPreferences sharedPref = c.getSharedPreferences("Questions", MODE_PRIVATE);
        return sharedPref.getInt("numQs", 0);
    }


}

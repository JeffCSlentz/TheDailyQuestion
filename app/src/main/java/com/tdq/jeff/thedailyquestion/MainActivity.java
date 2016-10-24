package com.tdq.jeff.thedailyquestion;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        /*Intent intent = getIntent();
        String question = intent.getStringExtra(EditQuestionActivity.QUESTION_TEXT);
        if (question != null){
            questionList.add(question);
        }

        if (!questionList.isEmpty()){
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, questionList);
            ListView listview = (ListView) findViewById(R.id.questionList);
            listview.setAdapter(adapter);
        }*/

        populateQuestionList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.add_Q){
            addQuestion();
            return true;
        }

        //Deletes all of shared preferences and clears the UI
        if (id == R.id.delete_all){
            SharedPreferences sharedPref = getSharedPreferences("Questions", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.clear();
            editor.commit();
            populateQuestionList();
            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Starts the EditQuestion Activity
    public void addQuestion(){
        Intent intent = new Intent(this, EditQuestionActivity.class);
        intent.putExtra("QUESTION_NUMBER", "-1");
        startActivity(intent);
    }

    //Reads from sharedpreferences and adds each question to the list.
    public void populateQuestionList(){
        ArrayList<Question> questionList = new ArrayList<Question>();

        //SharedPreferences sharedPref = getSharedPreferences("Questions", MODE_PRIVATE);

        int numQuestions = PrefsAccessor.getNumberOfQuestions(this);

        if (numQuestions > 0){
            for(int i =0; i < numQuestions; i++){
                Question q = PrefsAccessor.loadQuestion(this, i);
                questionList.add(q);
            }
        }


        //Custom adapter to display a list of Questions in a list view.
        QuestionsAdapter qAdapter = new QuestionsAdapter(this, questionList);
        ListView listView = (ListView) findViewById(R.id.questionLV);
        listView.setAdapter(qAdapter);

    }
    /*
    public void editQuestion(View view){
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.add_Q);

    }*/
}

/*
qList = "1,2,3,4,5,6,7"

Sharedpref "Questions"
    numQ = integer telling how many questions are in shared pref
    q1 = string object for question 1
    q2 = string object for question 2
    qn = string object for question n ... etc


What I want
{"employees":[
    {"firstName":"John", "lastName":"Doe"},
    {"firstName":"Anna", "lastName":"Smith"},
    {"firstName":"Peter", "lastName":"Jones"}
]}

What I have

{"q1":{"question":"Whats up fam?"}, "answerformat":"etc"}}
{"q2":{"question":"Whats up fam?"}, "answerformat":"etc"}}



    */
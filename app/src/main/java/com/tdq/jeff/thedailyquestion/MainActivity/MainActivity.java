package com.tdq.jeff.thedailyquestion.MainActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.tdq.jeff.thedailyquestion.EditQuestion.EditQuestionActivity;
import com.tdq.jeff.thedailyquestion.PrefsAccessor;
import com.tdq.jeff.thedailyquestion.Question;
import com.tdq.jeff.thedailyquestion.R;
import com.tdq.jeff.thedailyquestion.Settings.SettingsActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class MainActivity extends AppCompatActivity implements LongClickQuestionFragment.OnCompleteListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
            startActivity(new Intent(this, SettingsActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    //Starts the EditQuestion Activity
    public void addQuestion(){
        Intent intent = new Intent(this, EditQuestionActivity.class);
        intent.putExtra(this.getString(R.string.questionID), "-1");
        startActivity(intent);
    }

    //Reads from sharedpreferences and adds each question to the list.
    public void populateQuestionList(){

        PrefsAccessor prefs = new PrefsAccessor(this);
        Set<String> questions = new TreeSet<>();
        questions = prefs.getAllQuestions();

        ArrayList<String> questionListofIDs = new ArrayList<>(questions);
        Collections.sort(questionListofIDs);

        ArrayList<Question> questionList = new ArrayList<Question>();

        Iterator itr = questionListofIDs.iterator();



        int numQuestions = prefs.getNumberOfQuestions();
        if (numQuestions > 0){
            while (itr.hasNext()){
                String qID = String.valueOf(itr.next());

                Question q = null;

                try{
                    q = prefs.loadQuestion(qID);
                }
                catch (Exception e){
                    System.out.println("Question load Failed in MainActivity.populateQuestionList: " + e.getMessage());
                }


                questionList.add(q);
            }
        }


        //Custom adapter to display a list of Questions in a list view.
        QuestionsAdapter qAdapter = new QuestionsAdapter(this, questionList);
        ListView listView = (ListView) findViewById(R.id.questionLV);
        listView.setAdapter(qAdapter);

    }

    public void onComplete(){
        populateQuestionList();
    }

}


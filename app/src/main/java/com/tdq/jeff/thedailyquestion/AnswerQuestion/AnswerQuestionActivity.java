package com.tdq.jeff.thedailyquestion.AnswerQuestion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.tdq.jeff.thedailyquestion.MainActivity.QuestionsAdapter;
import com.tdq.jeff.thedailyquestion.PrefsAccessor;
import com.tdq.jeff.thedailyquestion.Question;
import com.tdq.jeff.thedailyquestion.R;

import java.util.ArrayList;
import java.util.Iterator;

public class AnswerQuestionActivity extends AppCompatActivity {
    Question question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_question);

        Intent intent = getIntent();
        ArrayList<String> qNotificationsList  = intent.getStringArrayListExtra("qNotifications");

        ArrayList<Question> questionList = loadQuestions(qNotificationsList);
        populateUI(questionList);
    }

    public ArrayList<Question> loadQuestions(ArrayList<String> qIds){
        Iterator itr = qIds.iterator();
        ArrayList<Question> questionList = new ArrayList<>();

        PrefsAccessor prefs = new PrefsAccessor(this);

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

        return questionList;
    }
    public void populateUI(ArrayList<Question> questionArrayList){
        AnswersAdapter aAdapter = new AnswersAdapter(this, questionArrayList);
        ListView listView = (ListView) findViewById(R.id.questionAnswerListView);
        listView.setAdapter(aAdapter);
    }
}

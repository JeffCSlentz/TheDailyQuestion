package com.tdq.jeff.thedailyquestion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditQuestionActivity extends AppCompatActivity {

    public final static String QUESTION_TEXT = "";
    public final static String ACTIVITY = "EditQuestionActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_question);

    }
    public void okAddQuestion(View view){
        Intent intent = new Intent(this, MainActivity.class);

        EditText questionField = (EditText)findViewById(R.id.write_question_text);
        String question = questionField.getText().toString();

        intent.putExtra(QUESTION_TEXT, question);
        startActivity(intent);


        return;
    }
    public void cancelAddQuestion(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        return;
    }
}

package com.tdq.jeff.thedailyquestion;

import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;


public class EditQuestionActivity extends AppCompatActivity implements TimePickerFragment.OnCompleteListener {

    public final static String QUESTION_TEXT = "";
    public final static String ACTIVITY = "EditQuestionActivity";
    public static String qNum = "-1";

    public Question q = new Question();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_question);

        Intent intent = getIntent();
        qNum = intent.getStringExtra("QUESTION_NUMBER");

        if (!qNum.equals("-1")){
            Log.i("Editing question number", qNum);
            loadQuestion();
            updateTextFields();
        }

        //Create the spinner list from pre-defined list
        Spinner spinner = (Spinner) findViewById(R.id.answer_option_list);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.answer_Options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


    }

    private void loadQuestion() {
        Integer qNumInt = Integer.parseInt(qNum);
        q = PrefsAccessor.loadQuestion(this, qNumInt);

    }

    private void updateTextFields() {
        TextView qText = (TextView) findViewById(R.id.write_question_text);
        qText.setText(q.getQuestion());
    }

    //Called when the user clicks on OK button
    public void okAddQuestion(View view){

        //Pull the user's question from the EditText field
        EditText questionField = (EditText)findViewById(R.id.write_question_text);
        String question = questionField.getText().toString();

        //Pull the user's answer format choice from the spinner
        Spinner spinner = (Spinner) findViewById(R.id.answer_option_list);
        String answerFormat = spinner.getSelectedItem().toString();

        //Save both to the Question object q
        q.setQuestion(question);
        q.setAnswerFormat(answerFormat);


        Integer qNumInt = Integer.parseInt(qNum);


        PrefsAccessor.saveQuestion(this, q, qNumInt);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        return;
    }
    public void cancelAddQuestion(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        return;
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timepicker");

    }

    public void onComplete(String h, String m) {
        //After dialog fragment completes, this is called
        q.setQuestionTimeHour(h);
        q.setQuestionTimeMinute(m);

        updateQuestionTime();
    }

    public void updateQuestionTime(){
        TextView qText = (TextView) findViewById(R.id.question_time_title);
        qText.setText("Question will be asked at " + q.getQuestionTimeHour() + ":" + q.getQuestionTimeMinute());
    }
}

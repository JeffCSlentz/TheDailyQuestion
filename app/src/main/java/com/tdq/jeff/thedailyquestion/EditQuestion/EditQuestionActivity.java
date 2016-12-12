package com.tdq.jeff.thedailyquestion.EditQuestion;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.tdq.jeff.thedailyquestion.Alarm;
import com.tdq.jeff.thedailyquestion.MainActivity.MainActivity;
import com.tdq.jeff.thedailyquestion.PrefsAccessor;
import com.tdq.jeff.thedailyquestion.Question;
import com.tdq.jeff.thedailyquestion.R;
import com.tdq.jeff.thedailyquestion.TimePickerFragment;


public class EditQuestionActivity extends AppCompatActivity  implements TimePickerFragment.OnCompleteListener {

    public final static String QUESTION_TEXT = "";
    public final static String ACTIVITY = "EditQuestionActivity";
    public static String qNum = "-1";

    public Question q = new Question();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_question);

        Intent intent = getIntent();
        qNum = intent.getStringExtra(this.getString(R.string.questionID));
        q.setQuestionID(qNum);


        if (!qNum.equals("-1")){
            Log.i("Editing question number", qNum);
            loadQuestion();
            updateFields();

        }
        loadSpinner(qNum);
    }

    private void loadQuestion() {
        PrefsAccessor prefs = new PrefsAccessor(this);
        try{
            q = prefs.loadQuestion(qNum);
        }
        catch (Exception e){
            System.out.println("Question load failed in EditQuestionActivity.loadQuestion: " + e.getMessage());
        }
    }

    private void updateFields() {
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


        //Integer qNumInt = Integer.parseInt(qNum);
        q.setQuestionID(qNum);

        PrefsAccessor prefs = new PrefsAccessor(this);
        prefs.saveQuestion(q);

        Alarm alarm = new Alarm(this);
        alarm.setAlarm(q);

        returnToMainActivity();
    }
    public void cancelAddQuestion(View view){
        returnToMainActivity();
    }

    public void returnToMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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



    private void loadSpinner(String qNum) {
        Spinner spinner = (Spinner) findViewById(R.id.answer_option_list);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.answer_Options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        if(!qNum.equals("-1")){
            switch(q.getAnswerFormat()){
                case "Yes or No": spinner.setSelection(0, false);
                    break;
                case "Rating Scale": spinner.setSelection(1, false);
                    break;
                case "Multiple Choice": spinner.setSelection(2, false);
            }
        }

        spinner.setAdapter(adapter);
    }
}

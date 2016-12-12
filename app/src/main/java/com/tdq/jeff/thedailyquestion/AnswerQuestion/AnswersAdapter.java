package com.tdq.jeff.thedailyquestion.AnswerQuestion;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tdq.jeff.thedailyquestion.EditQuestion.EditQuestionActivity;
import com.tdq.jeff.thedailyquestion.MainActivity.LongClickQuestionFragment;
import com.tdq.jeff.thedailyquestion.Question;
import com.tdq.jeff.thedailyquestion.R;

import java.util.ArrayList;

/**
 * Created by Jeff on 10/23/2016.
 */

public class AnswersAdapter extends ArrayAdapter<Question> {
    private Context mContext;
    public AnswersAdapter(Context context, ArrayList<Question> questions) {
        super(context, 0, questions);
        mContext = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Question q = getItem(position);

        if (q.getAnswerFormat() == "Yes or No"){
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_answer_question_row_layout_yes_no, parent, false);
            }
        }
        else{
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_answer_question_row_layout_rating_scale, parent, false);
            }
        }
        TextView qText = (TextView) convertView.findViewById(R.id.questionText);
        qText.setText(q.getQuestion() + " --- Q# = " + position);
        qText.setTag(q.getQuestionID());


        // Return the completed view to render on screen
        return convertView;
    }



}

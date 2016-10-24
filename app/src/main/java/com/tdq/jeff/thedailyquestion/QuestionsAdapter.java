package com.tdq.jeff.thedailyquestion;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jeff on 10/23/2016.
 */

public class QuestionsAdapter extends ArrayAdapter<Question> {
    public QuestionsAdapter(Context context, ArrayList<Question> questions) {
        super(context, 0, questions);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Question q = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_question, parent, false);
        }
        // Lookup view for data population
        TextView qText = (TextView) convertView.findViewById(R.id.questionName);
        // Populate the data into the template view using the data object
        qText.setText(q.getQuestion() + " --- Q# = " + position);


        qText.setTag(position);

        qText.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                int position = (Integer) v.getTag();
                Log.i("The position is ", String.valueOf(position));

                Intent intent = new Intent(getContext(), EditQuestionActivity.class);
                intent.putExtra("QUESTION_NUMBER", String.valueOf(position));
                getContext().startActivity(intent);
            }
        });

        // Return the completed view to render on screen
        return convertView;
    }



}

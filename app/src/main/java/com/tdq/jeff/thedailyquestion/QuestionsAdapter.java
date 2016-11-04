package com.tdq.jeff.thedailyquestion;

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

import java.util.ArrayList;

/**
 * Created by Jeff on 10/23/2016.
 */

public class QuestionsAdapter extends ArrayAdapter<Question> {
    Context mContext;
    public QuestionsAdapter(Context context, ArrayList<Question> questions) {
        super(context, 0, questions);
        mContext = context;
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

        //Store question ID in the tag for each data item
        qText.setTag(q.getQuestionID());
        Log.i("QA: Question ID =  ", q.getQuestionID());

        qText.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //Get the question ID when a question is clicked
                String IDstring = v.getTag().toString();
                Integer ID = Integer.parseInt(IDstring);

                //Pass the question ID to the intent
                Intent intent = new Intent(getContext(), EditQuestionActivity.class);
                intent.putExtra(mContext.getString(R.string.questionID), String.valueOf(ID));
                getContext().startActivity(intent);
            }
        });

        qText.setOnLongClickListener(new View.OnLongClickListener(){
            public boolean onLongClick(View v){
                String IDString = v.getTag().toString();

                //Create a new bundle to pass in questionID
                Bundle bundle = new Bundle();
                bundle.putString(mContext.getString(R.string.questionID), IDString);

                //find the fragmentmanager from the given context
                FragmentManager fragManage = ((Activity) mContext).getFragmentManager();

                //Create a new Fragment
                LongClickQuestionFragment frag = new LongClickQuestionFragment();

                //Send bundle and show fragment
                frag.setArguments(bundle);
                frag.show(fragManage, IDString);

                return true;
            }

        });

        // Return the completed view to render on screen
        return convertView;
    }



}

package com.tdq.jeff.thedailyquestion.MainActivity;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.DialogFragment;

import com.tdq.jeff.thedailyquestion.PrefsAccessor;
import com.tdq.jeff.thedailyquestion.R;


public class LongClickQuestionFragment extends DialogFragment {

    private OnCompleteListener mListener;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        this.mListener = (OnCompleteListener)context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        final String questionID = getArguments().getString("questionID");

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.delete)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        PrefsAccessor prefs = new PrefsAccessor(getActivity());
                        prefs.deleteQuestion(questionID);
                        mListener.onComplete();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();

    }

    public interface OnCompleteListener{
        void onComplete();
    }



}
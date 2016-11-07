package com.tdq.jeff.thedailyquestion;

import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SettingsActivity extends AppCompatActivity implements TimePickerFragment.OnCompleteListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timepicker");

    }

    public void onComplete(String h, String m) {
        //After dialog fragment completes, this is called
        PrefsAccessor prefs = new PrefsAccessor(this);
        prefs.setGlobalTime(h, m);
    }


}

package com.tdq.jeff.thedailyquestion;

        import android.app.Activity;
        import android.app.Dialog;
        import android.app.DialogFragment;
        import android.app.TimePickerDialog;
        import android.content.Context;
        import android.content.Intent;
        import android.os.Bundle;
        import android.text.format.DateFormat;
        import android.widget.TextView;
        import android.widget.TimePicker;

        import org.w3c.dom.Text;

        import java.util.Calendar;

/**
 * Created by jeff on 10/23/16.
 */
public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    private OnCompleteListener mListener;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Use the current time as the default values for the picker
        int hour = 20;
        int minute = 0;

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        this.mListener.onComplete(String.valueOf(hourOfDay), String.valueOf(minute));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.mListener = (OnCompleteListener)context;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnCompleteListener");
        }
    }


    public static interface OnCompleteListener {
        public abstract void onComplete(String h, String m);
    }

}
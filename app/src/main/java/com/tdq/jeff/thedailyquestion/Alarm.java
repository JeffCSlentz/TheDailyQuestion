package com.tdq.jeff.thedailyquestion;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by Jeff on 11/3/2016.
 */

public class Alarm {
    Context mContext;

    public Alarm(Context mContext){
        this.mContext = mContext;
    }


    public void setAlarm(Question q) {

        System.out.println("setAlarm called with questionID " + q.getQuestionID());

        Resources r = mContext.getResources();

        Intent intent = new Intent(mContext, NotifyReceiver.class);
        intent.putExtra(r.getString(R.string.questionID), q.getQuestionID());

        PendingIntent pi = PendingIntent.getBroadcast(mContext, Integer.parseInt(q.getQuestionID()), intent, 0);


        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 5);
        //calendar.set(Calendar.HOUR_OF_DAY, 23);
        //calendar.set(Calendar.MINUTE, 34);
        //calendar.set(Calendar.SECOND, 00);

        System.out.println(calendar.get(Calendar.HOUR_OF_DAY)+ " " + calendar.get(Calendar.MINUTE) + " " + calendar.get(Calendar.SECOND));

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi);
    }

    public void cancelAlarm(Question q){

        Intent intent = new Intent(mContext, NotifyReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(mContext, Integer.parseInt(q.getQuestionID()), intent, 0);
        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(ALARM_SERVICE);

        alarmManager.cancel(pi);
    }
}

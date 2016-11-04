package com.tdq.jeff.thedailyquestion;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Jeff on 11/3/2016.
 */

public class NotifyReceiver extends BroadcastReceiver{
    Question q;

    public void onReceive(Context context, Intent intent) {

        System.out.println("NotifyReceiver called onReceive");

        Resources r = context.getResources();
        PrefsAccessor prefs = new PrefsAccessor(context);
        q = prefs.loadQuestion(intent.getStringExtra(r.getString(R.string.questionID)));
        showNotification(context, q);
    }

    private void showNotification(Context c, Question q){
        Resources r = c.getResources();

        //Set target activity that will be launched
        PendingIntent pi = PendingIntent.getActivity(c, 0, new Intent(c, MainActivity.class), 0);



        //Create a new notification
        Notification notification = new NotificationCompat.Builder(c)
                .setTicker(r.getString(R.string.app_name))
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentTitle(r.getString(R.string.app_name))
                .setContentText(q.getQuestion())
                .setContentIntent(pi)
                .setAutoCancel(true)
                .addAction(android.R.drawable.ic_menu_report_image, "Answer", pi)
                .build();

        //Add the notification to the notification manager
        NotificationManager notificationManager = (NotificationManager) c.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }
}

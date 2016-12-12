package com.tdq.jeff.thedailyquestion;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;

import com.tdq.jeff.thedailyquestion.AnswerQuestion.AnswerQuestionActivity;

import java.util.ArrayList;
import java.util.Set;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Jeff on 11/3/2016.
 */

public class NotifyReceiver extends BroadcastReceiver{

    public void onReceive(Context context, Intent intent) {
        System.out.println("NotifyReceiver called onReceive");

        Resources resources = context.getResources();

        //Load question
        PrefsAccessor prefs = new PrefsAccessor(context);

        Question question = null;
        try{
            question = prefs.loadQuestion(intent.getStringExtra(resources.getString(R.string.questionID)));
        }
        catch(Exception e){
            System.out.println("Load Failed in NotifyReceiver.onReceive: " + e.getMessage());
        }
        receiveNotification(context, question);
    }

    private void receiveNotification(Context context, Question question){

        PrefsAccessor prefs = new PrefsAccessor(context);

        //Add question to the saved set of qNotifications, then load it.
        prefs.addqNotification(question);
        Set<String> qNotifications = prefs.getqNotifications();

        //If the set has only one question in it, build a notification
        //that will have the question text in it.
        Notification notification = buildNotification(context, qNotifications);

        sendNotification(context, notification);
    }
    public void sendNotification(Context context, Notification notification){

        //Add the notification to the notification manager and fire it
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }

    public void removeNotification(Context context, String questionID){
        PrefsAccessor prefs = new PrefsAccessor(context);
        Set<String> qNotifications = prefs.getqNotifications();

        if (qNotifications.contains(questionID)){
            qNotifications.remove(questionID);
        }
        Notification notification = buildNotification(context, qNotifications);

        sendNotification(context, notification);
    }

    public Notification buildNotification(Context context, Set<String> qNotifications){

        Notification notification;
        Resources r = context.getResources();

        //Convert set to list for intent.putExtra
        ArrayList<String> qNotificationsList = new ArrayList<>(qNotifications);

        //Build the intent
        Intent intent = new Intent(context, AnswerQuestionActivity.class);

        //Attach list of notifications for AnswerQuestionActivity Activity
        intent.putExtra("qNotifications", qNotificationsList);

        //Set target activity that will be launched
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);


        String notificationText;

        if (qNotifications.size() == 1){
            String questionString = qNotificationsList.get(0);

            PrefsAccessor prefs = new PrefsAccessor(context);

            Question q = null;
            try{
                q = prefs.loadQuestion(questionString);
            }
            catch(Exception e){
                System.out.println("Load Failed in NotifyReceiver.buildNotification: " + e.getMessage());
            }
            notificationText = q.getQuestion();
        }
        //Otherwise, send a notification with the number of questions.
        else{
            notificationText = qNotifications.size() + " questions are awaiting an answer.";
        }

        notification = new NotificationCompat.Builder(context)
                .setTicker(r.getString(R.string.app_name))
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentTitle(r.getString(R.string.app_name))
                .setContentText(notificationText)
                .setContentIntent(pi)
                .setAutoCancel(true)
                //.addAction(android.R.drawable.ic_menu_report_image, "Answer", pi)
                .build();

        return notification;
    }
}

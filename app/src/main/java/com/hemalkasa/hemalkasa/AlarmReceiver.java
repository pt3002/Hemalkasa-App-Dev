package com.hemalkasa.hemalkasa;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Calendar;


public class AlarmReceiver extends BroadcastReceiver {

    MediaPlayer mediaPlayer;
    public static final String TAG="pratik";
    private static String NextVisit="DD MMM YYYY";
    public static int id=-1;
    public static final String CHANNEL_ID = "Hemalkasa";


    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent!=null){
            if(intent.hasExtra("NextVisit")){
                NextVisit=intent.getStringExtra("NextVisit");
            }
            if(intent.hasExtra("Id")){
                id=intent.getIntExtra("Id", -1);
            }
        }

        Log.d(TAG, "BEFORRRRRRRR");
        Log.d(TAG, CHANNEL_ID);
        Log.d(TAG, String.valueOf(id));
        
        Intent destinationIntent = new Intent(context, Notification_Landing.class);
        destinationIntent.putExtra("NextVisit", NextVisit);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingdestinationIntent = PendingIntent.getActivity(context,id,destinationIntent,PendingIntent.FLAG_MUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("Visit Reminder")
                .setContentText("Reminder for " + NextVisit)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.PRIORITY_HIGH)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingdestinationIntent)
                .setColor(Color.BLUE)
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                .setAutoCancel(true);


        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(123,builder.build());

        Log.d(TAG, "AFTERRRRRRRR");

        mediaPlayer=MediaPlayer.create(context, Settings.System.DEFAULT_RINGTONE_URI);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        Log.d(TAG, "Alarm Started At   " + String.valueOf(Calendar.getInstance().getTimeInMillis()));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "Music Stop   " +  String.valueOf(Calendar.getInstance().getTimeInMillis()));
                mediaPlayer.stop();
            }
        }, 15000);
    }

}

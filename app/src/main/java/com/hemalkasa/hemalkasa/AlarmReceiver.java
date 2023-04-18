package com.hemalkasa.hemalkasa;

import android.app.Notification;
import android.app.NotificationManager;
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
//    private NotificationManager notificationManager;
    private NotificationManager manager;
    private static String medicineName="Dummy";
    public static int id=-1;

    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent!=null){
            if(intent.hasExtra("MedicineName")){
                medicineName=intent.getStringExtra("MedicineName");
            }
            if(intent.hasExtra("Id")){
                id=intent.getIntExtra("Id", -1);
            }
        }

        Intent destinationIntent = new Intent(context,DestinationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingdestinationIntent = PendingIntent.getActivity(context,id,destinationIntent,PendingIntent.FLAG_MUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"foxandroid")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Medicine Reminder")
                .setContentText("Reminder for " + medicineName)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingdestinationIntent)
                .addAction(R.mipmap.ic_launcher, "Medicine Taken", null)
                .addAction(R.drawable.calendar, "Snooze", null)
                .setColor(Color.BLUE)
                .setAutoCancel(true);


        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(123,builder.build());

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
        }, 5000);

    }

}

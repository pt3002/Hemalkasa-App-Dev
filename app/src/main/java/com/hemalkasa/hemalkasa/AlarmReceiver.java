package com.hemalkasa.hemalkasa;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.provider.Settings;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmReceiver extends BroadcastReceiver {

    MediaPlayer mediaPlayer;
    public static final String TAG="pratik";
    private NotificationManager notificationManager;
    private String medicineName="Dummy";

    @Override
    public void onReceive(Context context, Intent intent) {

//        Intent i = new Intent(context,DestinationActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,i,0);
//
//        mediaPlayer=MediaPlayer.create(context, Settings.System.DEFAULT_RINGTONE_URI);
//        mediaPlayer.setLooping(true);
//        mediaPlayer.start();
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"foxandroid")
//                .setSmallIcon(R.drawable.ic_launcher_foreground)
//                .setContentTitle("Medicine Reminder")
//                .setContentText("2 Tablets with Warm Water")
//                .setAutoCancel(true)
//                .setDefaults(NotificationCompat.DEFAULT_ALL)
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setContentIntent(pendingIntent);
//
//        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
//        notificationManagerCompat.notify(123,builder.build());

//        Intent contentIntent = new Intent(this, MainActivity.class);
        Intent contentIntent=new Intent(context,MainActivity.class);
        PendingIntent contentPendingIntent = PendingIntent.getActivity(
                context, 0, contentIntent, 0);

        Notification notification = new NotificationCompat.Builder(context, Notification_Channel.CHANNEL_ONE_ID)
                .setSmallIcon(R.drawable.mother_icon)
                .setContentTitle("Hello")
                .setContentText("World")
                .setContentIntent(contentPendingIntent)
                .addAction(R.mipmap.ic_launcher, "Show Toast", null)
                .setColor(Color.BLUE)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(1,notification);
    }
}

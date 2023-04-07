package com.hemalkasa.hemalkasa;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.Settings;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmReceiver extends BroadcastReceiver {

    MediaPlayer mediaPlayer;

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent i = new Intent(context,DestinationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,i,0);

        mediaPlayer=MediaPlayer.create(context, Settings.System.DEFAULT_RINGTONE_URI);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"foxandroid")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Medicine Reminder")
                .setContentText("2 Tablets with Warm Water")
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(123,builder.build());


    }
}

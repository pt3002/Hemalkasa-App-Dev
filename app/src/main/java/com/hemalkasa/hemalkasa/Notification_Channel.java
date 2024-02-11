package com.hemalkasa.hemalkasa;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Notification_Channel  extends Application {

    public static final String CHANNEL_ID = "Hemalkasa";
    public static final String TAG="pratik";

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "Notificationn Chanellll");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d(TAG, "Inside IFFF");
            CharSequence name = "Visit Reminders";
            String description = "Reminders about next upcoming visit";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        else{
            Log.d(TAG, "Denined  " +  String.valueOf(Build.VERSION.SDK_INT));
        }
        Log.d(TAG, "Notification chanel comeplete");
    }

}

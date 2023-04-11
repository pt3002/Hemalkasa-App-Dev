package com.hemalkasa.hemalkasa;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Notification_Channel  extends Application {

    public static final String CHANNEL_ONE_ID = "channel_one_id";
    public static final String CHANNEL_TWO_ID = "channel_one_two";

    public static final String TAG="pratik";

    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d(TAG, "Allowed Channels  " +  String.valueOf(Build.VERSION.SDK_INT));
            NotificationChannel channelOne = new NotificationChannel(CHANNEL_ONE_ID,
                    "Channel One",
                    NotificationManager.IMPORTANCE_HIGH);

            channelOne.setDescription("This is channel one for video notifications");

            NotificationChannel channelTwo = new NotificationChannel(CHANNEL_TWO_ID,
                    "Channel Two",
                    NotificationManager.IMPORTANCE_DEFAULT);

            channelTwo.setDescription("This is channel two for audio notifications");

            NotificationManager manager = (NotificationManager)
                    getSystemService(NOTIFICATION_SERVICE);

            List<NotificationChannel> channels = new ArrayList<>();
            channels.add(channelOne);
            channels.add(channelTwo);

            manager.createNotificationChannels(channels);

        }
        else{
            Log.d(TAG, "Denined  " +  String.valueOf(Build.VERSION.SDK_INT));
        }
    }

}

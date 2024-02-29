package com.hemalkasa.hemalkasa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import java.io.File;

public class SplashScreen extends AppCompatActivity {

    LottieAnimationView lottie;
    TextView Appname;
    public static final String TAG="pratik";
    public static final String CHANNEL_ID = "Hemalkasa";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        lottie=findViewById(R.id.lottieSplash);

        createVideoFolder();
        createNotificationChannel();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent;
                SharedPreferences sharedPreferences =PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                if (!sharedPreferences.getBoolean("Registration", false)) {
//                    intent=new Intent(SplashScreen.this,Patient_Registration_Page.class);
//                }
//                else{
                    intent=new Intent(SplashScreen.this,Patient_Home_Page.class); // Earlier this was directed to MainActivity
//                    intent=new Intent(SplashScreen.this,MainActivity.class);
//                }
                startActivity(intent);
                finish();
            }
        }, 100);
    }

    private void createNotificationChannel() {
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

    private void createVideoFolder() {
        File path=getExternalFilesDir("Videos");
        if(!path.exists()){
            path.mkdir();
        }
    }
}
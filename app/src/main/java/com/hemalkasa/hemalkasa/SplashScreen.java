package com.hemalkasa.hemalkasa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class SplashScreen extends AppCompatActivity {

    LottieAnimationView lottie;
    TextView Appname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        lottie=findViewById(R.id.lottieSplash);
        Appname=findViewById(R.id.AppName);

//        Appname.animate().translationX(-320).setDuration(2000).setStartDelay(5200);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Appname.setVisibility(View.VISIBLE);
                Appname.animate().translationX(-225).setDuration(2000).setStartDelay(0);
            }
        }, 5200);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent;
                SharedPreferences sharedPreferences =PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                if (!sharedPreferences.getBoolean("Registration", false)) {
                    intent=new Intent(SplashScreen.this,Patient_Registration_Page.class);
                }
                else{
                    intent=new Intent(SplashScreen.this,MainActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, 8750);
    }
}
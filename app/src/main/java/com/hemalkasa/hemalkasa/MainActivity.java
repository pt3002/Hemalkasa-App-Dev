package com.hemalkasa.hemalkasa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button addMedicinePage,registrationPage, VideoPage,updatesPage,notesPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registrationPage=findViewById(R.id.registrationPage);
        addMedicinePage=findViewById(R.id.addMedicinePage);
        VideoPage =findViewById(R.id.video);
        updatesPage =findViewById(R.id.updates);
        notesPage =findViewById(R.id.notes);

        registrationPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Patient_Registration_Page.class);
                startActivity(intent);
            }
        });

        addMedicinePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Add_Medicines.class);
                startActivity(intent);
            }
        });

        VideoPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Video_MainScreen.class);
                startActivity(intent);
            }
        });


    }
}
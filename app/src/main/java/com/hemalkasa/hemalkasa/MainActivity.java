package com.hemalkasa.hemalkasa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "pratik";
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

        updatesPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Updates.class);
                startActivity(intent);
            }
        });

        notesPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllPatientDetails();
//                Intent intent=new Intent(MainActivity.this,Notes.class);
//                startActivity(intent);
            }
        });
    }

    public void getAllPatientDetails() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<PatientDetails> patientDetailsList = Database.getInstance(getApplicationContext())
                        .patientDetails_dao()
                        .getAllPatientDetails();

                Log.d(TAG, "run: " + patientDetailsList.toString());
//                Toast.makeText(MainActivity.this, patientDetailsList.toString(), Toast.LENGTH_LONG).show();
            }
        });
        thread.start();
    }
}
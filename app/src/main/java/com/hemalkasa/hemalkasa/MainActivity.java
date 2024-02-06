package com.hemalkasa.hemalkasa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "pratik";
    Button addMedicinePage,registrationPage, VideoPage,historyPage,notesPage;
    TextView welcome;
    private Prescription_Table_ViewModel prescriptionTableViewModel;
    private PatientDetails_ViewModel patientDetailsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registrationPage=findViewById(R.id.registrationPage);
        addMedicinePage=findViewById(R.id.addMedicinePage);
        VideoPage =findViewById(R.id.video);
        historyPage =findViewById(R.id.history);
//        notesPage =findViewById(R.id.notes);
//        welcome =findViewById(R.id.welcome);

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

        historyPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,History.class);
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

        prescriptionTableViewModel=  ViewModelProviders.of(this).get(Prescription_Table_ViewModel.class);
        patientDetailsViewModel=  ViewModelProviders.of(this).get(PatientDetails_ViewModel.class);

//        notesPage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                    // Set the Name
//                getAllPatientDetails();
//                patientDetailsViewModel.getPatientName(welcome);
//            }
//        });
    }

    public void getAllPatientDetails() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<PatientDetails> patientDetailsList = Database.getInstance(getApplicationContext())
                        .patientDetails_dao()
                        .getAllPatientDetails();

                Log.d(TAG, "run: " + patientDetailsList.toString());
            }
        });
        thread.start();
    }
}
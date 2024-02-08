package com.hemalkasa.hemalkasa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "pratik";
    Button addMedicinePage,registrationPage, videoPage,historyPage,riskPage;
    TextView welcome;
    private Prescription_Table_ViewModel prescriptionTableViewModel;
    private PatientDetails_ViewModel patientDetailsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registrationPage=findViewById(R.id.registrationPage);
        addMedicinePage=findViewById(R.id.addMedicinePage);
        videoPage =findViewById(R.id.video);
        historyPage =findViewById(R.id.history);
        riskPage =findViewById(R.id.riskFactors);
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

        videoPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Video_MainScreen.class);
                startActivity(intent);
            }
        });

        riskPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,RiskFactor.class);
                intent.putExtra("Access", true);
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

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.doctor_to_patient, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ChangeToPatient:
                Intent intent = new Intent(MainActivity.this, Patient_Home_Page.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                MainActivity.this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
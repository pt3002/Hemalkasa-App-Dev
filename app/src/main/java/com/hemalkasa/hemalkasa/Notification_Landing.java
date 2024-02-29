package com.hemalkasa.hemalkasa;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Notification_Landing extends AppCompatActivity {

    private TextView visitDate;
    private static final String TAG = "pratik";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_landing);
        visitDate=findViewById(R.id.visitDate);
        Intent intent=getIntent();
        if(intent!=null){
            if(intent.hasExtra("NextVisit")) {
                String visitdate = intent.getStringExtra("NextVisit");
                visitDate.setText(visitdate);
            }
        }
        setNextVisitDate();
    }

    private void setNextVisitDate() {
        Handler summaryHandler =new Handler();

        Runnable runnable=new Runnable() {
            List<Prescription_Table> lastPrescription;
            @Override
            public void run() {
                lastPrescription=Database.getInstance(Notification_Landing.this)
                        .prescriptionTableDao()
                        .getLastPrescription();

                summaryHandler.post(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run() {
                        try{
                            Log.d(TAG, "run: " + lastPrescription.get(0).getNext_visiting_date());
                            visitDate.setText(lastPrescription.get(0).getNext_visiting_date());
                        }catch (Exception e){
                            Log.d(TAG, e.getMessage());
                            Toast.makeText(Notification_Landing.this, "No Data Present", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        };

        Thread getSummaryThread=new Thread(runnable);
        getSummaryThread.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(Notification_Landing.this, Patient_Home_Page.class);
        startActivity(intent);
        finish();
    }
}
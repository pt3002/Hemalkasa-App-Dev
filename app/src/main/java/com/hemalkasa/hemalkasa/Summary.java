package com.hemalkasa.hemalkasa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Summary extends AppCompatActivity {
    RecyclerView medicineRecyclerView;
    MedicineAdapter medicineAdapter;
    private List<Medicine_Table> medicineList = new ArrayList<>();
    private Medicine_Table_ViewModel medicineTableViewModel;
    private static final String TAG = "pratik";
    EditText POGWeeks,POGDays,HB,EDD,VisitDate,NextVisitDate;
    Button Remarks;
    private Random randomId=new Random();
    String DESIGNATION="",NOTES="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summary);

        EDD = findViewById(R.id.EDD);
        POGWeeks = findViewById(R.id.POGWeeks);
        POGDays = findViewById(R.id.POGDays);
        HB = findViewById(R.id.HB);
        VisitDate = findViewById(R.id.VisitDate);
        NextVisitDate = findViewById(R.id.NextVisitDate);
        Remarks = findViewById(R.id.Remarks);
        medicineTableViewModel=  ViewModelProviders.of(this).get(Medicine_Table_ViewModel.class);

        medicineAdapter = new MedicineAdapter(true);
        medicineRecyclerView = findViewById(R.id.RecyclerView);
        medicineRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        medicineRecyclerView.setHasFixedSize(true);
        medicineRecyclerView.setAdapter(medicineAdapter);
        medicineRecyclerView.addItemDecoration(new DividerItemDecoration(medicineRecyclerView.getContext(), DividerItemDecoration.VERTICAL));  //Normal Horizontal Separator

        fetchSummary();

        Remarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!DESIGNATION.isEmpty() && !NOTES.isEmpty()) {
                    Intent notesIntent = new Intent(Summary.this, Updates.class);
                    notesIntent.putExtra("DESIGNATION", DESIGNATION);
                    notesIntent.putExtra("NOTES", NOTES);
                    startActivity(notesIntent);
                }
                else{
                    Toast.makeText(Summary.this, "No Remarks Yet", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void fetchSummary() {
        Handler summaryHandler =new Handler();

        Runnable runnable=new Runnable() {
            List<Prescription_Table> lastPrescription;
            List<PatientDetails> patientDetailsList;
            @Override
            public void run() {
//                lastPrescription=prescriptionTableViewModel.getLastPrescription();
                lastPrescription=Database.getInstance(Summary.this)
                        .prescriptionTableDao()
                        .getLastPrescription();

                patientDetailsList = Database.getInstance(Summary.this)
                        .patientDetails_dao()
                        .getAllPatientDetails();

                Log.d(TAG, "run: "+ lastPrescription.toString());
                summaryHandler.post(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run() {
                        try{
                            EDD.setText("EDD: " + patientDetailsList.get(0).getEdd());
                            VisitDate.setText("Last Visit: " + lastPrescription.get(0).getVisiting_date());
                            POGWeeks.setText("POG Weeks: " + lastPrescription.get(0).getPog_weeks());
                            POGDays.setText("POG Days: " + lastPrescription.get(0).getPog_days());
                            HB.setText("HB: " + lastPrescription.get(0).getHb());
                            NextVisitDate.setText("Next Visit: " + lastPrescription.get(0).getNext_visiting_date());
                            setAdapter(lastPrescription.get(0).getVisiting_date());
                            DESIGNATION=lastPrescription.get(0).getDesignation();
                            NOTES=lastPrescription.get(0).getNotes();
                        }catch (Exception e){
                            Log.d(TAG, e.getMessage());
                            Toast.makeText(Summary.this, "No Data Present", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        };

        Thread getSummaryThread=new Thread(runnable);
        getSummaryThread.start();
    }

    private void setAdapter(String visitingDate) {
        medicineTableViewModel.getMedicineByVisitingDate(visitingDate).observe(this, new Observer<List<Medicine_Table>>() {
            @Override
            public void onChanged(List<Medicine_Table> medicine_tables) {
                medicineAdapter.setMedicines(medicine_tables);
                medicineAdapter.notifyDataSetChanged();
            }
        });
    }

}
package com.hemalkasa.hemalkasa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Summary extends AppCompatActivity {
    MedicineAdapter medicineAdapter;
    private List<Medicine_Table> medicineList = new ArrayList<>();
    private Medicine_Table_ViewModel medicineTableViewModel;
    private static final String TAG = "pratik";
    private Random randomId = new Random();
    private String Designation = "", Notes = "", NextVisitDate = "";
    ConstraintLayout ActivityView;
    // TODO Hardcoded password
    final static String password = "1234";
    private TextView HIVStatus, HBsAgStatus, VDRLStatus, EDDStatus, VisitingDateStatus, PogWeeks, PogDays, HBReading, CheckPrescription, EmptyList;
    private Button NextButton, BackButton;
    private ScrollView SummaryScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summary);

        SummaryScrollView = findViewById(R.id.SummaryScrollView);
        EmptyList = findViewById(R.id.EmptyList);
        HIVStatus = findViewById(R.id.HIVStatus);
        HBsAgStatus = findViewById(R.id.HBsAgStatus);
        VDRLStatus = findViewById(R.id.VDRLStatus);
        EDDStatus = findViewById(R.id.EDDStatus);
        VisitingDateStatus = findViewById(R.id.VisitingDateStatus);
        PogWeeks = findViewById(R.id.PogWeeks);
        PogDays = findViewById(R.id.PogDays);
        HBReading = findViewById(R.id.HBReading);
        CheckPrescription = findViewById(R.id.CheckPrescription);
        NextButton = findViewById(R.id.NextButton);
        BackButton = findViewById(R.id.BackButton);

        medicineTableViewModel = ViewModelProviders.of(this).get(Medicine_Table_ViewModel.class);

        fetchSummary();

        CheckPrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!NextVisitDate.isEmpty()) {
                    Intent intent = new Intent(Summary.this, Add_Medicines.class);
                    intent.putExtra("VISITING_DATE", VisitingDateStatus.getText());
                    intent.putExtra("POG_WEEKS", PogWeeks.getText());
                    intent.putExtra("POG_DAYS", PogDays.getText());
                    intent.putExtra("HB", HBReading.getText());
                    intent.putExtra("NEXT_VISITING_DATE", NextVisitDate);
                    intent.putExtra("DESIGNATION", Designation);
                    intent.putExtra("NOTES", Notes);
                    startActivity(intent);
                } else {
                    Toast.makeText(Summary.this, "No Prescription Added Yet", Toast.LENGTH_SHORT).show();
                }
            }
        });

        NextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Summary.this, RiskFactor.class);
                startActivity(intent);
            }
        });

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
//                Intent intent = new Intent(Summary.this, Trimester.class);
//                startActivity(intent);
            }
        });

//        ActivityView = findViewById(R.id.ActivityView);
//        //noinspection AndroidLintClickableViewAccessibility
//        ActivityView.setOnTouchListener(new OnSwipeTouchListener(this) {
//            @Override
//            public void onSwipeRight() {
//                super.onSwipeRight();
//                Log.d(TAG, "RIGHTTTTT: ");
//                Intent intent = new Intent(Summary.this, Trimester.class);
//                startActivity(intent);
//            }
//
//            @Override
//            public void onSwipeLeft() {
//                super.onSwipeLeft();
//                Log.d(TAG, "LEFTTTTTTT: ");
//                Intent intent = new Intent(Summary.this, RiskFactor.class);
//                startActivity(intent);
//            }
//        });

    }

    private void fetchSummary() {
        Handler summaryHandler = new Handler();

        Runnable runnable = new Runnable() {
            List<Prescription_Table> lastPrescription;
            List<PatientDetails> patientDetailsList;

            @Override
            public void run() {
//                lastPrescription=prescriptionTableViewModel.getLastPrescription();
                lastPrescription = Database.getInstance(Summary.this)
                        .prescriptionTableDao()
                        .getLastPrescription();

                patientDetailsList = Database.getInstance(Summary.this)
                        .patientDetails_dao()
                        .getAllPatientDetails();

                Log.d(TAG, "last Prescription: " + lastPrescription.toString());
                Log.d(TAG, "Detailssss " + patientDetailsList.toString());
                summaryHandler.post(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run() {
                        try {
                            HIVStatus.setText(patientDetailsList.get(0).getHiv());
                            HBsAgStatus.setText(patientDetailsList.get(0).getHsbag());
                            VDRLStatus.setText(patientDetailsList.get(0).getVdrl());
                            EDDStatus.setText(patientDetailsList.get(0).getEdd());
                            VisitingDateStatus.setText(lastPrescription.get(0).getVisiting_date());
                            PogWeeks.setText(lastPrescription.get(0).getPog_weeks());
                            PogDays.setText(lastPrescription.get(0).getPog_days());
                            HBReading.setText(lastPrescription.get(0).getHb());
                            NextVisitDate = lastPrescription.get(0).getNext_visiting_date();
                            Designation = lastPrescription.get(0).getDesignation();
                            Notes = lastPrescription.get(0).getNotes();
                            if (!NextVisitDate.isEmpty()) {
                                SummaryScrollView.setVisibility(View.VISIBLE);
                                EmptyList.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {
                            Log.d(TAG, e.getMessage());
                            Toast.makeText(Summary.this, "No Data Present", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        };

        Thread getSummaryThread = new Thread(runnable);
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


    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.patient_to_doctor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ChangeToDoctor:
                checkpassword();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void checkpassword() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Summary.this);
        builder.setMessage("Access for Lok Biradri Doctors")
                .setTitle("Enter Password")
                .setCancelable(true);

        final View PasswordLayout = getLayoutInflater().inflate(R.layout.doctor_password_layout, null);
        builder.setView(PasswordLayout);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText Password = PasswordLayout.findViewById(R.id.password);
                        if (Password.getText().toString().equals(password)) {
                            dialog.dismiss();
                            Toast.makeText(Summary.this, "Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Summary.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(Summary.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                            Password.setText("");
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }

}
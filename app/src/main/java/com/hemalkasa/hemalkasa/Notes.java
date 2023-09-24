package com.hemalkasa.hemalkasa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class Notes extends AppCompatActivity {

    private static final String TAG = "pratik";
    private Button submit,clear;
    private EditText Notes;
    String fullname,mothername,hospRegNo,dateofbirth,bloodgroup,state,district,block,village,mobno, edd,lmp,pogWeeks,pogDays,gravida,parity,hiv,hbsag,vdrl;
    PatientDetails_ViewModel patientDetails_viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        clear=findViewById(R.id.Clear);
        submit=findViewById(R.id.Submit);
        Notes=findViewById(R.id.NotesText);
        patientDetails_viewModel=  ViewModelProviders.of(this).get(PatientDetails_ViewModel.class);

        Intent intent=getIntent();
        fullname=intent.getStringExtra("fullname");
        mothername=intent.getStringExtra("mothername");
        hospRegNo=intent.getStringExtra("hospRegNo");
        dateofbirth=intent.getStringExtra("dateofbirth");
        bloodgroup=intent.getStringExtra("bloodgroup");
        state=intent.getStringExtra("state");
        district=intent.getStringExtra("district");
        block=intent.getStringExtra("block");
        village=intent.getStringExtra("village");
        mobno=intent.getStringExtra("mobno");
        edd=intent.getStringExtra("edd");
        lmp=intent.getStringExtra("lmp");
        pogWeeks=intent.getStringExtra("pogWeeks");
        pogDays=intent.getStringExtra("pogDays");
        gravida=intent.getStringExtra("gravida");
        parity=intent.getStringExtra("parity");

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Notes.setText("");
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isEmpty()){
                    String notes=Notes.getText().toString();
                    PatientDetails patientDetails=new PatientDetails(1, fullname, mothername, hospRegNo, dateofbirth, bloodgroup, state, district, block, village, mobno, edd, pogWeeks, pogDays, hiv, hbsag, vdrl, gravida, parity, lmp, notes);
                    patientDetails_viewModel.updatePatientDetails(patientDetails);

                    SharedPreferences.Editor sharedPreferencesEditor =PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
                    sharedPreferencesEditor.putBoolean("Registration", true);
                    sharedPreferencesEditor.apply();

                    Intent mainActivity=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(mainActivity);
                    finish();
                }
            }
        });
    }

    private boolean isEmpty() {
        if(Notes.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "Enter Notes", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

}
package com.hemalkasa.hemalkasa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Updates extends AppCompatActivity {

    private static final String TAG = "pratik";
    private Button submit,clear;
    private Spinner desgination;
    private EditText notes;
    String VISITING_DATE,POG_WEEKS,POG_DAYS,EDD,NEXT_VISITING_DATE,DESIGNATION="";
    private Prescription_Table_ViewModel prescriptionTableViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updates);

        clear=findViewById(R.id.Clear);
        submit=findViewById(R.id.Submit);
        desgination=findViewById(R.id.NotesBySpinner);
        notes=findViewById(R.id.NotesText);
        prescriptionTableViewModel=  ViewModelProviders.of(this).get(Prescription_Table_ViewModel.class);

        ArrayAdapter<CharSequence> designationAdapter = ArrayAdapter.createFromResource(this, R.array.designation, android.R.layout.simple_spinner_item);
        designationAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        desgination.setAdapter(designationAdapter);
        desgination.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                desgination.setSelection(i);
                DESIGNATION = desgination.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                DESIGNATION = "";
            }
        });

        Intent intent=getIntent();
        if(intent.hasExtra("DESIGNATION")){
            String designationName=intent.getStringExtra("DESIGNATION");
            int spinnerPosition=designationAdapter.getPosition(designationName);
            desgination.setSelection(spinnerPosition);
            desgination.setEnabled(false);
            notes.setText(intent.getStringExtra("NOTES"));
            notes.setEnabled(false);
            clear.setVisibility(View.GONE);
            submit.setVisibility(View.GONE);
        }
        else {
            try {
                VISITING_DATE = intent.getStringExtra("VISITING_DATE");
                POG_WEEKS = intent.getStringExtra("POG_WEEKS");
                POG_DAYS = intent.getStringExtra("POG_DAYS");
                EDD = intent.getStringExtra("EDD");
                NEXT_VISITING_DATE = intent.getStringExtra("NEXT_VISITING_DATE");
            } catch (Exception e) {
                Log.d(TAG, e.getMessage());
            }

            clear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notes.setText("");
                }
            });

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String NOTES = notes.getText().toString();
                    prescriptionTableViewModel.insertPrescription(new Prescription_Table(VISITING_DATE, POG_WEEKS, POG_DAYS, EDD, NEXT_VISITING_DATE, DESIGNATION, NOTES));
                    Toast.makeText(Updates.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                    Intent mainActivity = new Intent(Updates.this, MainActivity.class);
                    startActivity(mainActivity);
                    finish();
                }
            });
        }
    }
}
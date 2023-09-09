package com.hemalkasa.hemalkasa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class Updates extends AppCompatActivity {

    private Button submit,clear;
    private Spinner desgination;
    private EditText notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updates);

        clear=findViewById(R.id.Clear);
        submit=findViewById(R.id.Submit);
        desgination=findViewById(R.id.NotesBySpinner);
        notes=findViewById(R.id.NotesText);

        ArrayAdapter<CharSequence> testResultAdapter = ArrayAdapter.createFromResource(this, R.array.designation, android.R.layout.simple_spinner_item);
        testResultAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        desgination.setAdapter(testResultAdapter);
        desgination.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                desgination.setSelection(i);
//                hiv = HIVSpinner.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
//                hiv = "";
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notes.setText("");
            }
        });

    }
}
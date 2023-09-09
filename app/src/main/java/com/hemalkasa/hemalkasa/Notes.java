package com.hemalkasa.hemalkasa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class Notes extends AppCompatActivity {

    private Button submit,clear;
    private EditText notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        clear=findViewById(R.id.Clear);
        submit=findViewById(R.id.Submit);
        notes=findViewById(R.id.NotesText);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notes.setText("");
            }
        });
    }
}
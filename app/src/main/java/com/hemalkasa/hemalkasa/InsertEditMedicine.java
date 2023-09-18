package com.hemalkasa.hemalkasa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

public class InsertEditMedicine extends AppCompatActivity {
    public static final String TAG="pratik";
    Button savebtn;
    Spinner nameSpinner,formSpinner,doseSpinner,frequencySpinner,routeSpinner;
    EditText periods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_new_medicine);

        savebtn =findViewById(R.id.saveBtn);
        nameSpinner =findViewById(R.id.nameSpinner);
        formSpinner =findViewById(R.id.formSpinner);
        doseSpinner =findViewById(R.id.doseSpinner);
        frequencySpinner =findViewById(R.id.frequencySpinner);
        routeSpinner =findViewById(R.id.routeSpinner);
        periods =findViewById(R.id.periods);
        final String[] selected = {"Folic Acid 5mg","Tablet","1 tablet","Once a day","Oral"};

        ArrayAdapter<CharSequence> nameAdapter = ArrayAdapter.createFromResource(this, R.array.name, android.R.layout.simple_spinner_item);
        nameAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        nameSpinner.setAdapter(nameAdapter);
        nameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                nameSpinner.setSelection(i);
                selected[0] = nameSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selected[0] = "Folic Acid 5mg";
            }
        });
        ArrayAdapter<CharSequence> formAdapter = ArrayAdapter.createFromResource(this, R.array.form, android.R.layout.simple_spinner_item);
        formAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        formSpinner.setAdapter(formAdapter);
        formSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                formSpinner.setSelection(i);
                selected[1] = formSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selected[1] = "Tablet";
            }
        });
        ArrayAdapter<CharSequence> doseAdapter = ArrayAdapter.createFromResource(this, R.array.dose, android.R.layout.simple_spinner_item);
        doseAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        doseSpinner.setAdapter(doseAdapter);
        doseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                doseSpinner.setSelection(i);
                selected[2] = doseSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selected[2] = "1 tablet";
            }
        });
        ArrayAdapter<CharSequence> frequencyAdapter = ArrayAdapter.createFromResource(this, R.array.frequency, android.R.layout.simple_spinner_item);
        frequencyAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        frequencySpinner.setAdapter(frequencyAdapter);
        frequencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                frequencySpinner.setSelection(i);
                selected[3] = frequencySpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selected[3] = "Once a day";
            }
        });

        ArrayAdapter<CharSequence> routeAdapter = ArrayAdapter.createFromResource(this, R.array.route, android.R.layout.simple_spinner_item);
        routeAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        routeSpinner.setAdapter(routeAdapter);
        routeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                routeSpinner.setSelection(i);
                selected[4] = routeSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selected[4] = "Oral";
            }
        });


        Intent intent=getIntent();
        if(intent.hasExtra("Id")){
            // TODO set the medicine,dose spinner carry forward value
            periods.setText(intent.getStringExtra("Period"));
        }

            // TODO
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                add_medicines.addMedicineArrData(selected[1], selected[0], dayTxt.getText().toString(), timeTxt.getText().toString(), frequencyET.getText().toString());
//                dismiss();
                String Name=selected[0];
                String Form=selected[1];
                String Dose=selected[2];
                String Frequency=selected[3];
                String Route=selected[4];
                String Period=periods.getText().toString();

                Intent data=new Intent();
                data.putExtra("Name", Name);
                data.putExtra("Form", Form);
                data.putExtra("Dose", Dose);
                data.putExtra("Frequency", Frequency);
                data.putExtra("Route", Route);
                data.putExtra("Period", Period);

                int id=getIntent().getIntExtra("Id", -1);
                if(id!=-1){
                    data.putExtra("Id",id);
                }

                setResult(RESULT_OK, data);
                finish();
            }
        });
    }
}
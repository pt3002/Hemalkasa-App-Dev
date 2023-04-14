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
    Spinner medicineSpinner;
    Spinner doseSpinner;
    EditText frequencyET;
    TextView frequencyTxt;
    TextView dayTxt, timeTxt;
    TextView day;
    TextView time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_new_medicine);

        savebtn =findViewById(R.id.saveBtn);
        medicineSpinner = findViewById(R.id.medicineSpinner);
        doseSpinner = findViewById(R.id.doseSpinner);
        dayTxt =findViewById(R.id.showDayTxt);
        timeTxt =findViewById(R.id.showTimeTxt);
        day =findViewById(R.id.dayTxt);
        time =findViewById(R.id.timeTxt);
        frequencyET =findViewById(R.id.frequencyEditTxt);
        frequencyTxt =findViewById(R.id.frequencyTxt);
        final String[] selected = {"1 tablet", "Medicine 1"};

        Intent intent=getIntent();
        if(intent.hasExtra("Id")){
            // TODO set the medicine,dose spinner carry forward value
            frequencyET.setText(intent.getStringExtra("Frequency"));
            dayTxt.setText(intent.getStringExtra("Day"));
            timeTxt.setText(intent.getStringExtra("Time"));
        }

        ArrayAdapter<CharSequence> doseAdapter = ArrayAdapter.createFromResource(this, R.array.dose, android.R.layout.simple_spinner_item);
        doseAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        doseSpinner.setAdapter(doseAdapter);
        doseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                doseSpinner.setSelection(i);
                selected[0] = doseSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selected[0] = "1/2 tablet";
            }
        });
        ArrayAdapter<CharSequence> medicineAdapter = ArrayAdapter.createFromResource(this, R.array.medicine, android.R.layout.simple_spinner_item);
        medicineAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        medicineSpinner.setAdapter(medicineAdapter);
        medicineSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                medicineSpinner.setSelection(i);
                selected[1] = medicineSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selected[1] = "Medicine 1";
            }
        });

            // TODO
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                add_medicines.addMedicineArrData(selected[1], selected[0], dayTxt.getText().toString(), timeTxt.getText().toString(), frequencyET.getText().toString());
//                dismiss();
                String medicine=selected[1];
                String dose=selected[0];
                String day=dayTxt.getText().toString();
                String time=timeTxt.getText().toString();
                String frequency=frequencyET.getText().toString();

//                Log.d(TAG, "Medicine # " + medicine);
//                Log.d(TAG, "Dose # " + dose);
//                Log.d(TAG, "Day # " + day);
//                Log.d(TAG, "Time # " + time);
//                Log.d(TAG, "Frequency # " + frequency);

                Intent data=new Intent();
                data.putExtra("Medicine", medicine);
                data.putExtra("Dose", dose);
                data.putExtra("Day", day);
                data.putExtra("Time", time);
                data.putExtra("Frequency", frequency);

                int id=getIntent().getIntExtra("Id", -1);
                if(id!=-1){
                    data.putExtra("Id",id);
                }

                setResult(RESULT_OK, data);
                finish();
            }
        });

        MaterialDatePicker.Builder<Pair<Long, Long>> materialDateBuilder = MaterialDatePicker.Builder.dateRangePicker();

        MaterialDatePicker materialDatePicker = materialDateBuilder.build();
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                dayTxt.setText(materialDatePicker.getHeaderText());
            }
        });

        day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(InsertEditMedicine.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if(hourOfDay < 10 && minute < 10) {
                            timeTxt.setText("0" + Integer.toString(hourOfDay) + " : 0" + Integer.toString(minute));
                        }
                        else if(hourOfDay < 10 && minute >= 10){
                            timeTxt.setText("0" + Integer.toString(hourOfDay) + " : " + Integer.toString(minute));
                        }
                        else if(hourOfDay >= 10 && minute < 10){
                            timeTxt.setText(Integer.toString(hourOfDay) + " : 0" + Integer.toString(minute));
                        }
                        else{
                            timeTxt.setText(Integer.toString(hourOfDay) + " : " + Integer.toString(minute));

                        }
                    }
                }, 12, 0, true);
                timePickerDialog.show();
            }
        });

    }
}
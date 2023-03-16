package com.hemalkasa.hemalkasa;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

public class AddNewMedicine extends BottomSheetDialogFragment {
    Button savebtn;
    EditText medicineET;
    EditText doseET;
    TextView dayTxt, timeTxt;
    Add_Medicines add_medicines;
    TextView day;
    TextView time;
    public AddNewMedicine(Add_Medicines add_medicines) {
        // Required empty public constructor
        this.add_medicines = add_medicines;
    }

//    public static AddNewPrescription newInstance(String pathString) {
//
//        AddNewPrescription fragment = new AddNewPrescription();
//        Bundle args = new Bundle();
//        args.putString("path", pathString);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_new_medicine, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        savebtn = view.findViewById(R.id.saveBtn);
        medicineET = view.findViewById(R.id.medicineEditTxt);
        doseET = view.findViewById(R.id.doseEditTxt);
        dayTxt = view.findViewById(R.id.showDayTxt);
        timeTxt = view.findViewById(R.id.showTimeTxt);
        day = view.findViewById(R.id.dayTxt);
        time = view.findViewById(R.id.timeTxt);
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_medicines.addMedicineArrData(medicineET.getText().toString(), doseET.getText().toString(), dayTxt.getText().toString(), timeTxt.getText().toString());
                dismiss();
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
                materialDatePicker.show(add_medicines.getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog((Context) add_medicines, new TimePickerDialog.OnTimeSetListener() {
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
package com.hemalkasa.hemalkasa;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class EditMedicine extends BottomSheetDialogFragment {
    String medicineVal;
    String doseVal;
    String dayVal;
    String timeVal;
    EditText medET, doseET;
    TextView dayTxt, timeTxt;
    Button saveBtn;
    Add_Medicines add_medicines;
    int position;
    TextView day;
    TextView time;
    public EditMedicine(Add_Medicines add_medicines, int position) {
        this.add_medicines = add_medicines;
        this.position = position;
    }
    public static EditMedicine newInstance(String m, String dose, String day, String time, Add_Medicines add_medicines, int position){
        EditMedicine frag = new EditMedicine(add_medicines, position);
        Bundle args = new Bundle();
        args.putString("medicine", m);
        args.putString("dose", dose);
        args.putString("day", day);
        args.putString("time", time);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            this.medicineVal = getArguments().getString("medicine");
            this.doseVal = getArguments().getString("dose");
            this.dayVal = getArguments().getString("day");
            this.timeVal = getArguments().getString("time");
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_new_medicine, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        medET = view.findViewById(R.id.medicineEditTxt);
        doseET = view.findViewById(R.id.doseEditTxt);
        dayTxt = view.findViewById(R.id.showDayTxt);
        timeTxt = view.findViewById(R.id.showTimeTxt);
        saveBtn = view.findViewById(R.id.saveBtn);
        day = view.findViewById(R.id.dayTxt);
        time = view.findViewById(R.id.timeTxt);
        medET.setText(this.medicineVal);
        doseET.setText(this.doseVal);
        dayTxt.setText(this.dayVal);
        timeTxt.setText(this.timeVal);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Map<String, Object> map = new HashMap<>();
//                map.put("medicine", medET.getText().toString());
//                map.put("dose", doseET.getText().toString());
//                map.put("day", dayTxt.getText().toString());
//                map.put("time", timeTxt.getText().toString());
//                FirebaseDatabase.getInstance().getReference().child("entries").child(path).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        dismiss();
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        dismiss();
//                    }
//                });
                add_medicines.editMedicineArrData(medET.getText().toString(), doseET.getText().toString(), dayTxt.getText().toString(), timeTxt.getText().toString(), position);
                dismiss();

            }
        });
        day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog((Context) add_medicines, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dayTxt.setText(Integer.toString(dayOfMonth) + " - " + Integer.toString(month + 1) + " - " + Integer.toString(year));
                    }
                }, 2023, 0, 1);
                datePickerDialog.show();
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

package com.example.prescriptionrecyclerapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class editPrescription extends BottomSheetDialogFragment {
    String medicineVal;
    String doseVal;
    String dayVal;
    String timeVal;
    EditText medET, doseET;
    TextView dayTxt, timeTxt;
    Button saveBtn;
    String path;
    MainActivity mainActivity;
    int position;
    public editPrescription(MainActivity mainActivity, int position) {
        this.mainActivity = mainActivity;
        this.position = position;
    }
    public static editPrescription newInstance(String m, String dose, String day, String time, String pathString, MainActivity mainActivity, int position){
        editPrescription frag = new editPrescription(mainActivity, position);
        Bundle args = new Bundle();
        args.putString("medicine", m);
        args.putString("dose", dose);
        args.putString("day", day);
        args.putString("time", time);
        args.putString("path", pathString);
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
            this.path = getArguments().getString("path");
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_new_prescription, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        medET = view.findViewById(R.id.medicineEditTxt);
        doseET = view.findViewById(R.id.doseEditTxt);
        dayTxt = view.findViewById(R.id.showDayTxt);
        timeTxt = view.findViewById(R.id.showTimeTxt);
        saveBtn = view.findViewById(R.id.saveBtn);

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
                mainActivity.editArrData(medET.getText().toString(), doseET.getText().toString(), dayTxt.getText().toString(), timeTxt.getText().toString(), position);
                dismiss();

            }
        });
    }
}

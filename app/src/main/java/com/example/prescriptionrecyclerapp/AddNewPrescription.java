package com.example.prescriptionrecyclerapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class AddNewPrescription extends BottomSheetDialogFragment {
    Button savebtn;
    EditText medicineET;
    EditText doseET;
    TextView dayTxt, timeTxt;
    String path;
    MainActivity mainActivity;
    public AddNewPrescription(MainActivity mainActivity) {
        // Required empty public constructor
        this.mainActivity = mainActivity;
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
        if (getArguments() != null) {
            path = getArguments().getString("path");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_new_prescription, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        savebtn = view.findViewById(R.id.saveBtn);
        medicineET = view.findViewById(R.id.medicineEditTxt);
        doseET = view.findViewById(R.id.doseEditTxt);
        dayTxt = view.findViewById(R.id.showDayTxt);
        timeTxt = view.findViewById(R.id.showTimeTxt);
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Map<String, Object> map = new HashMap<>();
//                map.put("medicine", medicineET.getText().toString());
//                map.put("dose", doseET.getText().toString());
//                map.put("day", dayTxt.getText().toString());
//                map.put("time", timeTxt.getText().toString());
                mainActivity.AddArrData(medicineET.getText().toString(), doseET.getText().toString(), dayTxt.getText().toString(), timeTxt.getText().toString());
                dismiss();
//                FirebaseDatabase.getInstance().getReference().child("entries").push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        Toast.makeText(getContext(), "Prescription added succesfully", Toast.LENGTH_SHORT).show();
//                        dismiss();
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(getContext(), "Prescription not added!!", Toast.LENGTH_SHORT).show();
//                        dismiss();
//                    }
//                });
            }
        });

    }
}
package com.hemalkasa.hemalkasa;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.LiveData;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.util.List;

public class Patient_Registration_Page2 extends Fragment {

    private static final String TAG = "pratik";
    View view;
    Button Back,Submit;
    TextView EDD,LMP;
    EditText POGWeeks,POGDays,Gravida,Parity;
    Spinner HIVSpinner,HBSAGSpinner, VDRLSpinner;
    String edd,lmp,pogWeeks,pogDays,gravida,parity,hiv,hbsag,vdrl;

    public Patient_Registration_Page2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.patient__registration__page2, container, false);

        Back=(Button) view.findViewById(R.id.Back);
        Submit=(Button) view.findViewById(R.id.Submit);
        EDD=(TextView) view.findViewById(R.id.EDD);
        LMP=(TextView) view.findViewById(R.id.LMP);
        POGWeeks=(EditText) view.findViewById(R.id.POGWeeks);
        POGDays=(EditText) view.findViewById(R.id.POGDays);
        Gravida=(EditText) view.findViewById(R.id.Gravida);
        Parity=(EditText) view.findViewById(R.id.Parity);
        HIVSpinner=(Spinner) view.findViewById(R.id.HIVSpinner);
        HBSAGSpinner=(Spinner) view.findViewById(R.id.HBSAGSpinner);
        VDRLSpinner=(Spinner) view.findViewById(R.id.VDRLSpinner);

        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("Select Date");

        MaterialDatePicker eddDatePicker = materialDateBuilder.build();
        eddDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                EDD.setText(eddDatePicker.getHeaderText());
            }
        });
        EDD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eddDatePicker.show(getParentFragmentManager(), "EDD_DATE_PICKER");
            }
        });

        MaterialDatePicker lmpDatePicker = materialDateBuilder.build();
        lmpDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                LMP.setText(lmpDatePicker.getHeaderText());
            }
        });
        LMP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lmpDatePicker.show(getParentFragmentManager(), "LMP_DATE_PICKER");
            }
        });

        ArrayAdapter<CharSequence> testResultAdapter = ArrayAdapter.createFromResource(getContext(), R.array.testResult, android.R.layout.simple_spinner_item);
        testResultAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        HIVSpinner.setAdapter(testResultAdapter);
        HIVSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                HIVSpinner.setSelection(i);
                hiv = HIVSpinner.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                hiv = "";
            }
        });

        HBSAGSpinner.setAdapter(testResultAdapter);
        HBSAGSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                HBSAGSpinner.setSelection(i);
                hbsag = HBSAGSpinner.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                hbsag = "";
            }
        });

        VDRLSpinner.setAdapter(testResultAdapter);
        VDRLSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                VDRLSpinner.setSelection(i);
                vdrl = VDRLSpinner.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                vdrl = "";
            }
        });



        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
//                getAllPatientDetails();
            }
        });

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "onClick: Started");
                if(isEmpty()) {
                    edd=EDD.getText().toString();
                    lmp=LMP.getText().toString();
                    pogWeeks=POGWeeks.getText().toString();
                    pogDays=POGDays.getText().toString();
                    gravida=Gravida.getText().toString();
                    parity=Parity.getText().toString();
//                    PatientDetails patientDetails = new PatientDetails(FullName, MotherName,MobNo, HospRegNo, bloodgrp, dob, state, distict, block, village);
//                    InsertAsyncTask insertAsyncTask = new InsertAsyncTask();
//                    insertAsyncTask.execute(patientDetails);

//                    Toast.makeText(getContext(), patientDetails.toString(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onClick: Ended");
                }
            }
        });

        getParentFragmentManager().setFragmentResultListener("Page1", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
//                FullName=result.getString("FullName");
//                MotherName=result.getString("MotherName");
            }
        });
        return view;
    }

    private boolean isEmpty() {
        if(EDD.getText().toString().trim().isEmpty()){
            Toast.makeText(getContext(), "Enter EDD", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(POGWeeks.getText().toString().trim().isEmpty()){
            Toast.makeText(getContext(), "Enter POG Weeks", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(POGDays.getText().toString().trim().isEmpty()){
            Toast.makeText(getContext(), "Enter POG Days", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(hiv.isEmpty()){
            Toast.makeText(getContext(), "Select HIV", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(hbsag.isEmpty()){
            Toast.makeText(getContext(), "Select HsbAg", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(vdrl.isEmpty()){
            Toast.makeText(getContext(), "Select VDRL", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(Gravida.getText().toString().trim().isEmpty()){
            Toast.makeText(getContext(), "Enter Gravida", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(Parity.getText().toString().trim().isEmpty()){
            Toast.makeText(getContext(), "Enter Parity", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(LMP.getText().toString().trim().isEmpty()){
            Toast.makeText(getContext(), "Enter LMP", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    class InsertAsyncTask extends AsyncTask<PatientDetails, Void, Void> {

        @Override
        protected Void doInBackground(PatientDetails... patientDetails) {

            Database.getInstance(getContext())
                    .patientDetails_dao()
                    .insertPatientDetails(patientDetails[0]);

            Log.d(TAG, "doInBackground: Successful");
            return null;
        }
    }

}
package com.hemalkasa.hemalkasa;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class Patient_Registration_Page2 extends Fragment {

    private static final String TAG = "pratik";
    View view;
    Button Back,Submit;
    EditText DOB,BloodGroup,State,District,Block,Village;
    String FullName,MotherName,MobNo,HospRegNo;

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
        DOB=(EditText) view.findViewById(R.id.DOB);
        BloodGroup=(EditText) view.findViewById(R.id.BloodGroup);
        State=(EditText) view.findViewById(R.id.State);
        District=(EditText) view.findViewById(R.id.District);
        Block=(EditText) view.findViewById(R.id.Block);
        Village=(EditText) view.findViewById(R.id.Village);

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
                    String dob=DOB.getText().toString();
                    String bloodgrp=BloodGroup.getText().toString();
                    String state=State.getText().toString();
                    String distict=District.getText().toString();
                    String block=Block.getText().toString();
                    String village=Village.getText().toString();
                    PatientDetails patientDetails = new PatientDetails(FullName, MotherName,MobNo, HospRegNo, bloodgrp, dob, state, distict, block, village);
                    InsertAsyncTask insertAsyncTask = new InsertAsyncTask();
                    insertAsyncTask.execute(patientDetails);

                Log.d(TAG, "onClick: Ended");
                }
            }
        });

        getParentFragmentManager().setFragmentResultListener("Page1", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                FullName=result.getString("FullName");
                MotherName=result.getString("MotherName");
                MobNo=result.getString("MobNo");
                HospRegNo=result.getString("HospRegNo");

                Log.d(TAG, HospRegNo + MobNo + MotherName + FullName);
            }
        });



        return view;
    }

    private boolean isEmpty() {
        if(DOB.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Enter Birth Date", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(BloodGroup.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Enter Blood Group", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(State.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Enter State", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(District.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Enter District", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(Block.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Enter Block", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(Village.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Enter Village", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void getAllPatientDetails() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<PatientDetails> patientDetailsList = Database.getInstance(getContext())
                        .patientDetails_dao()
                        .getAllPatientDetails();

                Log.d(TAG, "run: " + patientDetailsList.toString());
            }
        });
        thread.start();
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
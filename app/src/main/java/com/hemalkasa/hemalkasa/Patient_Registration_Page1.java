package com.hemalkasa.hemalkasa;

import android.app.Activity;
import static android.app.Activity.RESULT_OK;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class Patient_Registration_Page1 extends Fragment {

    View view;
    ImageView proImg;
    FloatingActionButton fab;
    Button nextpage,showPatients;
    EditText FullName,MotherName,HospRegNo,BloodGroup;
    int SEC = 1;
    String TAG="Pratik";

    public Patient_Registration_Page1() {
        // Required empty public constructor
    }

    ActivityResultLauncher<Intent> launcher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode()==RESULT_OK && result.getData()!=null){
                    Uri uri = result.getData().getData();
                    Glide.with(getContext()).load(uri).circleCrop()
                            .placeholder(R.drawable.hospital_icon).error(R.drawable.hospital_icon).into(proImg);
                }
            });


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.patient__registration__page1, container, false);
        proImg = (ImageView) view.findViewById(R.id.profile_image);
        fab = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);
        nextpage=(Button) view.findViewById(R.id.next);
        showPatients=(Button) view.findViewById(R.id.show);
        FullName=(EditText) view.findViewById(R.id.fullname);
        MotherName=(EditText) view.findViewById(R.id.MotherName);
        HospRegNo=(EditText) view.findViewById(R.id.HospRegNo);
        BloodGroup=(EditText) view.findViewById(R.id.BloodGroup);


        fab.setOnClickListener(view ->{
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            launcher.launch(intent);
        });

        nextpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fullname=FullName.getText().toString();
                String mothername=MotherName.getText().toString();
                String hospiregno=HospRegNo.getText().toString();
                String bloodgrp=BloodGroup.getText().toString();

                Log.d(TAG, "onClick: Started");

                PatientDetails patientDetails = new PatientDetails(fullname,mothername,hospiregno,bloodgrp);
                InsertAsyncTask insertAsyncTask = new InsertAsyncTask();
                insertAsyncTask.execute(patientDetails);

                Log.d(TAG, "onClick: Ended");
//                Patient_Registration_Page2 patient_registration_page2=new Patient_Registration_Page2();
//                FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.FragContainer, patient_registration_page2).addToBackStack("patientRegistration").commit();
            }
        });

        showPatients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllPatientDetails();
            }
        });


        return view;
    }

    public void getAllPatientDetails() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<PatientDetails> patientDetailsList = PatientDetails_Database.getInstance(getContext())
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

            PatientDetails_Database.getInstance(getContext())
                    .patientDetails_dao()
                    .insertPatientDetails(patientDetails[0]);

            Log.d(TAG, "doInBackground: Successful");
            return null;
        }
    }

}
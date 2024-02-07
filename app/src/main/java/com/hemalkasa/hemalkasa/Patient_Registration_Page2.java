package com.hemalkasa.hemalkasa;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import android.os.Handler;
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
    Button Back, Submit;
    TextView EDD, LMP;
    EditText POGWeeks, POGDays, Gravida, Parity;
    Spinner HIVSpinner, HBSAGSpinner, VDRLSpinner;
    String fullname, mothername, hospRegNo, dateofbirth, bloodgroup, state, district, block, village, mobno;
    String edd, lmp, pogWeeks, pogDays, gravida, parity, hiv, hbsag, vdrl;
    String DefaultHIV, DefaultHBSAG, DefaultVDRL;
    PatientDetails_ViewModel patientDetails_viewModel;

    public Patient_Registration_Page2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.patient__registration__page2, container, false);

        Back = (Button) view.findViewById(R.id.Back);
        Submit = (Button) view.findViewById(R.id.Submit);
        EDD = (TextView) view.findViewById(R.id.EDD);
        LMP = (TextView) view.findViewById(R.id.LMP);
        POGWeeks = (EditText) view.findViewById(R.id.POGWeeks);
        POGDays = (EditText) view.findViewById(R.id.POGDays);
        Gravida = (EditText) view.findViewById(R.id.Gravida);
        Parity = (EditText) view.findViewById(R.id.Parity);
        HIVSpinner = (Spinner) view.findViewById(R.id.HIVSpinner);
        HBSAGSpinner = (Spinner) view.findViewById(R.id.HBSAGSpinner);
        VDRLSpinner = (Spinner) view.findViewById(R.id.VDRLSpinner);

        patientDetails_viewModel = ViewModelProviders.of(this).get(PatientDetails_ViewModel.class);

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
        DefaultHIV = getResources().getStringArray(R.array.testResult)[0];
        int HIVSpinnerPosition = testResultAdapter.getPosition(DefaultHIV);
        HIVSpinner.setSelection(HIVSpinnerPosition);
        Log.d(TAG, "HIV00000000: " + DefaultHIV + String.valueOf(HIVSpinnerPosition));
        HIVSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                HIVSpinner.setSelection(i);
                Log.d(TAG, "HIV1111111111111: " + DefaultHIV + String.valueOf(HIVSpinnerPosition));
//                hiv = HIVSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                int HIVSpinnerPosition = testResultAdapter.getPosition(DefaultHIV);
                HIVSpinner.setSelection(HIVSpinnerPosition);
                Log.d(TAG, "HIV22222222222222222: " + DefaultHIV + String.valueOf(HIVSpinnerPosition));
            }
        });

        HBSAGSpinner.setAdapter(testResultAdapter);
        DefaultHBSAG = getResources().getStringArray(R.array.testResult)[0];
        int HBSAGSpinnerPosition = testResultAdapter.getPosition(DefaultHBSAG);
        HBSAGSpinner.setSelection(HBSAGSpinnerPosition);
        HBSAGSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                HBSAGSpinner.setSelection(i);
//                hbsag = HBSAGSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                int HBSAGSpinnerPosition = testResultAdapter.getPosition(DefaultHBSAG);
                HBSAGSpinner.setSelection(HBSAGSpinnerPosition);
            }
        });

        VDRLSpinner.setAdapter(testResultAdapter);
        DefaultVDRL = getResources().getStringArray(R.array.testResult)[0];
        int VRDLSpinnerPosition = testResultAdapter.getPosition(DefaultVDRL);
        VDRLSpinner.setSelection(VRDLSpinnerPosition);
        VDRLSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                VDRLSpinner.setSelection(i);
//                vdrl = VDRLSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                int VRDLSpinnerPosition = testResultAdapter.getPosition(DefaultVDRL);
                VDRLSpinner.setSelection(VRDLSpinnerPosition);
            }
        });


        getAllPatientDetails();

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

                if (!isEmpty()) {
                    edd = EDD.getText().toString();
                    lmp = LMP.getText().toString();
                    pogWeeks = POGWeeks.getText().toString();
                    pogDays = POGDays.getText().toString();
                    gravida = Gravida.getText().toString();
                    parity = Parity.getText().toString();

                    hiv = HIVSpinner.getSelectedItem().toString();
                    hbsag = HBSAGSpinner.getSelectedItem().toString();
                    vdrl = VDRLSpinner.getSelectedItem().toString();

                    Intent intent = new Intent(getContext(), Notes.class);
                    intent.putExtra("fullname", fullname);
                    intent.putExtra("mothername", mothername);
                    intent.putExtra("hospRegNo", hospRegNo);
                    intent.putExtra("dateofbirth", dateofbirth);
                    intent.putExtra("bloodgroup", bloodgroup);
                    intent.putExtra("state", state);
                    intent.putExtra("district", district);
                    intent.putExtra("block", block);
                    intent.putExtra("village", village);
                    intent.putExtra("mobno", mobno);
                    intent.putExtra("edd", edd);
                    intent.putExtra("lmp", lmp);
                    intent.putExtra("pogWeeks", pogWeeks);
                    intent.putExtra("pogDays", pogDays);
                    intent.putExtra("hiv", hiv);
                    intent.putExtra("hbsag", hbsag);
                    intent.putExtra("vdrl", vdrl);
                    intent.putExtra("gravida", gravida);
                    intent.putExtra("parity", parity);
                    startActivity(intent);
                }
            }
        });

        getParentFragmentManager().setFragmentResultListener("Page1", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                fullname = result.getString("fullname");
                mothername = result.getString("mothername");
                hospRegNo = result.getString("hospRegNo");
                dateofbirth = result.getString("dateofbirth");
                bloodgroup = result.getString("bloodgroup");
                state = result.getString("state");
                district = result.getString("district");
                block = result.getString("block");
                village = result.getString("village");
                mobno = result.getString("mobno");
            }
        });
        return view;
    }

    private boolean isEmpty() {
        if (EDD.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext(), "Enter EDD", Toast.LENGTH_SHORT).show();
            return true;
        } else if (POGWeeks.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext(), "Enter POG Weeks", Toast.LENGTH_SHORT).show();
            return true;
        } else if (POGDays.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext(), "Enter POG Days", Toast.LENGTH_SHORT).show();
            return true;
        }
//        else if(hiv.isEmpty()){
//            Toast.makeText(getContext(), "Select HIV", Toast.LENGTH_SHORT).show();
//            return true;
//        }
//        else if(hbsag.isEmpty()){
//            Toast.makeText(getContext(), "Select HsbAg", Toast.LENGTH_SHORT).show();
//            return true;
//        }
//        else if(vdrl.isEmpty()){
//            Toast.makeText(getContext(), "Select VDRL", Toast.LENGTH_SHORT).show();
//            return true;
//        }
        else if (Gravida.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext(), "Enter Gravida", Toast.LENGTH_SHORT).show();
            return true;
        } else if (Parity.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext(), "Enter Parity", Toast.LENGTH_SHORT).show();
            return true;
        } else if (LMP.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext(), "Enter LMP", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    public void getAllPatientDetails() {
        Handler getPatientDetailsHandler = new Handler();

        Runnable runnable = new Runnable() {
            List<PatientDetails> patientDetailsList;

            @Override
            public void run() {
                patientDetailsList = Database.getInstance(getContext())
                        .patientDetails_dao()
                        .getAllPatientDetails();

                Log.d(TAG, "run: " + patientDetailsList.toString());
                getPatientDetailsHandler.post(new Runnable() {
                    @Override
                    public void run() {
//                        Toast.makeText(getContext(), patientDetailsList.get(0).getFull_name(), Toast.LENGTH_SHORT).show();
                        try {
                            EDD.setText(patientDetailsList.get(0).getEdd());
                            POGWeeks.setText(patientDetailsList.get(0).getPog_weeks());
                            POGDays.setText(patientDetailsList.get(0).getPog_days());
                            Gravida.setText(patientDetailsList.get(0).getGravida());
                            Parity.setText(patientDetailsList.get(0).getParity());
                            LMP.setText(patientDetailsList.get(0).getLmp());

                            ArrayAdapter<CharSequence> testResultAdapter = ArrayAdapter.createFromResource(getContext(), R.array.testResult, android.R.layout.simple_spinner_item);
                            DefaultHIV = patientDetailsList.get(0).getHiv();
                            int HIVSpinnerPosition = testResultAdapter.getPosition(DefaultHIV);
                            HIVSpinner.setSelection(HIVSpinnerPosition);
                            Log.d(TAG, "HIV33333333333: " + DefaultHIV + String.valueOf(HIVSpinnerPosition));


                            DefaultHBSAG = patientDetailsList.get(0).getHsbag();
                            int HBSAGSpinnerPosition = testResultAdapter.getPosition(DefaultHBSAG);
                            HBSAGSpinner.setSelection(HBSAGSpinnerPosition);

                            DefaultVDRL = patientDetailsList.get(0).getVdrl();
                            int VDRLSpinnerPosition = testResultAdapter.getPosition(DefaultVDRL);
                            VDRLSpinner.setSelection(VDRLSpinnerPosition);
                        } catch (Exception e) {
                            Log.d(TAG, e.getMessage());
                            Toast.makeText(getContext(), "Cannot Fetch Details", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        };
        Thread getPatientDetailsThread = new Thread(runnable);
        getPatientDetailsThread.start();
    }

}
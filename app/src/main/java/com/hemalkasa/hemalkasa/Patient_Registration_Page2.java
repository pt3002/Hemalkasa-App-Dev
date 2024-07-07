package com.hemalkasa.hemalkasa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import android.os.Handler;
import android.preference.PreferenceManager;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class Patient_Registration_Page2 extends Fragment {

    private static final String TAG = "pratik";
    View view;
    Button Back, Submit;
    TextView EDD, LMP;
    EditText POGWeeks, POGDays, Gravida, Parity;
    Spinner HIVSpinner, HBSAGSpinner, VDRLSpinner;
    String fullname, mothername, hospRegNo, dateofbirth, bloodgroup, trimester, state, district, block, village, mobno, ashaworker;
    String edd = "EDD", lmp, pogWeeks = "0", pogDays = "0", gravida, parity, hiv, hbsag, vdrl;
    String DefaultHIV, DefaultHBSAG, DefaultVDRL;
    PatientDetails_ViewModel patientDetails_viewModel;
    SimpleDateFormat format;

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
        format = new SimpleDateFormat("dd-MMM-yyyy");


        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("Select Date");

//        MaterialDatePicker eddDatePicker = materialDateBuilder.build();
//        eddDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
//            @Override
//            public void onPositiveButtonClick(Object selection) {
//                EDD.setText(eddDatePicker.getHeaderText());
//            }
//        });
        MaterialDatePicker<Long> eddDatePicker = materialDateBuilder.build();
        eddDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                calendar.setTimeInMillis(selection);
                String formattedDate = format.format(calendar.getTime());
                EDD.setText(formattedDate);
            }
        });
        EDD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eddDatePicker.show(getParentFragmentManager(), "EDD_DATE_PICKER");
            }
        });

        MaterialDatePicker<Long> lmpDatePicker = materialDateBuilder.build();
        lmpDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                calendar.setTimeInMillis(selection);
                String formattedDate = format.format(calendar.getTime());
                LMP.setText(formattedDate);
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
        HIVSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                HIVSpinner.setSelection(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                int HIVSpinnerPosition = testResultAdapter.getPosition(DefaultHIV);
                HIVSpinner.setSelection(HIVSpinnerPosition);
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
                if (!isEmpty() && validateFields()) {
                    edd = EDD.getText().toString();
                    lmp = LMP.getText().toString();
                    pogWeeks = POGWeeks.getText().toString();
                    pogDays = POGDays.getText().toString();
                    gravida = Gravida.getText().toString();
                    parity = Parity.getText().toString();

                    hiv = HIVSpinner.getSelectedItem().toString();
                    hbsag = HBSAGSpinner.getSelectedItem().toString();
                    vdrl = VDRLSpinner.getSelectedItem().toString();

                    PatientDetails patientDetails = new PatientDetails(1, fullname, mothername, hospRegNo, dateofbirth, bloodgroup, trimester, state, district, block, village, mobno, ashaworker, edd, pogWeeks, pogDays, hiv, hbsag, vdrl, gravida, parity, lmp);
//                    patientDetails_viewModel.updatePatientDetails(patientDetails);
                    patientDetails_viewModel.insertPatientDetails(patientDetails);

                    SharedPreferences.Editor sharedPreferencesEditor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
                    sharedPreferencesEditor.putBoolean("Registration", true);
                    sharedPreferencesEditor.apply();

                    Intent mainActivity = new Intent(getContext(), MainActivity.class);
//                    mainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(mainActivity);
                    getActivity().finish();

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
                trimester = result.getString("trimester");
                state = result.getString("state");
                district = result.getString("district");
                block = result.getString("block");
                village = result.getString("village");
                mobno = result.getString("mobno");
                ashaworker = result.getString("ashaworker");
            }
        });
        return view;
    }

    private boolean validateFields() {
        pogDays = POGDays.getText().toString().trim();
        Integer days;
        try {
            days = Integer.parseInt(pogDays);
        } catch (NumberFormatException nfe) {
            Toast.makeText(getContext(), "Enter Numerical Value in POG DAYS", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(days>6 || days <0){
            Toast.makeText(getContext(), "POG DAY range should be between 0 to 6", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
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
        else if(Integer.parseInt(POGDays.getText().toString().trim()) > 6){
            Toast.makeText(getContext(), "POG Days should be less than 7", Toast.LENGTH_SHORT).show();
            return true;
        }
//        else if(hiv.isEmpty()){
//            Toast.makeText(getContext(), "Select HIV", Toast.LENGTH_SHORT).show();
//            return true;
//        }
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

                getPatientDetailsHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // If EDD field is changed from DB that means new entry in DB is added. So We need to set the EditText
                            if (!patientDetailsList.get(0).getEdd().equals("EDD")) {
                                EDD.setText(patientDetailsList.get(0).getEdd());
                            }
                            if (!patientDetailsList.get(0).getPog_weeks().equals("0")) {
                                POGWeeks.setText(patientDetailsList.get(0).getPog_weeks());
                            }
                            if (!patientDetailsList.get(0).getPog_days().equals("0")) {
                                POGDays.setText(patientDetailsList.get(0).getPog_days());
                            }
                            Gravida.setText(patientDetailsList.get(0).getGravida());
                            Parity.setText(patientDetailsList.get(0).getParity());
                            LMP.setText(patientDetailsList.get(0).getLmp());
                            ArrayAdapter<CharSequence> testResultAdapter = ArrayAdapter.createFromResource(getContext(), R.array.testResult, android.R.layout.simple_spinner_item);
                            DefaultHIV = patientDetailsList.get(0).getHiv();
                            int HIVSpinnerPosition = testResultAdapter.getPosition(DefaultHIV);
                            HIVSpinner.setSelection(HIVSpinnerPosition);

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
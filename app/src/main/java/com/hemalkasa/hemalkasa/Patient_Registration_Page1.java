package com.hemalkasa.hemalkasa;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class Patient_Registration_Page1 extends Fragment {

    View view;
    ImageView proImg;
    FloatingActionButton fab;
    Button nextpage, showPatients;
    EditText HospRegNo, FullName, MotherName, State, District, Block, Village, MobNo, AshaWorker;
    TextView DateOfBirth, BloodGroup, Trimester;
    String DefaultBloodGroup;
    String DefaultTrimester;
    PatientDetails_ViewModel patientDetails_viewModel;
    Spinner BloodGroupSpinner;
    Spinner TrimesterSpinner;
    SimpleDateFormat format;
    int SEC = 1;
    String TAG = "Pratik";

    public Patient_Registration_Page1() {
        // Required empty public constructor
    }

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri uri = result.getData().getData();
                    Glide.with(getContext()).load(uri).circleCrop()
                            .placeholder(R.drawable.hospital_icon).error(R.drawable.hospital_icon).into(proImg);
                }
            });


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.patient__registration__page1, container, false);
        proImg = (ImageView) view.findViewById(R.id.profile_image);
        // fab = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);
        nextpage = (Button) view.findViewById(R.id.next);
        HospRegNo = (EditText) view.findViewById(R.id.HospRegNo);
        FullName = (EditText) view.findViewById(R.id.fullname);
        MotherName = (EditText) view.findViewById(R.id.MotherName);
        BloodGroup = (TextView) view.findViewById(R.id.BloodGroup);
        BloodGroupSpinner = (Spinner) view.findViewById(R.id.BloodGroupSpinner);
        Trimester = (TextView) view.findViewById(R.id.Trimester);
        TrimesterSpinner = (Spinner) view.findViewById(R.id.TrimesterSpinner);
        State = (EditText) view.findViewById(R.id.State);
        District = (EditText) view.findViewById(R.id.District);
        Block = (EditText) view.findViewById(R.id.Block);
        Village = (EditText) view.findViewById(R.id.Village);
        MobNo = (EditText) view.findViewById(R.id.MobNo);
        AshaWorker = (EditText) view.findViewById(R.id.AshaWorker);
        DateOfBirth = (TextView) view.findViewById(R.id.DateOfBirth);

        patientDetails_viewModel = ViewModelProviders.of(this).get(PatientDetails_ViewModel.class);
        format = new SimpleDateFormat("dd-MMM-yyyy");

        DefaultBloodGroup = getResources().getStringArray(R.array.bloodGroup)[0]; // Initilize as 1st default Blood Group
        DefaultTrimester = getResources().getStringArray(R.array.trimester)[0];

        ArrayAdapter<CharSequence> bloodGroupAdapter = ArrayAdapter.createFromResource(getContext(), R.array.bloodGroup, android.R.layout.simple_spinner_item);
        bloodGroupAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        BloodGroupSpinner.setAdapter(bloodGroupAdapter);
        int spinnerPosition = bloodGroupAdapter.getPosition(DefaultBloodGroup);
        Log.d(TAG, "onNothingSelected: " + DefaultBloodGroup + "    : " + String.valueOf(spinnerPosition));
        BloodGroupSpinner.setSelection(spinnerPosition);
        BloodGroupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                BloodGroupSpinner.setSelection(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                int spinnerPosition = bloodGroupAdapter.getPosition(DefaultBloodGroup);
//                Log.d(TAG, "onNothingSelected: " + DefaultBloodGroup + String.valueOf(spinnerPosition));
                BloodGroupSpinner.setSelection(spinnerPosition);
            }
        });

        ArrayAdapter<CharSequence> trimesterAdapter = ArrayAdapter.createFromResource(getContext(), R.array.trimester, android.R.layout.simple_spinner_item);
        trimesterAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        TrimesterSpinner.setAdapter(trimesterAdapter);
        int trimesterSpinnerPosition = trimesterAdapter.getPosition(DefaultTrimester);
        TrimesterSpinner.setSelection(trimesterSpinnerPosition);
        TrimesterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TrimesterSpinner.setSelection(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                int trimesterSpinnerPosition = trimesterAdapter.getPosition(DefaultTrimester);
                TrimesterSpinner.setSelection(trimesterSpinnerPosition);
            }
        });


        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("Select Date of Birth");
        MaterialDatePicker<Long> materialDatePicker = materialDateBuilder.build();
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                calendar.setTimeInMillis(selection);
                String formattedDate = format.format(calendar.getTime());
                DateOfBirth.setText(formattedDate);
            }
        });

        DateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
                if(materialDatePicker.isAdded()) {
                    materialDatePicker.dismiss();
                }
                else{
                    materialDatePicker.show(getParentFragmentManager(), "DOB_DATE_PICKER");
                }
            }
        });

        getAllPatientDetails();

        nextpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Patient_Registration_Page2 patient_registration_page2 = new Patient_Registration_Page2();
//                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.FragContainer, patient_registration_page2).addToBackStack("patientRegistration").commit();

                if (!isEmpty()) {
                    String hospRegNo = HospRegNo.getText().toString();
                    String fullname = FullName.getText().toString();
                    String mothername = MotherName.getText().toString();
                    String dateofbirth = DateOfBirth.getText().toString();
                    String bloodgroup = BloodGroupSpinner.getSelectedItem().toString();
                    String trimester = TrimesterSpinner.getSelectedItem().toString();
                    String state = State.getText().toString();
                    String district = District.getText().toString();
                    String block = Block.getText().toString();
                    String village = Village.getText().toString();
                    String mobno = MobNo.getText().toString();
                    String ashaworker = AshaWorker.getText().toString();

                    Bundle bundle = new Bundle();
                    bundle.putString("fullname", fullname);
                    bundle.putString("mothername", mothername);
                    bundle.putString("hospRegNo", hospRegNo);
                    bundle.putString("dateofbirth", dateofbirth);
                    bundle.putString("bloodgroup", bloodgroup);
                    bundle.putString("trimester", trimester);
                    bundle.putString("state", state);
                    bundle.putString("district", district);
                    bundle.putString("block", block);
                    bundle.putString("village", village);
                    bundle.putString("mobno", mobno);
                    bundle.putString("ashaworker", ashaworker);
//                    patientDetails_viewModel.updatePatientDetails(new PatientDetails(1,fullname, mothername, hospRegNo, bloodgroup, dateofbirth, state, district, block, village,mobNo));
                    getParentFragmentManager().setFragmentResult("Page1", bundle);
                    Patient_Registration_Page2 patient_registration_page2 = new Patient_Registration_Page2();
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.FragContainer, patient_registration_page2).addToBackStack("patientRegistration").commit();
                }
                else{
                    Bundle bundle=new Bundle();
                    if(HospRegNo.getText().toString().trim().isEmpty()){
                        bundle.putString("hospRegNo", "");
                    }
                    else{
                        bundle.putString("hospRegNo", hospRegNo);
                    }

                    if(FullName.getText().toString().trim().isEmpty()){
                        bundle.putString("fullname", "");
                    }
                    else{
                        bundle.putString("fullname", fullname);
                    }

                    if(MotherName.getText().toString().trim().isEmpty()){
                        bundle.putString("mothername", "");
                    }
                    else{
                        bundle.putString("mothername", mothername);
                    }

                    if(DateOfBirth.getText().toString().trim().isEmpty()){
                        bundle.putString("dateofbirth", "");
                    }
                    else{
                        bundle.putString("dateofbirth", dateofbirth);
                    }

                    if(BloodGroup.getText().toString().trim().isEmpty()){
                        bundle.putString("bloodgroup", "A+ve");
                    }
                    else{
                        bundle.putString("bloodgroup", bloodgroup);
                    }

                    if(Trimester.getText().toString().trim().isEmpty()){
                        bundle.putString("trimester", "1");
                    }
                    else{
                        bundle.putString("trimester", trimester);
                    }

                    if(State.getText().toString().trim().isEmpty()){
                        bundle.putString("state", "");
                    }
                    else{
                        bundle.putString("state", state);
                    }

                    if(District.getText().toString().trim().isEmpty()){
                        bundle.putString("district", "");
                    }
                    else{
                        bundle.putString("district", district);
                    }

                    if(Block.getText().toString().trim().isEmpty()){
                        bundle.putString("block", "");
                    }
                    else{
                        bundle.putString("block", block);
                    }

                    if(Village.getText().toString().trim().isEmpty()){
                        bundle.putString("village", "");
                    }
                    else{
                        bundle.putString("village", village);
                    }

                    if(MobNo.getText().toString().trim().isEmpty()){
                        bundle.putString("mobno", "");
                    }
                    else{
                        bundle.putString("mobno", mobno);
                    }

                    if(AshaWorker.getText().toString().trim().isEmpty()){
                        bundle.putString("ashaworker", "");
                    }
                    else{
                        bundle.putString("ashaworker", ashaworker);
                    }
                    getParentFragmentManager().setFragmentResult("Page1", bundle);
                    Patient_Registration_Page2 patient_registration_page2 = new Patient_Registration_Page2();
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.FragContainer, patient_registration_page2).addToBackStack("patientRegistration").commit();
                }
            }
        });

        return view;
    }

    private boolean isEmpty() {
            //.trim Removes leading empty Spaces..
        if(HospRegNo.getText().toString().trim().isEmpty()){
            //Toast.makeText(getContext(), "Enter Hospital No", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(FullName.getText().toString().trim().isEmpty()){
            //Toast.makeText(getContext(), "Enter Full Name", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(MotherName.getText().toString().trim().isEmpty()){
            //Toast.makeText(getContext(), "Enter Mother's Name", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(DateOfBirth.getText().toString().trim().isEmpty()){
            //Toast.makeText(getContext(), "Enter Date of Birth", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(BloodGroup.getText().toString().trim().isEmpty()){
            //Toast.makeText(getContext(), "Enter Blood Group", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(Trimester.getText().toString().trim().isEmpty()){
            //Toast.makeText(getContext(), "Enter Trimester", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(State.getText().toString().trim().isEmpty()){
            //Toast.makeText(getContext(), "Enter State", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(District.getText().toString().trim().isEmpty()){
            //Toast.makeText(getContext(), "Enter District", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(Block.getText().toString().trim().isEmpty()){
            //Toast.makeText(getContext(), "Enter Block", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(Village.getText().toString().trim().isEmpty()){
            //Toast.makeText(getContext(), "Enter Village", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(MobNo.getText().toString().trim().isEmpty()){
            //Toast.makeText(getContext(), "Enter Mobile No", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(AshaWorker.getText().toString().trim().isEmpty()){
            //Toast.makeText(getContext(), "Enter Asha No", Toast.LENGTH_SHORT).show();
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
                        try {
                            HospRegNo.setText(patientDetailsList.get(0).getHospital_no());
                            FullName.setText(patientDetailsList.get(0).getFull_name());
                            MotherName.setText(patientDetailsList.get(0).getMother_name());
                            DateOfBirth.setText(patientDetailsList.get(0).getDob());
                            State.setText(patientDetailsList.get(0).getState());
                            District.setText(patientDetailsList.get(0).getDistrict());
                            Block.setText(patientDetailsList.get(0).getTehsil());
                            Village.setText(patientDetailsList.get(0).getVillage());
                            MobNo.setText(patientDetailsList.get(0).getPhone_no());
                            if (patientDetailsList.get(0).getAsha_worker().equals("0")) {
                                Toast.makeText(getContext(), "Enter Asha Worker Number", Toast.LENGTH_SHORT).show();
                            } else {
                                AshaWorker.setText(patientDetailsList.get(0).getAsha_worker());
                            }

                            DefaultBloodGroup = patientDetailsList.get(0).getBlood_group();
                            ArrayAdapter<CharSequence> bloodGroupAdapter = ArrayAdapter.createFromResource(getContext(), R.array.bloodGroup, android.R.layout.simple_spinner_item);
                            int spinnerPosition = bloodGroupAdapter.getPosition(DefaultBloodGroup);
                            Log.d(TAG, "onNothingSelected: " + DefaultBloodGroup + "    : " + String.valueOf(spinnerPosition));
                            BloodGroupSpinner.setSelection(spinnerPosition);

                            DefaultTrimester = patientDetailsList.get(0).getTrimester();
                            ArrayAdapter<CharSequence> trimesterAdapter = ArrayAdapter.createFromResource(getContext(), R.array.trimester, android.R.layout.simple_spinner_item);
                            int trimesterSpinnerPosition = trimesterAdapter.getPosition(DefaultTrimester);
                            TrimesterSpinner.setSelection(trimesterSpinnerPosition);
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
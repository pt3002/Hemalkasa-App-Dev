package com.hemalkasa.hemalkasa;

import static android.app.Activity.RESULT_OK;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class Patient_Registration_Page1 extends Fragment {

    View view;
    ImageView proImg;
    FloatingActionButton fab;
    Button nextpage,showPatients;
    EditText HospRegNo, FullName,MotherName,BloodGroup,State,District,Block,Village,MobNo;
    TextView DateOfBirth;
    PatientDetails_ViewModel patientDetails_viewModel;
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
        HospRegNo =(EditText) view.findViewById(R.id.HospRegNo);
        FullName=(EditText) view.findViewById(R.id.fullname);
        MotherName=(EditText) view.findViewById(R.id.MotherName);
        BloodGroup=(EditText) view.findViewById(R.id.BloodGroup);
        State=(EditText) view.findViewById(R.id.State);
        District=(EditText) view.findViewById(R.id.District);
        Block=(EditText) view.findViewById(R.id.Block);
        Village=(EditText) view.findViewById(R.id.Village);
        MobNo=(EditText) view.findViewById(R.id.MobNo);
        DateOfBirth=(TextView) view.findViewById(R.id.DateOfBirth);

        patientDetails_viewModel=  ViewModelProviders.of(this).get(PatientDetails_ViewModel.class);



        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("Select Date of Birth");
        MaterialDatePicker materialDatePicker = materialDateBuilder.build();
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                DateOfBirth.setText(materialDatePicker.getHeaderText());
            }
        });

        DateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
                materialDatePicker.show(getParentFragmentManager(), "DOB_DATE_PICKER");
            }
        });




        fab.setOnClickListener(view ->{
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            launcher.launch(intent);
        });

        nextpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String hospRegNo=HospRegNo.getText().toString();
                String fullname=FullName.getText().toString();
                String mothername=MotherName.getText().toString();
                String dateofbirth=DateOfBirth.getText().toString();
                String bloodgroup=BloodGroup.getText().toString();
                String state=State.getText().toString();
                String district=District.getText().toString();
                String block=Block.getText().toString();
                String village=Village.getText().toString();
                String mobno= MobNo.getText().toString();


//                Patient_Registration_Page2 patient_registration_page2 = new Patient_Registration_Page2();
//                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.FragContainer, patient_registration_page2).addToBackStack("patientRegistration").commit();

                if(!isEmpty()) {
                    Bundle bundle=new Bundle();
                    bundle.putString("fullname", fullname);
                    bundle.putString("mothername", mothername);
                    bundle.putString("hospRegNo", hospRegNo);
                    bundle.putString("dateofbirth", dateofbirth);
                    bundle.putString("bloodgroup", bloodgroup);
                    bundle.putString("state", state);
                    bundle.putString("district", district);
                    bundle.putString("block", block);
                    bundle.putString("village", village);
                    bundle.putString("mobno", mobno);
//                    // TODO Save in saved preferences. Get Unique Key. Search from DB and assign it to patient
//                    patientDetails_viewModel.updatePatientDetails(new PatientDetails(1,fullname, mothername, hospRegNo, bloodgroup, dateofbirth, state, district, block, village,mobNo));
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
            Toast.makeText(getContext(), "Enter Hospital No", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(FullName.getText().toString().trim().isEmpty()){
            Toast.makeText(getContext(), "Enter Full Name", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(MotherName.getText().toString().trim().isEmpty()){
            Toast.makeText(getContext(), "Enter Mother's Name", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(DateOfBirth.getText().toString().trim().isEmpty()){
            Toast.makeText(getContext(), "Enter Date of Birth", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(BloodGroup.getText().toString().trim().isEmpty()){
            Toast.makeText(getContext(), "Enter Blood Group", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(State.getText().toString().trim().isEmpty()){
            Toast.makeText(getContext(), "Enter State", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(District.getText().toString().trim().isEmpty()){
            Toast.makeText(getContext(), "Enter District", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(Block.getText().toString().trim().isEmpty()){
            Toast.makeText(getContext(), "Enter Block", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(Village.getText().toString().trim().isEmpty()){
            Toast.makeText(getContext(), "Enter Village", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(MobNo.getText().toString().trim().isEmpty()){
            Toast.makeText(getContext(), "Enter Mobile No", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
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


}
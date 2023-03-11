package com.hemalkasa.hemalkasa;

import android.app.Activity;
import static android.app.Activity.RESULT_OK;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Patient_Registration_Page1 extends Fragment {

    View view;
    ImageView proImg;
    FloatingActionButton fab;
    Button nextpage;
    int SEC = 1;

    public Patient_Registration_Page1() {
        // Required empty public constructor
    }

    ActivityResultLauncher<Intent> launcher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode()==RESULT_OK && result.getData()!=null){
                    Uri uri = result.getData().getData();
                    proImg.setImageURI(uri);
                }
            });


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.patient__registration__page1, container, false);
        proImg = (ImageView) view.findViewById(R.id.profile_image);
        fab = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);
        nextpage=(Button) view.findViewById(R.id.next);

        fab.setOnClickListener(view ->{
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            launcher.launch(intent);
        });

        nextpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Patient_Registration_Page2 patient_registration_page2=new Patient_Registration_Page2();
                FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.FragContainer, patient_registration_page2).addToBackStack("patientRegistration").commit();
            }
        });

        return view;
    }
}
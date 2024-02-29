package com.hemalkasa.hemalkasa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Patient_Registration_Page extends AppCompatActivity {

    Button backBtn, nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_registration_page);
        getSupportFragmentManager().beginTransaction().replace(R.id.FragContainer, Patient_Registration_Page1.class, null).commit();
    }
}
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
//        backBtn = findViewById(R.id.back);
//        nextBtn = findViewById(R.id.next);
//        backBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getSupportFragmentManager().beginTransaction().replace(R.id.FragContainer, Patient_Registration_Page1.class, null).setReorderingAllowed(true).addToBackStack("name").commit();
//            }
//        });
//        nextBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getSupportFragmentManager().beginTransaction().replace(R.id.FragContainer, Patient_Registration_Page2.class, null).setReorderingAllowed(true).addToBackStack("name").commit();
//            }
//        });

    }
}
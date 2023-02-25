package com.example.patientregistration;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.FragContainer, Frag1.class, null).setReorderingAllowed(true).addToBackStack("name").commit();
        Button backBtn = findViewById(R.id.back);
        Button nextBtn = findViewById(R.id.next);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.FragContainer, Frag1.class, null).setReorderingAllowed(true).addToBackStack("name").commit();
            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.FragContainer, Frag2.class, null).setReorderingAllowed(true).addToBackStack("name").commit();
            }
        });
    }

}
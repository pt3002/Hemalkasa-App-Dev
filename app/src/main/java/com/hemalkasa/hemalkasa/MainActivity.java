package com.hemalkasa.hemalkasa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button addMedicinePage,registrationPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registrationPage=findViewById(R.id.registrationPage);
        addMedicinePage=findViewById(R.id.addMedicinePage);

        registrationPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Patient_Registration_Page.class);
                startActivity(intent);
            }
        });



    }
}
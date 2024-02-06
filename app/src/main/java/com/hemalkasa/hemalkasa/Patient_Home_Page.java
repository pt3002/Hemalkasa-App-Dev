package com.hemalkasa.hemalkasa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Patient_Home_Page extends AppCompatActivity {
    Button Video,Summary,Notification,Risk,Emergency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_home_page);

        Video=findViewById(R.id.Video);
        Summary=findViewById(R.id.Summary);
        Notification=findViewById(R.id.Notification);
        Risk=findViewById(R.id.Risk);
        Emergency=findViewById(R.id.Emergency);

        Video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Patient_Home_Page.this,Video_MainScreen.class);
                startActivity(intent);
            }
        });
        Summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        Notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        Risk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        Emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Patient_Home_Page.this,Emergency_Contact.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.patient_to_doctor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.ChangeToDoctor:
                Intent intent=new Intent(Patient_Home_Page.this,MainActivity.class);
                startActivity(intent);
//                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
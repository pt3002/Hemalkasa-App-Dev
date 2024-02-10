package com.hemalkasa.hemalkasa;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Patient_Home_Page extends AppCompatActivity {
    ImageButton Video, Summary, Notification, Risk, Emergency;
    // TODO Hardcoded password
    final static String password="1234";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_home_page);

        Video = findViewById(R.id.Video);
        Summary = findViewById(R.id.Summary);
        Notification = findViewById(R.id.Notification);
        Risk = findViewById(R.id.Risk);
        Emergency = findViewById(R.id.Emergency);

        Video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Patient_Home_Page.this, Video_MainScreen.class);
                startActivity(intent);
            }
        });
        Summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Patient_Home_Page.this, Summary.class);
                startActivity(intent);
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
                Intent intent = new Intent(Patient_Home_Page.this, RiskFactor.class);
//                intent.putExtra("Access", false);
                startActivity(intent);
            }
        });
        Emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Patient_Home_Page.this, Emergency_Contact.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.patient_to_doctor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ChangeToDoctor:
                checkpassword();
//                Intent intent=new Intent(Patient_Home_Page.this,MainActivity.class);
//                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void checkpassword() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Patient_Home_Page.this);
        builder.setMessage("Access for Lok Biradri Doctors")
                .setTitle("Enter Password")
                .setCancelable(true);

        final View PasswordLayout = getLayoutInflater().inflate(R.layout.doctor_password_layout, null);
        builder.setView(PasswordLayout);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText Password = PasswordLayout.findViewById(R.id.password);
                        if(Password.getText().toString().equals(password)){
                            dialog.dismiss();
                            Toast.makeText(Patient_Home_Page.this, "Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Patient_Home_Page.this, MainActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(Patient_Home_Page.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                            Password.setText("");
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }
}
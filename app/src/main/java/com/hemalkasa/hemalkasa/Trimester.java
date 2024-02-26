package com.hemalkasa.hemalkasa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Trimester extends AppCompatActivity {

    public static final String TAG="pratik";
    ConstraintLayout ActivityView;
    // TODO Hardcoded password
    final static String password = "1234";

    private TextView trimesterNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trimester);

        //Log.d(TAG, "before set trimester: ");
        trimesterNumber = findViewById(R.id.TrimesterNumberHeading);

        setTrimester();

        ActivityView = findViewById(R.id.ActivityView);
        //noinspection AndroidLintClickableViewAccessibility
        ActivityView.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeRight() {
                super.onSwipeRight();
                Log.d(TAG, "RIGHTTTTT: ");
                Intent intent = new Intent(Trimester.this, Video_MainScreen.class);
                startActivity(intent);
            }

            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();
                Log.d(TAG, "LEFTTTTTTT: ");
                Intent intent = new Intent(Trimester.this, Summary.class);
                startActivity(intent);
            }
        });
    }

    private void setTrimester() {
        Handler getTrimester = new Handler();

        Runnable runnable = new Runnable() {

            List<PatientDetails> patientDetailsList;
            @Override
            public void run() {
                patientDetailsList = Database.getInstance(Trimester.this)
                        .patientDetails_dao()
                        .getAllPatientDetails();
                Log.d(TAG,patientDetailsList.get(0).getTrimester());
                getTrimester.post(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            //Log.d(TAG, "run: " + patientDetailsList.get(0).getTrimester());
                            if(patientDetailsList.get(0).getTrimester().equals("1")){
                                trimesterNumber.setText(R.string.first_trimester);
                            }
                            else if(patientDetailsList.get(0).getTrimester().equals("2")){
                                trimesterNumber.setText(R.string.second_trimester);
                            }
                            else if(patientDetailsList.get(0).getTrimester().equals("3")){
                                trimesterNumber.setText(R.string.third_trimester);
                            }

                        }catch (Exception e){
                            Log.d(TAG, e.getMessage());
                            Toast.makeText(Trimester.this, "No Trimester Present", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        };

        Thread getTrimesterThread = new Thread(runnable);
        getTrimesterThread.start();
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
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void checkpassword() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Trimester.this);
        builder.setMessage("Access for Lok Biradri Doctors")
                .setTitle("Enter Password")
                .setCancelable(true);

        final View PasswordLayout = getLayoutInflater().inflate(R.layout.doctor_password_layout, null);
        builder.setView(PasswordLayout);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText Password = PasswordLayout.findViewById(R.id.password);
                        if (Password.getText().toString().equals(password)) {
                            dialog.dismiss();
                            Toast.makeText(Trimester.this, "Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Trimester.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(Trimester.this, "Invalid Password", Toast.LENGTH_SHORT).show();
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
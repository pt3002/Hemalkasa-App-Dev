package com.hemalkasa.hemalkasa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

public class Emergency_Contact extends AppCompatActivity {
    public static final String TAG="pratik";
    LinearLayout ContactAshaWorker,Contact108,ContactLBPH;
    // TODO Change the phone number
    private static final String _108="108",PHC="8275957192",LBPH="7588772819";
    private String AshaWorker="";
    private static final int REQUEST_CALL_CODE =1;
    private static final String PERMISSION_CALL=Manifest.permission.CALL_PHONE;
    ConstraintLayout ActivityView;
    // TODO Hardcoded password
    final static String password = "1234";
    private Button NextButton,BackButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emergency_contact);
        ContactAshaWorker=findViewById(R.id.ContactAshaWorker);
        Contact108=findViewById(R.id.Contact108);
        ContactLBPH=findViewById(R.id.ContactLBPH);
        NextButton = findViewById(R.id.NextButton);
        BackButton = findViewById(R.id.BackButton);

        getAshaWorkerNumber();

        ContactAshaWorker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestRunTimePermission();
                makePhoneCall(AshaWorker);
            }
        });
        Contact108.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestRunTimePermission();
                makePhoneCall(_108);
            }
        });
        ContactLBPH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestRunTimePermission();
                makePhoneCall(LBPH);
            }
        });


        NextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Emergency_Contact.this, Patient_Home_Page.class);
                startActivity(intent);
            }
        });

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
//                Intent intent = new Intent(Emergency_Contact.this, RiskFactor.class);
//                startActivity(intent);
            }
        });

//        ActivityView = findViewById(R.id.ActivityView);
//        //noinspection AndroidLintClickableViewAccessibility
//        ActivityView.setOnTouchListener(new OnSwipeTouchListener(this) {
//            @Override
//            public void onSwipeRight() {
//                super.onSwipeRight();
//                Log.d(TAG, "RIGHTTTTT: ");
//                Intent intent = new Intent(Emergency_Contact.this, RiskFactor.class);
//                startActivity(intent);
//            }
//
//            @Override
//            public void onSwipeLeft() {
//                super.onSwipeLeft();
//                Log.d(TAG, "LEFTTTTTTT: ");
//                Intent intent = new Intent(Emergency_Contact.this, Video_MainScreen.class);
//                startActivity(intent);
//            }
//        });

    }

    private void getAshaWorkerNumber() {
        Handler getPatientDetailsHandler=new Handler();

        Runnable runnable=new Runnable() {
            List<PatientDetails> patientDetailsList;
            @Override
            public void run() {
                patientDetailsList = Database.getInstance(Emergency_Contact.this)
                        .patientDetails_dao()
                        .getAllPatientDetails();

                getPatientDetailsHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            AshaWorker= patientDetailsList.get(0).getAsha_worker();
                        }catch (Exception e){
                            Log.d(TAG, e.getMessage());
                            Toast.makeText(Emergency_Contact.this, "Cannot Fetch Asha Worker Number", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        };
        Thread getPatientDetailsThread=new Thread(runnable);
        getPatientDetailsThread.start();
    }

    private void requestRunTimePermission(){
        if(ActivityCompat.checkSelfPermission(this,PERMISSION_CALL)== PackageManager.PERMISSION_GRANTED){
//            Toast.makeText(this, "CAllllllll", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(ActivityCompat.shouldShowRequestPermissionRationale(this,PERMISSION_CALL)){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setMessage("Permission Needed to Make Call")
                    .setTitle("Permission Required")
                    .setCancelable(false)
                    .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(Emergency_Contact.this,new String[]{PERMISSION_CALL}, REQUEST_CALL_CODE);
                            dialog.dismiss();
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
        else{
            ActivityCompat.requestPermissions(this, new String[]{PERMISSION_CALL},REQUEST_CALL_CODE);
        }
    }

    private void makePhoneCall(String number) {
        if(ContextCompat.checkSelfPermission(Emergency_Contact.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Emergency_Contact.this , new String[] {Manifest.permission.CALL_PHONE} , REQUEST_CALL_CODE);
        }else{
            if(number.length()==3 || number.length()==10 || number.length()==13){
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL , Uri.parse(dial)));
            }
            else{
                Toast.makeText(this, "Invalid Number", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CALL_CODE: {
                Log.d(TAG, "onRequestPermissionResult: ");
                if(grantResults.length >0 &&grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this , " Permission Granted " , Toast.LENGTH_SHORT).show();
                }
                else if(!ActivityCompat.shouldShowRequestPermissionRationale(this, PERMISSION_CALL)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("This feature is unavailable because it requires permission. Allow Phone Call permission to make call")
                            .setTitle("Permission Required")
                            .setCancelable(false)
                            .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent settingIntent=new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri =Uri.fromParts("package",getPackageName(),null);
                                    settingIntent.setData(uri);
                                    startActivity(settingIntent);
                                    dialog.dismiss();
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
                else {
                    Toast.makeText(this , " Please Give Permission" , Toast.LENGTH_SHORT).show();
                    requestRunTimePermission();
                }
                return;
            }
        }
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
        AlertDialog.Builder builder = new AlertDialog.Builder(Emergency_Contact.this);
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
                            Toast.makeText(Emergency_Contact.this, "Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Emergency_Contact.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(Emergency_Contact.this, "Invalid Password", Toast.LENGTH_SHORT).show();
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
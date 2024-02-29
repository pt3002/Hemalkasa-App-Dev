package com.hemalkasa.hemalkasa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "pratik";
    Button addMedicinePage,registrationPage,historyPage,riskPage, videoPath,sliderPage;
    TextView welcome;
    private Prescription_Table_ViewModel prescriptionTableViewModel;
    private PatientDetails_ViewModel patientDetailsViewModel;
    private static final int REQUEST_SCHEDULE_EXACT_ALARM_CODE =1;
    private static final String PERMISSION_SCHEDULE_EXACT_ALARM= Manifest.permission.SCHEDULE_EXACT_ALARM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registrationPage=findViewById(R.id.registrationPage);
        addMedicinePage=findViewById(R.id.addMedicinePage);
        videoPath =findViewById(R.id.VideoPath);
        historyPage =findViewById(R.id.history);
        riskPage =findViewById(R.id.riskFactors);
//        sliderPage = findViewById(R.id.slider);

        checkAlarmPermission();

        registrationPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Patient_Registration_Page.class);
                startActivity(intent);
            }
        });

        addMedicinePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Add_Medicines.class);
                startActivity(intent);
            }
        });

        historyPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,History.class);
                startActivity(intent);
            }
        });

        videoPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File path=getExternalFilesDir("Videos");
                Log.d(TAG, "Video Directory: " + path.getAbsolutePath());
                Toast.makeText(MainActivity.this, "Video Directory: " + path.getAbsolutePath(), Toast.LENGTH_LONG).show();
//                Intent intent=new Intent(MainActivity.this,Video_MainScreen.class);
//                startActivity(intent);
            }
        });

        riskPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,RiskFactor.class);
                intent.putExtra("Access", true);
                startActivity(intent);
            }
        });

//        sliderPage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, Slider_MainPage.class);
//                Intent intent = new Intent(MainActivity.this, MainActivitySlider.class);
//                startActivity(intent);
//            }
//        });

        prescriptionTableViewModel=  ViewModelProviders.of(this).get(Prescription_Table_ViewModel.class);
        patientDetailsViewModel=  ViewModelProviders.of(this).get(PatientDetails_ViewModel.class);

//        notesPage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                    // Set the Name
//                getAllPatientDetails();
//                patientDetailsViewModel.getPatientName(welcome);
//            }
//        });
    }

    private void checkAlarmPermission() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.S){
            Log.d(TAG, "checkAlarmPermission: IF condition");
            if(ActivityCompat.checkSelfPermission(this,PERMISSION_SCHEDULE_EXACT_ALARM)== PackageManager.PERMISSION_GRANTED){
                Log.d(TAG, "Permission ALready Granted");
                return;
            }
            else if(ActivityCompat.shouldShowRequestPermissionRationale(this, PERMISSION_SCHEDULE_EXACT_ALARM)){
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setMessage("Permission Needed to Set Reminders")
                        .setTitle("Permission Required")
                        .setCancelable(false)
                        .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(MainActivity.this,new String[]{PERMISSION_SCHEDULE_EXACT_ALARM}, REQUEST_SCHEDULE_EXACT_ALARM_CODE);
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
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{PERMISSION_SCHEDULE_EXACT_ALARM}, REQUEST_SCHEDULE_EXACT_ALARM_CODE);
            }
        }
        else{
            Log.d(TAG, "checkAlarmPermission: Else Condition");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_SCHEDULE_EXACT_ALARM_CODE:
                Log.d(TAG, "onRequestPermissionsResult: ");
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                }
                else if(!ActivityCompat.shouldShowRequestPermissionRationale(this, PERMISSION_SCHEDULE_EXACT_ALARM)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("This feature is unavailable because it requires permission. Allow Alarm permission to make set reminders")
                            .setTitle("Permission Required")
                            .setCancelable(false)
                            .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent settingIntent=new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
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
                else{
                    Toast.makeText(this , " Please Give Permission" , Toast.LENGTH_SHORT).show();
                    checkAlarmPermission();
                }
                return;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.doctor_to_patient, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ChangeToPatient:
                Intent intent = new Intent(MainActivity.this, Video_MainScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                MainActivity.this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
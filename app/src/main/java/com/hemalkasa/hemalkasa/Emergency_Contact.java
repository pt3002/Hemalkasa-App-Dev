package com.hemalkasa.hemalkasa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Emergency_Contact extends AppCompatActivity {
    public static final String TAG="pratik";
    LinearLayout ContactAshaWorker,ContactPHC,Contact108,ContactLBPH;
    // TODO Change the phone number
    private static final String AshaWorker="8452991487",_108="108",PHC="7588231901",LBPH="8452991487";
    private static final int REQUEST_CALL_CODE =1;
    private static final String PERMISSION_CALL=Manifest.permission.CALL_PHONE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emergency_contact);
        ContactAshaWorker=findViewById(R.id.ContactAshaWorker);
        Contact108=findViewById(R.id.Contact108);
        ContactPHC=findViewById(R.id.ContactPHC);
        ContactLBPH=findViewById(R.id.ContactLBPH);

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
        ContactPHC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestRunTimePermission();
                makePhoneCall(PHC);
            }
        });
        ContactLBPH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestRunTimePermission();
                makePhoneCall(LBPH);
            }
        });

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
            String dial = "tel:" + number;
            startActivity(new Intent(Intent.ACTION_CALL , Uri.parse(dial)));
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
}
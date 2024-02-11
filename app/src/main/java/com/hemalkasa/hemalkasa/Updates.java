package com.hemalkasa.hemalkasa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;

public class Updates extends AppCompatActivity {

    private static final String TAG = "pratik";
    private Button submit,clear;
    private Spinner desgination;
    private EditText notes;
    String VISITING_DATE,POG_WEEKS,POG_DAYS,HB,NEXT_VISITING_DATE,DESIGNATION="";
    private Prescription_Table_ViewModel prescriptionTableViewModel;
    private CountDownTimer countDownTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updates);

        clear=findViewById(R.id.Clear);
        submit=findViewById(R.id.Submit);
        desgination=findViewById(R.id.NotesBySpinner);
        notes=findViewById(R.id.NotesText);
        prescriptionTableViewModel=  ViewModelProviders.of(this).get(Prescription_Table_ViewModel.class);

        ArrayAdapter<CharSequence> designationAdapter = ArrayAdapter.createFromResource(this, R.array.designation, android.R.layout.simple_spinner_item);
        designationAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        desgination.setAdapter(designationAdapter);
        desgination.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                desgination.setSelection(i);
                DESIGNATION = desgination.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                DESIGNATION = "";
            }
        });

        Intent intent=getIntent();
        if(intent.hasExtra("DESIGNATION")){
            String designationName=intent.getStringExtra("DESIGNATION");
            int spinnerPosition=designationAdapter.getPosition(designationName);
            desgination.setSelection(spinnerPosition);
            desgination.setEnabled(false);
            notes.setText(intent.getStringExtra("NOTES"));
//            notes.setEnabled(false);
            notes.setFocusable(false);
            clear.setVisibility(View.GONE);
            submit.setVisibility(View.GONE);
        }
        else {
            try {
                VISITING_DATE = intent.getStringExtra("VISITING_DATE");
                POG_WEEKS = intent.getStringExtra("POG_WEEKS");
                POG_DAYS = intent.getStringExtra("POG_DAYS");
                HB = intent.getStringExtra("HB");
                NEXT_VISITING_DATE = intent.getStringExtra("NEXT_VISITING_DATE");
            } catch (Exception e) {
                Log.d(TAG, e.getMessage());
            }

            clear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notes.setText("");
                }
            });

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String NOTES = notes.getText().toString();
                    prescriptionTableViewModel.insertPrescription(new Prescription_Table(VISITING_DATE, POG_WEEKS, POG_DAYS, HB, NEXT_VISITING_DATE, DESIGNATION, NOTES));
                    setAlarm(VISITING_DATE,NEXT_VISITING_DATE);
                    Toast.makeText(Updates.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                    Intent mainActivity = new Intent(Updates.this, MainActivity.class);
                    startActivity(mainActivity);
                    finish();
                }
            });
        }
    }

    private void setAlarm(String VISITING_DATE, String NEXT_VISITING_DATE) {
        int id=12;
        int hour=9;
        int minute=0;
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // At the time of filling medicine if time is already pass then we pass +1 day
//        if(calendar.before(Calendar.getInstance())){
//            calendar.add(Calendar.DATE, 1);
//        }
        if(calendar.before(Calendar.getInstance())){
            calendar.add(Calendar.MINUTE, 1);
        }

        AlarmManager alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent alarmReceiverIntent=new Intent(this,AlarmReceiver.class);
        alarmReceiverIntent.putExtra("NextVisit", NEXT_VISITING_DATE);
        alarmReceiverIntent.putExtra("Id", id);
        // TODO Use appropriate Flags
        PendingIntent broadcastPendingIntent=PendingIntent.getBroadcast(this,id, alarmReceiverIntent, PendingIntent.FLAG_MUTABLE);

        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),broadcastPendingIntent);
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, broadcastPendingIntent);
        Log.d(TAG, String.valueOf(id) + "  " + NEXT_VISITING_DATE);
        Log.d(TAG, "setTime " + calendar.getTimeInMillis());
        Long remainingTime=calendar.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
        Log.d(TAG, "remainingTime " +String.valueOf(remainingTime));
        counter(remainingTime);
    }

    private void counter(Long remainingTime) {

        countDownTimer=new CountDownTimer(remainingTime, 1000) {
            public void onTick(long millisUntilFinished) {
                // Used for formatting digit to be in 2 digits only
                NumberFormat f = new DecimalFormat("00");
                long hour = (millisUntilFinished / 3600000) % 24;
                long min = (millisUntilFinished / 60000) % 60;
                long sec = (millisUntilFinished / 1000) % 60;
                Log.d(TAG, (f.format(hour) + ":" + f.format(min) + ":" + f.format(sec)));
            }
            // When the task is over it will print 00:00:00 there
            public void onFinish() {
                Log.d(TAG, "Timer Finished");
            }
        }.start();
    }

    private void cancelAlarm(int id){
        Log.d(TAG, "Cancel " + id);
        AlarmManager alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent alarmReceiver=new Intent(this,AlarmReceiver.class);
        PendingIntent broadcastIntent=PendingIntent.getBroadcast(this,id, alarmReceiver, PendingIntent.FLAG_MUTABLE);

        alarmManager.cancel(broadcastIntent);
        Toast.makeText(this, "Removed Successfully", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "cancelAlarm");
        countDownTimer.cancel();
    }
}
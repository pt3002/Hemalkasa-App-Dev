package com.hemalkasa.hemalkasa;

import androidx.annotation.NonNull;
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
    private Button submit, clear;
    private Spinner desgination;
    private EditText notes;
    String VISITING_DATE, POG_WEEKS, POG_DAYS, HB, NEXT_VISITING_DATE, DESIGNATION = "";
    private Prescription_Table_ViewModel prescriptionTableViewModel;
    private CountDownTimer countDownTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updates);

        clear = findViewById(R.id.Clear);
        submit = findViewById(R.id.Submit);
        desgination = findViewById(R.id.NotesBySpinner);
        notes = findViewById(R.id.NotesText);
        prescriptionTableViewModel = ViewModelProviders.of(this).get(Prescription_Table_ViewModel.class);

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

        Intent intent = getIntent();
        if (intent.hasExtra("DESIGNATION")) {
            String designationName = intent.getStringExtra("DESIGNATION");
            int spinnerPosition = designationAdapter.getPosition(designationName);
            desgination.setSelection(spinnerPosition);
            desgination.setEnabled(false);
            notes.setText(intent.getStringExtra("NOTES"));
//            notes.setEnabled(false);
            notes.setFocusable(false);
            clear.setVisibility(View.GONE);
            submit.setVisibility(View.GONE);
        } else {
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
                    // TODO Uncomments this lines
//                    prescriptionTableViewModel.insertPrescription(new Prescription_Table(VISITING_DATE, POG_WEEKS, POG_DAYS, HB, NEXT_VISITING_DATE, DESIGNATION, NOTES));
                    setAlarm(NEXT_VISITING_DATE);
                    Toast.makeText(Updates.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
//                    Intent mainActivity = new Intent(Updates.this, MainActivity.class);
//                    startActivity(mainActivity);
//                    finish();
                }
            });
        }
    }

    private void setAlarm(String NEXT_VISITING_DATE) {
        int hour = 20;    // TODO
        int minute = 3;  // TODO
        int visitDay = getDay(NEXT_VISITING_DATE);
        int visitMonth = getMonth(NEXT_VISITING_DATE);
        int visitYear = getYear(NEXT_VISITING_DATE);
        int visitId = Integer.parseInt(String.valueOf(visitDay) + String.valueOf(visitMonth) + String.valueOf(visitYear));

        Calendar calendarVisitDate = Calendar.getInstance();
        calendarVisitDate.set(visitYear, visitMonth, visitDay);
        calendarVisitDate.set(Calendar.HOUR_OF_DAY, hour);
        calendarVisitDate.set(Calendar.MINUTE, minute);
        calendarVisitDate.set(Calendar.SECOND, 0);
        calendarVisitDate.set(Calendar.MILLISECOND, 0);


        AlarmManager VisitDateAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent VisitDateAlarmReceiverIntent = new Intent(this, AlarmReceiver.class);
        VisitDateAlarmReceiverIntent.putExtra("NextVisit", NEXT_VISITING_DATE);
        VisitDateAlarmReceiverIntent.putExtra("Id", visitId);
        PendingIntent VisitDateBroadcastPendingIntent = PendingIntent.getBroadcast(this, visitId, VisitDateAlarmReceiverIntent, PendingIntent.FLAG_MUTABLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (VisitDateAlarmManager.canScheduleExactAlarms()) {
                VisitDateAlarmManager.setExact(AlarmManager.RTC_WAKEUP, calendarVisitDate.getTimeInMillis(), VisitDateBroadcastPendingIntent);
            } else {
                VisitDateAlarmManager.set(AlarmManager.RTC_WAKEUP, calendarVisitDate.getTimeInMillis(), VisitDateBroadcastPendingIntent);
            }
        } else {
            VisitDateAlarmManager.setExact(AlarmManager.RTC_WAKEUP, calendarVisitDate.getTimeInMillis(), VisitDateBroadcastPendingIntent);
        }

        Calendar calendarPreviousDate = Calendar.getInstance();
        calendarPreviousDate = calendarVisitDate;
//        calendarPreviousDate.add(Calendar.DATE,-1 );
        calendarPreviousDate.add(Calendar.MINUTE, -2);  // TODO
//        int previousId = visitId - 1;
        int previousId=visitId-10;      // TODO

        AlarmManager PreviousDateAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent PreviousDateAlarmReceiverIntent = new Intent(this, AlarmReceiver.class);
        PreviousDateAlarmReceiverIntent.putExtra("NextVisit", NEXT_VISITING_DATE);
        PreviousDateAlarmReceiverIntent.putExtra("Id", previousId);
        PendingIntent PreviousDateBroadcastPendingIntent = PendingIntent.getBroadcast(this, previousId, PreviousDateAlarmReceiverIntent, PendingIntent.FLAG_MUTABLE);
        Log.d(TAG, "Version:  " + Build.VERSION.SDK_INT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            Log.d(TAG, "SDK VERSION S FOUND");
            if (PreviousDateAlarmManager.canScheduleExactAlarms()) {
                Log.d(TAG, "Exact Alarmmm");
                PreviousDateAlarmManager.setExact(AlarmManager.RTC_WAKEUP, calendarPreviousDate.getTimeInMillis(), PreviousDateBroadcastPendingIntent);
            } else {
                Log.d(TAG, "Cannot Schedule Exacttt");
                PreviousDateAlarmManager.set(AlarmManager.RTC_WAKEUP, calendarPreviousDate.getTimeInMillis(), PreviousDateBroadcastPendingIntent);
            }
        } else {
            Log.d(TAG, "Low SDK VERSIONNN");
            Log.d(TAG, "Exact Alarmmm");
            PreviousDateAlarmManager.setExact(AlarmManager.RTC_WAKEUP, calendarPreviousDate.getTimeInMillis(), PreviousDateBroadcastPendingIntent);
        }


        //        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, broadcastPendingIntent);
//        Log.d(TAG, String.valueOf(previousId) + "  " + calendarPreviousDate);
//        Log.d(TAG, "setTime " + calendarVisitDate.getTimeInMillis());
//        Long remainingTime=calendarPreviousDate.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
//        Log.d(TAG, "remainingTime " +String.valueOf(remainingTime));
//        counter(remainingTime);
    }

    private void counter(Long remainingTime) {

        countDownTimer = new CountDownTimer(remainingTime, 1000) {
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

    private void cancelAlarm(int id) {
        Log.d(TAG, "Cancel " + id);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent alarmReceiver = new Intent(this, AlarmReceiver.class);
        PendingIntent broadcastIntent = PendingIntent.getBroadcast(this, id, alarmReceiver, PendingIntent.FLAG_MUTABLE);

        alarmManager.cancel(broadcastIntent);
        Toast.makeText(this, "Removed Successfully", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "cancelAlarm");
        countDownTimer.cancel();
    }


    private int getDay(@NonNull String NEXT_VISITING_DATE) {
        Log.d(TAG, "getDay: " + NEXT_VISITING_DATE.substring(0, 2));
        return Integer.parseInt(NEXT_VISITING_DATE.substring(0, 2));
    }

    private int getMonth(@NonNull String NEXT_VISITING_DATE) {
        String month = NEXT_VISITING_DATE.substring(3, 6);
        switch (month) {
            case "Jan":
                return 0;
            case "Feb":
                return 1;
            case "Mar":
                return 2;
            case "Apr":
                return 3;
            case "May":
                return 4;
            case "Jun":
                return 5;
            case "Jul":
                return 6;
            case "Aug":
                return 7;
            case "Sep":
                return 8;
            case "Oct":
                return 9;
            case "Nov":
                return 10;
            default:
                return 11;
        }
    }

    private int getYear(@NonNull String NEXT_VISITING_DATE) {
        Log.d(TAG, "getYear: " + NEXT_VISITING_DATE);
        return Integer.parseInt(NEXT_VISITING_DATE.substring(7, 11));
    }
}
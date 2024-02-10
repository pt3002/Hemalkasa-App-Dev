package com.hemalkasa.hemalkasa;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class Add_Medicines extends AppCompatActivity {
    public static final int INSERT_MEDICINE=1;
    public static final int EDIT_MEDICINE=2;
    FloatingActionButton addMedicineBtn;
    RecyclerView medicineRecyclerView;
    MedicineAdapter medicineAdapter;
    private List<Medicine_Table> medicineList = new ArrayList<>();
    private Medicine_Table_ViewModel medicineTableViewModel;
    private static final String TAG = "pratik";
    EditText POGWeeks,POGDays,EDD;
    TextView VisitDate,NextVisitDate;
    Button Submit;
    private Random randomId=new Random();
    String visitingDate="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_medicines);
        addMedicineBtn = findViewById(R.id.addMedBtn);
        POGWeeks = findViewById(R.id.POGWeeks);
        POGDays = findViewById(R.id.POGDays);
        EDD = findViewById(R.id.EDD);
        VisitDate = findViewById(R.id.VisitDate);
        NextVisitDate = findViewById(R.id.NextVisitDate);
        Submit = findViewById(R.id.Submit);
        medicineTableViewModel=  ViewModelProviders.of(this).get(Medicine_Table_ViewModel.class);

        createNotificationChannel();

        Intent historyIntent=getIntent();
        if(historyIntent.hasExtra("VISITING_DATE")){
            medicineAdapter = new MedicineAdapter(true);
            medicineRecyclerView = findViewById(R.id.RecyclerView);
            medicineRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            medicineRecyclerView.setHasFixedSize(true);
            medicineRecyclerView.setAdapter(medicineAdapter);
            medicineRecyclerView.addItemDecoration(new DividerItemDecoration(medicineRecyclerView.getContext(), DividerItemDecoration.VERTICAL));  //Normal Horizontal Separator

            VisitDate.setText(historyIntent.getStringExtra("VISITING_DATE"));
            POGWeeks.setText(historyIntent.getStringExtra("POG_WEEKS"));
            POGDays.setText(historyIntent.getStringExtra("POG_DAYS"));
            EDD.setText(historyIntent.getStringExtra("EDD"));
            NextVisitDate.setText(historyIntent.getStringExtra("NEXT_VISITING_DATE"));

                // Disabling the editable field
//            POGWeeks.setEnabled(false);
//            POGDays.setEnabled(false);
//            EDD.setEnabled(false);
            POGWeeks.setFocusable(false);
            POGDays.setFocusable(false);
            EDD.setFocusable(false);
            Submit.setText("Next Page");
            Submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent notesIntent = new Intent(Add_Medicines.this, Updates.class);
                    notesIntent.putExtra("DESIGNATION",historyIntent.getStringExtra("DESIGNATION"));
                    notesIntent.putExtra("NOTES", historyIntent.getStringExtra("NOTES"));
                    startActivity(notesIntent);
                }
            });
            addMedicineBtn.setVisibility(View.GONE);
            setAdapter(historyIntent.getStringExtra("VISITING_DATE"));
        }
        else {
            medicineAdapter = new MedicineAdapter(false);
            medicineRecyclerView = findViewById(R.id.RecyclerView);
            medicineRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            medicineRecyclerView.setHasFixedSize(true);
            medicineRecyclerView.setAdapter(medicineAdapter);
            medicineRecyclerView.addItemDecoration(new DividerItemDecoration(medicineRecyclerView.getContext(), DividerItemDecoration.VERTICAL));  //Normal Horizontal Separator

            new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                    ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    return false;   //This is Used for Drag And Drop. Since  we are not using this so we return false
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    Medicine_Table medicineTable = medicineAdapter.getMedicalTableAt(viewHolder.getAdapterPosition());

                    String medicine = medicineTable.getName();
                    int medicineId;
                    try {
                        medicineId = medicineTableViewModel.getMedicineById(medicine);
                    } catch (Exception exception) {
                        Log.d(TAG, "Error getting ID");
                        Log.d(TAG, exception.getMessage());
                        medicineId = randomId.nextInt();
                    }
                    cancelAlarm(medicineId);
                    medicineTableViewModel.deleteMedicine(medicineTable);
                }
            }).attachToRecyclerView(medicineRecyclerView);

            medicineAdapter.setOnItemClickListener(new MedicineAdapter.OnItemClickListener() {
                @Override
                public void editClick(Medicine_Table medicineTable) {
                    Intent intent = new Intent(Add_Medicines.this, InsertEditMedicine.class);
                    intent.putExtra("Id", medicineTable.getId());
                    intent.putExtra("Name", medicineTable.getName());
                    intent.putExtra("Form", medicineTable.getForm());
                    intent.putExtra("Dose", medicineTable.getDose());
                    intent.putExtra("Frequency", medicineTable.getFrequency());
                    intent.putExtra("Route", medicineTable.getRoute());
                    intent.putExtra("Period", medicineTable.getPeriod());
                    startActivityForResult(intent, EDIT_MEDICINE);
                }

                @Override
                public void deleteClick(Medicine_Table medicineTable) {
                    String medicine = medicineTable.getName();
                    int medicineId;
                    try {
                        medicineId = medicineTableViewModel.getMedicineById(medicine);
                    } catch (Exception exception) {
                        Log.d(TAG, "Error getting ID");
                        Log.d(TAG, exception.getMessage());
                        medicineId = randomId.nextInt();
                    }
                    cancelAlarm(medicineId);
                    medicineTableViewModel.deleteMedicine(medicineTable);
                }
            });


            addMedicineBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (visitingDate.isEmpty()) {
                        Toast.makeText(Add_Medicines.this, "Select Today's Date", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(Add_Medicines.this, InsertEditMedicine.class);
                        startActivityForResult(intent, INSERT_MEDICINE);
                    }
                }
            });

            MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
            materialDateBuilder.setTitleText("Select Date");

            MaterialDatePicker visitDatePicker = materialDateBuilder.build();
            visitDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                @Override
                public void onPositiveButtonClick(Object selection) {
                    VisitDate.setText(visitDatePicker.getHeaderText());
                    visitingDate = visitDatePicker.getHeaderText();
                    setAdapter(visitingDate);
                }
            });
            VisitDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    visitDatePicker.show(getSupportFragmentManager(), "VISIT_DATE_PICKER");
                }
            });

            MaterialDatePicker nextVisitDatePicker = materialDateBuilder.build();
            nextVisitDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                @Override
                public void onPositiveButtonClick(Object selection) {
                    NextVisitDate.setText(nextVisitDatePicker.getHeaderText());
                }
            });
            NextVisitDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nextVisitDatePicker.show(getSupportFragmentManager(), "NEXT_VISIT_DATE_PICKER");
                }
            });

            Submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Add_Medicines.this, Updates.class);
                    if (!isempty()) {
                        intent.putExtra("VISITING_DATE", visitingDate);
                        intent.putExtra("POG_WEEKS", POGWeeks.getText().toString().trim());
                        intent.putExtra("POG_DAYS", POGDays.getText().toString().trim());
                        intent.putExtra("EDD", EDD.getText().toString().trim());
                        intent.putExtra("NEXT_VISITING_DATE", NextVisitDate.getText().toString().trim());
                        startActivity(intent);
                    }
                }
            });
        }
    }

    private boolean isempty() {
        if(visitingDate.isEmpty()) {
            Toast.makeText(this, "Enter Visiting Date", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(POGWeeks.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "Enter POG Weeks", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(POGDays.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "Enter POG Days", Toast.LENGTH_SHORT).show();
            return true;
        }else if(EDD.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "Enter EDD", Toast.LENGTH_SHORT).show();
            return true;
        }else if(NextVisitDate.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "Enter Next Visit Date", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private void setAdapter(String visitingDate) {
        medicineTableViewModel.getMedicineByVisitingDate(visitingDate).observe(this, new Observer<List<Medicine_Table>>() {
            @Override
            public void onChanged(List<Medicine_Table> medicine_tables) {
//                Update the Recycler
//                Passing the Arraylist of adapter here cuz everytime Live data changes we can change the adapter list automatically
//                No need for managing the adpter list again and again
                medicineAdapter.setMedicines(medicine_tables);
                medicineAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if(requestCode==INSERT_MEDICINE && resultCode==RESULT_OK){
            String name=data.getStringExtra("Name");
            String form=data.getStringExtra("Form");
            String dose=data.getStringExtra("Dose");
            String frequency=data.getStringExtra("Frequency");
            String route=data.getStringExtra("Route");
            String period=data.getStringExtra("Period");

            Medicine_Table medicineTable=new Medicine_Table(name, form, dose, frequency, route,period,visitingDate);
            medicineTableViewModel.insertMedicine(medicineTable);

//            int medicineId;
//            try{
//                medicineId=medicineTableViewModel.getMedicineById(name);
//            }catch (Exception exception){
//                Log.d(TAG, "Error getting ID");
//                Log.d(TAG, exception.getMessage());
//                medicineId=randomId.nextInt();
//            }
//
//            Log.d(TAG,"Heloooo    " +  String.valueOf(medicineId));
//
//            int hour=Integer.parseInt(time.substring(0,2));
//            int minute=Integer.parseInt(time.substring(5).trim());
//            setAlarm(hour,minute,medicineId,medicine);
        }
        else if(requestCode==EDIT_MEDICINE && resultCode==RESULT_OK){
            int id=data.getIntExtra("Id", -1);

            if(id==-1){
                Log.d(TAG, "Not Edited");
                return;
            }

            String name=data.getStringExtra("Name");
            String form=data.getStringExtra("Form");
            String dose=data.getStringExtra("Dose");
            String frequency=data.getStringExtra("Frequency");
            String route=data.getStringExtra("Route");
            String period=data.getStringExtra("Period");

            Medicine_Table medicineTable=new Medicine_Table(name, form, dose, frequency, route,period,visitingDate);
            medicineTable.setId(id);
            medicineTableViewModel.updateMedicine(medicineTable);
            Log.d(TAG, "Edited");

//            int hour=Integer.parseInt(time.substring(0,2));
//            int minute=Integer.parseInt(time.substring(5).trim());
//            setAlarm(hour,minute,id,medicine);
        }
        else{
            Log.d(TAG, "Medicine Not Added");
            Toast.makeText(this, "Medicine Not Added", Toast.LENGTH_SHORT).show();
        }
    }

    private void setAlarm(int hour, int minute, int id, String medicineName) {
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // At the time of filling medicine if time is already pass then we pass +1 day
        if(calendar.before(Calendar.getInstance())){
            calendar.add(Calendar.DATE, 1);
        }

        AlarmManager alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent alarmReceiverIntent=new Intent(Add_Medicines.this,AlarmReceiver.class);
        alarmReceiverIntent.putExtra("MedicineName", medicineName);
        alarmReceiverIntent.putExtra("Id", id);
            // TODO Use appropriate Flags
        PendingIntent broadcastPendingIntent=PendingIntent.getBroadcast(Add_Medicines.this,id, alarmReceiverIntent, PendingIntent.FLAG_MUTABLE);

//        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),broadcastPendingIntent);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, broadcastPendingIntent);
        Log.d(TAG, String.valueOf(id) + "  " + medicineName);
        Log.d(TAG, "setTime " + calendar.getTimeInMillis());
        Long remainingTime=calendar.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
        Log.d(TAG, "remainingTime " +String.valueOf(remainingTime));
        counter(remainingTime);
    }

    private void counter(Long remainingTime) {

        new CountDownTimer(remainingTime, 1000) {
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
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

            // TODO Once all medicines are cleared, delete the alarm which has being assigned to them
        switch (item.getItemId()){
            case R.id.Delete_All_Medicines:
                medicineTableViewModel.deleteAllMedicines();
                Log.d(TAG, "All Medicines Cleared");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // TODO Notification channel should be created once the app gets installed. Notificaton_Channel extends Application
    private void createNotificationChannel() {

            // foxandroid is the channel id
            // Once channel id is changed everything is changed

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Channel Name....";
            String description = "Channel Description in short words...";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("foxandroid", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
    }
}
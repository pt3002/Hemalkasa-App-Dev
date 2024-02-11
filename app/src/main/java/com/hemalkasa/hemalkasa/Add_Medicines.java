package com.hemalkasa.hemalkasa;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

public class Add_Medicines extends AppCompatActivity {
    public static final int INSERT_MEDICINE=1;
    public static final int EDIT_MEDICINE=2;
    FloatingActionButton addMedicineBtn;
    RecyclerView medicineRecyclerView;
    MedicineAdapter medicineAdapter;
    private List<Medicine_Table> medicineList = new ArrayList<>();
    private Medicine_Table_ViewModel medicineTableViewModel;
    private static final String TAG = "pratik";
    EditText POGWeeks,POGDays,HB;
    TextView VisitDate,NextVisitDate;
    Button Submit;
    private Random randomId=new Random();
    String visitingDate="";
    SimpleDateFormat format;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_medicines);
        addMedicineBtn = findViewById(R.id.addMedBtn);
        POGWeeks = findViewById(R.id.POGWeeks);
        POGDays = findViewById(R.id.POGDays);
        HB = findViewById(R.id.HB);
        VisitDate = findViewById(R.id.VisitDate);
        NextVisitDate = findViewById(R.id.NextVisitDate);
        Submit = findViewById(R.id.Submit);
        medicineTableViewModel=  ViewModelProviders.of(this).get(Medicine_Table_ViewModel.class);
        format = new SimpleDateFormat("dd-MMM-yyyy");


        Intent historyIntent=getIntent();
        if(historyIntent.hasExtra("VISITING_DATE")){
            medicineAdapter = new MedicineAdapter(true);
            medicineRecyclerView = findViewById(R.id.RecyclerView);
            medicineRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            medicineRecyclerView.setHasFixedSize(true);
            medicineRecyclerView.setAdapter(medicineAdapter);
            medicineRecyclerView.addItemDecoration(new DividerItemDecoration(medicineRecyclerView.getContext(), DividerItemDecoration.VERTICAL));  //Normal Horizontal Separator

            VisitDate.setText("Last Visit: " + historyIntent.getStringExtra("VISITING_DATE"));
            POGWeeks.setText("POG Weeks: " + historyIntent.getStringExtra("POG_WEEKS"));
            POGDays.setText("POG Days: " + historyIntent.getStringExtra("POG_DAYS"));
            HB.setText("HB: " + historyIntent.getStringExtra("HB"));
            NextVisitDate.setText("Next Visit: " + historyIntent.getStringExtra("NEXT_VISITING_DATE"));

                // Disabling the editable field
//            POGWeeks.setEnabled(false);
//            POGDays.setEnabled(false);
//            EDD.setEnabled(false);
            POGWeeks.setFocusable(false);
            POGDays.setFocusable(false);
            HB.setFocusable(false);
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
//                    cancelAlarm(medicineId);
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
//                    cancelAlarm(medicineId);
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

            MaterialDatePicker <Long> visitDatePicker = materialDateBuilder.build();
            visitDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                @Override
                public void onPositiveButtonClick(Long selection) {
                    Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                    calendar.setTimeInMillis(selection);
                    String formattedDate  = format.format(calendar.getTime());
                    VisitDate.setText(formattedDate);
                    visitingDate=formattedDate;
                    setAdapter(visitingDate);
                }
            });
            VisitDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    visitDatePicker.show(getSupportFragmentManager(), "VISIT_DATE_PICKER");
                }
            });

            MaterialDatePicker<Long> nextVisitDatePicker = materialDateBuilder.build();
            nextVisitDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                @Override
                public void onPositiveButtonClick(Long selection) {
                    Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                    calendar.setTimeInMillis(selection);
                    String formattedDate  = format.format(calendar.getTime());
                    NextVisitDate.setText(formattedDate);
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
                        intent.putExtra("HB", HB.getText().toString().trim());
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
        }else if(HB.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "Enter HB", Toast.LENGTH_SHORT).show();
            return true;
        }else if(NextVisitDate.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "Enter Next Visit Date", Toast.LENGTH_SHORT).show();
            return true;
        }
        int day=getDay(NextVisitDate.getText().toString().trim());
        int month=getMonth(NextVisitDate.getText().toString().trim());
        int year=getYear(NextVisitDate.getText().toString().trim());
        Calendar calendar=Calendar.getInstance();
        calendar.set(year, month, day);
        calendar.set(Calendar.HOUR_OF_DAY, 6); // TODO Keep Proper Hour time
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        if(calendar.before(Calendar.getInstance())){
            Toast.makeText(this, "Next Visit Date should be after Current Visit Day", Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.Delete_All_Medicines:
                medicineTableViewModel.deleteAllMedicines();
                Log.d(TAG, "All Medicines Cleared");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private int getDay(@NonNull String NEXT_VISITING_DATE) {
        return Integer.parseInt(NEXT_VISITING_DATE.substring(0, 2));
    }
    private int getMonth(@NonNull String NEXT_VISITING_DATE) {
        String month=NEXT_VISITING_DATE.substring(3,6);
        switch (month){
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
        return Integer.parseInt(NEXT_VISITING_DATE.substring(7,11));
    }
}
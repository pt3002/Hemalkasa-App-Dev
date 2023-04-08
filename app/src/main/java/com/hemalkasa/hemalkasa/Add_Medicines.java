package com.hemalkasa.hemalkasa;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class Add_Medicines extends AppCompatActivity {
    public static final int INSERT_MEDICINE=1;
    FloatingActionButton addMedicineBtn;
    RecyclerView medicineRecyclerView;
//    ArrayList<MedicineModel> medicineArr = new ArrayList<>();
    MedicineAdapter medicineAdapter;
    private Medicine_Table_ViewModel medicineTableViewModel;
    private static final String TAG = "pratik";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_medicines);
//        medicineArr.add(new MedicineModel("Paracetamol", "2", "22 - 2 - 2023", "06 : 00", "2"));
//        medicineArr.add(new MedicineModel("Paracetamol", "2", "22 - 2 - 2023", "06 : 00", "2"));
//        medicineArr.add(new MedicineModel("Paracetamol", "2", "22 - 2 - 2023", "06 : 00", "2"));
//        medicineArr.add(new MedicineModel("Paracetamol", "2", "22 - 2 - 2023", "06 : 00", "2"));
//        medicineArr.add(new MedicineModel("Paracetamol", "2", "22 - 2 - 2023", "06 : 00", "2"));
        addMedicineBtn = findViewById(R.id.addMedBtn);

        medicineAdapter = new MedicineAdapter();
        medicineRecyclerView = findViewById(R.id.RecyclerView);
        medicineRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        medicineRecyclerView.setHasFixedSize(true);
        medicineRecyclerView.setAdapter(medicineAdapter);
            //Normal Horizontal Separator
        medicineRecyclerView.addItemDecoration(new DividerItemDecoration(medicineRecyclerView.getContext(), DividerItemDecoration.VERTICAL));

        addMedicineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                AddNewMedicine_BottomSheet addNewMedicine = new AddNewMedicine_BottomSheet(Add_Medicines.this);
//                addNewMedicine.show(getSupportFragmentManager(), "test");
                Intent intent=new Intent(Add_Medicines.this,InsertNewMedicine.class);
                startActivityForResult(intent, INSERT_MEDICINE);
            }
        });

        medicineTableViewModel=  ViewModelProviders.of(this).get(Medicine_Table_ViewModel.class);
        medicineTableViewModel.getAllMedicines().observe(this, new Observer<List<Medicine_Table>>() {
            @Override
            public void onChanged(List<Medicine_Table> medicine_tables) {
                //Update the Recycler
                //Passing the Arraylist of adapter here cuz everytime Live data changes we can change the adapter list automatically
                // No need for managing the adpter list again and again
                medicineAdapter.setMedicines(medicine_tables);
//                Log.d(TAG, "Observing Live Data");
                medicineAdapter.notifyDataSetChanged();
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;   //This is Used for Drag And Drop. Since  we are not using this so we return false
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Medicine_Table medicineTable=medicineAdapter.getMedicalTableAt(viewHolder.getAdapterPosition());
                medicineTableViewModel.deleteMedicine(medicineTable);
            }
        }).attachToRecyclerView(medicineRecyclerView);

    }

    public void addMedicineArrData(String medicine, String dose, String day, String time, String frequency){
        Log.d(TAG, "Medicine" + medicine);
        Log.d(TAG, "Dose" + dose);
        Log.d(TAG, "Day" + day);
        Log.d(TAG, "Time" + time);
        Log.d(TAG, "Frequency" + frequency);

//        Medicine_Table medicineTable=new Medicine_Table(medicine, dose, frequency, day, time);
//        InsertAsyncMedicine insertAsyncMedicine=new InsertAsyncMedicine();
//        insertAsyncMedicine.execute(medicineTable);

//        medicineArr.add(new MedicineModel(medicine, dose, day, time, frequency));
//        medicineAdapter.notifyDataSetChanged();
    }

    public void editMedicineArrData(String medicine, String dose, String day, String time, String frequency, int position){
//        medicineArr.set(position, new MedicineModel(medicine, dose, day, time, frequency));
//        medicineAdapter.notifyDataSetChanged();
    }
//    public void delMedicineArrData(int position){
//        medicineArr.remove(position);
//        medicineAdapter.notifyDataSetChanged();
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if(requestCode==INSERT_MEDICINE && resultCode==RESULT_OK){
            String medicine=data.getStringExtra("Medicine");
            String dose=data.getStringExtra("Dose");
            String day=data.getStringExtra("Day");
            String time=data.getStringExtra("Time");
            String frequency=data.getStringExtra("Frequency");

            Log.d(TAG, "Data Received");

            Medicine_Table medicineTable=new Medicine_Table(medicine, dose, frequency, day, time);
            medicineTableViewModel.insertMedicine(medicineTable);
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
}
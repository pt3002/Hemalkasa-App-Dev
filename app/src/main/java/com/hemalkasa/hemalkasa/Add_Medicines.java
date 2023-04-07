package com.hemalkasa.hemalkasa;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Add_Medicines extends AppCompatActivity {
    FloatingActionButton addMedicineBtn;
    RecyclerView medicineRecyclerView;
    ArrayList<MedicineModel> medicineArr = new ArrayList<>();
    MedicineAdapter medicineAdapter;
    private Medicine_Table_ViewModel medicineTableViewModel;
    private static final String TAG = "pratik";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_medicines);
        medicineArr.add(new MedicineModel("Paracetamol", "2", "22 - 2 - 2023", "06 : 00", "2"));
//        medicineArr.add(new MedicineModel("Paracetamol", "2", "22 - 2 - 2023", "06 : 00", "2"));
//        medicineArr.add(new MedicineModel("Paracetamol", "2", "22 - 2 - 2023", "06 : 00", "2"));
//        medicineArr.add(new MedicineModel("Paracetamol", "2", "22 - 2 - 2023", "06 : 00", "2"));
//        medicineArr.add(new MedicineModel("Paracetamol", "2", "22 - 2 - 2023", "06 : 00", "2"));
        medicineRecyclerView = findViewById(R.id.RecyclerView);
        addMedicineBtn = findViewById(R.id.addMedBtn);
        medicineRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        medicineAdapter = new MedicineAdapter(this, medicineArr);
        medicineRecyclerView.setAdapter(medicineAdapter);
        addMedicineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNewMedicine addNewMedicine = new AddNewMedicine(Add_Medicines.this);
                addNewMedicine.show(getSupportFragmentManager(), "test");
            }
        });

        medicineTableViewModel=  ViewModelProviders.of(this).get(Medicine_Table_ViewModel.class);
        medicineTableViewModel.getAllMedicines().observe(this, new Observer<List<Medicine_Table>>() {
            @Override
            public void onChanged(List<Medicine_Table> medicine_tables) {
                //Update the Recycler
            }
        });

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

        medicineArr.add(new MedicineModel(medicine, dose, day, time, frequency));
        medicineAdapter.notifyDataSetChanged();
    }
    public void editMedicineArrData(String medicine, String dose, String day, String time, String frequency, int position){
        medicineArr.set(position, new MedicineModel(medicine, dose, day, time, frequency));
        medicineAdapter.notifyDataSetChanged();
    }
    public void delMedicineArrData(int position){
        medicineArr.remove(position);
        medicineAdapter.notifyDataSetChanged();
    }

}
package com.hemalkasa.hemalkasa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Add_Medicines extends AppCompatActivity {
    FloatingActionButton addMedicineBtn;
    RecyclerView medicineRecyclerView;
    ArrayList<MedicineModel> medicineArr = new ArrayList<>();
    MedicineAdapter medicineAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_medicines);
        medicineArr.add(new MedicineModel("Paracetamol", "2", "22 - 2 - 2023", "06 : 00", "2"));
        medicineArr.add(new MedicineModel("Paracetamol", "2", "22 - 2 - 2023", "06 : 00", "2"));
        medicineArr.add(new MedicineModel("Paracetamol", "2", "22 - 2 - 2023", "06 : 00", "2"));
        medicineArr.add(new MedicineModel("Paracetamol", "2", "22 - 2 - 2023", "06 : 00", "2"));
        medicineArr.add(new MedicineModel("Paracetamol", "2", "22 - 2 - 2023", "06 : 00", "2"));
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

    }
    public void addMedicineArrData(String medicine, String dose, String day, String time, String frequency){
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
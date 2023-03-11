package com.example.prescriptionrecyclerapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton addNewTaskBtn;
//    NewAdapter adapter;
    ArrayList<PrescriptionModel> arrData = new ArrayList<>();
    RecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView;
        addNewTaskBtn = findViewById(R.id.newTaskBtn);
        recyclerView = findViewById(R.id.PrescriptionrecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrData.add(new PrescriptionModel("Nise", "1", "26th Jan", "7:00"));
        arrData.add(new PrescriptionModel("Paracetamol", "1", "26th Jan", "7:00"));
        arrData.add(new PrescriptionModel("Paracetamol", "1", "26th Jan", "7:00"));
        arrData.add(new PrescriptionModel("Paracetamol", "1", "26th Jan", "7:00"));
        arrData.add(new PrescriptionModel("Paracetamol", "1", "26th Jan", "7:00"));
        adapter = new RecyclerViewAdapter(this, arrData);
        recyclerView.setAdapter(adapter);
//        arrData.add(new PrescriptionModel("etamol", "1", "26th Jan", "7:00"));

//        FirebaseRecyclerOptions<PrescriptionModel> options = new FirebaseRecyclerOptions.Builder<PrescriptionModel>()
//                .setQuery(FirebaseDatabase.getInstance().getReference().child("entries"), PrescriptionModel.class).build();
//        adapter = new NewAdapter(options, this);
//        recyclerView.setAdapter(adapter);
        addNewTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            AddNewPrescription addNewPrescription = new AddNewPrescription(MainActivity.this);
            addNewPrescription.show(getSupportFragmentManager(), addNewPrescription.getTag());
//                arrData.add(new PrescriptionModel("combi", "1", "26th Jan", "7:00"));
//                AddArrData("Combiflame", "2", "Sunday", "3:00");


            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }
//    @Override
//    protected void onStart() {
//        super.onStart();
//        adapter.startListening();
//    }
//    @Override
//    protected void onStop() {
//        super.onStop();
//        adapter.stopListening();
//    }
    public void AddArrData(String medicine, String dose, String day, String time){
        arrData.add(new PrescriptionModel(medicine, dose, day, time));
        adapter.notifyDataSetChanged();
    }
    public void editArrData(String medicine, String dose, String day, String time, int position){
        PrescriptionModel prescriptionModel = new PrescriptionModel(medicine, dose, day, time);
        arrData.set(position, prescriptionModel);
        adapter.notifyDataSetChanged();
    }
    public void delArrData(int position){
        arrData.remove(position);
        adapter.notifyDataSetChanged();
    }
}
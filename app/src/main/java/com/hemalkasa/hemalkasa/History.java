package com.hemalkasa.hemalkasa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity {
    private static final String TAG = "pratik";
    private Prescription_Table_ViewModel prescriptionTableViewModel;

    RecyclerView historyRecyclerView;
    HistoryAdapter historyAdapter;
    private List<Prescription_Table> prescription = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        prescriptionTableViewModel=  ViewModelProviders.of(this).get(Prescription_Table_ViewModel.class);
        historyAdapter=new HistoryAdapter();
        prescriptionTableViewModel.getAll(historyAdapter);
        historyRecyclerView=findViewById(R.id.HistoryRecyclerView);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        historyRecyclerView.setHasFixedSize(true);
        historyRecyclerView.setAdapter(historyAdapter);

        historyAdapter.setOnItemClickListener(new HistoryAdapter.OnItemClickListener() {
            @Override
            public void getHistory(Prescription_Table prescriptionTable) {

            }
        });




    }
}
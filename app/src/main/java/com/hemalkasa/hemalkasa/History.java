package com.hemalkasa.hemalkasa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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
        setContentView(R.layout.history);

        historyAdapter=new HistoryAdapter();
        prescriptionTableViewModel=  ViewModelProviders.of(this).get(Prescription_Table_ViewModel.class);
        prescriptionTableViewModel.getAll(historyAdapter);
        historyRecyclerView=findViewById(R.id.HistoryRecyclerView);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        historyRecyclerView.setHasFixedSize(true);
        historyRecyclerView.setAdapter(historyAdapter);

        historyAdapter.setOnItemClickListener(new HistoryAdapter.OnItemClickListener() {
            @Override
            public void getHistory(Prescription_Table prescriptionTable) {
                Intent intent = new Intent(History.this,Add_Medicines.class);
                intent.putExtra("VISITING_DATE", prescriptionTable.getVisiting_date());
                intent.putExtra("POG_WEEKS", prescriptionTable.getPog_weeks());
                intent.putExtra("POG_DAYS", prescriptionTable.getPog_days());
                intent.putExtra("HB", prescriptionTable.getHb());
                intent.putExtra("NEXT_VISITING_DATE", prescriptionTable.getNext_visiting_date());
                intent.putExtra("DESIGNATION", prescriptionTable.getDesignation());
                intent.putExtra("NOTES", prescriptionTable.getNotes());
                startActivity(intent);
            }
        });




    }
}
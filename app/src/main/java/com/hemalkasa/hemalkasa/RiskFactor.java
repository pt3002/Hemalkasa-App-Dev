package com.hemalkasa.hemalkasa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CheckedTextView;

import java.util.ArrayList;
import java.util.List;

public class RiskFactor extends AppCompatActivity {
    private static final String TAG = "pratik";
    private Risk_Factor_Table_ViewModel riskFactorTableViewModel;

    RecyclerView riskFactorRecyclerView;
    RiskFactorAdaptor riskFactorAdaptor;
    private List<Risk_Factor_Table> riskFactorTableList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.risk_factor);

        Intent riskIntent=getIntent();
        if(riskIntent.hasExtra("Access")) {
            riskFactorAdaptor = new RiskFactorAdaptor(true);
            riskFactorTableViewModel = ViewModelProviders.of(this).get(Risk_Factor_Table_ViewModel.class);
            riskFactorTableViewModel.getAllRiskFactors(riskFactorAdaptor);
            riskFactorRecyclerView = findViewById(R.id.RiskFactorRecyclerView);
            riskFactorRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            riskFactorRecyclerView.setHasFixedSize(true);
            riskFactorRecyclerView.setAdapter(riskFactorAdaptor);

            riskFactorAdaptor.setOnItemClickListener(new RiskFactorAdaptor.OnItemClickListener() {
                @Override
                public void toggleRisk(Risk_Factor_Table riskFactorTable) {
                    Boolean isActive = riskFactorTable.getRisk();
                    riskFactorTable.setRisk(!isActive);
                    riskFactorTableViewModel.updateRiskFactor(riskFactorTable);
                    riskFactorTableViewModel.getAllRiskFactors(riskFactorAdaptor);
                    Log.d(TAG, riskFactorTable.getName() + ": Editeddd");
                }
            });
        }
        else{
            riskFactorAdaptor = new RiskFactorAdaptor(false);
            riskFactorTableViewModel = ViewModelProviders.of(this).get(Risk_Factor_Table_ViewModel.class);
            riskFactorTableViewModel.getActiveRiskFactors(riskFactorAdaptor);
            riskFactorRecyclerView = findViewById(R.id.RiskFactorRecyclerView);
            riskFactorRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            riskFactorRecyclerView.setHasFixedSize(true);
            riskFactorRecyclerView.setAdapter(riskFactorAdaptor);
        }
    }
}
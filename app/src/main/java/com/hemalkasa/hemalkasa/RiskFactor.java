package com.hemalkasa.hemalkasa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class RiskFactor extends AppCompatActivity {
    private static final String TAG = "pratik";
    private Risk_Factor_Table_ViewModel riskFactorTableViewModel;

    RecyclerView riskFactorRecyclerView;
    RiskFactorAdaptor riskFactorAdaptor;
    private List<Risk_Factor_Table> riskFactorTableList=new ArrayList<>();
    ConstraintLayout ActivityView;
    private ImageView NextButtonImage,BackButtonImage;
    private Button NextButton,BackButton;
    private RelativeLayout BackRelativeLayout,NextRelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.risk_factor);

        NextButton = findViewById(R.id.NextButton);
        BackButton = findViewById(R.id.BackButton);
        NextButtonImage = findViewById(R.id.NextButtonImage);
        BackButtonImage = findViewById(R.id.BackButtonImage);
        BackRelativeLayout = findViewById(R.id.BackRelativeLayout);
        NextRelativeLayout = findViewById(R.id.NextRelativeLayout);

        Intent riskIntent=getIntent();
        if(riskIntent.hasExtra("Access")) {
            NextButton.setVisibility(View.GONE);
            BackButton.setVisibility(View.GONE);
            NextButtonImage.setVisibility(View.GONE);
            BackButtonImage.setVisibility(View.GONE);
            NextRelativeLayout.setVisibility(View.GONE);
            BackRelativeLayout.setVisibility(View.GONE);
            riskFactorAdaptor = new RiskFactorAdaptor(true);
            riskFactorTableViewModel = ViewModelProviders.of(this).get(Risk_Factor_Table_ViewModel.class);
            riskFactorTableViewModel.getAllRiskFactors(riskFactorAdaptor);
            riskFactorRecyclerView = findViewById(R.id.RiskFactorRecyclerView);
            riskFactorRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            riskFactorRecyclerView.setHasFixedSize(true);
            riskFactorRecyclerView.setAdapter(riskFactorAdaptor);

            int activeNoRisks = riskFactorAdaptor.getActiveRisks();
            Log.d(TAG, "onCreate: Active Risks" + activeNoRisks);


            riskFactorAdaptor.setOnItemClickListener(new RiskFactorAdaptor.OnItemClickListener() {
                @Override
                public void toggleRisk(Risk_Factor_Table riskFactorTable) {
                    //Log.d(TAG, "toggleRisk: " + riskFactorTable);
                    Boolean isActive = riskFactorTable.getRisk();
                    riskFactorTable.setRisk(!isActive);
                    riskFactorTableViewModel.updateRiskFactor(riskFactorTable);
                    riskFactorTableViewModel.getAllRiskFactors(riskFactorAdaptor);

                    //Log.d(TAG, riskFactorTable.getName() + ": Editeddd");
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


            NextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(RiskFactor.this, Emergency_Contact.class);
                    startActivity(intent);
                }
            });

            BackButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
//                    Intent intent = new Intent(RiskFactor.this, Summary.class);
//                    startActivity(intent);
                }
            });

//            ActivityView = findViewById(R.id.ActivityView);
//            //noinspection AndroidLintClickableViewAccessibility
//            ActivityView.setOnTouchListener(new OnSwipeTouchListener(this) {
//                @Override
//                public void onSwipeRight() {
//                    super.onSwipeRight();
//                    Log.d(TAG, "RIGHTTTTT: ");
//                    Intent intent = new Intent(RiskFactor.this, Summary.class);
//                    startActivity(intent);
//                }
//
//                @Override
//                public void onSwipeLeft() {
//                    super.onSwipeLeft();
//                    Log.d(TAG, "LEFTTTTTTT: ");
//                    Intent intent = new Intent(RiskFactor.this, Emergency_Contact.class);
//                    startActivity(intent);
//                }
//            });
        }
    }
}
package com.hemalkasa.hemalkasa;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class Risk_Factor_Table_ViewModel extends AndroidViewModel {
    private Risk_Factor_Table_Repository riskFactorTableRepository;
    private static final String TAG = "pratik";
    Application application;    // Remove this line

    public Risk_Factor_Table_ViewModel(@NonNull Application application) {
        super(application);
        this.application=application;
        riskFactorTableRepository=new Risk_Factor_Table_Repository(application);
    }

    public void insertRiskFactor(Risk_Factor_Table riskFactorTable){
        riskFactorTableRepository.insertRiskFactor(riskFactorTable);
    }
    public void updateRiskFactor(Risk_Factor_Table riskFactorTable){
        riskFactorTableRepository.updateRiskFactor(riskFactorTable);
    }
    public void deleteRiskFactor(Risk_Factor_Table riskFactorTable){
        riskFactorTableRepository.deleteRiskFactor(riskFactorTable);
    }
    public void getAllRiskFactors(RiskFactorAdaptor riskFactorAdaptor){
        riskFactorTableRepository.getAllRiskFactors(riskFactorAdaptor);
    }
    public void getActiveRiskFactors(RiskFactorAdaptor riskFactorAdaptor){
        riskFactorTableRepository.getActiveRiskFactors(riskFactorAdaptor);
    }

}

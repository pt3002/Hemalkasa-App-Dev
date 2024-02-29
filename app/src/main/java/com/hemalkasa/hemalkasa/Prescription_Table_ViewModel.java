package com.hemalkasa.hemalkasa;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.List;

public class Prescription_Table_ViewModel extends AndroidViewModel {
    private Prescription_Table_Repository prescriptionTableRepository;
    private static final String TAG = "pratik";
    Application application;    // Remove this line


    public Prescription_Table_ViewModel(@NonNull Application application) {
        super(application);
        this.application=application;
        prescriptionTableRepository=new Prescription_Table_Repository(application);
    }

    public void insertPrescription(Prescription_Table prescriptionTable){
        prescriptionTableRepository.insertPrescription(prescriptionTable);
    }

    public void updatePrescription(Prescription_Table prescriptionTable){
        prescriptionTableRepository.updatePrescription(prescriptionTable);
    }

    public void deletePrescription(Prescription_Table prescriptionTable){
        prescriptionTableRepository.deletePrescription(prescriptionTable);
    }

    public void getAll(HistoryAdapter historyAdapter){
         prescriptionTableRepository.getAll(historyAdapter);
    }

    public List<Prescription_Table> getPrescriptionByVisitingDate(String visiting_date){
        return prescriptionTableRepository.getPrescriptionByVisitingDate(visiting_date);
    }

    public List<Prescription_Table> getLastPrescription(){
        return prescriptionTableRepository.getLastPrescription();
    }
}

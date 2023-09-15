package com.hemalkasa.hemalkasa;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.List;

public class Prescription_Table_ViewModel extends AndroidViewModel {
    private Prescription_Table_Repository prescriptionTableRepository;

    public Prescription_Table_ViewModel(@NonNull Application application) {
        super(application);
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

    public List<Prescription_Table> getPrescriptionByVisitingDate(String visiting_date){
        return prescriptionTableRepository.getPrescriptionByVisitingDate(visiting_date);
    }
}

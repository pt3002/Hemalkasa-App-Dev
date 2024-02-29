package com.hemalkasa.hemalkasa;

import android.app.Application;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class PatientDetails_ViewModel extends AndroidViewModel {
    private PatientDetails_Repository patientDetails_repository;
    public PatientDetails_ViewModel(@NonNull Application application) {
        super(application);
        patientDetails_repository=new PatientDetails_Repository(application);
    }

    public void insertPatientDetails(PatientDetails patientDetails){
        patientDetails_repository.insertPatientDetails(patientDetails);
    }

    public void updatePatientDetails(PatientDetails patientDetails){
        patientDetails_repository.updatePatientDetails(patientDetails);
    }

    public void getPatientName(TextView welcome){
         patientDetails_repository.getPatientName(welcome);
    }

}

package com.hemalkasa.hemalkasa;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PatientDetails_DAO {

    @Insert
    void insertPatientDetails(PatientDetails patientDetails);   //Inserting the entity

    @Insert
    void insertMultiplePatientDetails(List<PatientDetails> patientDetails);

    @Update
    void updatePatientDetails(PatientDetails patientDetails);

    @Delete
    void deleteTodo(PatientDetails patientDetails);

    @Query("SELECT * FROM PATIENT_DETAILS")
    List<PatientDetails> getAllPatientDetails();
}

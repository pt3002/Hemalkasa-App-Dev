package com.hemalkasa.hemalkasa;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface Prescription_Table_DAO {

    @Insert
    void insertPrescription(Prescription_Table prescription_table);

    @Update
    void updatePrescription(Prescription_Table prescription_table);

    @Delete
    void deletePrescription(Prescription_Table prescription_table);

    @Query("SELECT * FROM PRESCRIPTION ORDER BY VISITING_DATE")
    List<Prescription_Table> getAll();

    @Query("SELECT * FROM PRESCRIPTION WHERE VISITING_DATE= :visiting_date ORDER BY ID")
    List<Prescription_Table> getPrescriptionByVisitingDate(String visiting_date);

}

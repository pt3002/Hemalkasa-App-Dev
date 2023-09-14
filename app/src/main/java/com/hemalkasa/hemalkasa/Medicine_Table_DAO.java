package com.hemalkasa.hemalkasa;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface Medicine_Table_DAO {

    @Insert
    void insertMedicine(Medicine_Table medicine_table);

    @Update
    void updateMedicine(Medicine_Table medicine_table);

    @Delete
    void deleteMedicine(Medicine_Table medicine_table);

    @Query("DELETE FROM MEDICINE")
    void deleteAllMedicines();

    @Query("SELECT * FROM MEDICINE ORDER BY VISITING_DATE")
    LiveData<List<Medicine_Table>> getAllMedicines();


    @Query("SELECT * FROM MEDICINE ORDER BY VISITING_DATE")
    List<Medicine_Table> getAll();

    @Query("SELECT ID FROM MEDICINE WHERE NAME= :name ORDER BY ID DESC LIMIT 1")
    Integer getMedicineById(String name);

    @Query("SELECT * FROM MEDICINE WHERE VISITING_DATE= :visiting_date ORDER BY ID")
    List<Medicine_Table> getMedicineByVisitingDate(String visiting_date);

}

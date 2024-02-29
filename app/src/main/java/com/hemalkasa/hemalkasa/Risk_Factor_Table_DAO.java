package com.hemalkasa.hemalkasa;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface Risk_Factor_Table_DAO {
    @Insert
    void insertRiskFactor(Risk_Factor_Table risk_factor_table);   //Inserting the entity

    @Update
    void updateRiskFactor(Risk_Factor_Table risk_factor_table);

    @Delete
    void deleteRiskFactor(Risk_Factor_Table risk_factor_table);

    @Query("SELECT * FROM RISKFACTOR ORDER BY NAME")
    List<Risk_Factor_Table> getAllRiskFactors();

    @Query("SELECT * FROM RISKFACTOR WHERE RISK=:isActive ORDER BY NAME")
    List<Risk_Factor_Table> getActiveRiskFactors(Boolean isActive);     // isActive is True
}

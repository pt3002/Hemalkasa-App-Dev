package com.hemalkasa.hemalkasa;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "RISKFACTOR")
public class Risk_Factor_Table {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private int id;

    @ColumnInfo(name="NAME")
    private String name;

    @ColumnInfo(name="RISK")
    private Boolean risk;
}

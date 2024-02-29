package com.hemalkasa.hemalkasa;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "RISKFACTOR")
public class Risk_Factor_Table {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private int id;

    @ColumnInfo(name = "NAME")
    private String name;

    @ColumnInfo(name = "RISK")
    private Boolean risk;

    public Risk_Factor_Table(String name, Boolean risk) {
        this.name = name;
        this.risk = risk;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getRisk() {
        return risk;
    }

    public void setRisk(Boolean risk) {
        this.risk = risk;
    }

    @Override
    public String toString() {
        return "Risk_Factor_Table{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", risk=" + risk +
                '}';
    }
}

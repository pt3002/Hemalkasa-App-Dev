package com.hemalkasa.hemalkasa;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "MEDICINE")
public class Medicine_Table {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private int id;

    @ColumnInfo(name="NAME")
    private String name;

    @ColumnInfo(name="DOSE")
    private String dose;

    @ColumnInfo(name="FREQUENCY")
    private String frequency;

    @ColumnInfo(name="DATE")
    private String date;

    @ColumnInfo(name="TIME")
    private String time;

    public Medicine_Table(String name, String dose, String frequency, String date, String time) {
        this.name = name;
        this.dose = dose;
        this.frequency = frequency;
        this.date = date;
        this.time = time;
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

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Medicine_Table{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dose='" + dose + '\'' +
                ", frequency='" + frequency + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}

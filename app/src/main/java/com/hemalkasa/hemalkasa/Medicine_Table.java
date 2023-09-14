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

    @ColumnInfo(name="FORM")
    private String form;

    @ColumnInfo(name="DOSE")
    private String dose;

    @ColumnInfo(name="FREQUENCY")
    private String frequency;

    @ColumnInfo(name="PERIOD")
    private String period;

    @ColumnInfo(name="VISITING_DATE")
    private String visiting_date;

    public Medicine_Table(String name, String form, String dose, String frequency, String period, String visiting_date) {
        this.name = name;
        this.form = form;
        this.dose = dose;
        this.frequency = frequency;
        this.period = period;
        this.visiting_date = visiting_date;
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

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
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

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getVisiting_date() {
        return visiting_date;
    }

    public void setVisiting_date(String visiting_date) {
        this.visiting_date = visiting_date;
    }

    @Override
    public String toString() {
        return "Medicine_Table{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", form='" + form + '\'' +
                ", dose='" + dose + '\'' +
                ", frequency='" + frequency + '\'' +
                ", period='" + period + '\'' +
                ", visiting_date='" + visiting_date + '\'' +
                '}';
    }
}

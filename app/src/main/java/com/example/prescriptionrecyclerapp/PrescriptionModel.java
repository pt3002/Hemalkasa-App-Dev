package com.example.prescriptionrecyclerapp;

public class PrescriptionModel {
    String medicine;
    String dose;
    String day;
    String time;
    public PrescriptionModel(String m, String dose, String day, String time){
        this.medicine = m;
        this.dose = dose;
        this.day = day;
        this.time = time;
    }
    public PrescriptionModel(){}

    public String getMedicine() {
        return medicine;
    }

    public String getDose() {
        return dose;
    }

    public String getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

package com.hemalkasa.hemalkasa;

public class MedicineModel {
    String medicine;
    String dose;
    String day;
    String time;
    public MedicineModel(String m, String dose, String day, String time){
        this.medicine = m;
        this.dose = dose;
        this.day = day;
        this.time = time;
    }
    public MedicineModel(){}

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

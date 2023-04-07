package com.hemalkasa.hemalkasa;

public class MedicineModel {
    String medicine;
    String dose;
    String day;
    String time;
    String frequency;

    public MedicineModel(String medicine, String dose, String day, String time, String frequency){
        this.medicine = medicine;
        this.dose = dose;
        this.day = day;
        this.time = time;
        this.frequency = frequency;
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

    public void setFrequency(String frequency){
        this.frequency = frequency;
    }
}

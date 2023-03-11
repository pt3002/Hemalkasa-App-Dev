package com.hemalkasa.hemalkasa;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "PATIENT_DETAILS")
public class PatientDetails {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private int id;

    @ColumnInfo(name="FULL_NAME")
    private String full_name;

    @ColumnInfo(name="MOTHER_NAME")
    private String mother_name;

    @ColumnInfo(name = "HOSPITAL_NO")
    private String hospital_no;

    @ColumnInfo(name = "BLOOD_GROUP")
    private String blood_group;

//    @ColumnInfo(name="DOB")
//    private String dob;
//
//    @ColumnInfo(name="PHONE_NO")
//    private int phone_no;
//
//    @ColumnInfo(name = "STATE")
//    private String state;
//
//    @ColumnInfo(name = "DISTRICT")
//    private String district;
//
//    @ColumnInfo(name = "TEHSIL")
//    private String tehsil;
//
//    @ColumnInfo(name = "VILLAGE")
//    private String village;
//
//    public PatientDetails(String full_name, String mother_name, String hospital_no, String blood_group, String dob, int phone_no, String state, String district, String tehsil, String village) {
//        this.full_name = full_name;
//        this.mother_name = mother_name;
//        this.hospital_no = hospital_no;
//        this.blood_group = blood_group;
//        this.dob = dob;
//        this.phone_no = phone_no;
//        this.state = state;
//        this.district = district;
//        this.tehsil = tehsil;
//        this.village = village;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getMother_name() {
        return mother_name;
    }

    public void setMother_name(String mother_name) {
        this.mother_name = mother_name;
    }

    public String getHospital_no() {
        return hospital_no;
    }

    public void setHospital_no(String hospital_no) {
        this.hospital_no = hospital_no;
    }

    public String getBlood_group() {
        return blood_group;
    }

    public void setBlood_group(String blood_group) {
        this.blood_group = blood_group;
    }

    public PatientDetails(String full_name, String mother_name, String hospital_no, String blood_group) {
        this.full_name = full_name;
        this.mother_name = mother_name;
        this.hospital_no = hospital_no;
        this.blood_group = blood_group;
    }
//    public String getDob() {
//        return dob;
//    }
//
//    public void setDob(String dob) {
//        this.dob = dob;
//    }
//
//    public int getPhone_no() {
//        return phone_no;
//    }
//
//    public void setPhone_no(int phone_no) {
//        this.phone_no = phone_no;
//    }
//
//    public String getState() {
//        return state;
//    }
//
//    public void setState(String state) {
//        this.state = state;
//    }
//
//    public String getDistrict() {
//        return district;
//    }
//
//    public void setDistrict(String district) {
//        this.district = district;
//    }
//
//    public String getTehsil() {
//        return tehsil;
//    }
//
//    public void setTehsil(String tehsil) {
//        this.tehsil = tehsil;
//    }
//
//    public String getVillage() {
//        return village;
//    }
//
//    public void setVillage(String village) {
//        this.village = village;
//    }

    @Override
    public String toString() {
        return "PatientDetails{" +
                "id=" + id +
                ", full_name='" + full_name + '\'' +
                ", mother_name='" + mother_name + '\'' +
                ", hospital_no='" + hospital_no + '\'' +
                ", blood_group='" + blood_group + '\'' +
//                ", dob='" + dob + '\'' +
//                ", phone_no=" + phone_no +
//                ", state='" + state + '\'' +
//                ", district='" + district + '\'' +
//                ", tehsil='" + tehsil + '\'' +
//                ", village='" + village + '\'' +
                '}';
    }

}

package com.hemalkasa.hemalkasa;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "PATIENT_DETAILS")
public class PatientDetails {

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "ID")
    private Integer id;

    @ColumnInfo(name = "FULL_NAME")
    private String full_name;

    @ColumnInfo(name = "MOTHER_NAME")
    private String mother_name;

    @ColumnInfo(name = "HOSPITAL_NO")
    private String hospital_no;

    @ColumnInfo(name = "DOB")
    private String dob;

    @ColumnInfo(name = "BLOOD_GROUP")
    private String blood_group;

    @ColumnInfo(name = "TRIMESTER")
    private String trimester;

    @ColumnInfo(name = "STATE")
    private String state;

    @ColumnInfo(name = "DISTRICT")
    private String district;

    @ColumnInfo(name = "TEHSIL")
    private String tehsil;

    @ColumnInfo(name = "VILLAGE")
    private String village;

    @ColumnInfo(name = "PHONE_NO")
    private String phone_no;

    @ColumnInfo(name = "ASHA_WORKER")
    private String asha_worker;

    @ColumnInfo(name = "EDD")
    private String edd;

    @ColumnInfo(name = "POG_WEEKS")
    private String pog_weeks;

    @ColumnInfo(name = "POG_DAYS")
    private String pog_days;

    @ColumnInfo(name = "HIV")
    private String hiv;

    @ColumnInfo(name = "HSBAG")
    private String hsbag;

    @ColumnInfo(name = "VDRL")
    private String vdrl;

    @ColumnInfo(name = "Gravida")
    private String gravida;

    @ColumnInfo(name = "PARITY")
    private String parity;

    @ColumnInfo(name = "LMP")
    private String lmp;

    public PatientDetails(Integer id, String full_name, String mother_name, String hospital_no, String dob, String blood_group, String trimester, String state, String district, String tehsil, String village, String phone_no, String asha_worker,String edd, String pog_weeks, String pog_days, String hiv, String hsbag, String vdrl, String gravida, String parity, String lmp) {
        this.id = id;
        this.full_name = full_name;
        this.mother_name = mother_name;
        this.hospital_no = hospital_no;
        this.dob = dob;
        this.blood_group = blood_group;
        this.trimester = trimester;
        this.state = state;
        this.district = district;
        this.tehsil = tehsil;
        this.village = village;
        this.phone_no = phone_no;
        this.asha_worker = asha_worker;
        this.edd = edd;
        this.pog_weeks = pog_weeks;
        this.pog_days = pog_days;
        this.hiv = hiv;
        this.hsbag = hsbag;
        this.vdrl = vdrl;
        this.gravida = gravida;
        this.parity = parity;
        this.lmp = lmp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getBlood_group() {
        return blood_group;
    }

    public void setBlood_group(String blood_group) {
        this.blood_group = blood_group;
    }

    public String getTrimester() {
        return trimester;
    }

    public void setTrimester(String trimester) { this.trimester = trimester;}
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getTehsil() {
        return tehsil;
    }

    public void setTehsil(String tehsil) {
        this.tehsil = tehsil;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getAsha_worker() {
        return asha_worker;
    }

    public void setAsha_worker(String asha_worker) {
        this.asha_worker = asha_worker;
    }

    public String getEdd() {
        return edd;
    }

    public void setEdd(String edd) {
        this.edd = edd;
    }

    public String getPog_weeks() {
        return pog_weeks;
    }

    public void setPog_weeks(String pog_weeks) {
        this.pog_weeks = pog_weeks;
    }

    public String getPog_days() {
        return pog_days;
    }

    public void setPog_days(String pog_days) {
        this.pog_days = pog_days;
    }

    public String getHiv() {
        return hiv;
    }

    public void setHiv(String hiv) {
        this.hiv = hiv;
    }

    public String getHsbag() {
        return hsbag;
    }

    public void setHsbag(String hsbag) {
        this.hsbag = hsbag;
    }

    public String getVdrl() {
        return vdrl;
    }

    public void setVdrl(String vdrl) {
        this.vdrl = vdrl;
    }

    public String getGravida() {
        return gravida;
    }

    public void setGravida(String gravida) {
        this.gravida = gravida;
    }

    public String getParity() {
        return parity;
    }

    public void setParity(String parity) {
        this.parity = parity;
    }

    public String getLmp() {
        return lmp;
    }

    public void setLmp(String lmp) {
        this.lmp = lmp;
    }

    @Override
    public String toString() {
        return "PatientDetails{" +
                ", id='" + id + '\'' +
                ", full_name='" + full_name + '\'' +
                ", mother_name='" + mother_name + '\'' +
                ", hospital_no='" + hospital_no + '\'' +
                ", dob='" + dob + '\'' +
                ", blood_group='" + blood_group + '\'' +
                ", trimester='" + trimester + '\'' +
                ", state='" + state + '\'' +
                ", district='" + district + '\'' +
                ", tehsil='" + tehsil + '\'' +
                ", village='" + village + '\'' +
                ", phone_no='" + phone_no + '\'' +
                ", edd='" + edd + '\'' +
                ", pog_weeks='" + pog_weeks + '\'' +
                ", pog_days='" + pog_days + '\'' +
                ", hiv='" + hiv + '\'' +
                ", hsbag='" + hsbag + '\'' +
                ", vdrl='" + vdrl + '\'' +
                ", gravida='" + gravida + '\'' +
                ", parity='" + parity + '\'' +
                ", lmp='" + lmp + '\'' +
                '}';
    }

}

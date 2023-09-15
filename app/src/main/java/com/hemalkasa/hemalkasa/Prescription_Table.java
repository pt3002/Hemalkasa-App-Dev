package com.hemalkasa.hemalkasa;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "PRESCRIPTION")
public class Prescription_Table {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private int id;

    @ColumnInfo(name = "VISITING_DATE")
    private String visiting_date;

    @ColumnInfo(name = "POG_WEEKS")
    private String pog_weeks;

    @ColumnInfo(name = "POG_DAYS")
    private String pog_days;

    @ColumnInfo(name = "EDD")
    private String edd;

    @ColumnInfo(name = "NEXT_VISITING_DATE")
    private String next_visiting_date;

    @ColumnInfo(name = "DESIGNATION")
    private String designation;

    @ColumnInfo(name = "NOTES")
    private String notes;

    public Prescription_Table(String visiting_date, String pog_weeks, String pog_days, String edd, String next_visiting_date, String designation, String notes) {
        this.visiting_date = visiting_date;
        this.pog_weeks = pog_weeks;
        this.pog_days = pog_days;
        this.edd = edd;
        this.next_visiting_date = next_visiting_date;
        this.designation = designation;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVisiting_date() {
        return visiting_date;
    }

    public void setVisiting_date(String visiting_date) {
        this.visiting_date = visiting_date;
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

    public String getEdd() {
        return edd;
    }

    public void setEdd(String edd) {
        this.edd = edd;
    }

    public String getNext_visiting_date() {
        return next_visiting_date;
    }

    public void setNext_visiting_date(String next_visiting_date) {
        this.next_visiting_date = next_visiting_date;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}

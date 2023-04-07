package com.hemalkasa.hemalkasa;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {PatientDetails.class},version = 1 ,exportSchema = false)
public abstract class PatientDetails_Database extends RoomDatabase{

    public abstract PatientDetails_DAO patientDetails_dao();
    private static volatile PatientDetails_Database INSTANCE;

    static PatientDetails_Database getInstance(Context context){
        if(INSTANCE==null){
            synchronized (PatientDetails_Database.class){
                if(INSTANCE==null){
                    // Patient_Database is the name of database
                    INSTANCE=Room.databaseBuilder(context.getApplicationContext(),
                            PatientDetails_Database.class,"Patient_Database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

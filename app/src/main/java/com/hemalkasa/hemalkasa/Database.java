package com.hemalkasa.hemalkasa;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {PatientDetails.class,Medicine_Table.class},version = 1 ,exportSchema = false)
public abstract class Database extends RoomDatabase{

    public abstract PatientDetails_DAO patientDetails_dao();
    public abstract Medicine_Table_DAO medicineTableDao();
    private static volatile Database INSTANCE;

    static synchronized Database getInstance(Context context){
        if(INSTANCE==null){
            synchronized (Database.class){
                if(INSTANCE==null){
                    // Local_Database is the name of database
                    INSTANCE=Room.databaseBuilder(context.getApplicationContext(),
                            Database.class,"Local_Database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

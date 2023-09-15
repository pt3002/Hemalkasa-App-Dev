package com.hemalkasa.hemalkasa;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.List;

@androidx.room.Database(entities = {PatientDetails.class,Medicine_Table.class,Prescription_Table.class},version = 1 ,exportSchema = false)
public abstract class Database extends RoomDatabase{
    private static final String TAG = "pratik";

    public abstract PatientDetails_DAO patientDetails_dao();
    public abstract Medicine_Table_DAO medicineTableDao();
    public abstract Prescription_Table_DAO prescriptionTableDao();

    private static volatile Database INSTANCE;

    static synchronized Database getInstance(Context context){
        if(INSTANCE==null){
            synchronized (Database.class){
                if(INSTANCE==null){
                    // Local_Database is the name of database
                    INSTANCE=Room.databaseBuilder(context.getApplicationContext(),
                            Database.class,"Local_Database")
                            .fallbackToDestructiveMigration()
                            .addCallback(roomCallback)  //Once database instance is created this will call a callback function
                            .build();
                }
            }
        }
        return INSTANCE;
    }

        //In callback we can do anything which we wish to do
    private static RoomDatabase.Callback roomCallback=new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
                //Here we pass some initial dummy data of medicines for testing
            // TODO add Patient registration info by default if you donot want to use shared preferences
            new PopulateDbAsyncTask(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void>{
        private Medicine_Table_DAO medicineTableDao;
        private PatientDetails_DAO patientDetails_dao;
        private Prescription_Table_DAO prescriptionTableDao;

        private PopulateDbAsyncTask(Database db){
            medicineTableDao=db.medicineTableDao();
            patientDetails_dao=db.patientDetails_dao();
            prescriptionTableDao=db.prescriptionTableDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            patientDetails_dao.insertPatientDetails(new PatientDetails(1,"FullName", "MotherName", "1234567890", "HospitalNo", "BloodGroup", "DOB","State","District","Tehsil","Village","edd","pog_weeks","pog_days","hiv","hsbag","vdrl","gravida","parity","lmp","notes"));
            //            medicineTableDao.insertMedicine(new Medicine_Table("Medicine 111", "AAA", "BB", "CC", "DDD"));
//            medicineTableDao.insertMedicine(new Medicine_Table("Medicine 222", "zzz", "yy", "xx", "hh"));
            return null;
        }

//        @Override
//        protected void onPostExecute(Void unused) {
//            super.onPostExecute(unused);
//            getSuper();
//            Log.d(TAG, "Callback Finished");
//        }
//
//        public void getSuper() {
//            Thread thread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    List<Medicine_Table> md = medicineTableDao.getAll();
//                    Log.d(TAG, "run: " + md.toString());
//                }
//            });
//            thread.start();
//        }
    }



}

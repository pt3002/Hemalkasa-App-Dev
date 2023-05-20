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

@androidx.room.Database(entities = {PatientDetails.class,Medicine_Table.class},version = 1 ,exportSchema = false)
public abstract class Database extends RoomDatabase{
    private static final String TAG = "pratik";

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

        private PopulateDbAsyncTask(Database db){
            medicineTableDao=db.medicineTableDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
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

package com.hemalkasa.hemalkasa;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

public class Medcine_Table_Repository {
    public static final String TAG = "pratik";

    private Medicine_Table_DAO medicineTableDao;    //DAO of medical table
    private LiveData<List<Medicine_Table>> allMedicines; // Contains list of all medicines

    public Medcine_Table_Repository(Application application){
        medicineTableDao= Database.getInstance(application).medicineTableDao();
        allMedicines=medicineTableDao.getAllMedicines();
    }

    public void insertMedicine(Medicine_Table medicineTable){
        InsertAsyncMedicine insertAsyncMedicine=new InsertAsyncMedicine(medicineTableDao);
        insertAsyncMedicine.execute(medicineTable);
    }

    public void updateMedicine(Medicine_Table medicine_table){
        new UpdateAsyncMedicine(medicineTableDao).execute(medicine_table);  //Another Shortcut of executing async code
    }

    public void deleteMedicine(Medicine_Table medicine_table){
        new DeleteAsyncMedicine(medicineTableDao).execute(medicine_table);
    }

    public void  deleteAllMedicines(){
        new DeleteAllAsyncMedicine(medicineTableDao).execute();
    }

    public LiveData<List<Medicine_Table>> getAllMedicines(){
        return allMedicines;
    }




    private static class InsertAsyncMedicine extends AsyncTask<Medicine_Table,Void,Void> {
        private Medicine_Table_DAO medicineTableDao;
        private InsertAsyncMedicine(Medicine_Table_DAO medicineTableDao){
            this.medicineTableDao=medicineTableDao; //Using the constructor we are actually getting the DAO of Medicine which we have already taken while initializing the repository class
        }

        @Override
        protected Void doInBackground(Medicine_Table... medicine_tables) {
            medicineTableDao.insertMedicine(medicine_tables[0]);
            Log.d(TAG, "Medicine Added: Successful");
            return null;
        }
    }

    private static class UpdateAsyncMedicine extends AsyncTask<Medicine_Table,Void,Void> {
        private Medicine_Table_DAO medicineTableDao;
        private UpdateAsyncMedicine(Medicine_Table_DAO medicineTableDao){
            this.medicineTableDao=medicineTableDao; //Using the constructor we are actually getting the DAO of Medicine which we have already taken while initializing the repository class
        }

        @Override
        protected Void doInBackground(Medicine_Table... medicine_tables) {
            medicineTableDao.updateMedicine(medicine_tables[0]);
            Log.d(TAG, "Medicine Updated");
            return null;
        }
    }

    private static class DeleteAsyncMedicine extends AsyncTask<Medicine_Table,Void,Void> {
        private Medicine_Table_DAO medicineTableDao;
        private DeleteAsyncMedicine(Medicine_Table_DAO medicineTableDao){
            this.medicineTableDao=medicineTableDao; //Using the constructor we are actually getting the DAO of Medicine which we have already taken while initializing the repository class
        }

        @Override
        protected Void doInBackground(Medicine_Table... medicine_tables) {
            medicineTableDao.deleteMedicine(medicine_tables[0]);
            Log.d(TAG, "Medicine Deleted");
            return null;
        }
    }

    private static class DeleteAllAsyncMedicine extends AsyncTask<Medicine_Table,Void,Void> {
        private Medicine_Table_DAO medicineTableDao;
        private DeleteAllAsyncMedicine(Medicine_Table_DAO medicineTableDao){
            this.medicineTableDao=medicineTableDao; //Using the constructor we are actually getting the DAO of Medicine which we have already taken while initializing the repository class
        }

        @Override
        protected Void doInBackground(Medicine_Table... medicine_tables) {
            medicineTableDao.deleteAllMedicines();
            Log.d(TAG, "All Medicines Cleared");
            return null;
        }
    }

}

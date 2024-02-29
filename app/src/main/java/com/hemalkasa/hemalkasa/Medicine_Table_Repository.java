package com.hemalkasa.hemalkasa;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class Medicine_Table_Repository {
    public static final String TAG = "pratik";

    private Medicine_Table_DAO medicineTableDao;    //DAO of medical table
    private LiveData<List<Medicine_Table>> allMedicines; // Contains list of all medicines

    public Medicine_Table_Repository(Application application){
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

    public int getMedicineById(String medicineName){
        int x=0;
        try {
            x = new getMedicineByIdAsync(medicineTableDao).execute(medicineName).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return x;
    }

        //  TODO This queery is running in main UI Tread. Try to run it like normal.
    public LiveData<List<Medicine_Table>> getMedicineByVisitingDate(String visiting_date){
        try{
            return medicineTableDao.getMedicineByVisitingDate(visiting_date);
            // cannot cast to java.util.List. Resolve this error
//            return (List<Medicine_Table>) new GetMedicineByVisitngDateAsync(medicineTableDao).execute(visiting_date);
        }catch (Exception exception){
            Log.d(TAG, exception.getMessage());
        }
        return null;
    }



    private static class InsertAsyncMedicine extends AsyncTask<Medicine_Table,Void,Void> {
        private Medicine_Table_DAO medicineTableDao;
        private InsertAsyncMedicine(Medicine_Table_DAO medicineTableDao){
            this.medicineTableDao=medicineTableDao; //Using the constructor we are actually getting the DAO of Medicine which we have already taken while initializing the repository class
        }

        @Override
        protected Void doInBackground(Medicine_Table... medicine_tables) {
            medicineTableDao.insertMedicine(medicine_tables[0]);
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

    private static class getMedicineByIdAsync extends AsyncTask<String,Void,Integer>{
        private Medicine_Table_DAO medicineTableDao;
        public getMedicineByIdAsync(Medicine_Table_DAO medicineTableDao) {
            this.medicineTableDao = medicineTableDao;
        }

        @Override
        protected Integer doInBackground(String... strings) {
            return medicineTableDao.getMedicineById(strings[0]);
        }
    }

//    private static class GetMedicineByVisitngDateAsync extends AsyncTask<String,Void,List<Medicine_Table>>{
//        private Medicine_Table_DAO medicineTableDao;
//        public GetMedicineByVisitngDateAsync(Medicine_Table_DAO medicineTableDao) {
//            this.medicineTableDao = medicineTableDao;
//        }
//
//        @Override
//        protected List<Medicine_Table> doInBackground(String... strings) {
//            return medicineTableDao.getMedicineByVisitingDate(strings[0]);
//        }
//    }


}

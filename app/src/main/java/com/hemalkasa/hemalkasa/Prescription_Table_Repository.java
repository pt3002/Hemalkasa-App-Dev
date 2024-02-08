package com.hemalkasa.hemalkasa;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

public class Prescription_Table_Repository {
    public static final String TAG = "pratik";
    private Prescription_Table_DAO prescriptionTableDao;

    public Prescription_Table_Repository(Application application){
        prescriptionTableDao=Database.getInstance(application).prescriptionTableDao();
    }

    public void insertPrescription(Prescription_Table prescriptionTable){
        new InsertAsyncPrescription(prescriptionTableDao).execute(prescriptionTable);
    }

    public void updatePrescription(Prescription_Table prescriptionTable){
        new UpdateAsyncPrescription(prescriptionTableDao).execute(prescriptionTable);
    }

    public void deletePrescription(Prescription_Table prescriptionTable){
        new DeleteAsyncPrescription(prescriptionTableDao).execute(prescriptionTable);
    }

    public void getAll(HistoryAdapter historyAdapter){
//        Thread getAllThread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                List<Prescription_Table> allPrescriptions=prescriptionTableDao.getAll();
//                Log.d(TAG, allPrescriptions.toString());
//            }
//        });
//        getAllThread.start();
        new GetAsyncAllPresciption(prescriptionTableDao,historyAdapter).execute();
    }

    public List<Prescription_Table> getPrescriptionByVisitingDate(String visiting_date){
        try{
            return (List<Prescription_Table>) new GetPresciptionByVisitingDateAsync(prescriptionTableDao).execute(visiting_date);
        }catch (Exception exception){
            Log.d(TAG, "Error Occured" + exception.getMessage());
        }
        return null;
    }



    private static class InsertAsyncPrescription extends AsyncTask<Prescription_Table,Void,Void>{
        private Prescription_Table_DAO prescriptionTableDao;

        public InsertAsyncPrescription(Prescription_Table_DAO prescriptionTableDao) {
            this.prescriptionTableDao = prescriptionTableDao;
        }

        @Override
        protected Void doInBackground(Prescription_Table... prescription_tables) {
            prescriptionTableDao.insertPrescription(prescription_tables[0]);
            return null;
        }
    }

    private static class UpdateAsyncPrescription extends AsyncTask<Prescription_Table,Void,Void>{
        private Prescription_Table_DAO prescriptionTableDao;

        public UpdateAsyncPrescription(Prescription_Table_DAO prescriptionTableDao) {
            this.prescriptionTableDao = prescriptionTableDao;
        }

        @Override
        protected Void doInBackground(Prescription_Table... prescription_tables) {
            prescriptionTableDao.updatePrescription(prescription_tables[0]);
            return null;
        }
    }

    private static class DeleteAsyncPrescription extends AsyncTask<Prescription_Table,Void,Void>{
        private Prescription_Table_DAO prescriptionTableDao;

        public DeleteAsyncPrescription(Prescription_Table_DAO prescriptionTableDao) {
            this.prescriptionTableDao = prescriptionTableDao;
        }

        @Override
        protected Void doInBackground(Prescription_Table... prescription_tables) {
            prescriptionTableDao.deletePrescription(prescription_tables[0]);
            return null;
        }
    }

    private static class GetAsyncAllPresciption extends AsyncTask<Void,Void,List<Prescription_Table>>{
        private Prescription_Table_DAO prescriptionTableDao;
        HistoryAdapter historyAdapter;
        public GetAsyncAllPresciption(Prescription_Table_DAO prescriptionTableDao, HistoryAdapter historyAdapter) {
            this.prescriptionTableDao = prescriptionTableDao;
            this.historyAdapter=historyAdapter;
        }

        @Override
        protected List<Prescription_Table> doInBackground(Void... voids) {
            return prescriptionTableDao.getAll();
        }

        @Override
        protected void onPostExecute(List<Prescription_Table> prescription_tables) {
            super.onPostExecute(prescription_tables);
            historyAdapter.setHistoryList(prescription_tables); // Setting the adapter here itself after fetching the data
        }
    }

    private static class GetPresciptionByVisitingDateAsync extends AsyncTask<String,Void,List<Prescription_Table>>{
        private Prescription_Table_DAO prescriptionTableDao;
        public GetPresciptionByVisitingDateAsync(Prescription_Table_DAO prescriptionTableDao) {
            this.prescriptionTableDao = prescriptionTableDao;
        }

        @Override
        protected List<Prescription_Table> doInBackground(String... strings) {
            return prescriptionTableDao.getPrescriptionByVisitingDate(strings[0]);
        }
    }

}

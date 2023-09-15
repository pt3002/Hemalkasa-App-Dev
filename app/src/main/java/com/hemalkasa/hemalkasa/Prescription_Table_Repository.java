package com.hemalkasa.hemalkasa;

import android.app.Application;
import android.os.AsyncTask;
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
            prescriptionTableDao.insertPrescription(prescription_tables[0]);
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
            prescriptionTableDao.insertPrescription(prescription_tables[0]);
            return null;
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

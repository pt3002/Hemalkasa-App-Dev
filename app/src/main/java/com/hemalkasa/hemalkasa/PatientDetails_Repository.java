package com.hemalkasa.hemalkasa;

import android.app.Application;
import android.os.AsyncTask;

public class PatientDetails_Repository {

    private PatientDetails_DAO patientDetails_dao;


    public PatientDetails_Repository(Application application) {
        patientDetails_dao=Database.getInstance(application).patientDetails_dao();
    }

    public void insertPatientDetails(PatientDetails patientDetails){
        new InsertAsyncPatientDetails(patientDetails_dao).execute(patientDetails);
    }

    public void updatePatientDetails(PatientDetails patientDetails){
        new UpdateAsyncPatientDetails(patientDetails_dao).execute(patientDetails);
    }



    private static class InsertAsyncPatientDetails extends AsyncTask<PatientDetails,Void,Void>{
        private PatientDetails_DAO patientDetails_dao;
        private InsertAsyncPatientDetails(PatientDetails_DAO patientDetails_dao){
            this.patientDetails_dao=patientDetails_dao;
        }


        @Override
        protected Void doInBackground(PatientDetails... patientDetails) {
            patientDetails_dao.insertPatientDetails(patientDetails[0]);
            return null;
        }
    }

    private static class UpdateAsyncPatientDetails extends AsyncTask<PatientDetails,Void,Void>{
        private PatientDetails_DAO patientDetails_dao;
        private UpdateAsyncPatientDetails(PatientDetails_DAO patientDetails_dao){
            this.patientDetails_dao=patientDetails_dao;
        }

        @Override
        protected Void doInBackground(PatientDetails... patientDetails) {
            patientDetails_dao.updatePatientDetails(patientDetails[0]);
            return null;
        }
    }

}

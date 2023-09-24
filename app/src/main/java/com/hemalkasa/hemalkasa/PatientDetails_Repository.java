package com.hemalkasa.hemalkasa;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

public class PatientDetails_Repository {

    private PatientDetails_DAO patientDetails_dao;
    public static final String TAG = "pratik";


    public PatientDetails_Repository(Application application) {
        patientDetails_dao=Database.getInstance(application).patientDetails_dao();
    }

    public void insertPatientDetails(PatientDetails patientDetails){
        new InsertAsyncPatientDetails(patientDetails_dao).execute(patientDetails);
    }

    public void updatePatientDetails(PatientDetails patientDetails){
        new UpdateAsyncPatientDetails(patientDetails_dao).execute(patientDetails);
    }

    public void getPatientName(TextView welcome){
        new GetAsyncPatientName(patientDetails_dao,welcome).execute();
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

    private static class GetAsyncPatientName extends AsyncTask<Void,Void,String>{
        private PatientDetails_DAO patientDetails_dao;
        private TextView welcome;
        public GetAsyncPatientName(PatientDetails_DAO patientDetails_dao,TextView welcome) {
            this.patientDetails_dao = patientDetails_dao;
            this.welcome=welcome;
        }

        @Override
        protected String doInBackground(Void... voids) {
            return patientDetails_dao.getPatientName();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            welcome.setText("Welcome " + s);
        }
    }

}
